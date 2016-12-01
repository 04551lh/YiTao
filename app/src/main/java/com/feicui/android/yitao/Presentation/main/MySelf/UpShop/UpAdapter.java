package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feicui.android.yitao.Model.MyCameraentry;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30.
 */
public class UpAdapter extends RecyclerView.Adapter<UpAdapter.UpShopViewHolder> {

    private ArrayList<MyCameraentry> list;
    private Context context;

    public UpAdapter(ArrayList<MyCameraentry> list){
        this.list = list;
    }

    public void addList(ArrayList<MyCameraentry> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(MyCameraentry m){
        list.add(m);
        notifyDataSetChanged();
    }

    @Override
    public UpShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.upshop_item, null);
        return new UpShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpShopViewHolder holder, int position) {
        if(position == 0){
            holder.image.setImageResource(R.drawable.camera);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyCamera.class));
            }
        });
        if(list.size() != 0){
            ImageLoader.getInstance().displayImage("file:///"+list.get(position), holder.image, ImageLoadOptions.build_mycamera());
        }
        else{

        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class UpShopViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.upshop_image)
        ImageView image;
        public UpShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
