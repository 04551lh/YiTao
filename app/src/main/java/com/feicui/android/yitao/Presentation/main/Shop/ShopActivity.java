package com.feicui.android.yitao.Presentation.main.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.Ads;
import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Presentation.Other.GoodsActivity;
import com.feicui.android.yitao.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/11/23.
 *
 */
public class ShopActivity extends AppCompatActivity implements ShopView, View.OnClickListener {
    private static int typeNo;
    public static final String[]titles = {"", "household", "electron", "dress", "toy", "book", "gift", "other"};
    @Bind(R.id.rv_data)
    RecyclerView rv_data;
    @Bind(R.id.pf_refresh)
    PtrClassicFrameLayout pf_refresh;

    @Bind(R.id.backTop)
    ImageView backTop;
    private ShopPresentater presentater;
    private ShopAdapter adapter;
    private TitleViewHolder.TitleAdapter titleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_goods);
        presentater = new ShopPresentater();
        adapter = new ShopAdapter();
        titleAdapter = new TitleViewHolder.TitleAdapter();
        EventBus.getDefault().register(this);
        presentater.onCreate();
        ButterKnife.bind(this);
        presentater.attachView(this);
        initView();
        rv_data.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter.getItemCount() == 2){
            pf_refresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pf_refresh.autoRefresh();
                }
            }, 200);
        }
    }

    private GridLayoutManager.SpanSizeLookup sizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            if(position == 0 || position == 1){
                return 2;
            }
            return 1;
        }
    };
    private void initView(){
        GridLayoutManager manager = new GridLayoutManager(ShopActivity.this, 2);
        manager.setSpanSizeLookup(sizeLookup);
        rv_data.setLayoutManager(manager);
        rv_data.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof RecyclerView.LayoutManager){
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if(manager.findFirstVisibleItemPosition() > 1){
                        backTop.setVisibility(View.VISIBLE);
                    }
                    else{
                        backTop.setVisibility(View.GONE);
                    }
                }
            }
        });
        backTop.setOnClickListener(this);
        pf_refresh.setLastUpdateTimeFooterRelateObject(this);
        pf_refresh.setBackgroundResource(R.color.black);
        pf_refresh.setDurationToCloseHeader(1500);
        pf_refresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                presentater.getMoreData(titles[typeNo]);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presentater.refreshData(1, titles[typeNo]);
            }
        });

        titleAdapter.setListener(new TitleViewHolder.TitleAdapter.OnTextItemClickListener() {
            @Override
            public void onTitleked(int position) {
                typeNo = position;
                presentater.refreshData(1, titles[typeNo]);
            }
        });
        adapter.setListener(new ShopAdapter.OnItemClickedListener() {
            @Override
            public void onPhotoClicked(GoodsEntry goodsEntry) {
                Intent intent = GoodsActivity.getStartIntent(ShopActivity.this,goodsEntry.getUuid(), "0");
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Ads event){
        Toast.makeText(ShopActivity.this, event.getLink(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presentater.onDestry();
    }

    @Override
    public void shutData() {
        pf_refresh.refreshComplete();
    }

    @Override
    public void addMoreData(ArrayList<GoodsEntry> list) {
        if(list != null){
            adapter.addData(list);
        }

    }

    @Override
    public void addRefreshData(ArrayList<GoodsEntry> list) {
        adapter.clear();
        if(list != null){
            adapter.addData(list);
        }
    }

    @Override
    public void addErrorData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backTop:
                rv_data.scrollToPosition(0);
                backTop.setVisibility(View.GONE);
        }
    }
}
