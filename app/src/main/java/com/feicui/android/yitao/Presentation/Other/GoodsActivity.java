package com.feicui.android.yitao.Presentation.Other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.DetailEntry;
import com.feicui.android.yitao.NetWork.ShopAPI;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/11/18.
 */
public class GoodsActivity extends AppCompatActivity implements DetailView {
    private static final String UUID = "uuid";
    private static final String STATES = "state";
    private String uuid;
    private String state = "";
    private ArrayList<String> urlList;
    private ArrayList<ImageView> imageList;
    private DetailAdapter adapter;
    private DetailPresenter presenter;

    @Bind(R.id.good_toolbar)
    Toolbar tb_goods;
    @Bind(R.id.vp_goods)
    ViewPager vp_goods;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.tv_detail_type)
    TextView tv_detail_type;
    @Bind(R.id.tv_detail_name)
    TextView tv_detail_name;
    @Bind(R.id.tv_detail_price)
    TextView tv_detail_price;
    @Bind(R.id.tv_detail_master)
    TextView tv_detail_master;
    @Bind(R.id.tv_detail_description)
    TextView tv_detail_description;
    @Bind(R.id.bt_goods)
    Button bt_goods;
    @Bind(R.id.good_tv_title)
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
    }

    public static Intent getStartIntent(Context context, String uuid, String state){
        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(UUID, uuid);
        intent.putExtra(STATES, state);
        return intent;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(tb_goods);
        getSupportActionBar().setTitle(R.string.goods_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageList = new ArrayList<>();
        urlList = new ArrayList<>();
        presenter = new DetailPresenter();
        presenter.attachView(this);
        adapter = new DetailAdapter(imageList);
        initView();
        vp_goods.setAdapter(adapter);
    }

    private void initView(){
        uuid = getIntent().getStringExtra(UUID);
        state = getIntent().getStringExtra(STATES);
        presenter.getData(uuid);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GoodsActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                presenter.Delete(uuid);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        select();
    }

    private void select(){
        Log.i("state", state+"");
        if(state .equals("my")){
            textView.setVisibility(View.VISIBLE);
            bt_goods.setVisibility(View.GONE);
        }
    }
    private void setImages(ArrayList<String> list){
        for (int i = 0; i < list.size(); i++) {
            ImageView view = new ImageView(this);
            ImageLoader.getInstance().displayImage(
                    ShopAPI.IMAGE_URL + list.get(i),
                    view, ImageLoadOptions.build_item()
            );
            imageList.add(view);
        }
    }

    @Override
    public void setData(DetailEntry data) {
        tv_detail_name.setText(data.getName());
        tv_detail_type.setText(data.getType());
        tv_detail_price.setText(data.getPrice());
        tv_detail_description.setText(data.getDescription());
        tv_detail_master.setText(data.getMaster());
    }

    @Override
    public void setImageData(ArrayList<String> list) {
        urlList = list;
        setImages(urlList);
        adapter.notifyDataSetChanged();
        indicator.setViewPager(vp_goods);
    }

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
        presenter.detachView();
    }
}
