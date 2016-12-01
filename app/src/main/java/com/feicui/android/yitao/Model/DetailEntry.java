package com.feicui.android.yitao.Model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/18.
 */
public class DetailEntry {
    private String uuid;
    private String name;
    private String type;
    private String price;
    private String description;
    private String master;
    private ArrayList<ImageUrl> pages;


    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getMaster() {
        return master;
    }

    public ArrayList<ImageUrl> getList() {
        return pages;
    }

    public class  ImageUrl{
        private String uri;

        public String getUri() {
            return uri;
        }
    }
}
