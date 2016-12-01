package com.feicui.android.yitao.Presentation.main.MySelf.MyInformation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Model.Login;
import com.feicui.android.yitao.Model.UserEntry;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfPresentator;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfView;
import com.feicui.android.yitao.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/24.
 *
 */
public class LoginActivity extends AppCompatActivity implements TextWatcher, MyselfView {
    /**
     * 用户名
     */
    @Bind(R.id.login_phoneNum)
    EditText userText;
    /**
     * 密码
     */
    @Bind(R.id.login_pwd)
    EditText pwdText;
    /**
     * 登录按钮
     */
    @Bind(R.id.login_login)
    Button login;
    /**
     * 找回密码
     */
    @Bind(R.id.login_findPwd)
    TextView findPwd;
    /**
     * 注册
     */
    @Bind(R.id.login_register)
    TextView register;

    @Bind(R.id.tb_goods)
    Toolbar tb_goods;

    private MyselfPresentator presentator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(tb_goods);
        getSupportActionBar().setTitle(R.string.Login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presentator = new MyselfPresentator();
        presentator.attachView(this);
        // 给editText添加文本改变事件
        pwdText.addTextChangedListener(this);
        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到注册Activity
                        startActivity(new Intent(LoginActivity.this,
                                RegisterActivity.class));
                    }
                });
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pattern();
            }
        });
        // 忘记密码
       findPwd.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到找回密码页面
                        Toast.makeText(LoginActivity.this, "找回密码！", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("NewApi")
    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        // 当文本框里面长度大于0的时候，登录按钮发生改变
        if (str.length() > 0) {
            login.setEnabled(true);
            login.setBackground(getResources().getDrawable(
                    R.drawable.login_button_select));
        } else {
            login.setEnabled(false);
            login.setBackground(getResources().getDrawable(
                    R.drawable.login_button_click_before));
        }
    }

    /**
     * 清空所有editText
     */
    private void initEditText() {
        userText.setText("");
        pwdText.setText("");
    }

    /**
     * 判断逻辑
     */
    private void pattern() {
        // 获取昵称文本框里的字符串
        String name = this.userText.getText().toString();
        // 获取密码
        String pwd = this.pwdText.getText().toString();

            if (name.matches("\\w{6,}")) {
                // //w 包括 0-9A-Za-z_
                // 在{}里面需要多少位以上，只需要加一个"，"
                if (pwd.matches("\\w{6,}")) {
                    presentator.getLoginData(name, pwd);
                    finish();
                    EventBus.getDefault().post(new Login("login"));
                    }
                else {
                        initEditText();
                        Toast.makeText(LoginActivity.this, "密码不能小于6位，必须是数字、字母、下划线！", Toast.LENGTH_SHORT)
                                .show();
                    }

            } else {
                initEditText();
                Toast.makeText(LoginActivity.this, "用户名不能小于6位，必须是数字、字母、下划线！", Toast.LENGTH_SHORT)
                        .show();
            }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
