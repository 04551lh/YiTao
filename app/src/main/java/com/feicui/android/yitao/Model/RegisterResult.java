package com.feicui.android.yitao.Model;

/**
 * Created by Administrator on 2016/11/25.
 *
 */
public class RegisterResult {
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    private String msg;
    private UserEntry data;

    public UserEntry getUserEntry() {
        return data;
    }

    public void setUserEntry(UserEntry userEntry) {
        this.data = userEntry;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
