package com.feicui.android.yitao.Model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/11/28.
 */
public class GetBitmap {
    public GetBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private static Bitmap bitmap;

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
