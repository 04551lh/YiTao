package com.feicui.android.yitao.Presentation.main.Shop;

import android.support.annotation.NonNull;

import com.feicui.android.yitao.Base.MvpPresenter;
import com.feicui.android.yitao.Model.GoodsResult;
import com.feicui.android.yitao.NetWork.ShopClient;
import com.feicui.android.yitao.NetWork.UICallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/17.
 *
 */
public class ShopPresentater extends MvpPresenter<ShopView> {
    @NonNull
    @Override
    protected ShopView getNullObject() {
        return ShopView.NULL;
    }

    private Call call;
    private int pageInt = 1;
    public void refreshData(int pageNo, String type){
        call = ShopClient.getInstance().getData(pageNo, type);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
            }

            @Override
            public void onResponseUI(Call call, String context) {
                GoodsResult result = new Gson().fromJson(context, GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        getView().addRefreshData(result.getDatas());
                        getView().shutData();
                        pageInt = 2;
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    public void getMoreData(String type){
        call = ShopClient.getInstance().getData(pageInt, type);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
            }

            @Override
            public void onResponseUI(Call call, String context) {
                GoodsResult result = new Gson().fromJson(context, GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        getView().addMoreData(result.getDatas());
                        getView().shutData();
                        pageInt ++;
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        if(call != null){
            call.cancel();
        }
    }
}
