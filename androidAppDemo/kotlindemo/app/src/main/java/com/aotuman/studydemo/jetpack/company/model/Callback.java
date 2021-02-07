package com.aotuman.studydemo.jetpack.company.model;

public interface Callback<T> {

    public void onSuccess(T t);

    public void onFailed(String msg);
}