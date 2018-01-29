package com.magicsu.android.magicassistant.application;

import android.app.Application;

import com.magicsu.android.magicassistant.util.Constant;

import cn.bmob.v3.Bmob;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.application
 * file: BaseApplication
 * author : admin
 * date: 2018/1/26
 * description: application 基类
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Bmob
        Bmob.initialize(this, Constant.BMOB_APP_ID);
    }
}
