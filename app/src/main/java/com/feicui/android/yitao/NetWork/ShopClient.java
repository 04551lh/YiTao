package com.feicui.android.yitao.NetWork;

import com.feicui.android.yitao.Model.UserEntry;
import com.feicui.android.yitao.Model.UserUp;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2016/11/16.
 * @description 用户访问网络数据
 */
public class ShopClient {
    private OkHttpClient okHttpClient;

    private static ShopClient shopClient;


    private ShopClient(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static ShopClient getInstance(){
        if(shopClient == null){
            shopClient = new ShopClient();
        }
        return shopClient;
    }

    public Call getData(int pageNo, String type){
        RequestBody requestBody = new FormBody.Builder()
                .add("pageNo", String.valueOf(pageNo))
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL + ShopAPI.ALL_GOODS)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getMyShopData(int pageNo, String type, String master){
        RequestBody requestBody = new FormBody.Builder()
                .add("pageNo", String.valueOf(pageNo))
                .add("type", type)
                .add("master", master)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL + ShopAPI.ALL_GOODS)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getDetailData(String uuid){
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid", uuid)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL + ShopAPI.DETAIL)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getLogin(String name, String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", name)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL + ShopAPI.LOGIN)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getRegister(String name, String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", name)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL + ShopAPI.REGISTER)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getUpData(UserEntry userEntry, File file){
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("user", new Gson().toJson(userEntry))
                .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL +ShopAPI.UPDATE)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getUpImage(File file){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL +ShopAPI.UPDATE)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call getData(UserUp userEntry){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", new Gson().toJson(userEntry))
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL +ShopAPI.UPDATE)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call Delete(String uuid){
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid", uuid)
                .build();
        Request request = new Request.Builder()
                .url(ShopAPI.BASE_URL + ShopAPI.DELETE)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }

}
