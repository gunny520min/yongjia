package com.yongjia.model;

public class WxMenu {

    private String type;
    private String name;
    private String key;
    private String url;
    private WxMenu sub_button;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WxMenu getSub_button() {
        return sub_button;
    }

    public void setSub_button(WxMenu sub_button) {
        this.sub_button = sub_button;
    }

}