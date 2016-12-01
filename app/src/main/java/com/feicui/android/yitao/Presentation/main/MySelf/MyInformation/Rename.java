package com.feicui.android.yitao.Presentation.main.MySelf.MyInformation;

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

import com.feicui.android.yitao.Model.CachePrefereces;
import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Model.UserEntry;
import com.feicui.android.yitao.Model.UserUp;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfPresentator;
import com.feicui.android.yitao.Presentation.main.MySelf.MyselfView;
import com.feicui.android.yitao.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/28.
 *
 */
public class Rename extends AppCompatActivity implements MyselfView {

    @Bind(R.id.et_renames_nick)
    EditText nick;

    @Bind(R.id.rename_register)
    Button rename;

    @Bind(R.id.tb_goods)
    Toolbar toolbar;
    private UserUp userUp;

    private MyselfPresentator presentator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename);
        ButterKnife.bind(this);
        presentator = new MyselfPresentator();
        presentator.onCreate();
        initView();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("修改");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presentator.attachView(this);
        nick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                // 当文本框里面长度大于0的时候，登录按钮发生改变
                if (str.length() > 0) {
                    rename.setEnabled(true);
                    rename.setBackground(getResources().getDrawable(
                            R.drawable.login_button_select));
                } else {
                    rename.setEnabled(false);
                    rename.setBackground(getResources().getDrawable(
                            R.drawable.login_button_click_before));
                }
            }
        });
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nick.getText().toString();
                if(!nickname.equals(CachePrefereces.getUserEntry().getNickname())){
                    userUp = new UserUp(CachePrefereces.getUserEntry().getUsernname(),
                            nickname);
                    UserEntry userEntry = CachePrefereces.getUserEntry();
                    userEntry.setNickname(nickname);
                    CachePrefereces.setUser(userEntry);
                }
                presentator.getData(userUp);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
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
