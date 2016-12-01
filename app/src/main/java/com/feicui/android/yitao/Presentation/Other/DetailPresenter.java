package com.feicui.android.yitao.Presentation.Other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.feicui.android.yitao.Base.MvpPresenter;
import com.feicui.android.yitao.Model.DeleteEntry;
import com.feicui.android.yitao.Model.DetailEntry;
import com.feicui.android.yitao.Model.DetailResult;
import com.feicui.android.yitao.NetWork.ShopClient;
import com.feicui.android.yitao.NetWork.UICallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/18.
 */
public class DetailPresenter extends MvpPresenter<DetailView> {
    private Call call;

    public void getData(String uuid){
        call = ShopClient.getInstance().getDetailData(uuid);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String context) {
                DetailResult result = new Gson().fromJson(context, DetailResult.class);
                if(result.getCode() == 1){
                    DetailEntry detailEntry = result.getDatas();
                    Log.i("yzg", context);
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < detailEntry.getList().size(); i++) {
                        list.add(detailEntry.getList().get(i).getUri());
                    }
                    getView().setImageData(list);
                    getView().setData(detailEntry);
                }
            }
        });
    }

    public void Delete(String uuid){
        call = ShopClient.getInstance().Delete(uuid);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String context) {
                DeleteEntry deleteEntry = new Gson().fromJson(context, DeleteEntry.class);
                switch (deleteEntry.getCode()){
                    case 1:
                        Toast.makeText((Context) getView(), deleteEntry.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText((Context) getView(), deleteEntry.getMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @NonNull
    @Override
    protected DetailView getNullObject() {
        return DetailView.NULL;
    }

    @Override
    public void detachView() {
        super.detachView();
        if(call != null){
            call.cancel();
        }
    }
}
