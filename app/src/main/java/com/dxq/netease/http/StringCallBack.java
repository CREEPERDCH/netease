package com.dxq.netease.http;

import java.io.IOException;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public interface StringCallBack {

    void onFail(IOException e);

    void onSuccess(String result);
}
