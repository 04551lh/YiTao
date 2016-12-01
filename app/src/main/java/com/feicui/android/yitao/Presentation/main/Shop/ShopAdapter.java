package com.feicui.android.yitao.Presentation.main.Shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.NetWork.ShopAPI;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/17.
 *
 */
public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<GoodsEntry> list = new ArrayList<>();
    private OnItemClickedListener listener;
    public static final int TITLE_AD = 0x001;
    public static final int TITLE = 0x002;

    public void addData(List<GoodsEntry> data){
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TITLE_AD;
        }
        if(position == 1){
            return TITLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = null;
        if(viewType == TITLE_AD){
            view = LayoutInflater.from(context).inflate(R.layout.good_ads, null);
            return new PaperViewHolder(view , context);
        }
        if(viewType == TITLE){
            view = LayoutInflater.from(context).inflate(R.layout.good_title, null);
            return new TitleViewHolder(view, context);
        }

        view = LayoutInflater.from(context).inflate(R.layout.good_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).tv_goods_name.setText(list.get(position - 2).getName());
            ((ViewHolder)holder).tv_goods_price.setText(list.get(position -2 ).getPrice() + "￥");
            ((ViewHolder)holder).im_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onPhotoClicked(list.get(position -2));
                    }
                }
            });
            ImageLoader.getInstance().displayImage(ShopAPI.IMAGE_URL + list.get(position -2).getPage(), ((ViewHolder)holder).im_goods, ImageLoadOptions.build_item());
        }
    }


    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.im_goods)
        ImageView im_goods;
        @Bind(R.id.tv_goods_name)
        TextView tv_goods_name;
        @Bind(R.id.tv_goods_price)
        TextView tv_goods_price;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(OnItemClickedListener listener){
        this.listener = listener;
    }

    public interface OnItemClickedListener{
        void onPhotoClicked(GoodsEntry goodsEntry);
    }
}
