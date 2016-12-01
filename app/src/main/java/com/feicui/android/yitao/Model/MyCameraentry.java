package com.feicui.android.yitao.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/30.
 *
 */
public class MyCameraentry implements Serializable {
    private String name;
    private String path;

    private int position;
    private boolean isSelect;
    private boolean isdelect;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isdelect() {
        return isdelect;
    }

    public void setIsdelect(boolean isdelect) {
        this.isdelect = isdelect;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
