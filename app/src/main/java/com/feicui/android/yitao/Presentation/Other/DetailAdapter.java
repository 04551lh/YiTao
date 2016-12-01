package com.feicui.android.yitao.Presentation.Other;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/18.
 */
public class DetailAdapter extends PagerAdapter {
    private ArrayList<ImageView> viewsList = new ArrayList<>();
    private OnItemClickListener listener;

    public DetailAdapter(ArrayList<ImageView> list){
        viewsList = list;
    }

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return viewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = viewsList.get(position);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick();
//            }
//        });
        container.addView(viewsList.get(position));

        return viewsList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnItemClickListener{
        void onItemClick();
    }
}
