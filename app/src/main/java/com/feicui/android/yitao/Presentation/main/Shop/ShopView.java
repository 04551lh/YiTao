package com.feicui.android.yitao.Presentation.main.Shop;

import com.feicui.android.yitao.Base.MvpView;
import com.feicui.android.yitao.Model.GoodsEntry;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/17.
 *
 */
public interface ShopView extends MvpView {

    void shutData();

    void addMoreData(ArrayList<GoodsEntry> list);

    void addRefreshData(ArrayList<GoodsEntry> list);

    void addErrorData();

    ShopView NULL = new ShopView() {
        @Override
        public void shutData() {

        }

        @Override
        public void addMoreData(ArrayList<GoodsEntry> list) {

        }


        @Override
        public void addRefreshData(ArrayList list) {

        }

        @Override
        public void addErrorData() {

        }
    };
}
