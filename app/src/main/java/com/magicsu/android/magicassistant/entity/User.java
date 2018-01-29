package com.magicsu.android.magicassistant.entity;

import cn.bmob.v3.BmobUser;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.entity
 * file: User
 * author: admin
 * date: 2018/1/29
 * description: 用户实体类
 */

public class User extends BmobUser {
    private int age;
    private boolean sex; // true为男 false为女
    private String description;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
