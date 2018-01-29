package com.magicsu.android.magicassistant.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.magicsu.android.magicassistant.MainActivity;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.SP;
import com.magicsu.android.magicassistant.util.UtilTool;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: SplashActivity
 * author: admin
 * date: 2018/1/26
 * description: 引导页
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.text_view_splash) TextView mTextView;

    private final Handler mHandler = new SplashHandler(this);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        initView();
    }

    /**
     * 闪屏屏蔽返回按钮
     */
    @Override
    public void onBackPressed() {
    }

    private void initView() {
        // 开启子线程延时跳转
        mHandler.sendEmptyMessageDelayed(Constant.HANDLER_SPLASH, 2000);

        //设置字体
        Typeface typeface = UtilTool.getFontType(this, "FONT.TTF");
        mTextView.setTypeface(typeface);
    }

    /**
     * 判断是否是第一次登录
     * @return boolean
     */
    public boolean isFirst() {
        boolean isFirst = SP.getBoolean(this, Constant.SHARED_IS_FIRST, true);
        if (isFirst) {
            SP.putBoolean(this, Constant.SHARED_IS_FIRST, false);
        }
        return isFirst;
    }

    private static class SplashHandler extends Handler {
        // 内部类弱引用外部类 防止内存泄漏
        private final WeakReference<SplashActivity> mActivity;


        SplashHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mActivity.get();
            switch (msg.what) {
                case Constant.HANDLER_SPLASH:
                    if (activity.isFirst()) {
                        activity.startActivity(new Intent(activity, GuideActivity.class));
                    } else {
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                    activity.finish();
                    break;
            }
        }
    }
}
