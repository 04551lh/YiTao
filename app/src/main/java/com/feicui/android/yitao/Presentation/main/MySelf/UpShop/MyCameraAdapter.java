package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
public class MyCameraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MyCameraentry> list;
    public static final int CAMERA_IMAGE = 0x001;
    private onCheckListener listener;
    private Context context;
    private int count = 0;

    public void setOnCheckListener(onCheckListener listener){
        this.listener = listener;
    }

    public ArrayList<MyCameraentry> getList(){
        return list;
    }

    public void setList(ArrayList<MyCameraentry> list){
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if(viewType == CAMERA_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.mycamera_image , null);
            return new MyFirstViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.mycamera_item, null);
        return new MyCameraViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return CAMERA_IMAGE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof  MyFirstViewHolder){
            ((MyFirstViewHolder) holder).firstImage.setImageResource(R.drawable.camera);
            ((MyFirstViewHolder) holder).firstImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.toCameraClick();
                }
            });
        }
        if(holder instanceof MyCameraViewHolder){
            final int var = position -1;
            final MyCameraViewHolder h = (MyCameraViewHolder) holder;
            ImageLoader.getInstance().displayImage("file:///" + list.get(var).getPath(), h.image, ImageLoadOptions.build_mycamera());
            h.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.imageOnClick(var);
                }
            });

            h.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(var).setSelect(isChecked);
                }
            });
            h.checkBox.setTag(list.get(var).getPath());
            h.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCheck(list.get(var), h.checkBox, var);
                }
            });

            if (!list.get(var).isdelect()) {
                h.checkBox.setChecked(list.get(var).isSelect());
            } else {
                h.checkBox.setChecked(false);
                list.get(var).setIsdelect(false);
                list.get(var).setSelect(false);
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    class MyCameraViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.mycamera_image)
        ImageView image;
        @Bind(R.id.mycamera_cb)
        CheckBox checkBox;

        public MyCameraViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class MyFirstViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.mycamera_firstimage)
        ImageView firstImage;
        public MyFirstViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onCheckListener{
        void toCameraClick();

        void onCheck(MyCameraentry myCameraentry,  CheckBox checkBox, int position);

        void imageOnClick(int positon);
    }
}

