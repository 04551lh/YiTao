package com.feicui.android.yitao.Presentation.main.MySelf;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.CachePrefereces;
import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Model.UserEntry;
import com.feicui.android.yitao.Presentation.main.Shop.TitleViewHolder;
import com.feicui.android.yitao.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/28.
 *
 */
public class MyShop extends AppCompatActivity implements  MyselfView, MyshopAdapter.setOnLongClick {
    @Bind(R.id.myshop_toolbar)
    Toolbar toolbar;
    @Bind(R.id.myshop_title)
    ImageView title;
    @Bind(R.id.rv_myshop)
    RecyclerView rv_myshop;
    @Bind(R.id.myshop_tv_title)
    TextView tv_title;
    @Bind(R.id.ll_myshop_bt)
    LinearLayout myshop_bt;
    @Bind(R.id.ll_layout_myshop)
    RelativeLayout myshop_layout;
    @Bind(R.id.myshop_type)
    TextView type;

    private PopupWindow pp;
    private RecyclerView rv_pp;
    private MyselfPresentator presentator;
    private TitleViewHolder.TitleAdapter adapter;
    private MyshopAdapter myshopAdapter;
    private static int typeNo;
    private static String MASTER;
    private boolean isback;
    public static final String[]titles = {"", "household", "electron", "dress", "toy", "book", "gift", "other"};
    public final String[] myshop_CN = {"全部", "家用", "电子", "服饰", "玩具", "图书", "礼品", "其他"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshop);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        presentator = new MyselfPresentator();
        presentator.onCreate();
        presentator.attachView(this);
        MASTER = CachePrefereces.getUserEntry().getUsernname();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Myshop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myshopAdapter = new MyshopAdapter(myshop_layout);
        rv_myshop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initPP();
        presentator.getMyshopData(1, "", MASTER);
        rv_myshop.setAdapter(myshopAdapter);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pp != null && pp.isShowing()){
                    pp.dismiss();
                }
                else{
                    pp.showAtLocation(findViewById(R.id.myshop_toolbar), Gravity.BOTTOM, 10, 10);
                    pp.setOutsideTouchable(true);
                }
                Toast.makeText(MyShop.this,"title", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setListener(new TitleViewHolder.TitleAdapter.OnTextItemClickListener() {
            @Override
            public void onTitleked(int position) {
                typeNo = position;
                presentator.getMyshopData(1, titles[typeNo],MASTER);
                type.setText(myshop_CN[position]);
                pp.dismiss();
            }
        });
        myshopAdapter.setlListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    private void initPP(){
        pp = new PopupWindow();
        View view = getLayoutInflater().inflate(R.layout.pp_myshop, null);
        pp.setContentView(view);
        pp.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pp.setFocusable(true);
        pp.setBackgroundDrawable(new BitmapDrawable());
        rv_pp = (RecyclerView) view.findViewById(R.id.rv_pp);
        rv_pp.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new TitleViewHolder.TitleAdapter();
        rv_pp.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presentator.onDestry();
    }

    @Override
    public void addData(UserEntry entry) {

    }

    @Override
    public void addMoreData(ArrayList<GoodsEntry> list) {
        if(list!=null){
            myshopAdapter.addData(list);
        }
    }

    @Override
    public void addRefreshData(ArrayList<GoodsEntry> list) {
        if (list != null){
            myshopAdapter.clearData();
            myshopAdapter.addData(list);
        }

    }

    @Override
    public void setListener() {
        myshop_bt.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        tv_title.setVisibility(View.VISIBLE);
        isback =true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isback){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                myshop_bt.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.GONE);
                myshopAdapter.setisLong(false);
                isback = false;
            }
        }
        else{
            isback =  super.onKeyDown(keyCode, event);
        }
       return isback;
    }
}
