package com.magicsu.android.magicassistant.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.service.SmsService;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.L;
import com.magicsu.android.magicassistant.util.SP;
import com.magicsu.android.magicassistant.util.UtilTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: SettingActivity
 * author: admin
 * date: 2018/1/26
 * description: 设置 activity
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.switch_sms)
    Switch mSmsSwitch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        boolean sms = SP.getBoolean(this, Constant.SETTING_SMS, false);
        mSmsSwitch.setChecked(sms);

    }
    @OnCheckedChanged(R.id.switch_sms)
    void smsSwitchClick() {
        boolean isChecked = mSmsSwitch.isChecked();
        Intent service = new Intent(this, SmsService.class);
        if (isChecked) {
            startService(service);
        } else {
            stopService(service);
        }
    }
}
