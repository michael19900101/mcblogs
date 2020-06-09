package com.aotuman.studydemo.proxy.staticproxy;

// 目标对象：UserDao
public class UserDao implements IUserDao{

    @Override
    public void save() {
        System.out.println("保存数据");
    }
}