package com.jushi.muisc.chat.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.fragment.ChartFragment;
import com.jushi.muisc.chat.music.fragment.HomePageFragment;
import com.jushi.muisc.chat.music.fragment.VideoAndLiveFragment;
import com.jushi.muisc.chat.utils.ShadowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paocai on 2018/5/4.
 */

public class MusicLayout extends RelativeLayout {

    private Context mContext;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FrameLayout tabLayoutShadowContainer;
    private List<String> tabTitles = new ArrayList<>();
    private List<Fragment> pages = new ArrayList<>();
    private HomePageFragment homePageFragment;
    private VideoAndLiveFragment liveFragment;
    private ChartFragment chartFragment;

    public MusicLayout(Context context) {
        this(context,null);
    }

    public MusicLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        findWidget();
        viewPagerSetAdapter();
    }

    private void findWidget() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_music,this);
        tabLayout = findViewById(R.id.music_TabLayout);
        viewPager = findViewById(R.id.music_ViewPager);
        tabLayoutShadowContainer = findViewById(R.id.FrameLayout_music_layout);
        ShadowUtils.setShadowDown_2(mContext,tabLayoutShadowContainer);
    }

    private void viewPagerSetAdapter(){
        tabTitles.add(mContext.getResources().getString(R.string.homepage));
        tabTitles.add(mContext.getResources().getString(R.string.video_live));
        tabTitles.add(mContext.getResources().getString(R.string.chart));
        for (int i = 0;i < tabTitles.size();i++){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabTitles.get(i));
            tabLayout.addTab(tab);
        }
        homePageFragment = new HomePageFragment();
        liveFragment = new VideoAndLiveFragment();
        chartFragment = new ChartFragment();
        pages.add(homePageFragment);
        pages.add(liveFragment);
        pages.add(chartFragment);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MusicAdapter(((FragmentActivity)mContext).getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    class MusicAdapter extends FragmentPagerAdapter{

        public MusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}
