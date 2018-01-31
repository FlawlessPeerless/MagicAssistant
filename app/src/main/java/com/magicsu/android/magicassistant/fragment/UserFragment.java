package com.magicsu.android.magicassistant.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.User;
import com.magicsu.android.magicassistant.ui.ExpressCheckActivity;
import com.magicsu.android.magicassistant.ui.LoginActivity;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.L;
import com.magicsu.android.magicassistant.util.SP;
import com.magicsu.android.magicassistant.util.UtilTool;
import com.magicsu.android.magicassistant.view.CustomDialog;
import com.magicsu.android.magicassistant.view.CustomDialog.BottomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int REQUEST_CODE_CAMERA = 201;
    public static final int REQUEST_CODE_ALBUM = 202;
    public static final int REQUEST_CODE_CROP = 203;

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

    @BindView(R.id.text_view_recruitment)
    TextView mRecruitmentTextView;
    @BindView(R.id.text_view_phone_query)
    TextView mPhoneQueryTextView;

    @BindView(R.id.button_log_out)
    Button mLogOutButton;

    private BottomDialog mBottomDialog;
    private Uri mImageUri;

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

        // 检查默认头像
        UtilTool.getImageFromSP(getContext(), mCircleImageView);
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
     * 物流查询
     */
    @OnClick(R.id.text_view_recruitment)
    void goToRecruitment(View view) {
        startActivity(new Intent(getActivity(), ExpressCheckActivity.class));
    }

    /**
     * 打开相册
     */
    private void dialogOpenAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
        mBottomDialog.dismiss();
    }

    /**
     * 打开相机
     */
    private void dialogOpenCamera() {
        File outputImage = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), PHOTO_IMAGE_FILE_NAME);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mImageUri = FileProvider.getUriForFile(getContext(),"com.android.magicassistant.fileprovider", outputImage);
        } else {
            mImageUri = Uri.fromFile(outputImage);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
        mBottomDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 存储头像
        UtilTool.putImageToSP(getContext(), mCircleImageView);
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
                dialogOpenCamera();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) return;
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    imageResizer(mImageUri);
                }
                break;
            case REQUEST_CODE_ALBUM:
                // 相册数据
                imageResizer(data.getData());
                break;
            case REQUEST_CODE_CROP:
                if (data  == null) return;
                setImageToView(data);
                break;
        }
    }

    /**
     * 图片裁剪
     */
    private void imageResizer(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            mCircleImageView.setImageBitmap(bitmap);
        }
    }
}

