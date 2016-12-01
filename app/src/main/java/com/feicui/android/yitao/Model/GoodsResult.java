package com.feicui.android.yitao.Model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/18.
 */
public class GoodsResult {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public ArrayList<GoodsEntry> getDatas() {
        return datas;
    }


    private ArrayList<GoodsEntry> datas;

}
