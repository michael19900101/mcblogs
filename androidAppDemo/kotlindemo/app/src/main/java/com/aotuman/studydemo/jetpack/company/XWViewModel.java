package com.aotuman.studydemo.jetpack.company;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

// todo 不是Jetpack ViewModel类可以拥有LiveData类型的成员变量吗？这样还可以观察到lifeOwner吗？
public class XWViewModel {
    private String code;
    private String type;
    private int width;
    private int height;

    // viewModel里可能不止一种数据要被观察
    private List<MutableLiveData<Object>> liveDataList = new ArrayList<>();

    public List<MutableLiveData<Object>> getLiveDataList() {
        return liveDataList;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
