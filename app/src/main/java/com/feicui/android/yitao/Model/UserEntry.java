package com.feicui.android.yitao.Model;

/**
 * Created by Administrator on 2016/11/18.
 */
public class UserEntry {
    private String username;
    private String password;
    private String name;
    private String uuid;
    private String other;

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    private String nickname;

    public String getUsernname() {
        return username;
    }

    public void setUsernname(String usernname) {
        this.username = usernname;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
