package com.feicui.android.yitao.Presentation.main.MySelf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.NetWork.ShopAPI;
import com.feicui.android.yitao.Presentation.Other.GoodsActivity;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/29.
 *
 */
public class MyshopAdapter extends RecyclerView.Adapter<MyshopAdapter.MyshopViewHolder>{
    private ArrayList<GoodsEntry> mlist = new ArrayList<>();
    private Context context;
    private ArrayList<Boolean> booleanList;
    private setOnLongClick onLongClick;
    private RelativeLayout layout;
    private boolean isLong;
    private boolean isOk;
    private static int count;
    private  View view;
    private TextView  COUNT;
    private void setLists(){
        for (int i = 0; i < mlist.size(); i++) {
            booleanList.add(false);
        }
    }

    public MyshopAdapter(ViewGroup view){
//        lists = new ArrayList();
        booleanList = new ArrayList<>();
        layout = (RelativeLayout) view;
    }
    public void addData(ArrayList<GoodsEntry> list){
        mlist.addAll(list);
        setLists();
        notifyDataSetChanged();
    }

    public void setisLong(boolean isLong){
        this.isLong = isLong;
        notifyDataSetChanged();
    }
    public void clearData(){
        mlist.clear();
        notifyDataSetChanged();
    }


    @Override
    public MyshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.myshop_goods, null);
        return new MyshopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyshopViewHolder holder, final int position) {
        if(isLong){
           holder.checkBox.setVisibility(View.VISIBLE);
        }
        else{
            holder.checkBox.setVisibility(View.GONE);
        }
//        holder.name.setText(lists.get(position)+"");
        holder.name.setText(mlist.get(position).getName());
        holder.price.setText(mlist.get(position).getPrice());
        holder.description.setText(mlist.get(position).getDescription());
        ImageLoader.getInstance().displayImage(ShopAPI.IMAGE_URL+mlist.get(position).getPage(), holder.image, ImageLoadOptions.build_item());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(booleanList.get(position)){
                    booleanList.add(position, false);
                    count --;
                }
                else{
                    booleanList.add(position, true);
                    count ++;
                }
                COUNT.setText("删除"+count);
                if(count>0){
                    COUNT.setEnabled(true);
                    COUNT.setBackgroundResource(R.drawable.button_backgroud);
                }
                else{
                    COUNT.setEnabled(false);
                    COUNT.setBackgroundResource(R.drawable.normalbutton_normal);
                }
            }

        });
        COUNT.setText("删除"+count);
        if(count>0){
            COUNT.setEnabled(true);
            COUNT.setBackgroundResource(R.drawable.button_backgroud);
        }
        else{
            COUNT.setEnabled(false);
            COUNT.setBackgroundResource(R.drawable.normalbutton_normal);
        }
        holder.checkBox.setChecked(booleanList.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GoodsActivity.getStartIntent(context, mlist.get(position).getUuid(), "my");
                context.startActivity(intent);
            }
        });
        setAllListener();
    }



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    private void setAllListener(){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClick.setListener();
                isLong = true;
                notifyDataSetChanged();
                return true;
            }
        });
        layout.findViewById(R.id.bt_myhsop_allremove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOk){
                    mlist.clear();
                    booleanList.clear();
                    isOk = false;
                    notifyDataSetChanged();
                    count = 0;
                }
                else{
                    count = 0;
                    for (int i = 0; i < mlist.size(); i++) {
                        booleanList.add(i, true);
                        count++;
                    }
                    notifyDataSetChanged();
                    isOk = true;
                }
            }
        });

        layout.findViewById(R.id.bt_myshop_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < booleanList.size(); i++) {
                    if(booleanList.get(i)){
                        mlist.remove(i);
                        booleanList.remove(i);
                        i--;
                    }
                }
                count = 0;
                notifyDataSetChanged();
            }
        });
    }
    class MyshopViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.myshop_iv)
        ImageView image;
        @Bind(R.id.tv_myshop_name)
        TextView name;
        @Bind(R.id.tv_myshop_price)
        TextView price;
        @Bind(R.id.tv_myshop_description)
        TextView description;
        @Bind(R.id.cb_myshop)
        CheckBox checkBox;
        public MyshopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            COUNT = (TextView) layout.findViewById(R.id.bt_myshop_remove);
        }
    }

    public void setlListener(setOnLongClick longClick){
        this.onLongClick = longClick;
    }
    public interface setOnLongClick{
        void setListener();
    }
}
