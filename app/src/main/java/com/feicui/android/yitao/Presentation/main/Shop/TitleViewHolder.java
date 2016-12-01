package com.feicui.android.yitao.Presentation.main.Shop;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.android.yitao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/22.
 */
public class TitleViewHolder extends RecyclerView.ViewHolder {
    RecyclerView rv_title;
    public TitleViewHolder(View itemView, Context content) {
        super(itemView);
        rv_title = (RecyclerView) itemView.findViewById(R.id.rv_title);
        rv_title.setLayoutManager(new GridLayoutManager(content, 4));
        rv_title.setAdapter(new TitleAdapter());
    }

    public static class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.MyHolderView> {
        public final String[] titles = {"全部", "家用", "电子", "服饰", "玩具", "图书", "礼品", "其他"};
        private int[] images = {R.drawable.img_all, R.drawable.img_household, R.drawable.img_electron
                , R.drawable.img_dress, R.drawable.img_toys, R.drawable.img_book,
                R.drawable.img_gift, R.drawable.img_other};
        private Context context;
        private static OnTextItemClickListener listener;

        @Override
        public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.select_title, null);
            return new MyHolderView(view);
        }

        @Override
        public void onBindViewHolder(MyHolderView holder, final int position) {
            holder.tv_tt.setText(titles[position]);
            holder.im_title.setImageResource(images[position]);
            for (int i = 0; i < images.length; i++) {
                if(i == position){
                    holder.im_title.setBackgroundResource(R.drawable.pp_title);
                }
            }
            holder.im_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTitleked(position);
                }
            });
            holder.tv_tt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTitleked(position);
                }
            });
        }


        @Override
        public int getItemCount() {
            return titles.length;
        }


        public class MyHolderView extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_tt)
            TextView tv_tt;
            @Bind(R.id.im_title)
            ImageView im_title;

            public MyHolderView(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public static void setListener(OnTextItemClickListener listeners) {
            listener = listeners;
        }

        public interface OnTextItemClickListener {
            void onTitleked(int position);
        }
    }
}
