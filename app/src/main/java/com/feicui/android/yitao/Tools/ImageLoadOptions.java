package com.feicui.android.yitao.Tools;

import com.feicui.android.yitao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Administrator on 2016/11/17.
 * @description 设置加载图片参数
 */
public class ImageLoadOptions {
    private ImageLoadOptions(){

    }

    private static DisplayImageOptions options_item;

    public static DisplayImageOptions build_item(){
        if(options_item == null){
            options_item = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.image_error)
                    .showImageOnLoading(R.drawable.image_loding)
                    .showImageOnFail(R.drawable.image_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .resetViewBeforeLoading(true)
                    .build();
        }
        return options_item;
    }

    private static DisplayImageOptions options_mycamera;

    public static DisplayImageOptions build_mycamera(){
        if(options_item == null){
            options_item = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.mycamera_back)
                    .showImageOnLoading(R.drawable.mycamera_back)
                    .showImageOnFail(R.drawable.mycamera_back)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .resetViewBeforeLoading(true)
                    .build();
        }
        return options_mycamera;
    }
}
