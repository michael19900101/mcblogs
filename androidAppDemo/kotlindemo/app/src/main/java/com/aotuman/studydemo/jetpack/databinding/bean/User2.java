package com.aotuman.studydemo.jetpack.databinding.bean;


public class User2{

    private String name;
    private String level;
    private String avatar;


    public User2(String name, String level) {
        this.name = name;
        this.level = level;
        avatar = "https://avatars2.githubusercontent.com/u/20123477?s=400&u=3ea5988670fd59076aecd1f4030ba7ca51402982&v=4";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) return;
        if (name.equals(this.name)) {
            //解决双向绑定的死循环：数据没变就return'
            return;
        }
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}