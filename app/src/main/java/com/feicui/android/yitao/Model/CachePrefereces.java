package com.feicui.android.yitao.Model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/24.
 */
public class CachePrefereces {

    public static final String NAME = "Login";
    private static final String USR_NAME = "username";
    private static final String PWD = "password";
    private static final String HX_ID = "hx_id";
    private static final String UUID = "uuid";
    private static final String IMAGE = "portrait";
    private static final String NICKNAME = "nickname";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private CachePrefereces(){};

    public static void init(Context context){
        preferences = context.getSharedPreferences(NAME, Context.MODE_APPEND);
        editor = preferences.edit();
    }
    public static void cleanAllData(){
        editor.clear();
        editor.apply();
    }
    public static void setUser(UserEntry user){
        editor.putString(USR_NAME, user.getUsernname());
        editor.putString(PWD, user.getOther());
        editor.putString(UUID, user.getUuid());
        editor.putString(IMAGE, user.getOther());
        editor.putString(HX_ID, user.getName());
        editor.putString(NICKNAME, user.getNickname());
        editor.apply();
    }
    public static UserEntry getUserEntry(){
        UserEntry entry = new UserEntry();
        entry.setUsernname(preferences.getString(USR_NAME, null));
        entry.setPassword(preferences.getString(PWD, null));
        entry.setOther(preferences.getString(IMAGE, null));
        entry.setName(preferences.getString(HX_ID, null));
        entry.setUuid(preferences.getString(UUID, null));
        entry.setNickname(preferences.getString(NICKNAME, null));
        return entry;
    }
}
