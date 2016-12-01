package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicui.android.yitao.Model.MyCameraentry;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ImageAdapter extends PagerAdapter {
    private ArrayList<MyCameraentry> list;
    private Context context;

    public ImageAdapter(ArrayList<MyCameraentry> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(context);
        ImageLoader.getInstance().displayImage(
                "file:///"+list.get(position).getPath(),
                view,
                ImageLoadOptions.build_mycamera());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
