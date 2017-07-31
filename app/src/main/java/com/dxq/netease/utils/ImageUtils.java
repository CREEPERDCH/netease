package com.dxq.netease.utils;

import android.widget.ImageView;

import com.dxq.netease.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class ImageUtils {

    private DisplayImageOptions mOptions;

    private ImageUtils() {
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_default)
                .delayBeforeLoading(500)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private static volatile ImageUtils sInstance;

    public static ImageUtils getSingleTon() {
        if (sInstance == null) {
            synchronized (ImageUtils.class) {
                if (sInstance == null) {
                    sInstance = new ImageUtils();
                }
            }
        }
        return sInstance;
    }

    public void showImage(String url, ImageView imageView) {
        showImage(url, imageView, R.drawable.icon_default);
    }


    private int mLastResId = R.drawable.icon_default;

    private void showImage(String url, ImageView imageView, int resId) {
        if (mLastResId != resId) {
            mLastResId = resId;
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_default)
                    .delayBeforeLoading(500)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
        }
        ImageLoader.getInstance().displayImage(url, imageView, mOptions);
    }
}
