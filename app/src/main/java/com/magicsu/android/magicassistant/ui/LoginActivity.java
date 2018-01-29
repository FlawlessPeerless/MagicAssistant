package com.magicsu.android.magicassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.magicsu.android.magicassistant.MainActivity;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.User;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.SP;
import com.magicsu.android.magicassistant.view.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: LoginActivity
 * author: admin
 * date: 2018/1/29
 * description: 登录
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.button_login)
    Button mLoginButton;
    @BindView(R.id.button_register)
    Button mRegistButton;
    @BindView(R.id.button_forget_password)
    TextView mForgetPwdButton;

    private CustomDialog mCustomDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 保存状态
        SP.putBoolean(this, Constant.LOGIN_KEEP_PASSWORD, mCheckBox.isChecked());
        if (mCheckBox.isChecked()) {
            SP.putString(this, Constant.LOGIN_REMEMBER_USERNAME, mEditUsername.getText().toString().trim());
            SP.putString(this, Constant.LOGIN_REMEMBER_PASSWORD, mEditPassword.getText().toString().trim());
        } else {
            SP.delete(this, Constant.LOGIN_REMEMBER_USERNAME);
            SP.delete(this, Constant.LOGIN_REMEMBER_PASSWORD);
        }
    }

    /**
     * 页面初始化
     */
    private void initView() {
        boolean keepPassword = SP.getBoolean(this, Constant.LOGIN_KEEP_PASSWORD, false);
        mCheckBox.setChecked(keepPassword);
        if (keepPassword) {
            mEditUsername.setText(SP.getString(this, Constant.LOGIN_REMEMBER_USERNAME, ""));
            mEditPassword.setText(SP.getString(this, Constant.LOGIN_REMEMBER_PASSWORD, ""));
        }

        mCustomDialog = new CustomDialog(this,100,100, R.layout.dialog_loading, R.style.AppTheme_Dialog, Gravity.CENTER, R.style.pop_anim_style);
        mCustomDialog.setCancelable(false);
    }

    @OnClick(R.id.button_login)
    void clickLogin(View view) {
        String username = mEditUsername.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) & TextUtils.isEmpty(password)) {
            Toast.makeText(this, "输入框不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        mCustomDialog.show();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                // mCustomDialog.dismiss();
                if (e == null) {
                    // 成功
                    if (!user.getEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "请先验证邮箱", Toast.LENGTH_SHORT)
                            .show();
                        return;
                    }
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                } else {
                    // 失败
                    Toast.makeText(LoginActivity.this, "登录失败："+e.toString(), Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });
    }

    @OnClick(R.id.button_register)
    void clickRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    /**
     * 忘记密码
     */
    @OnClick(R.id.button_forget_password)
    void clickResetPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }
}

