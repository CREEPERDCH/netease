package com.dxq.netease.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.adapter.ShowPicAdapter;
import com.dxq.netease.bean.NewsDetailImageBean;
import com.dxq.netease.utils.ImageUtils;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class ShowPicActivity extends AppCompatActivity {

    public static final String SHOW_PIC_INDEX = "SHOW_PIC_INDEX";
    public static final String SHOW_PIC_LAST = "SHOW_PIC_LAST";
    private int mIndex;
    private ArrayList<NewsDetailImageBean> mImgArrayList;

    private TextView tv_index;
    private TextView tv_total;
    private ViewPager viewPager_show_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_show_pic);

        Intent intent = getIntent();
        if (intent != null) {
            mIndex = intent.getIntExtra(SHOW_PIC_INDEX, 0);
            mImgArrayList = (ArrayList<NewsDetailImageBean>) intent.getSerializableExtra(SHOW_PIC_LAST);
            Log.d(getClass().getSimpleName() + "dxq", "onCreate: index" + mIndex + "mImgArrayList" + mImgArrayList);
        }
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        tv_index = (TextView) findViewById(R.id.tv_index);
        tv_total = (TextView) findViewById(R.id.tv_total);
        viewPager_show_pic = (ViewPager) findViewById(R.id.viewPager_show_pic);
    }

    private void initData() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < mImgArrayList.size(); i++) {
            PhotoView photoView = new PhotoView(getApplicationContext());
            NewsDetailImageBean newsDetailImageBean = mImgArrayList.get(i);
            ImageUtils.getSingleTon().showImage(newsDetailImageBean.getSrc(), photoView);
            imageViews.add(photoView);
        }
        ShowPicAdapter showPicAdapter = new ShowPicAdapter(imageViews);
        viewPager_show_pic.setAdapter(showPicAdapter);

        tv_index.setText(mIndex + 1 + "/");
        tv_total.setText(mImgArrayList.size() + "");

        viewPager_show_pic.setCurrentItem(mIndex);
    }

    private void initEvent() {
        viewPager_show_pic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv_index.setText(position + 1 + "/");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
