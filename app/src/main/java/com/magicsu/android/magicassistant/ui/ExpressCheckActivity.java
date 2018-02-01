package com.magicsu.android.magicassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.adapter.ExpressDataAdapter;
import com.magicsu.android.magicassistant.entity.ExpressData;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.ui
 * file: ExpressCheckActivity
 * author: admin
 * date: 2018/1/31
 * description: 快递查询
 */

public class ExpressCheckActivity extends BaseActivity {
    @BindView(R.id.edit_express_company_name)
    EditText mEditCompany;
    @BindView(R.id.edit_express_number)
    EditText mEditNumber;
    @BindView(R.id.button_search)
    Button mSearchButton;
    @BindView(R.id.list_express_information)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_check);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {

    }

    /**
     * 查询物流
     * @param view 视图
     */
    @OnClick(R.id.button_search)
    void searchExpress(View view) {
        String name = mEditCompany.getText().toString().trim();
        String number = mEditNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
            Toast.makeText(ExpressCheckActivity.this, "请填写完整查询信息" , Toast.LENGTH_SHORT)
                .show();
            return;
        }
        // 开始查询
        String url = "http://v.juhe.cn/exp/index?key=" + Constant.JUHE_APP_KEY + "&com="+ name + "&no=" + number;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i(t);
                ArrayList<ExpressData> list = parseJson(t);
                ExpressDataAdapter adapter = new ExpressDataAdapter(ExpressCheckActivity.this, list);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(ExpressCheckActivity.this));
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

    /**
     * 解析数据
     * @param json json字符串
     * @return 数据实体类对象
     */
    private ArrayList<ExpressData> parseJson(String json) {
        ArrayList<ExpressData> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");

            for (int i = jsonArray.length()-1; i > 0; i--) {
                JSONObject jsonData = (JSONObject) jsonArray.get(i);
                ExpressData data = new ExpressData();
                data.setRemark(jsonData.getString("remark"));
                data.setZone(jsonData.getString("zone"));
                data.setDatetime(jsonData.getString("datetime"));
                list.add(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
