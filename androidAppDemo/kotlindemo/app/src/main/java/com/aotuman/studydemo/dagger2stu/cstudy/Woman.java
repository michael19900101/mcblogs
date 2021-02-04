package com.aotuman.studydemo.dagger2stu.cstudy;

//女人拥有灵魂
public class Woman {
    private Soul soul;

    public Soul getSoul() {
        return soul;
    }

    public Woman(Soul soul) {
        this.soul = soul;
    }
}