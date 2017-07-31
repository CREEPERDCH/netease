package com.dxq.netease.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dxq.netease.R;
import com.dxq.netease.adapter.AddTitleAdapter;
import com.dxq.netease.adapter.HomePagerAdapter;
import com.dxq.netease.adapter.ShowTitleAdapter;
import com.dxq.netease.conf.Constant;
import com.dxq.netease.event.TabHostVisiableEvent;
import com.dxq.netease.fragment.home.EmptyFragment;
import com.dxq.netease.fragment.home.HotNewsFragment;
import com.dxq.netease.utils.JsonUtils;
import com.dxq.netease.utils.SpUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CREEPER_D on 2017/7/20.
 */

public class HomeFragment extends LogFragment {

    private ViewPager frag_home_vp;
    private SmartTabLayout mSmartTabLayout;
    private TextView mTvChangeTip;
    private ImageButton mIbtnArrow;
    private TextView mTvChangeDone;
    private FrameLayout mFlChangeTitle;
    private ValueAnimator mValueAnimUp;
    private ValueAnimator mValueAnimDown;
    private boolean isFinish = true;
    private boolean isDown = true;
    private TranslateAnimation mTranslateAnimShow;
    private TranslateAnimation mTranslateAnimHide;
    private GridView mGvShowTitle;
    private GridView mGvAddTitle;
    private ShowTitleAdapter mShowTitleAdapter;
    private AddTitleAdapter mAddTitleAdapter;

    private String oldData = "";
    private HomePagerAdapter mAdapter;

    @Override
    public View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_home, container, false);
        frag_home_vp = inflate.findViewById(R.id.frag_home_vp);
        mSmartTabLayout = inflate.findViewById(R.id.viewpagertab);
        mTvChangeTip = inflate.findViewById(R.id.tv_change_tip);
        mIbtnArrow = inflate.findViewById(R.id.ibtn_arrow);
        mTvChangeDone = inflate.findViewById(R.id.tv_change_done);
        mFlChangeTitle = inflate.findViewById(R.id.fl_change_title);
        initAnim();
        initView();
        initChangeTitle();
        return inflate;
    }

    //初始化白屏遮盖区域的布局
    private void initChangeTitle() {
        View inflate = View.inflate(getContext(), R.layout.view_change_title, null);
        mGvShowTitle = inflate.findViewById(R.id.gv_show_title);
        mGvAddTitle = inflate.findViewById(R.id.gv_add_title);
        mFlChangeTitle.addView(inflate);

        //设置这些GridView的点击/长按事件
        mGvShowTitle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //让左上角的图片展示出来
                mTvChangeDone.setVisibility(View.VISIBLE);
                //让Adapter去刷新,并显示删除图片
                mShowTitleAdapter.setShowDelete(true);
                //改为true 代表将事件完全消费掉,不会再触摸Click事件
                return true;
            }
        });
        mGvShowTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //展示了删除的图片后,点击就删除
                boolean showDelete = mShowTitleAdapter.isShowDelete();
                if (showDelete) {
                    if (i == 0) {
                        return;
                    }
                    String deleteItem = mShowTitleAdapter.deleteItem(i);
                    mAddTitleAdapter.addItem(deleteItem);
                } else {
                    //没有展示的话,点击就收回白屏区域,并且切换ViewPager的显示
                    mIbtnArrow.performClick();
                    frag_home_vp.setCurrentItem(i);
                }
            }
        });
        mTvChangeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                mShowTitleAdapter.setShowDelete(false);
            }
        });
        mGvAddTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String deleteItem = mAddTitleAdapter.deleteItem(i);
                mShowTitleAdapter.addItem(deleteItem);
            }
        });
    }

    private void initView() {
        mIbtnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //必须要动画播放完毕我才去继续执行做些正常的逻辑
                if (!isFinish) {
                    return;
                }
                //播放一个动画,并且要去展示或者隐藏白屏的遮盖区域
                //如果箭头是向下的,就播放up
                //点击时通过EventBus发送一个Event
                if (isDown) {
                    mValueAnimUp.start();
                    //展示白屏区域
                    mFlChangeTitle.setVisibility(View.VISIBLE);
                    mTvChangeTip.setVisibility(View.VISIBLE);
                    mFlChangeTitle.startAnimation(mTranslateAnimShow);

                    EventBus.getDefault().post(new TabHostVisiableEvent(false));
                } else {
                    //如果当前的标题数据跟先前的数据没有变化,那就没有必要去保存

                    //当前标题: 新闻   先前的标题:null
                    //当前标题: 新闻 娱乐   先前的标题:新闻
                    //当前标题: 新闻 娱乐 体育   先前的标题:新闻 娱乐
                    String curData = JsonUtils.list2String(mShowTitleAdapter.getData());
                    if (!curData.equals(oldData)) {
                        oldData = curData;
                        saveCache();
                        refreshFragment();
                    }
                    mValueAnimDown.start();
                    mFlChangeTitle.setVisibility(View.GONE);
                    mTvChangeTip.setVisibility(View.GONE);
                    mFlChangeTitle.startAnimation(mTranslateAnimHide);

                    EventBus.getDefault().post(new TabHostVisiableEvent(true));
                }
                isDown = !isDown;
            }
        });
    }

    private void saveCache() {
        Log.d(getClass().getSimpleName() + "dxq", "saveCache: 保存缓存");
        String showTitle = JsonUtils.list2String(mShowTitleAdapter.getData());
        SpUtils.setString(getContext(), Constant.SHOW_TITLE, showTitle);

        String addTitle = JsonUtils.list2String(mAddTitleAdapter.getData());
        SpUtils.setString(getContext(), Constant.ADD_TITLE, addTitle);
    }

    private void refreshFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> data = mShowTitleAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (i == 0) {
                fragments.add(new HotNewsFragment());
            } else {
                fragments.add(new EmptyFragment());
            }
        }
        mAdapter.updateData(fragments, data);
        mSmartTabLayout.setViewPager(frag_home_vp);
    }

    private void initAnim() {
        mValueAnimUp = ValueAnimator.ofFloat(0, 180).setDuration(500);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mIbtnArrow.setRotation(animatedValue);
            }
        };
        mValueAnimUp.addUpdateListener(animatorUpdateListener);
        mValueAnimDown = ValueAnimator.ofFloat(180, 0).setDuration(500);
        mValueAnimDown.addUpdateListener(animatorUpdateListener);

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isFinish = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        mValueAnimDown.addListener(animatorListener);
        mValueAnimUp.addListener(animatorListener);
        //mIbtnArrow 要修改的属性具备get和set方法

        //补间动画 位移
        mTranslateAnimShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        mTranslateAnimShow.setDuration(500);
        mTranslateAnimHide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
        mTranslateAnimHide.setDuration(500);
    }

    @Override
    protected void initData() {
        //给标题GridView来设置数据Adapter
        ArrayList<String> showTitleList = new ArrayList<>();
        ArrayList<String> addTitleList = new ArrayList<>();
        String showTitles = SpUtils.getString(getContext(), Constant.SHOW_TITLE);
        String addTitles = SpUtils.getString(getContext(), Constant.ADD_TITLE);
        List<String> showTitleCache = JsonUtils.string2List(showTitles);
        List<String> addTitleCache = JsonUtils.string2List(addTitles);
        if (!TextUtils.isEmpty(showTitles) && showTitleCache != null && showTitleCache.size() > 0) {
            showTitleList.addAll(showTitleCache);
            addTitleList.addAll(addTitleCache);
        } else {
            String[] showTitleArray = getResources().getStringArray(R.array.news_titles);
            //如果是通过读取资源数组转化成的list,该list是无法进行修改,(增删数据)
            showTitleList.addAll(Arrays.asList(showTitleArray));
            String[] addTitleArray = getResources().getStringArray(R.array.to_add_news_titles);
            //如果是通过读取资源数组转化成的list,该list是无法进行修改,(增删数据)
            addTitleList.addAll(Arrays.asList(addTitleArray));
        }

        mShowTitleAdapter = new ShowTitleAdapter(showTitleList);
        mGvShowTitle.setAdapter(mShowTitleAdapter);
        mAddTitleAdapter = new AddTitleAdapter(addTitleList);
        mGvAddTitle.setAdapter(mAddTitleAdapter);

        String oldData = JsonUtils.list2String(mShowTitleAdapter.getData());


        //请求网络准备数据
        //准备一个fragment集合
        ArrayList<Fragment> fragments = new ArrayList<>();
        //从strings文件中拿取标题数据

        for (int i = 0; i < showTitleList.size(); i++) {
            if (i == 0) {
                fragments.add(new HotNewsFragment());
            } else {
                fragments.add(new EmptyFragment());
            }
        }
        mAdapter = new HomePagerAdapter(getChildFragmentManager(), fragments, showTitleList);
        frag_home_vp.setAdapter(mAdapter);

        mSmartTabLayout.setViewPager(frag_home_vp);
    }

    public boolean onBackPressed() {
        //如果已经显示了标题GridView的左上角删除图片,就先隐藏,不要关闭页面
        if (mShowTitleAdapter.isShowDelete()) {
            mShowTitleAdapter.setShowDelete(false);
            mTvChangeDone.setVisibility(View.GONE);
            return false;
        } else if (!isDown) {
            //否则,如果已经显示了白屏遮盖区域,就先隐藏白屏遮盖区域,不要关闭页面
            mIbtnArrow.performClick();
            return false;
        }
        //否则关闭页面
        return true;
    }
}
