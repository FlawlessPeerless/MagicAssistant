package com.magicsu.android.magicassistant.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.magicsu.android.magicassistant.R;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.view
 * file: CustomDialog
 * author: admin
 * date: 2018/1/29
 * description: 自定义dialog
 */

public class CustomDialog {
    public static Dialog create(Context context, int layout) {
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(layout, null, false);
        WindowManager.LayoutParams layoutParams = progressDialog.getWindow().getAttributes();
        progressDialog.getWindow().setAttributes(layoutParams);
        progressDialog.setContentView(view);
        return progressDialog;
    }
}
