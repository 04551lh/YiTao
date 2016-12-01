package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.feicui.android.yitao.Model.MyCameraentry;
import com.feicui.android.yitao.Model.UpShop;
import com.feicui.android.yitao.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30.
 *
 */
public class UpShopActivity extends AppCompatActivity {
    @Bind(R.id.tb_goods)
    Toolbar toolbar;
    @Bind(R.id.rv_upshop)
    RecyclerView recyclerView;

    private UpAdapter adapter;
    private ArrayList<MyCameraentry> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upshop);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        initView();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(UpShop.getList()!=null){
            list = UpShop.getList();
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Upshop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        adapter = new UpAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(UpShopActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void setMylist(UpShop event){
//        if(event.getList() != null){
//            this.list = event.getList();
//            adapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
