package com.feicui.android.yitao.Presentation.main.MySelf;

import com.feicui.android.yitao.Base.MvpView;
import com.feicui.android.yitao.Model.GoodsEntry;
import com.feicui.android.yitao.Model.UserEntry;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/24.
 *
 */
public interface MyselfView extends MvpView {

    void addData(UserEntry entry);

    void addMoreData(ArrayList<GoodsEntry> list);

    void addRefreshData(ArrayList<GoodsEntry> list);


    MyselfView NULL = new MyselfView() {
        @Override
        public void addData(UserEntry entry) {

        }

        @Override
        public void addMoreData(ArrayList<GoodsEntry> list) {

        }

        @Override
        public void addRefreshData(ArrayList<GoodsEntry> list) {

        }
    };
}
