package com.magicsu.android.magicassistant.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.magicsu.android.magicassistant.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: PhoneQueryActivity
 * author: admin
 * date: 2018/2/1
 * description: 号码归属地查询
 */

public class PhoneQueryActivity extends BaseActivity {
    @BindViews({R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,
    R.id.btn_9,R.id.btn_del,R.id.btn_search})
    List<Button> buttons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_query);
        ButterKnife.bind(this);

        initView();

    }

    /**
     * 界面初始化
     */
    private void initView() {
    }

    @OnClick({R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,
            R.id.btn_9,R.id.btn_del,R.id.btn_search})
    void calcEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                calcOutput(view);
                break;
            case R.id.btn_del:
                break;
            case R.id.btn_search:
                calcExecute();
                break;
        }
    }

    /**
     * 键盘输入
     * @param view 当前控件
     */
    private void calcOutput(View view) {
        Button button = (Button) view;
        // TODO 这里执行输入操作
        String key = button.getText().toString();
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始执行查询
     */
    private void calcExecute() {

    }
}
