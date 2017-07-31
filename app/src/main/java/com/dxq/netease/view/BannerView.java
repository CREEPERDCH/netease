package com.dxq.netease.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.adapter.BannerAdapter;
import com.dxq.netease.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by CREEPER_D on 2017/7/25.
 */
//1 继承View 写onDraw onMeasure
//2 继承ViewGroup 写onLayout
//3 继承系统已有的布局控件(自定义控件展示的效果一般可以用一些系统的控件组合生成) 组合控件
//4 继承系统已有的控件(不是布局控件)   将系统的控件的特性进行修改
public class BannerView extends RelativeLayout {

    private Handler handler;

    private ViewPager viewpager_banner;
    private TextView tv_banner;
    private LinearLayout ll_dots;

    public BannerView(Context context) {
        /**
         * 注意this和super的区别
         */
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private int time = 2000;

    private void init() {
        //第三种自定义控件的时候,打气筒第三个参数设置为this,将xml中的控件全部放到当前自定义控件中来显示
        View view = View.inflate(getContext(), R.layout.view_banner, this);
        viewpager_banner = view.findViewById(R.id.viewpager_banner);
        tv_banner = view.findViewById(R.id.tv_banner);
        ll_dots = view.findViewById(R.id.ll_dots);

        viewpager_banner.addOnPageChangeListener(new MyPageChangeListener());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int currentItem = viewpager_banner.getCurrentItem();
                currentItem++;
                viewpager_banner.setCurrentItem(currentItem);
                this.sendEmptyMessageDelayed(0, time);
            }
        };
    }

    private ArrayList<String> mTitles;
    private ArrayList<String> mPicUrls;

    public void setData(ArrayList<String> titles, ArrayList<String> picUrls) {
        mTitles = titles;
        mPicUrls = picUrls;

        initPic();
        initDots();

        selectDot(0);
        tv_banner.setText(mTitles.get(0));

        int currentItem = Integer.MAX_VALUE / 2;
        currentItem = currentItem - currentItem % mPicUrls.size();
        viewpager_banner.setCurrentItem(currentItem);
        handler.sendEmptyMessageDelayed(0, time);
    }

    private void initPic() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < mPicUrls.size(); i++) {
            String picUrl = mPicUrls.get(i);
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtils.getSingleTon().showImage(picUrl, imageView);
            imageViews.add(imageView);
        }

        BannerAdapter bannerAdapter = new BannerAdapter(imageViews);
        viewpager_banner.setAdapter(bannerAdapter);
    }

    private void initDots() {
        ll_dots.removeAllViews();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2);
        layoutParams.setMargins(0, 0, 10, 0);
        for (int i = 0; i < mTitles.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.bg_dot);
            ll_dots.addView(imageView, layoutParams);
        }
    }

    private int mLastIndex = 0;

    private void selectDot(int index) {
        ImageView child = (ImageView) ll_dots.getChildAt(mLastIndex);
        child.setImageResource(R.drawable.bg_dot);
        mLastIndex = index;
        child = (ImageView) ll_dots.getChildAt(index);
        child.setImageResource(R.drawable.bg_dot_selected);
    }

    //更新数据
    public void updateData(ArrayList<String> titles, ArrayList<String> picUrls) {
        mTitles = titles;
        mPicUrls = picUrls;

        initPic();
        initDots();

        selectDot(0);
        tv_banner.setText(mTitles.get(0));

        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(0, time);
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            position = position % mPicUrls.size();
            selectDot(position);
            tv_banner.setText(mTitles.get(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(getClass().getSimpleName() + "dxq", "dispatchTouchEvent: " + "down");
                handler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(getClass().getSimpleName() + "dxq", "dispatchTouchEvent: " + "move");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(getClass().getSimpleName() + "dxq", "dispatchTouchEvent: " + "up");
                handler.sendEmptyMessageDelayed(0, time);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
