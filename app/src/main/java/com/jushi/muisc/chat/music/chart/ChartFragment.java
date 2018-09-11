package com.jushi.muisc.chat.music.chart;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jushi.muisc.chat.app.JSApplication;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.chart.adapter.ChartDataAdapter;
import com.jushi.muisc.chat.music.common.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.common.manager.ActivityManager;
import com.jushi.muisc.chat.music.chart.model.ChartDataModel;
import com.jushi.muisc.chat.music.common.service.NetWorkService;
import com.jushi.muisc.chat.common.utils.Constant;
import com.jushi.muisc.chat.music.common.utils.music.MusicDataUtils;
import com.jushi.muisc.chat.common.utils.NetWorkUtils;
import com.jushi.muisc.chat.common.view.JSTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 榜单
 */
public class ChartFragment extends Fragment {
    private static final String SAVE_KEY = "contentBeanXs";
    private View rootView;
    private static RecyclerView recyclerView;
    private static JSTextView tvLoad;
    private static ChartDataAdapter chartAdapter;
    private static NetWorkService workService;
    private static Handler handler;
    private static List<ChartDataModel.ContentBeanX> contentBeanXs = new ArrayList<>();
    private static boolean isRefresh = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_music_chart, container, false);
        workService = NetWorkService.getInstance(getContext());
        handler = new Handler();
        initView();
        return rootView;
    }

    private void initView() {
        tvLoad = rootView.findViewById(R.id.chart_fragment_loading_tv);
        recyclerView = rootView.findViewById(R.id.chart_fragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chartAdapter = new ChartDataAdapter(getContext(),contentBeanXs);
        recyclerView.setAdapter(chartAdapter);
        getChartData();
        setRecyclerClickListener();
    }

    private void setRecyclerClickListener() {
        chartAdapter.setOnItemClickListener(new ChartDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChartDataModel.ContentBeanX contentBeanX, int position) {
                Constant.contentBeanX = contentBeanX;
                ActivityManager.startChartDetailActivity(getContext(),contentBeanX.getName(),contentBeanX.getWeb_url());
            }
        });
    }

    private static void getChartData() {
        if (NetWorkUtils.isNetworkAvailable(JSApplication.getContext())) {
            new ChartDataTask().run();
        }else {
            if (!isRefresh){
                contentBeanXs.addAll((List<ChartDataModel.ContentBeanX>) MusicDataUtils.getInstance(JSApplication.getContext()).getSaveData(SAVE_KEY,ChartDataModel.ContentBeanX.class));
                showChartData();
            }
        }
    }

    static class ChartDataTask extends Thread{
        @Override
        public void run() {
            contentBeanXs.clear();
            workService.getChartData(new MusicDataAdapter() {
                @Override
                public void onChartData(List<ChartDataModel.ContentBeanX> contentBeans) {
                    contentBeanXs.addAll(contentBeans);
                    showChartData();
                    MusicDataUtils.getInstance(JSApplication.getContext()).saveData(SAVE_KEY,contentBeanXs);
                }
            });

        }
    }

    private static void showChartData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                chartAdapter.notifyDataSetChanged();
                if (recyclerView.getVisibility() == View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                    tvLoad.setVisibility(View.GONE);
                }
            }
        });
    }

    public static void refreshData(){
        isRefresh = true;
        contentBeanXs.clear();
        getChartData();
    }
}
