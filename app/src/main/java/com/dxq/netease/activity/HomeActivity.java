package com.dxq.netease.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.dxq.netease.R;
import com.dxq.netease.event.TabHostVisiableEvent;
import com.dxq.netease.fragment.HomeFragment;
import com.dxq.netease.fragment.MeFragment;
import com.dxq.netease.fragment.PlayerFragment;
import com.dxq.netease.fragment.TopicFragment;
import com.dxq.netease.view.MyFragmentTabHost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HomeActivity extends AppCompatActivity {

    private MyFragmentTabHost mHome_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_home);

        //使用EventBus来观察事件之前,需要先注册
        EventBus.getDefault().register(this);

        initTabHost();
    }

    @Subscribe
    public void mmp(TabHostVisiableEvent tabHostVisiableEvent) {
        boolean isVisiable = tabHostVisiableEvent.isVisiable;
        mHome_tab.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
    }

    public void setTabHostVisible(boolean isVisible) {
        mHome_tab.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public Class[] fragments = {HomeFragment.class, PlayerFragment.class, TopicFragment.class, MeFragment.class};
    public String[] tabTexts = {"主页", "直播", "话题", "我"};
    public int[] mResIds = {R.drawable.tab_home, R.drawable.tab_player, R.drawable.tab_topic, R.drawable.tab_me};

    private void initTabHost() {
        mHome_tab = (MyFragmentTabHost) findViewById(R.id.home_tab);
        //初始化,做些准备工作 比如将manger传给它
        mHome_tab.setup(getApplicationContext(), getSupportFragmentManager(), R.id.home_fl);
        //添加tab
        for (int i = 0; i < fragments.length; i++) {
            TabHost.TabSpec tabSpec = mHome_tab.newTabSpec(i + "");
            View inflate = View.inflate(getApplicationContext(), R.layout.item_tab, null);

            ImageView ivTab = inflate.findViewById(R.id.item_tab_iv);
            TextView tvTab = inflate.findViewById(R.id.item_tab_tv);

            ivTab.setImageResource(mResIds[i]);
            tvTab.setText(tabTexts[i]);

            tabSpec.setIndicator(inflate);
            mHome_tab.addTab(tabSpec, fragments[i], null);
        }

        mHome_tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Toast.makeText(HomeActivity.this, "tabId=" + s, Toast.LENGTH_SHORT).show();
            }
        });

        mHome_tab.setCurrentTab(0);
    }

    @Override
    public void onBackPressed() {
        if (mHome_tab.getCurrentTab() == 0) {
            //当前显示的是NewsFragment
            //找出当前正在显示的NewsFragment
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("0");
            //调用NewsFragment的onBackPressed方法
            if (fragment != null && fragment instanceof HomeFragment) {
                boolean isNeedClosed = ((HomeFragment) fragment).onBackPressed();
                if (isNeedClosed) {
                    //返回为true,就关闭页面
                    super.onBackPressed();
                    return;
                } else {
                    //否则不关闭,直接return
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}
