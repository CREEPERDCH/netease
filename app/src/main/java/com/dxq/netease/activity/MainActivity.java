package com.dxq.netease.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dxq.netease.R;
import com.dxq.netease.bean.AdsBean;
import com.dxq.netease.bean.AdsListBean;
import com.dxq.netease.conf.Constant;
import com.dxq.netease.service.DownloadPicIntentService;
import com.dxq.netease.utils.HashCodeUtils;
import com.dxq.netease.utils.JsonUtils;
import com.dxq.netease.utils.SpUtils;
import com.dxq.netease.view.SkipVIew;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.dxq.netease.activity.AdDetailActivity.AD_DETAIL_URL;
import static com.dxq.netease.service.DownloadPicIntentService.DOWNLOAD_SERVICE_DATA;

public class MainActivity extends AppCompatActivity {

    private ImageView main_iv_ad;
    private SkipVIew main_sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();

        String name = Thread.currentThread().getName();
        Log.d(getClass().getSimpleName() + "dxq", "onCreate: " + name);
        initData();
    }

    private void initView() {
        main_iv_ad = (ImageView) findViewById(R.id.main_iv_ad);
        main_sv = (SkipVIew) findViewById(R.id.main_sv);
    }

    private void initData() {

        String json = SpUtils.getString(MainActivity.this, Constant.AD_JSON);


        long out_date_time = SpUtils.getLong(MainActivity.this, Constant.OUT_DATE_TIME);
        long currentTimeMillis = System.currentTimeMillis();

        if (TextUtils.isEmpty(json) || currentTimeMillis > out_date_time) {
            requestDataFromHttp();
            Log.d(getClass().getSimpleName() + "dxq", "initData: 无缓存或缓存过期,开始请求数据");
            return;
        }
        Log.d(getClass().getSimpleName() + "dxq", "initData: 发现缓存,不再请求数据");

        AdsListBean adsListBean = JsonUtils.parseJson(json, AdsListBean.class);
        List<AdsBean> ads = adsListBean.getAds();
        int index = SpUtils.getInt(MainActivity.this, Constant.AD_PIC_INDEX);

        index = index % ads.size();
        AdsBean adsBean = ads.get(index);
        String picUrl = adsBean.getRes_url()[0];
        String fileName = getExternalCacheDir() + "/" + HashCodeUtils.getHashCodeFileName(picUrl) + ".jpg";
        File file = new File(fileName);
        Log.d(getClass().getSimpleName() + "dxq", "initData: " + file.length());
        //检查文件是否存在
        if (file.exists() && file.length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            main_iv_ad.setImageBitmap(bitmap);

            //设置skipview的跳转
            main_sv.setVisibility(View.VISIBLE);
            main_sv.setmOnSkipListener(new SkipVIew.OnSkipListener() {
                @Override
                public void onSkip() {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            });
            main_sv.start();

            index++;
            SpUtils.setInt(MainActivity.this, Constant.AD_PIC_INDEX, index);

            Log.d("TAG", "adsBean=" + adsBean.toString());

            //            if (adsBean.getActionParams() != null) {
            //
            //            }
            final String link_url = adsBean.getActionParams().getLink_url();

            Log.d("TAG", "link_url=" + link_url);
            if (TextUtils.isEmpty(link_url)) {
                //                    return;
            }
            main_iv_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main_sv.stop();
                    Intent intent = new Intent(MainActivity.this, AdDetailActivity.class);
                    intent.putExtra(AD_DETAIL_URL, link_url);
                    Intent intent1 = new Intent(MainActivity.this, HomeActivity.class);
                    Intent[] intents = {intent1, intent};
                    startActivities(intents);
                    finish();
                }
            });
        }
    }

    private void requestDataFromHttp() {
        //new出OKHTTPCLIENT
        OkHttpClient okHttpClient = new OkHttpClient();
        //准备一个请求
        Request request = new Request.Builder().url(Constant.URL.AD_URL).build();
        //准备一个call对象
        Call call = okHttpClient.newCall(request);
        //异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(getClass().getSimpleName() + "dxq", "onFailure: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if (!successful) {
                    onFailure(call, new IOException("响应未成功"));
                    return;
                }
                //成功拿到数据
                ResponseBody body = response.body();
                //区分toString()和string()
                String result = body.string();
                //将其缓存
                SpUtils.setString(MainActivity.this, Constant.AD_JSON, result);
                Log.d("TAG12", "result=" + result);

                AdsListBean adsListBean = JsonUtils.parseJson(result, AdsListBean.class);
                Log.d("TAG", "onResponse: " + adsListBean);
                //开始在后台偷偷下载
                startDownloadInBackgtound(adsListBean);
            }
        });
    }

    private void startDownloadInBackgtound(AdsListBean adsListBean) {
        //json-->jsonstr
        //        Gson gson = new Gson();
        //        String jsonStr = gson.toJson(adsListBean);

        //保存过期时间
        long outDateTime = System.currentTimeMillis() + adsListBean.getNext_req() * 60 * 1000;
        SpUtils.setLong(MainActivity.this, Constant.OUT_DATE_TIME, outDateTime);
        /**
         * 使用服务来进行后台下载
         * IntentService与Service的区别:
         * 1.构造方法不一样,需要设置传递一个String作为它内部的后台线程的线程名
         * 2.可以直接执行一些后台任务,执行完任务后,会stopSelf,自杀
         * 注意!!! 传递的JavaBean要能够序列化 JavaBean内部中所嵌套的JavaBean也要能够实现序列化
         * Serialiable
         */
        Intent intent = new Intent(getApplicationContext(), DownloadPicIntentService.class);
        intent.putExtra(DOWNLOAD_SERVICE_DATA, adsListBean);
        startService(intent);

        Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent2);

    }
}
