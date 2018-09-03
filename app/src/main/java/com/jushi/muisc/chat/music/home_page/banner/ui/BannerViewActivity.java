package com.jushi.muisc.chat.music.home_page.banner.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jushi.muisc.chat.R;
import com.jushi.muisc.chat.utils.DisplayUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 点击轮播图显示轮播图信息的页面
 */
public class BannerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private LinearLayout linearLayout;
    private ImageView backButton;

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
        setContentView(R.layout.activity_web_view);
        DisplayUtils.setStatusBarColor(this, R.color.color_status);
        findWidget();
    }

    private void findWidget() {
        webView = (WebView) findViewById(R.id.web_view_activity_webview);
        linearLayout = (LinearLayout) findViewById(R.id.web_show_hint);
        backButton = findViewById(R.id.BannerViewActivity_back_button);
        backButton.setOnClickListener(this);

        initWebView();
    }

    private void initWebView() {
        Intent intent = getIntent();
        String linkUrl = intent.getStringExtra("linkUrl");
        //获得WebView的WebSettings对象
        WebSettings settings = webView.getSettings();
        //设置参数
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf_8");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setSupportZoom(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(linkUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BannerViewActivity_back_button:
                finish();
                break;
        }
    }

    class MyWebViewClient extends WebViewClient {

        //开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        //地址加载中
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        //加载完成
        @Override
        public void onPageFinished(WebView view, String url) {
            linearLayout.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        //加载错误
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Toast.makeText(BannerViewActivity.this, "加载出错", Toast.LENGTH_SHORT).show();
            final AlertDialog alertDialog = new AlertDialog.Builder(BannerViewActivity.this).create();
            alertDialog.setTitle("加载出错");
            alertDialog.setMessage("无网络链接或网速较慢");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            super.onReceivedError(view, request, error);
        }
    }
}
