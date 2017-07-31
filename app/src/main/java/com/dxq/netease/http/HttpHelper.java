package com.dxq.netease.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class HttpHelper {

    private OkHttpClient mOkHttpClient;

    private HttpHelper() {
        mOkHttpClient = new OkHttpClient();
    }

    static HttpHelper sHttpHelper;

    public static HttpHelper getInstance() {
        if (sHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (sHttpHelper == null) {
                    sHttpHelper = new HttpHelper();
                }
            }
        }
        return sHttpHelper;
    }

    public void requestForAsyncGET(String url, final StringCallBack stringCallBack) {
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.d(getClass().getSimpleName() + "dxq", "onFailure: " + e);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        stringCallBack.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onFailure(call, new IOException("响应失败"));
                    return;
                }
                final String string = response.body().string();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        stringCallBack.onSuccess(string);
                    }
                });
            }
        });
    }
}
