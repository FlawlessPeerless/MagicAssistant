package com.magicsu.android.magicassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.magicsu.android.magicassistant.R;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: GestureLockActivity
 * author: admin
 * date: 2018/2/8
 * description: 手势解锁
 */

public class GestureLockActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);
    }
}
