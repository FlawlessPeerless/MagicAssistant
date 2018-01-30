package com.magicsu.android.magicassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.User;
import com.magicsu.android.magicassistant.ui.LoginActivity;
import com.magicsu.android.magicassistant.util.L;
import com.magicsu.android.magicassistant.view.CustomDialog;
import com.magicsu.android.magicassistant.view.CustomDialog.BottomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.fragment
 * file: UserFragment
 * author: admin
 * date: 2018/1/26
 * description: 用户中心 fragment
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.profile_image)
    CircleImageView mCircleImageView;
    @BindView(R.id.edit_username)
    EditText mEditUsername;
    @BindView(R.id.edit_sex)
    EditText mEditSex;
    @BindView(R.id.edit_age)
    EditText mEditAge;
    @BindView(R.id.edit_description)
    EditText mEditDescription;
    @BindView(R.id.button_sure_modify)
    Button mModifyButton;

    @BindView(R.id.button_log_out)
    Button mLogOutButton;

    private BottomDialog mBottomDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * view 初始化
     */
    private void initView() {
        widgetSetEnabled(false);
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) return;
        mEditUsername.setText(user.getUsername());
        mEditAge.setText(String.valueOf(user.getAge()));
        mEditSex.setText(user.isSex() ? "男":"女");
        mEditDescription.setText(user.getDescription());

        // 初始化弹窗
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_photo, null, false);
        Button cancelButton = dialogView.findViewById(R.id.button_cancel);
        Button photoButton = dialogView.findViewById(R.id.button_take_photo);
        Button albumButton = dialogView.findViewById(R.id.button_photo_album);
        cancelButton.setOnClickListener(this);
        photoButton.setOnClickListener(this);
        albumButton.setOnClickListener(this);

        mBottomDialog = CustomDialog.createBottomDialog(getContext(), dialogView);
    }

    /**
     * 退出登录
     */
    @OnClick(R.id.button_log_out)
    void logOut(View view) {
        User.logOut();
        Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT)
            .show();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    /**
     * 修改用户信息
     */
    @OnClick(R.id.button_edit_profile)
    void editUserInfo(View view) {
        widgetSetEnabled(true);
        mModifyButton.setVisibility(View.VISIBLE);
    }
    /**
     * 确认修改
     */
    @OnClick(R.id.button_sure_modify)
    void sureModify(View view) {
        String username = mEditUsername.getText().toString().trim();
        String age = mEditAge.getText().toString().trim();
        String sex = mEditSex.getText().toString().trim();
        String description = mEditDescription.getText().toString().trim();
        if (TextUtils.isEmpty(username)
                | TextUtils.isEmpty(age)
                | TextUtils.isEmpty(sex)) {
            Toast.makeText(getContext(), "用户数据不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setAge(Integer.parseInt(age));
        user.setSex(sex.equals("男"));
        user.setDescription(TextUtils.isEmpty(description) ? "这个人很懒，什么也没有留下" : description);
        User oldUser = BmobUser.getCurrentUser(User.class);
        user.update(oldUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    // 成功
                    widgetSetEnabled(false);
                    mModifyButton.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 失败
                    Toast.makeText(getContext(), "更新失败: "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 修改头像
     */
    @OnClick(R.id.profile_image)
    void modifyHead(View view) {
        mBottomDialog.show();
    }

    /**
     * edit是否可点击
     */
    private void widgetSetEnabled(boolean enabled) {
        mEditUsername.setEnabled(enabled);
        mEditAge.setEnabled(enabled);
        mEditSex.setEnabled(enabled);
        mEditDescription.setEnabled(enabled);
    }

    /**
     * 关闭弹窗
     */
    void dialogCancel() {
        mBottomDialog.dismiss();
    }

    /**
     * 打开相册
     */
    private void dialogOpenAlbum() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                dialogCancel();
                break;
            case R.id.button_photo_album:
                dialogOpenAlbum();
                break;
            case R.id.button_take_photo:
                // todo拍照功能
                break;
        }
    }
}
