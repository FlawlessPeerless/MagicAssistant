package com.magicsu.android.magicassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.magicsu.android.magicassistant.MainActivity;
import com.magicsu.android.magicassistant.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页
 * Created by 36268 on 2018/1/26.
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.button_skip) ImageView mSkipButton;
    @BindView(R.id.point_group) LinearLayout pointGroup;

    private List<View> mViewList = new ArrayList<>();
    private View guideView1, guideView2, guideView3;

    private int mCurrentPoint = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mSkipButton = findViewById(R.id.button_skip);
        mSkipButton.setOnClickListener(this);
        guideView1 = LayoutInflater.from(this).inflate(R.layout.pager_item_guide_1, null, false);
        guideView2 = LayoutInflater.from(this).inflate(R.layout.pager_item_guide_2, null, false);
        guideView3 = LayoutInflater.from(this).inflate(R.layout.pager_item_guide_3, null, false);
        guideView3.findViewById(R.id.button_start).setOnClickListener(this);
        mViewList.add(guideView1);
        mViewList.add(guideView2);
        mViewList.add(guideView3);

        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setCurrentPoint(position);
                mSkipButton.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
            }
        });

        pointGroup.getChildAt(mCurrentPoint).setSelected(true);
    }

    /**
     * 设置当前选中的point
     * @param pos 位置
     */
    private void setCurrentPoint(int pos) {
        if (pointGroup != null) {
            if (pos == mCurrentPoint) return;
            pointGroup.getChildAt(mCurrentPoint).setSelected(false);
            pointGroup.getChildAt(pos).setSelected(true);
            mCurrentPoint = pos;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
            case R.id.button_skip:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }


}
