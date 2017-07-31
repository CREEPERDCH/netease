package com.dxq.netease.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dxq.netease.R;
import com.dxq.netease.activity.NewsDetailActivity;
import com.dxq.netease.adapter.HotNewsAdapter;
import com.dxq.netease.bean.BannerBean;
import com.dxq.netease.bean.HomeBean;
import com.dxq.netease.bean.HomeListBean;
import com.dxq.netease.conf.Constant;
import com.dxq.netease.fragment.LogFragment;
import com.dxq.netease.http.HttpHelper;
import com.dxq.netease.http.StringCallBack;
import com.dxq.netease.utils.JsonUtils;
import com.dxq.netease.view.BannerView;

import java.io.IOException;
import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.dxq.netease.activity.NewsDetailActivity.NEWS_ID;

/**
 * Created by CREEPER_D on 2017/7/23.
 */

public class HotNewsFragment extends LogFragment {

    String text = getClass().getSimpleName();
    private ListView mListView;
    private BannerView mBannerView;
    private HotNewsAdapter mHotNewsAdapter;
    private PtrClassicFrameLayout mPtr_frame;

    @Override
    public View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_hot_news, container, false);
        mListView = inflate.findViewById(R.id.hot_news_lv);
        mPtr_frame = inflate.findViewById(R.id.ptr_frame);
        setListView();
        setRefresh();
        return inflate;
    }

    private void setRefresh() {
        mPtr_frame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                requestData(false);
            }
        });
    }

    private void setListView() {
        View footView = View.inflate(getContext(), R.layout.view_foot, null);
        mListView.addFooterView(footView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                HomeBean item = (HomeBean) parent.getAdapter().getItem(position);
                intent.putExtra(NEWS_ID, item.getId());
                startActivity(intent);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //判断滚动的状态   如果状态是停下来了,开始判断是否要加载更多
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //整个列表的最后一个条目的位置
                    int lastItemPosition = view.getAdapter().getCount() - 1;
                    //当前可见的最后一个条目
                    int lastVisiblePosition = view.getLastVisiblePosition();
                    if (lastItemPosition == lastVisiblePosition) {
                        //加载更多,请求数据
                        Log.d(getClass().getSimpleName() + "dxq", "onScrollStateChanged: " + "请求数据");
                        requestData(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    protected void initData() {
        requestData(false);
    }

    private int loadMoreCount = 0;

    private void requestData(final boolean isLoadMore) {
        String homeUrl = Constant.getHomeUrl(0, 9);
        if (isLoadMore) {
            loadMoreCount++;
            homeUrl = Constant.getHomeUrl(0 + loadMoreCount * 10, 9 + loadMoreCount * 10);
        }
        HttpHelper.getInstance().requestForAsyncGET(homeUrl, new StringCallBack() {
            @Override
            public void onFail(IOException e) {
                Log.d(getClass().getSimpleName() + "dxq", "onFail: 获取失败");
                //将下拉刷新的头部收起来
                if (mPtr_frame.isRefreshing()) {
                    mPtr_frame.refreshComplete();
                }
            }

            @Override
            public void onSuccess(String result) {
                //将下拉刷新的头部收起来
                if (mPtr_frame.isRefreshing()) {
                    mPtr_frame.refreshComplete();
                }
                Log.d(getClass().getSimpleName() + "dxq", "onSuccess: " + result);
                HomeListBean homeListBean = JsonUtils.parseJson(result, HomeListBean.class);
                Log.d(getClass().getSimpleName() + "dxq", "onSuccess: homeListBean" + homeListBean.toString());
                ArrayList<HomeBean> t1348647909107 = homeListBean.getT1348647909107();
                if (!isLoadMore) {
                    //拿走第一条轮播图数据
                    HomeBean bannerData = t1348647909107.remove(0);
                    setBanner(bannerData);
                }
                if (mHotNewsAdapter == null) {
                    mHotNewsAdapter = new HotNewsAdapter(t1348647909107);
                    mListView.setAdapter(mHotNewsAdapter);
                } else {
                    //不是第一次进来了,直接添加数据进来,并刷新
                    if (isLoadMore) {
                        //添加数据
                        mHotNewsAdapter.addData(t1348647909107);
                    } else {
                        //下拉刷新时,不要直接add,应该将先前的数据清除掉,再add
                        mHotNewsAdapter.updateData(t1348647909107);
                    }
                }
            }
        });
    }


    private void setBanner(HomeBean bannerData) {
        ArrayList<BannerBean> ads = bannerData.getAds();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> picUrls = new ArrayList<>();
        for (int i = 0; i < ads.size(); i++) {
            BannerBean bannerBean = ads.get(i);
            String imgsrc = bannerBean.getImgsrc();
            String title = bannerBean.getTitle();
            titles.add(title);
            picUrls.add(imgsrc);
        }
        if (mBannerView == null) {
            mBannerView = new BannerView(getContext());
            mBannerView.setData(titles, picUrls);
            mListView.addHeaderView(mBannerView);
        } else {
            mBannerView.updateData(titles, picUrls);
        }
    }
}
