package com.magicsu.android.magicassistant.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.view
 * file: GestureLockView
 * author: admin
 * date: 2018/2/8
 * description: 手势解锁控件
 */

public class GestureLockView extends View {
    private static final int POINT_COUNT = 9;
    private static final int POINT_STATE_NONE = -1;

    private int[] residuePointArray;    // 剩余point数组
    private boolean[] pointStateArray;  // point状态数组
    private int mStartPoint;    // 起始点index
    private int mEndPoint;      // 结束点index


    public GestureLockView(Context context) {
        super(context);
        initView(context);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0, height = 0;
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        if (h < w) {
            width = h;
        } else {
            height = w;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 初始化内容数据
     * @param context 上下文
     */
    private void initView(Context context) {
        residuePointArray = new int[POINT_COUNT];
        pointStateArray = new boolean[POINT_COUNT];

        initState();
    }

    /**
     * 初始化状态
     */
    private void initState() {
        for (int i = 0; i < POINT_COUNT; i++) {
            residuePointArray[i] = POINT_STATE_NONE;
        }
        for (int i = 0; i < POINT_COUNT; i++) {
            pointStateArray[i] = false;
        }

        mStartPoint = POINT_STATE_NONE;
        mEndPoint = POINT_STATE_NONE;
    }



    /**
     * 手势点
     */
    public class GesturePoint {
        private int width;
        private int height;
        private int position;
    }
}
