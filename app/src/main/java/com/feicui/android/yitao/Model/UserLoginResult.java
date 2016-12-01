package com.feicui.android.yitao.Model;

/**
 * Created by Administrator on 2016/11/24.
 */
public class UserLoginResult {
    private String code;
    private String msg;

    public UserEntry getData() {
        return data;
    }

    public void setData(UserEntry data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private UserEntry data;
}
