package com.magicsu.android.magicassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.User;
import com.magicsu.android.magicassistant.util.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: RegisterActivity
 * author: admin
 * date: 2018/1/29
 * description: 注册
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_age)
    EditText mEditAge;
    @BindView(R.id.edit_description)
    EditText mEditDesc;
    @BindView(R.id.radio_group_sex)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_button_male)
    RadioButton mRadioMale;
    @BindView(R.id.radio_button_female)
    RadioButton mRadioFemale;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_password_again)
    EditText mEditPasswordAgain;
    @BindView(R.id.edit_mailbox)
    EditText mEditMailBox;
    @BindView(R.id.button_register)
    Button mRegisterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
    }

    /**
     * 注册按钮点击事件
     * @param view 视图
     */
    @OnClick(R.id.button_register)
    void clickRegister(View view) {
        // 获取输入框的值
        String username = mEditUsername.getText().toString().trim();
        String age = mEditAge.getText().toString().trim();
        String description = mEditDesc.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        String passwordAgain = mEditPasswordAgain.getText().toString().trim();
        String mailBox = mEditMailBox.getText().toString().trim();
        boolean isMale = mRadioGroup.getCheckedRadioButtonId() == R.id.radio_button_male;

         // 判断是否为空
         if (TextUtils.isEmpty(description)) {
             description = "这个人很懒，什么也没有留下";
         }
         if (TextUtils.isEmpty(username)
             | TextUtils.isEmpty(age)
             | TextUtils.isEmpty(password)
             | TextUtils.isEmpty(passwordAgain)
             | TextUtils.isEmpty(mailBox)) {
                Toast.makeText(this, "输入内容不能为空",Toast.LENGTH_SHORT).show();
                return;
         }
         // 判断两次输入的密码是否一致
         if (!password.equals(passwordAgain)) {
             Toast.makeText(this, "两次输入的密码不一致",Toast.LENGTH_SHORT).show();
             return;
         }
         // 开始注册
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(mailBox);
        user.setAge(Integer.parseInt(age));
        user.setSex(isMale);
        user.setDescription(description);

        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    // 成功
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // 失败
                    Toast.makeText(RegisterActivity.this, "注册失败"+ e.toString(), Toast.LENGTH_SHORT).show();
                    L.e(e.toString());
                }
            }
        });


    }
}
