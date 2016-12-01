package com.feicui.android.yitao.Model;

/**
 * Created by Administrator on 2016/11/18.
 *
 */
public class DetailResult {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    public DetailEntry getDatas() {
        return datas;
    }

    public void setDatas(DetailEntry datas) {
        this.datas = datas;
    }

    private DetailEntry datas;
}
