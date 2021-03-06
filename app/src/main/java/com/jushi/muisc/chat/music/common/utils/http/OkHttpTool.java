package com.jushi.muisc.chat.music.common.utils.http;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpTool {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static void httpClient(String url, final OnClientListener listener) {
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onError();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                listener.onResponse(response);
            }
        });
    }

    public interface OnClientListener {
        void onError();

        void onResponse(Response response);
    }
}
