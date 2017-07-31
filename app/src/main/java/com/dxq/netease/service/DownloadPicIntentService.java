package com.dxq.netease.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dxq.netease.utils.HashCodeUtils;
import com.dxq.netease.bean.AdsBean;
import com.dxq.netease.bean.AdsListBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DownloadPicIntentService extends IntentService {

    public static final String DOWNLOAD_SERVICE_DATA = "DOWNLOAD_SERVICE_DATA";

    //需要写构造方法
    public DownloadPicIntentService() {
        super("DownloadPicIntentService");
    }


    //onHandleIntent 该方法中的代码会被执行在后台服务中,一旦执行完,service就会自杀
    @Override
    protected void onHandleIntent(Intent intent) {
        String name = Thread.currentThread().getName();
        Log.d(getClass().getSimpleName() + "dxq", "onHandleIntent: " + name);
        if (intent != null) {
            //下载图片
            AdsListBean adsListBean = (AdsListBean) intent.getSerializableExtra(DOWNLOAD_SERVICE_DATA);
            List<AdsBean> ads = adsListBean.getAds();
            for (int i = 0; i < ads.size(); i++) {
                AdsBean adsBean = ads.get(i);
                String picUrl = adsBean.getRes_url()[0];
                //判断是否已经下载
                String fileName = getExternalCacheDir() + "/" + HashCodeUtils.getHashCodeFileName(picUrl) + ".jpg";
                File file = new File(fileName);
                if (file.exists() && file.length() > 0) {
                    //已经有图片了,不需要再下载
                    //continue 避免循环被直接打断
                    Log.d(getClass().getSimpleName() + "dxq", "onHandleIntent: 图片已存在,不需要再次下载");
                    continue;
                }
                downloadPic(picUrl);
            }
        }
    }

    private void downloadPic(final String picUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(picUrl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(getClass().getSimpleName() + "dxq", "onFailure: 下载失败啦");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d(getClass().getSimpleName() + "dxq", "onResponse: 又失败了");
                    return;
                }
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);

                //保存在本地缓存中
                String fileName = getExternalCacheDir() + "/" + HashCodeUtils.getHashCodeFileName(picUrl) + ".jpg";
                Log.d(getClass().getSimpleName() + "dxq", "onResponse: " + fileName);
                File file = new File(fileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Log.d(getClass().getSimpleName() + "dxq", "onResponse: 下载并保存成功");
            }
        });
    }

}
