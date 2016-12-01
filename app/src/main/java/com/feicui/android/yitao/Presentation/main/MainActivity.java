package com.feicui.android.yitao.Presentation.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.CachePrefereces;
import com.feicui.android.yitao.Model.Login;
import com.feicui.android.yitao.Presentation.main.Contrats.ContratsFragment;
import com.feicui.android.yitao.Presentation.main.Contrats.NoFragment;
import com.feicui.android.yitao.Presentation.main.Message.MessageFragment;
import com.feicui.android.yitao.Presentation.main.Message.NotFragment;
import com.feicui.android.yitao.Presentation.main.MySelf.MySelfFragment;
import com.feicui.android.yitao.Presentation.main.Shop.ShopFragment;
import com.feicui.android.yitao.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.vp_main)
    ViewPager viewPager;
    @Bind(R.id.tv_title)
    TextView title;
    @Bind({R.id.tv_shop, R.id.tv_message, R.id.tv_people, R.id.tv_identity})
    TextView[] tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestPermission();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title.setText("市场");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(CachePrefereces.getUserEntry().getName()==null){
            viewPager.setAdapter(fragmentPagerAdapter);
        }
    }

    private void initView(){
        tv[0].setSelected(true);
        tv[0].setTextColor(getResources().getColor(R.color.colorPrimary));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tv.length; i++) {
                    tv[i].setSelected(false);
                    tv[i].setTextColor(getResources().getColor(R.color.black));
                }
                tv[position].setSelected(true);
                tv[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                title.setText(tv[position].getText());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(fragmentPagerAdapter);
    }

    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_people, R.id.tv_identity})
    public void onClick(View view){
        for (int i = 0; i < tv.length; i++) {
            tv[i].setSelected(false);
            tv[i].setTextColor(getResources().getColor(R.color.black));
            tv[i].setTag(i);
        }
        view.setSelected(true);
        ((TextView)view).setTextColor(getResources().getColor(R.color.colorPrimary));
        viewPager.setCurrentItem((Integer) view.getTag(), true);
        title.setText(tv[(int) view.getTag()].getText());
    }
    private FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ShopFragment();
                case 1:
                    return new NoFragment();
                case 2:
                    return new NotFragment();
                case 3:
                    return new MySelfFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tv.length;
        }
    };

    private FragmentPagerAdapter loginPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ShopFragment();
                case 1:
                    return new MessageFragment();
                case 2:
                    return new ContratsFragment();
                case 3:
                    return new MySelfFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tv.length;
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLogin(Login event){
        if(event.getLogin().equals("login")){
            viewPager.setAdapter(loginPagerAdapter);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission_group.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"申请权限成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    finish();
                }
                break;
        }
    }
}
