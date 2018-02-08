package com.magicsu.android.magicassistant.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.magicsu.android.magicassistant.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.view
 * file: GestureLockView
 * author: admin
 * date: 2018/2/8
 * description: 手势解锁控件
 */

public class GestureLockView extends View {
    private enum GesturePointState {

    }
    private static final int POINT_COUNT = 9;
    private static final int POINT_STATE_NONE = -1;

    private int[] residuePointArray;    // 剩余point数组
    private boolean[] pointStateArray;  // point状态数组
    private int mStartPoint;    // 起始点index
    private int mEndPoint;      // 结束点index
    private List<GesturePoint> mGesturePoints; // 手势点列表
    private int mRowSpanWidth;


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
        int width = MeasureSpec.getSize(widthMeasureSpec), height = MeasureSpec.getSize(heightMeasureSpec);
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
        // 设置组件宽度
        mRowSpanWidth = w / 3;
        setGesturePoints(w);
    }

    // 计算 GesturePoint 点位置
    private void setGesturePoints(int width) {
        for (int i = 0; i < POINT_COUNT; i++) {
            GesturePoint point;
            int centerX = (i % 3) * mRowSpanWidth + (mRowSpanWidth / 2);
            int centerY = (i / 3) * mRowSpanWidth + (mRowSpanWidth / 2);
            if (mGesturePoints.size() < POINT_COUNT) {
                point = new GesturePoint(i);
                point.setDiameter(mRowSpanWidth);
                point.setCenterX(centerX);
                point.setCenterY(centerY);
                mGesturePoints.add(point);
            } else {
                point = mGesturePoints.get(i);
                point.setDiameter(mRowSpanWidth);
                point.setCenterX(centerX);
                point.setCenterY(centerY);
            }

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GesturePoint point = getTouchedPoint(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateGestureState(point.getId());
                break;
            case MotionEvent.ACTION_MOVE:
                updateGestureState(point.getId());
                break;
            case MotionEvent.ACTION_UP:
                updateGestureState(point.getId());
                initState();
                break;
            case MotionEvent.ACTION_CANCEL:
                initState();
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 初始化内容数据
     * @param context 上下文
     */
    private void initView(Context context) {
        residuePointArray = new int[POINT_COUNT];
        pointStateArray = new boolean[POINT_COUNT];
        mGesturePoints = new ArrayList<>();
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

    private GesturePoint getTouchedPoint(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int row = y / mRowSpanWidth;
        int col = x / mRowSpanWidth;
        int position = row * 3 + col;
        L.i(position + "");
        return mGesturePoints.get(position);
    }

    private void updateGestureState(int pos) {
        GesturePoint point = mGesturePoints.get(pos);
        if (point.isChecked()) return;
        point.setChecked(true);
        Toast.makeText(getContext(), "pos = " + point.getId(), Toast.LENGTH_SHORT).show();
    }


    /**
     * 手势点
     */
    public class GesturePoint {
        private int centerX;    // y
        private int centerY;    // x
        private int diameter;   // 直径
        private final int id;   // id
        private boolean checked;

        public GesturePoint(int id) {
            this.id = id;
            checked = false;
        }

        public int getId() {
            return id;
        }


        public int getCenterX() {
            return centerX;
        }

        public void setCenterX(int centerX) {
            this.centerX = centerX;
        }

        public int getCenterY() {
            return centerY;
        }

        public void setCenterY(int centerY) {
            this.centerY = centerY;
        }

        public int getDiameter() {
            return diameter;
        }

        public void setDiameter(int diameter) {
            this.diameter = diameter;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
