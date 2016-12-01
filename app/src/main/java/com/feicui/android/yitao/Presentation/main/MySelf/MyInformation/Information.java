package com.feicui.android.yitao.Presentation.main.MySelf.MyInformation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.feicui.android.yitao.Model.CachePrefereces;
import com.feicui.android.yitao.Model.GetBitmap;
import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Model.UserEntry;
import com.feicui.android.yitao.NetWork.ShopAPI;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfPresentator;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfView;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/24.
 *
 */
public class Information extends AppCompatActivity implements View.OnClickListener, MyselfView {
    @Bind(R.id.iv_show)
    CircularImageView image;
    @Bind(R.id.tv_show_nick)
    TextView nick;
    @Bind(R.id.tv_show_name)
    TextView name;
    @Bind(R.id.exit_login)
    Button exit;
    @Bind(R.id.rename)
    Button rename;
    @Bind(R.id.tb_goods)
    Toolbar toolbar;

    private PopupWindow pp;

    private TextView camera;
    private TextView graph;
    private TextView tv_exit;
    private Intent intent;
    private MyselfPresentator presentator;

    private File file;
    private Bitmap firstBitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        presentator = new MyselfPresentator();
        presentator.onCreate();
        presentator.attachView(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Self_Infoemation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageLoader.getInstance().displayImage(ShopAPI.IMAGE_URL+ CachePrefereces.getUserEntry().getOther(), image, ImageLoadOptions.build_item());
        nick.setText(CachePrefereces.getUserEntry().getNickname());
        name.setText(CachePrefereces.getUserEntry().getUsernname());
        initPP();
        image.setOnClickListener(this);
        exit.setOnClickListener(this);
        rename.setOnClickListener(this);
        camera.setOnClickListener(this);
        graph.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_login:
                CachePrefereces.cleanAllData();
                finish();
                break;
            case R.id.rename:
                startActivity(new Intent(Information.this, Rename.class));
                break;
            case R.id.iv_show:
                if(pp != null && pp.isShowing()){
                    pp.dismiss();
                }
                else{
                    pp.showAtLocation(findViewById(R.id.ll_pop), Gravity.BOTTOM, 10, 10);
                    pp.setOutsideTouchable(true);
                }
                break;
            case R.id.camera:
                CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
                intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                break;
            case R.id.graph:
                CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
                intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
                startActivityForResult(intent, CropHelper.REQUEST_CROP);
                break;
            case R.id.exit:
                pp.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            try {
                file = new File(uri.getPath());
                firstBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                image.setImageBitmap(firstBitmap);
                new GetBitmap(firstBitmap);
                presentator.getImage(file);
                pp.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 80;
            cropParams.aspectY = 80;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return Information.this;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if(CachePrefereces.getUserEntry().getNickname()!=null){
            nick.setText(CachePrefereces.getUserEntry().getNickname());
        }
        if(GetBitmap.getBitmap()!=null){
            image.setImageBitmap(GetBitmap.getBitmap());
        }
    }

    private void initPP(){
        pp = new PopupWindow();
        View view = getLayoutInflater().inflate(R.layout.pp, null);
        pp.setContentView(view);
        pp.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pp.setFocusable(true);
        pp.setBackgroundDrawable(new BitmapDrawable());
        camera = (TextView) view.findViewById(R.id.camera);
        graph = (TextView) view.findViewById(R.id.graph);
        tv_exit = (TextView) view.findViewById(R.id.exit);
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
        presentator.onDestry();
    }

    @Override
    public void addData(UserEntry entry) {

    }

    @Override
    public void addMoreData(ArrayList<GoodsEntry> list) {

    }

    @Override
    public void addRefreshData(ArrayList<GoodsEntry> list) {

    }
}
