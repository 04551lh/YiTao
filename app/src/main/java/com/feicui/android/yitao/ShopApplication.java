package com.feicui.android.yitao;

import android.app.Application;

import com.feicui.android.yitao.Model.CachePrefereces;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/11/17.
 */
public class ShopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCacheSize(4 * 1024 * 1024)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(configuration);

        CachePrefereces.init(this);
    }
}
