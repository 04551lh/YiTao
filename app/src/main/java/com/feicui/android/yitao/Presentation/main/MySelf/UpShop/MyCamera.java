package com.feicui.android.yitao.Presentation.main.MySelf.UpShop;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.MyCameraentry;
import com.feicui.android.yitao.R;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30
 *
 */
public class MyCamera extends AppCompatActivity implements MyCameraAdapter.onCheckListener {
    @Bind(R.id.rv_mycamera)
    RecyclerView recyclerView;
    @Bind(R.id.rv_mycamera_select)
    RecyclerView recyclerView_select;
    @Bind(R.id.tb_goods)
    Toolbar toolbar;
    @Bind(R.id.mycamera_done)
    TextView done;
    @Bind(R.id.mycamera_count)
    TextView count;
    @Bind(R.id.rl_mycamera)
    RelativeLayout mycamera;
    @Bind(R.id.ll_finish)
    LinearLayout finish;


    private static final int REQUEST_CODE_CAMERA = 100;

    private ArrayList<MyCameraentry> list;
    private ArrayList<MyCameraentry> showList;
    private MyCameraentry cameraentry;
    private MyCameraAdapter adapter;
    private MyCameraSelect cameraAdapter;
    private File photoFile;
    private Uri photoUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycamera);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.Picture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        getImages();

    }
    private void initView(){
        list = new ArrayList<>();
        showList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView_select.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MyCameraAdapter();
        adapter.setOnCheckListener(this);
        cameraAdapter = new MyCameraSelect(list);
//        recyclerView_select.setAdapter(cameraAdapter);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyCamera.this, "dianji", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImages(){
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] mProjection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        };
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED;
        Cursor cursor = getContentResolver().query(uri, mProjection, null, null, sortOrder+" DESC");
        if(cursor != null && cursor.getCount() > 0){
            int id_id = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int idName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            int idData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            do{
                cameraentry = new MyCameraentry();
                cameraentry.setName(cursor.getString(idName));
                cameraentry.setPath(cursor.getString(idData));
                list.add(cameraentry);
            }while (cursor.moveToNext());
            cursor.close();
        }
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
    }


    public void updataDonetrue(){
        count.setTextColor(getResources().getColor(R.color.whitesmoke));
        done.setTextColor(getResources().getColor(R.color.whitesmoke));
        finish.setEnabled(true);
    }

    public void updataDonefalse(){
        count.setTextColor(getResources().getColor(R.color.text_color_hint));
        done.setTextColor(getResources().getColor(R.color.text_color_hint));
        finish.setEnabled(false);
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
    }

    @Override
    public void toCameraClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        String systemPath = Environment.getExternalStorageDirectory() + File.separator + "easyshopphoto";
        File file = new File(systemPath);
        if (!file.exists()) {
            file.mkdir();
        }
        photoFile = new File(systemPath, fileName
                + ".jpg");
        photoUri = Uri.fromFile(photoFile);
        // 把拍照之后的图片保存到指定路径下
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 相机响应
     */
    private void cameraResult() {
        // 通知系统,更新系统图库数据库
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoUri));
        cameraentry = new MyCameraentry();
        cameraentry.setPath(photoFile.getAbsolutePath());
        cameraentry.setName(photoFile.getName());
        cameraentry.setPosition(0);
        if (showList.size() < 8) {
            cameraentry.setSelect(true);
            updataDonetrue();
            showList.add(cameraentry);
            count.setText(showList.size() + "/8");
        } else
        list.add(0, cameraentry);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && Activity.RESULT_OK == resultCode) {
            cameraResult();
        }
    }
    @Override
    public void onCheck(MyCameraentry myCameraentry, CheckBox checkBox, int position) {
        if(showList.size()<8){
            if(myCameraentry.isSelect()){
                updataDonetrue();
                showList.add(myCameraentry);
            }else{
                showDataDelete(myCameraentry);
            }
            cameraAdapter.notifyItemChanged(showList.size());

        }
        else{
            if(showDataDelete(myCameraentry)){
                checkBox.setChecked(false);
            }
        }
        if (showList.size() == 0){
            updataDonefalse();
        }
        count.setText(showList.size()+"/8");
    }


    public boolean showDataDelete(MyCameraentry myCameraentry){
        for (int i = 0; i < showList.size(); i++) {
            if (showList.get(i).getName().equals(myCameraentry.getName())){
                showList.remove(i);
                cameraAdapter.notifyDataSetChanged();
                return false;
            }
        }
        return true;
    }

    @Override
    public void imageOnClick(int positon) {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("photo", (Serializable) list);
        intent.putExtra("position",String.valueOf(positon));
        startActivity(intent);
    }
}
