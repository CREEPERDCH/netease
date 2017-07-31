package com.dxq.netease.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.dxq.netease.R;
import com.dxq.netease.utils.StatusUtils;

public class AdDetailActivity extends AppCompatActivity {

    public static final String AD_DETAIL_URL = "AD_DETAIL_URL";
    private WebView webView;
    private ImageView mIvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mIvStatus = (ImageView) findViewById(R.id.iv_status);
            mIvStatus.getLayoutParams().height = StatusUtils.getStatusHeight(getApplicationContext());
        }

        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        String linkUrl = "";
        if (intent != null) {
            linkUrl = intent.getStringExtra(AD_DETAIL_URL);
            Log.d(getClass().getSimpleName() + "dxq", "onCreate: " + linkUrl);
        }
        //重定向,默认调起其他应用来加载网页
        webView.setWebViewClient(new WebViewClient());
        //打开JavaScript开关
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(linkUrl);
    }

    //重写回退(如果网页能够回退到上一页)
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
