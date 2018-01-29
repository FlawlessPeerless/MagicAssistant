package com.magicsu.android.magicassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.User;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: ForgetPasswordActivity
 * author: admin
 * date: 2018/1/29
 * description: 忘记密码页面
 */

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.edit_password_now)
    EditText mEditPasswordNow;
    @BindView(R.id.edit_password_new)
    EditText mEditPasswordNew;
    @BindView(R.id.edit_password_again)
    EditText mEditPasswordAgain;
    @BindView(R.id.button_update_password)
    Button mUpdatePasswordButton;
    @BindView(R.id.edit_mailbox)
    EditText mEditEmail;
    @BindView(R.id.button_forget_password)
    Button mForgetPwdButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    /**
     * 忘记密码按钮
     */
    @OnClick(R.id.button_forget_password)
    void clickForgetPassword(View view) {
        String email = mEditEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgetPasswordActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT)
                .show();
            return;
        }
        User.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ForgetPasswordActivity.this, "重置密码的邮件已发送至 " + email, Toast.LENGTH_SHORT)
                        .show();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "邮箱发送失败 " + e.toString(), Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });

    }

    /**
     * 修改密码
     */
    @OnClick(R.id.button_update_password)
    void clickChangePassword(View view) {
        String passwordNow = mEditPasswordNow.getText().toString().trim();
        String passwordNew = mEditPasswordNew.getText().toString().trim();
        String passwordAgain = mEditPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(passwordNow)
            | TextUtils.isEmpty(passwordNew)
            | TextUtils.isEmpty(passwordAgain)) {
            Toast.makeText(ForgetPasswordActivity.this, "密码输入不能为空", Toast.LENGTH_SHORT)
                .show();
            return;
        }
        if (passwordNew.equals(passwordNow)) {
            Toast.makeText(ForgetPasswordActivity.this, "旧密码与新密码一致", Toast.LENGTH_SHORT)
                .show();
            return;
        }
        if(!passwordNew.equals(passwordAgain)) {
            Toast.makeText(ForgetPasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT)
                .show();
            return;
        }
        BmobUser user = User.getCurrentUser();
        user.updateCurrentUserPassword(passwordNow, passwordNew, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    // 成功
                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功", Toast.LENGTH_SHORT)
                        .show();
                    finish();
                } else {
                    // 失败
                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败"+e.toString(), Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });

    }
}
