package com.feicui.android.yitao.Base;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2016/11/17.
 */
public abstract class MvpPresenter <V extends MvpView>{
    private V view;

    public void onCreate(){

    }

    public void attachView(V view){
        this.view = view;
    }

    public V getView(){
        return view;
    }

    public void detachView(){
        this.view = getNullObject();
    }

    public void onDestry(){}

    protected abstract @NonNull
    V getNullObject();
}
