package com.jushi.muisc.chat.music.search.ui;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.WrapRecyclerView;
import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.music.search.adapter.HistorySearchAdapter;
import com.jushi.muisc.chat.music.search.adapter.SearchDataAdapter;
import com.jushi.muisc.chat.music.play_navgation.PlayController;
import com.jushi.muisc.chat.music.jsinterface.MusicDataAdapter;
import com.jushi.muisc.chat.music.public_model.LatestMusicModel;
import com.jushi.muisc.chat.music.localmusic.model.Song;
import com.jushi.muisc.chat.music.search.model.SearchDataModel;
import com.jushi.muisc.chat.music.public_model.SongDetail;
import com.jushi.muisc.chat.music.service.NetWorkService;
import com.jushi.muisc.chat.music.utils.DataUrlUtils;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.jushi.muisc.chat.music.utils.HistorySearchUtils;
import com.jushi.muisc.chat.music.utils.LocalMusicUtils;
import com.jushi.muisc.chat.utils.ShadowUtils;
import com.jushi.muisc.chat.utils.ToastUtils;
import com.jushi.muisc.chat.view.JSTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    private Toolbar toolbar;
    private RadioButton searchBtn;
    private EditText editText;
    private WrapRecyclerView searchRecyclerView;
    private RecyclerView historyRecyclerView;
    private PullToRefreshLayout refreshLayout;
    private TagFlowLayout tagFlowLayout;
    private LinearLayout newWordsContainer;
    private Handler handler;
    private NetWorkService workService;
    private List<SearchDataModel.SongListBean> songLists = new ArrayList<>();
    private SearchDataAdapter searchDataAdapter;
    private HistorySearchAdapter historySearchAdapter;
    //记录加载的页数(次数)
    private int pageNo = 1;
    private String keyWords, oldKeyWords = "";
    private HistorySearchUtils historyUtils;
    private boolean isLoaded = false;
    private PlayController playController;
    private String songId;
    private Song song = new Song();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        handler = new Handler();
        workService = NetWorkService.getInstance(this);
        historyUtils = HistorySearchUtils.getInstance(this);
        initView();
    }

    private void initView() {
        findWidget();
        setRefreshListener();
        setRecyclerAdapter();
        editTextSetListener();
        getHotWordsData();
        showHistorySearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playController = PlayController.getInstance(this);
        playController.showPlayControllerInfo();
    }

    private void findWidget() {
        toolbar = findViewById(R.id.search_activity_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ShadowUtils.setShadowDown_2(this, toolbar);
        searchBtn = findViewById(R.id.search_activity_search_button);
        editText = findViewById(R.id.search_activity_editText);
        searchBtn.setOnClickListener(this);
        //上拉加载更多的布局容器
        refreshLayout = findViewById(R.id.search_activity_refreshLayout);
//        refreshLayout.setCustomLoadmoreView(new LoadFootView(this));
        //显示搜索结果的recyclerView
        searchRecyclerView = (WrapRecyclerView) refreshLayout.getPullableView();
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //显示新歌热词、历史搜索的布局容器
        newWordsContainer = findViewById(R.id.new_music_hot_words_container);
        //流式布局容器显示每一个新歌热词
        tagFlowLayout = findViewById(R.id.TagFlowLayout_new_music_hot_words);
        //显示历史搜索的RecyclerView
        historyRecyclerView = findViewById(R.id.history_search_words_recyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRecyclerAdapter() {  //设置recyclerView的适配器
        searchDataAdapter = new SearchDataAdapter(this, songLists);
        searchRecyclerView.setAdapter(searchDataAdapter);
        setItemClickListener();
    }

    private void setItemClickListener() {  //设置搜索结果的item项的点击事件
        searchDataAdapter.setOnItemClickListener(new SearchDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchDataModel.SongListBean songBean, int position) {
                searchDataAdapter.setDateChanged(position);
                songId = songBean.getSong_id();
                new SongInfoTask().run();
            }
        });
    }

    private void setRefreshListener() {  //设置刷新、加载事件监听
        refreshLayout.setPullDownEnable(false);
        refreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pageNo += 1;  //上拉加载时，页数增加1  （分页加载）
                getSearchData();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_activity_search_button:
                keyWords = editText.getText().toString().trim().replace(" ", "+");
                loadSearchData();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && actionId == event.getAction()) {
            keyWords = editText.getText().toString().trim().replace(" ", "+");
            loadSearchData();
        }
        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    //加载搜索数据，并且上一次搜索的关键词等于当前搜索的关键词（便于在上拉加载时判断）
    private void loadSearchData() {
        if (keyWords == null || keyWords.equals(""))
            return;
        getSearchData();
        editText.setText("");
        oldKeyWords = keyWords;
        hideKeyBoard();
        saveKeyWords();
    }

    //获得搜索的内容，上拉加载时，如果上一次搜索关键词跟当前的关键词不一样，则清除结果重新搜索
    private void getSearchData() {
        if (oldKeyWords != null || !oldKeyWords.equals("")) {
            if (!oldKeyWords.equals(keyWords)) {
                songLists.clear();
                pageNo = 1;
                searchDataAdapter.notifyDataSetChanged();
            }
        }
        new SearchTask().run();
    }

    //保存搜索过的关键词（用于显示历史搜索）
    private void saveKeyWords() {
        List<String> historyWords = historyUtils.getKeyWords();
        for (int i = 0; i < historyWords.size(); i++) {
            if (keyWords.equalsIgnoreCase(historyWords.get(i))) {
                historyWords.remove(i);
            }
        }
        historyWords.add(0, keyWords);
        historyUtils.saveKeyWords(historyWords);
    }

    private void editTextSetListener() {
        editText.setOnEditorActionListener(this);
    }


    class SearchTask extends Thread {  //通过关键词、页数 搜索数据
        @Override
        public void run() {
            workService.getSearchData(keyWords, pageNo, new MusicDataAdapter() {
                @Override
                public void onSearchData(List<SearchDataModel.SongListBean> songListBeans) {
                    if (songListBeans.size() > 0) {
                        for (int i = 0; i < songListBeans.size(); i++) {
                            songLists.add(songListBeans.get(i));
                        }
                        showSearchData();
                    }
                }

                @Override
                public void onSuccess() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isLoaded){
                                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED); //数据加载成功
                                ToastUtils.show(SearchActivity.this, getString(R.string.load_success));
                            }
                            isLoaded = true;
                        }
                    },1300);
                }

                @Override
                public void onError() {
                    super.onError();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshFinish(PullToRefreshLayout.FAIL);  //数据加载失败
                            refreshLayout.setPullUpEnable(false);
                            ToastUtils.show(SearchActivity.this, getString(R.string.load_failed));
                        }
                    },1300);
                }
            });
        }
    }

    //显示搜索的数据，隐藏新歌热词、历史搜索
    private void showSearchData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                searchDataAdapter.notifyDataSetChanged();
                if (refreshLayout.getVisibility() == View.GONE) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    newWordsContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getHotWordsData() {  //获得新歌热词
        new HotWordsTask().run();
    }

    class HotWordsTask extends Thread {  //加载热门歌曲数据 （10条）
        @Override
        public void run() {
            workService.getChartDetailMusicData(DataUrlUtils.getHotChartUrl(10), new MusicDataAdapter() {
                @Override
                public void onLatestMusicListData(List<LatestMusicModel.SongListBean> songListBeans) {
                    showHotWords(songListBeans);
                }
            });
        }
    }

    //显示新歌热词数据（流式布局显示）
    private void showHotWords(final List<LatestMusicModel.SongListBean> songListBeans) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                tagFlowLayout.setAdapter(new TagAdapter(songListBeans) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        JSTextView view = (JSTextView) View.inflate(SearchActivity.this, R.layout.hot_words_text_layout, null);
                        view.setText(songListBeans.get(position).getTitle());
                        return view;
                    }
                });
                setTagClickListener(songListBeans);
            }
        });
    }

    //新歌热词item项的点击事件
    private void setTagClickListener(final List<LatestMusicModel.SongListBean> songListBeans) {
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                view.setBackgroundResource(R.drawable.hot_words_text_bg_selected);
                keyWords = songListBeans.get(position).getTitle().trim().replace(" ", "+");
                loadSearchData();
                return false;
            }
        });
    }

    private List<String> historyList;
    //显示历史搜索的关键词
    private void showHistorySearch(){
        historyList = historyUtils.getKeyWords();
        historySearchAdapter = new HistorySearchAdapter(this,historyList);
        historyRecyclerView.setAdapter(historySearchAdapter);
        historySearchAdapter.setOnItemClickListener(new HistorySearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final String keyWords, int position) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SearchActivity.this.keyWords = keyWords.replace(" ","+");
                        loadSearchData();
                    }
                },250);
            }

            @Override
            public void onDeleteButtonClick(String keyWords, final int position) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        historyList.remove(position);
                        historySearchAdapter.notifyDataSetChanged();
                        historyUtils.saveKeyWords(historyList);
                    }
                },250);
            }
        });
    }

    //隐藏软键盘
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_IMPLICIT_ONLY);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);//隐藏键盘
    }

    //数据转换
    class SongInfoTask extends Thread{
        @Override
        public void run() {
            if (songId.equals("0")){
                ToastUtils.show(SearchActivity.this,"无在线播放资源");
            }else {
                workService.getSongInfo(songId, new MusicDataAdapter() {
                    @Override
                    public void onSongDetail(SongDetail detail) {
                        song.setSongId(detail.getSonginfo().getSong_id());
                        song.setSongAlbum(detail.getSonginfo().getAlbum_title());
                        song.setSongAuthor(detail.getSonginfo().getAuthor());
                        song.setSongSize(LocalMusicUtils.getSongSize(detail.getBitrate().getFile_size()));
                        song.setSongDuration(detail.getBitrate().getFile_duration());
                        song.setSongName(detail.getSonginfo().getTitle());
                        song.setSongPath(detail.getBitrate().getShow_link());
                        song.setSongImagePath(detail.getSonginfo().getArtist_list().get(0).getAvatar_s300());
                        song.setLrcPath(detail.getSonginfo().getLrclink());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                playController.playOneMusic(song,0);
                            }
                        });
                    }
                });
            }
        }
    }
}
