package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.feicui.android.yitao.Model.MyCameraentry;
import com.feicui.android.yitao.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/1.
 *
 */
public class ImageDetailActivity extends AppCompatActivity {
    @Bind(R.id.tb_goods)
    Toolbar toolbar;
    @Bind(R.id.vp_image)
    ViewPager viewPager;

    private ArrayList<MyCameraentry> list;
    private ArrayList<ImageView> viewList;;
    private ImageAdapter adapter;
    private int position;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedetail);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("图片信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = new ArrayList<>();
        viewList = new ArrayList<>();
        list = (ArrayList<MyCameraentry>) this.getIntent().getSerializableExtra("photo");
        position = Integer.valueOf(this.getIntent().getStringExtra("position"));
        ImageView image = viewList.get(position);
        viewList.remove(position);
        viewList.add(position, viewList.get(0));
        viewList.add(0, image);
        adapter = new ImageAdapter(list, this);
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
