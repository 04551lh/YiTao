package com.feicui.android.yitao.Model;

/**
 * Created by Administrator on 2016/11/30.
 */
public class UserUp {
    private String username;
    public UserUp(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    private String nickname;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
