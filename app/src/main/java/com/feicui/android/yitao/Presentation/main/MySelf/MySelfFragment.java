package com.feicui.android.yitao.Presentation.main.MySelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui.android.yitao.Model.CachePrefereces;
import com.feicui.android.yitao.Model.GetBitmap;
import com.feicui.android.yitao.NetWork.ShopAPI;
import com.feicui.android.yitao.Presentation.main.MySelf.MyInformation.Information;
import com.feicui.android.yitao.Presentation.main.MySelf.MyInformation.LoginActivity;
import com.feicui.android.yitao.Presentation.main.MySelf.UpShop.UpShopActivity;
import com.feicui.android.yitao.R;
import com.feicui.android.yitao.Tools.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/17.
 * @description 自己
 */
public class MySelfFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.iv_my_login)
    CircularImageView iv_login;
    @Bind(R.id.tv_my_login)
    TextView tv_login;
    @Bind(R.id.tv_my_register)
    TextView tv_register;
    @Bind(R.id.tv_information)
    TextView tv_infor;
    @Bind(R.id.tv_MyShop)
    TextView tv_Myshop;
    @Bind(R.id.tv_up)
    TextView tv_up;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myself, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(CachePrefereces.getUserEntry().getName()!=null){
            tv_login.setText(CachePrefereces.getUserEntry().getNickname());
            tv_register.setVisibility(View.GONE);
            if(GetBitmap.getBitmap()!=null){
                iv_login.setImageBitmap(GetBitmap.getBitmap());
            }
            else{
                ImageLoader.getInstance().displayImage(ShopAPI.IMAGE_URL+
                        CachePrefereces.getUserEntry().getOther(), iv_login, ImageLoadOptions.build_item());

            }
        }
    }


    private void initView(){
        iv_login.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_infor.setOnClickListener(this);
        tv_Myshop.setOnClickListener(this);
        tv_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(CachePrefereces.getUserEntry().getName()!= null){
            switch (v.getId()){
                case R.id.iv_my_login:
                case R.id.tv_information:
                case R.id.tv_my_login:
                    Intent intent = new Intent(getContext(), Information.class);
                    startActivity(intent);
                    break;
                case R.id.tv_MyShop:
                    startActivity(new Intent(getActivity(), MyShop.class));
                    break;
                case R.id.tv_up:
                    startActivity(new Intent(getActivity(), UpShopActivity.class));
                    break;
            }
        }
        else{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

}
