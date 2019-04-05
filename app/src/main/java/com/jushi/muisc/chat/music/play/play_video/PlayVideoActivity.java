package com.jushi.muisc.chat.music.play.play_video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.common.utils.DisplayUtils;
import com.jushi.muisc.chat.common.utils.RefreshViewUtils;
import com.jushi.muisc.chat.common.view.JSTextView;
import com.umeng.analytics.MobclickAgent;


/**
 * 播放直播和MV的Activity
 */
public class PlayVideoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView webView;
    private JSTextView tvTitle;
    private String type;
    private String url;
    private String nickName;


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        url = getIntent().getStringExtra("videoLink");
        nickName = getIntent().getStringExtra("nickName");
        type = getIntent().getStringExtra("type");
        RefreshViewUtils.showRefreshDialog(this);
        initView();
    }

    private void initView() {
        buildVersion();
        findWidget();
        initMethod();
    }

    private void buildVersion() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    private void findWidget() {
        webView = findViewById(R.id.show_live_webview);
        toolbar = findViewById(R.id.play_video_activity_toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        tvTitle = findViewById(R.id.play_video_activity_title_text);
        tvTitle.setSelected(true);
    }

    private void initMethod() {
//        initWebviewLayoutParms();
        showNickName();
        loadLive();
    }


    private void showNickName() {
        if (nickName != null || !nickName.equals("")) {
            tvTitle.setText(nickName);
        }
    }

    private void initWebviewLayoutParms() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int h = getWindowManager().getDefaultDisplay().getHeight();
            int w = getWindowManager().getDefaultDisplay().getWidth();
            webView.getLayoutParams().height = h;
            webView.getLayoutParams().width = w;
            toolbar.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            int h = getWindowManager().getDefaultDisplay().getHeight();
            int w = getWindowManager().getDefaultDisplay().getWidth();
            webView.getLayoutParams().height = h / 2 + w / 2;
            webView.getLayoutParams().width = w;
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    private void loadLive() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                RefreshViewUtils.dismissRefreshDialog();
            }
        });
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    //横竖屏切换时回调
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            initWebviewLayoutParms();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            initWebviewLayoutParms();
        }
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }
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
}
