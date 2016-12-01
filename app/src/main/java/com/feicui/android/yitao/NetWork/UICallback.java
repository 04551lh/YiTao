package com.feicui.android.yitao.NetWork;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/16.
 *
 */
public abstract class UICallback implements Callback {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        Log.i("yzg", e.getMessage());
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailureUI(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response)  {

        try {
            if (!response.isSuccessful()) {
                throw new IOException("error code: " + response.code());
            }
            final String content = response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onResponseUI(call, content);
                }
            });
        } catch (IOException e) {
            onFailure(call, e);
        }
    }

    public abstract void onFailureUI(Call call, IOException e);
    public abstract void onResponseUI(Call call, String context);
}
