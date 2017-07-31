package com.dxq.netease.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.bean.NewsDetailBean;
import com.dxq.netease.bean.NewsDetailImageBean;
import com.dxq.netease.conf.Constant;
import com.dxq.netease.http.HttpHelper;
import com.dxq.netease.http.StringCallBack;
import com.dxq.netease.utils.JsonUtils;
import com.dxq.netease.utils.StatusUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.dxq.netease.activity.ShowPicActivity.SHOW_PIC_INDEX;
import static com.dxq.netease.activity.ShowPicActivity.SHOW_PIC_LAST;

public class NewsDetailActivity extends SwipeBackActivity {

    public static final String NEWS_ID = "news_id";
    private String mNews_id;
    private WebView webView;
    private EditText mEtReply;
    private TextView mTvReply;
    private ImageView mIvShare;
    private TextView mTvSendReply;
    private ArrayList<NewsDetailImageBean> imageBeanList;

    private boolean mIsFocusd = false;
    private Drawable drawLeft;
    private Drawable drawBottom;

    private ImageView news_iv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            news_iv_status = (ImageView) findViewById(R.id.news_iv_status);
            news_iv_status.getLayoutParams().height = StatusUtils.getStatusHeight(getApplicationContext());
            news_iv_status.setBackgroundColor(Color.WHITE);
        }

        Intent intent = getIntent();
        mNews_id = "";
        if (intent != null) {
            mNews_id = intent.getStringExtra(NEWS_ID);
        }
        Log.d(getClass().getSimpleName() + "dxq", "onCreate: " + mNews_id);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        mEtReply = (EditText) findViewById(R.id.et_reply);
        mTvReply = (TextView) findViewById(R.id.tv_reply);
        mIvShare = (ImageView) findViewById(R.id.iv_share);
        mTvSendReply = (TextView) findViewById(R.id.tv_send_reply);

        //开启Javascript的开关
        webView.getSettings().setJavaScriptEnabled(true);

        /**
         * 最灵活的交互方式:
         * 1 准备对应的要被调用的Java方法
         * 2 借助一个桥梁对象(js接口)来让html去调用这个java方法,比如mWebView.addJavascriptInterface(MainActivity.this,"吴亦凡");
         * mWebView.addJavascriptInterface(你要调用的那个java方法所处的那个对象,js接口的name);
         * 3 给第一步当中的所声明的方法去添加一个注解@JavascriptInterface
         * 4 在html中来调用,window.js接口的name.java方法名(需要的参数)
         */
        webView.addJavascriptInterface(NewsDetailActivity.this, "demo");


        drawLeft = getResources().getDrawable(R.drawable.icon_edit_icon);
        drawBottom = getResources().getDrawable(R.drawable.bg_edit_text);

        //设置边界区域大小用来展示
        drawLeft.setBounds(0, 0, drawLeft.getIntrinsicWidth(), drawLeft.getIntrinsicHeight());
        drawBottom.setBounds(0, 0, drawBottom.getIntrinsicWidth(), drawBottom.getIntrinsicHeight());
    }

    private void initData() {
        String newsDetailUrl = Constant.getNewsDetailUrl(mNews_id);
        HttpHelper.getInstance().requestForAsyncGET(newsDetailUrl, new StringCallBack() {
            @Override
            public void onFail(IOException e) {
            }

            @Override
            public void onSuccess(String result) {
                //最外层的键名会一直变,使用原生的来解析
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String string = jsonObject.getString(mNews_id);
                    //原生解析到里面json字符串后,就可以使用三方库来解析
                    NewsDetailBean newsDetailBean = JsonUtils.parseJson(string, NewsDetailBean.class);
                    Log.d(getClass().getSimpleName() + "dxq", "onSuccess: " + newsDetailBean.toString());
                    mTvReply.setText(newsDetailBean.getReplyCount() + "");
                    setWebView(newsDetailBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setWebView(NewsDetailBean newsDetailBean) {
        String body = newsDetailBean.getBody();
        //加载标题
        String titleHtml = "<p style = \"margin:25px 0px 0px 0px\"><span style='font-size:22px;'><strong>"
                + newsDetailBean.getTitle() + "</strong></span></p>";// 标题
        titleHtml = titleHtml + "<p><span style='color:#999999;font-size:14px;'>"
                + newsDetailBean.getSource() + "&nbsp&nbsp" + newsDetailBean.getPtime() + "</span></p>";//来源与时间
        titleHtml = titleHtml + "<div style=\"border-top:1px dotted #999999;margin:20px 0px\"></div>";//加条虚线

        //设置正文的字体
        body = "<div style='line-height:180%;'><span style='font-size:15px;'>" + body + "</span></div>";

        //替换body中图片标签,用来进行图片展示
        imageBeanList = (ArrayList<NewsDetailImageBean>) newsDetailBean.getImg();
        for (int i = 0; i < imageBeanList.size(); i++) {
            NewsDetailImageBean newsDetailImageBean = imageBeanList.get(i);
            String ref = newsDetailImageBean.getRef();
            String src = newsDetailImageBean.getSrc();
            //            body = body.replace(ref, "<img src=\'" + src + "\'onClick='show("+i+")'/>");
            body = body.replace(ref, "<img onClick=\"show(" + i + ")\" src=\"" + src + "\"/>");

        }

        body = "<html><head><style>img{width:100%}</style>" +
                "<script type=\'text/javascript\'>function show(i){window.demo.showPic(i)}</script>" +
                "</head>" + titleHtml + body + "</html>";
        //        webView.loadData(body, "text/html", "UTF-8");
        webView.loadData(body, "text/html;charset=UTF-8", null);
    }

    @JavascriptInterface
    public void showPic(int index) {
        Intent intent = new Intent(getApplicationContext(), ShowPicActivity.class);
        intent.putExtra(SHOW_PIC_INDEX, index);
        intent.putExtra(SHOW_PIC_LAST, imageBeanList);
        startActivity(intent);
    }

    private void initEvent() {
        //给输入框设置监听
        mEtReply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mIsFocusd = hasFocus;
                if (hasFocus) {
                    //隐藏左边图片
                    mEtReply.setCompoundDrawables(null, null, null, drawBottom);
                    //----------
                    mIvShare.setVisibility(View.GONE);
                    mTvReply.setVisibility(View.GONE);
                    mTvSendReply.setVisibility(View.VISIBLE);
                } else {
                    //隐藏左边图片
                    mEtReply.setCompoundDrawables(drawLeft, null, null, drawBottom);
                    //----------
                    mIvShare.setVisibility(View.VISIBLE);
                    mTvReply.setVisibility(View.VISIBLE);
                    mTvSendReply.setVisibility(View.GONE);
                }
            }
        });
        //给文本设置点击事件
        mTvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), KeyBoardManActivity.class);
                intent.putExtra(NEWS_ID, mNews_id);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mIsFocusd) {
            webView.requestFocus();
        } else {
            super.onBackPressed();
        }
    }
}
