package com.feicui.android.yitao.Presentation.main.MySelf.MyInformation;

import android.annotation.SuppressLint;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Model.UserEntry;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfPresentator;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfView;
import com.feicui.android.yitao.R;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/24.
 */
public class RegisterActivity extends AppCompatActivity implements MyselfView, View.OnClickListener {
    @Bind(R.id.tb_goods)
    Toolbar tb_goods;
    TextView camera;
    TextView graph;
    TextView exit;
    /**
     * 第二次输入密码的文本框
     */
    private EditText pwd2;
    /**
     * 注册按钮
     */
    private Button register;
    /**
     * 昵称文本框
     */
    private EditText name;
    /**
     * 手机号
     */
    private EditText phoneNum;
    /**
     * 第一次输入密码文本框
     */
    private EditText pwd1;
    @Bind(R.id.portrait)
    ImageView portrait;

    private PopupWindow pp;
    private Intent intent;
    private File file;

    private Bitmap firstBitmap, oldeBitmap;

    private MyselfPresentator presentator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
        presentator.attachView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void init() {
        setSupportActionBar(tb_goods);
        getSupportActionBar().setTitle(R.string.Register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        oldeBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.camera);
        portrait.setImageBitmap(oldeBitmap);
        presentator = new MyselfPresentator();
        initPP();
        name = (EditText) findViewById(R.id.register_name);
        phoneNum = (EditText) findViewById(R.id.register_phoneNum);
        pwd1 = (EditText) findViewById(R.id.register_pwd1);
        register = (Button) findViewById(R.id.register_register);
        pwd2 = (EditText) findViewById(R.id.register_pwd2);
        pwd2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.length() > 0) {
                    // 使注册按钮变为可点击
                    register.setEnabled(true);
                    // 修改背景
                    register.setBackground(getResources().getDrawable(
                            R.drawable.login_button_select));
                } else {
                    // 不可以点击
                    register.setEnabled(false);
                    // 修改背景
                    register.setBackground(getResources().getDrawable(
                            R.drawable.login_button_click_before));
                }
            }
        });
        portrait.setOnClickListener(this);
        camera.setOnClickListener(this);
        graph.setOnClickListener(this);
        exit.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register:
                pattern();
                break;
            case R.id.portrait:
                if(pp != null && pp.isShowing()){
                    pp.dismiss();
                }
                else{
                    pp.showAtLocation(findViewById(R.id.ll_pp), Gravity.BOTTOM, 10, 10);
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
                portrait.setImageBitmap(firstBitmap);
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
            return RegisterActivity.this;
        }
    };
    /**
     * 清空所有editText
     */
    private void initEditText() {
        name.setText("");
        phoneNum.setText("");
        pwd1.setText("");
        pwd2.setText("");
    }

    /**
     * 判断逻辑
     */
    private void pattern() {
        // 获取昵称文本框里的字符串
        String name = this.name.getText().toString();
        // 获取手机号
        String phoneNum = this.phoneNum.getText().toString();
        // 获取密码
        String pwd1 = this.pwd1.getText().toString();
        // 获取第二次的密码
        String pwd2 = this.pwd2.getText().toString();
        // 约束条件
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(name);
        if (m.find()) {
            // //d是0-9的数字 {10}里面表示0-9之间的任意一个数字显示10次
            // 判断是否为1开头的11为数字
            if (phoneNum.matches("\\w{6,}")) {
                // //w 包括 0-9A-Za-z_
                // 在{}里面需要多少位以上，只需要加一个"，"
                if (pwd1.matches("\\w{6,}")) {
                    // 两次输入密码是否一致
                    if (pwd1.equals(pwd2)) {
                        if(firstBitmap == null || firstBitmap == oldeBitmap){
                            Toast.makeText(this, "请添加头像", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            presentator.getRegister(phoneNum, pwd1, name, file);
                            Intent intent = new Intent(RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        initEditText();
                        Toast.makeText(RegisterActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    initEditText();
                    Toast.makeText(RegisterActivity.this,
                            "密码不能小于6位，必须是数字、字母、下划线！", Toast.LENGTH_SHORT).show();
                }
            } else {
                initEditText();
                Toast.makeText(RegisterActivity.this, "用户名不能小于6位，必须是数字、字母、下划线！", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            initEditText();
            Toast.makeText(RegisterActivity.this, "昵称必须为中文！", Toast.LENGTH_SHORT).show();
        }

    }

    private void initPP(){
        pp = new PopupWindow();
        View view =getLayoutInflater().inflate(R.layout.pp, null);
        pp.setContentView(view);
        pp.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pp.setFocusable(true);
        pp.setBackgroundDrawable(new BitmapDrawable());
        camera = (TextView) view.findViewById(R.id.camera);
        graph = (TextView) view.findViewById(R.id.graph);
        exit = (TextView) view.findViewById(R.id.exit);
    }
    @Override
    public void addData(UserEntry entry) {
        if(entry != null){

        }
        else{

        }
    }

    @Override
    public void addMoreData(ArrayList<GoodsEntry> list) {

    }

    @Override
    public void addRefreshData(ArrayList<GoodsEntry> list) {

    }

}
