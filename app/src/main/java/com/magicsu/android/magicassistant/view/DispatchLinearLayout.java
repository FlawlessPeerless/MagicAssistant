package com.magicsu.android.magicassistant.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.view
 * file: DispatchLinearLayout
 * author: admin
 * date: 2018/2/7
 * description: 事件分发view group
 */

public class DispatchLinearLayout extends LinearLayout {
    private DispatchKeyEventListener mDispatchKeyEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mDispatchKeyEventListener != null) {
            return mDispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return mDispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        mDispatchKeyEventListener = dispatchKeyEventListener;
    }

    public static interface DispatchKeyEventListener {
        boolean dispatchKeyEvent(KeyEvent event);
    }
}
