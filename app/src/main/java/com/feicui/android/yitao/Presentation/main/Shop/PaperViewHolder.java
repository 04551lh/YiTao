package com.feicui.android.yitao.Presentation.main.Shop;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicui.android.yitao.Model.Ads;
import com.feicui.android.yitao.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/11/22.
 *
 */
public class PaperViewHolder extends RecyclerView.ViewHolder implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ArrayList<ImageView> imageViews;
    private int [] images = {R.drawable.view1, R.drawable.view2,R.drawable.view3,R.drawable.view4};
    private AdsAdapter adsAdapter;
    private Context context;
    private Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (count == images.length) {
                    count = 0;
                    vp_ads.setCurrentItem(count, false);
                }
                vp_ads.setCurrentItem(count++);
                sendEmptyMessageDelayed(1, 2000);
            }
        }
    };
    private int count = 0;

//    @Bind(R.id.vp_ads)
    MyViewPager vp_ads;
//    @Bind(R.id.indicator_ads)
    CircleIndicator indicator_ads;
    public PaperViewHolder(View itemView , Context context) {
        super(itemView);
        vp_ads = (MyViewPager) itemView.findViewById(R.id.vp_ads);
        indicator_ads = (CircleIndicator) itemView.findViewById(R.id.indicator_ads);
        this.context = context;
        imageViews = new ArrayList<>();
        setAdsImage();
        adsAdapter = new AdsAdapter();
        vp_ads.setAdapter(adsAdapter);
        vp_ads.setNestParent((ViewGroup)vp_ads.getParent());
        indicator_ads.setViewPager(vp_ads);
        initView();
    }
    private void setAdsImage(){
        for (int i = 0; i < images.length; i++) {
            ImageView view = new ImageView(context);
            view.setImageResource(images[i]);
            view.setOnClickListener(this);
            view.setTag(i);
            imageViews.add(view);
        }
    }

    private void initView(){
        handler.sendEmptyMessageDelayed(1, 2000);
        vp_ads.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        count = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < imageViews.size(); i++) {
            if(v.getTag().equals(imageViews.get(i).getTag())){
                EventBus.getDefault().post(new Ads(i+"********"));
            }
        }

    }


    public class AdsAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

    }
}
