package com.yongjia.model;

import java.util.List;

public class CarModelParam {
    private String name;
    private List<CarModelItemParam> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModelItemParam> getValue() {
        return value;
    }

    public void setValue(List<CarModelItemParam> value) {
        this.value = value;
    }

}