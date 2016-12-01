package com.feicui.android.yitao.Model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/30.
 */
public class UpShop {
    private static ArrayList<MyCameraentry> list;

    public UpShop(ArrayList<MyCameraentry> list) {
        this.list = list;
    }

    public static ArrayList<MyCameraentry> getList() {
        return list;
    }

    public void setList(ArrayList<MyCameraentry> list) {
        this.list = list;
    }
}
