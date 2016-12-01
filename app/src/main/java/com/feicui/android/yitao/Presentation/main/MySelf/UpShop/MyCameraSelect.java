package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.Count;
import com.feicui.android.yitao.Model.MyCameraentry;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30.
 */
public class MyCameraSelect extends RecyclerView.Adapter<MyCameraSelect.MySelectViewHolder> {
    private ArrayList<Boolean> arrayList;
    private ArrayList<MyCameraentry> list;
    private Context context;
    private int count;

    public MyCameraSelect(ArrayList<MyCameraentry> list){
        this.list = list;
        arrayList = new ArrayList<>();
    }
    @Override
    public MySelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.mycamera_item, null);
        return new MySelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MySelectViewHolder holder, final int position) {
//            holder.name.setText(list.get(position).getName());
            ImageLoader.getInstance().displayImage("file:///" + list.get(position).getPath(), holder.image, ImageLoadOptions.build_mycamera());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.get(position)){
                    arrayList.add(position, false);
                    count --;
                }
                else {
                    arrayList.add(position, true);
                    count ++ ;
                }
                if(count<9){
                    EventBus.getDefault().post(new Count(count));
                    EventBus.getDefault().post(list.get(position));
                }
                else{
                    Toast.makeText(context, "图片已满", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MySelectViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.mycamera_image)
        ImageView image;
//        @Bind(R.id.mycamera_name)
//        TextView name;
        @Bind(R.id.mycamera_cb)
        CheckBox checkBox;

        public MySelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(i, false);
            }
        }
    }
}

