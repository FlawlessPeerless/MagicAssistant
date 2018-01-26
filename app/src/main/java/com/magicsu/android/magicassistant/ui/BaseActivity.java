package com.magicsu.android.magicassistant.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: BaseActivity
 * author: admin
 * date: 2018/1/26
 * description: activity 基类
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
