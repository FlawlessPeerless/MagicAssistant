package com.magicsu.android.magicassistant.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.util.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: PhoneQueryActivity
 * author: admin
 * date: 2018/2/1
 * description: 号码归属地查询
 */

public class PhoneQueryActivity extends BaseActivity {
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.operator_image)
    ImageView mImageOperator;
    @BindView(R.id.text_view_result)
    TextView mTextViewResult;
    @BindViews({R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,
    R.id.btn_9,R.id.btn_del,R.id.btn_search})
    List<Button> buttons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_query);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * 界面初始化
     */
    private void initView() {
    }

    @OnClick({R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,
            R.id.btn_9,R.id.btn_del,R.id.btn_search})
    void calcEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                calcOutput(view);
                break;
            case R.id.btn_del:
                calcDel();
                break;
            case R.id.btn_search:
                calcExecute();
                break;
        }
    }

    /**
     * 删除按钮
     */
    private void calcDel() {
        String str = mEditPhone.getText().toString();
        if (TextUtils.isEmpty(str)) return;
        String afterStr = str.substring(0, str.length()-1);
        mEditPhone.setText(afterStr);
        mEditPhone.setSelection(afterStr.length());
    }

    /**
     * 键盘输入
     * @param view 当前控件
     */
    @SuppressLint("SetTextI18n")
    private void calcOutput(View view) {
        Button button = (Button) view;
        String key = button.getText().toString();
        mEditPhone.setText(String.valueOf(mEditPhone.getText()) + key);
        mEditPhone.setSelection(mEditPhone.getText().length());
    }

    /**
     * 开始执行查询
     */
    private void calcExecute() {
        if (TextUtils.isEmpty(mEditPhone.getText())) return;
        // 清空上次操作
        mImageOperator.setImageResource(0);
        mTextViewResult.setText("");

        String phoneNumber = mEditPhone.getText().toString().trim();
        String url = "https://www.baifubao.com/callback?cmd=1059&callback=phone&phone=" + phoneNumber;
        new RxVolley.Builder()
                .url(url)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(0)
                .contentType(RxVolley.ContentType.JSON)
                .shouldCache(false)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        String tt = t.replace("/*fgg_again*/phone(","");

                        L.i(t);
                        parseJson(tt.substring(0, tt.length()-1));
                    }
                })
                .encoding("UTF-8")
                .doTask();
    }

    /**
     * 解析归属地json
     * @param t json字符串
     */
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject metaJson = jsonObject.getJSONObject("meta");
            if (metaJson.getInt("result") != 0) {
                Toast.makeText(PhoneQueryActivity.this, metaJson.getString("result_info"), Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject data = jsonObject.getJSONObject("data");
            String operator = data.getString("operator");
            String area = data.getString("area");
            String areaOperator = data.getString("area_operator");
            mTextViewResult.append("归属地：" + area + "\n");
            mTextViewResult.append("运营商：" + areaOperator + "\n");
            switch (operator) {
                case "移动":
                    mImageOperator.setImageResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    mImageOperator.setImageResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    mImageOperator.setImageResource(R.drawable.china_telecom);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 长按事件
     * @param view 视图
     */
    @OnLongClick({R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,
            R.id.btn_9,R.id.btn_del,R.id.btn_search})
    boolean calcLongEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_del:
                mEditPhone.setText("");
                break;
        }
        return false;
    }
}
