package com.magicsu.android.magicassistant.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.TransitionInflater;
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
        progressDialog.setContentView(view);
        return progressDialog;
    }

    public static BottomDialog createBottomDialog(Context context, View layout) {
        BottomDialog dialog = new BottomDialog(context);
        dialog.setContentView(layout);
        return dialog;
    }

    public static class BottomDialog extends Dialog {

        public BottomDialog(@NonNull Context context) {
            this(context, R.style.AppTheme_Dialog);
        }

        public BottomDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window dialogWindow = getWindow();

            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
            dialogWindow.setAttributes(params);
            dialogWindow.setWindowAnimations(R.style.WindowAnimationTransition);
            setCancelable(false);
        }
    }
}
