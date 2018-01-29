package com.magicsu.android.magicassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.magicsu.android.magicassistant.fragment.ButlerFragment;
import com.magicsu.android.magicassistant.fragment.GirlFragment;
import com.magicsu.android.magicassistant.fragment.UserFragment;
import com.magicsu.android.magicassistant.fragment.WeChatFragment;
import com.magicsu.android.magicassistant.ui.BaseActivity;
import com.magicsu.android.magicassistant.ui.SettingActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.fab_setting) FloatingActionButton mFloatingActionButton;

    private List<String> mTitle;
    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        mTitle = Arrays.asList(getResources().getStringArray(R.array.activity_tab_titles));
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.put(0, new ButlerFragment());
        mFragmentSparseArray.put(1, new WeChatFragment());
        mFragmentSparseArray.put(2, new GirlFragment());
        mFragmentSparseArray.put(3, new UserFragment());
    }

    private void initView() {
        mViewPager.setOffscreenPageLimit(mFragmentSparseArray.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentSparseArray.get(position);
            }
            @Override
            public int getCount() {
                return mFragmentSparseArray.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mFloatingActionButton.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

        mFloatingActionButton.setOnClickListener(this);
        mFloatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
