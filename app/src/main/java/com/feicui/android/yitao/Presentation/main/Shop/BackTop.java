package com.feicui.android.yitao.Presentation.main.Shop;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.feicui.android.yitao.R;

/**
 * Created by Administrator on 2016/11/22.
 */
public class BackTop extends View {


    public BackTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(getResources().getColor(R.color.btn_tell));
//        paint.setStrokeWidth(5f);
//        canvas.drawLine(25, 25, 25, 55, paint);
//        canvas.drawLine(25, 40, 35, 25, paint);
//        canvas.drawLine(25, 40, 35, 55, paint);
//        canvas.drawLine(25, 40, 60, 40, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.backtop), 0, 0, null);
    }
}
