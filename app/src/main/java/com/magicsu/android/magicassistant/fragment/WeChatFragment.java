package com.magicsu.android.magicassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.adapter.WechatAdapter;
import com.magicsu.android.magicassistant.entity.WechatData;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.fragment
 * file: WeChatFragment
 * author: admin
 * date: 2018/1/26
 * description: 微信精选fragment
 */

public class WeChatFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private List<WechatData> mDataList = new ArrayList<>();
    private WechatAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        ButterKnife.bind(this, view);

        initData();

        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String url = "http://v.juhe.cn/weixin/query?key="+ Constant.JUHE_WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parseJson(t);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {

        });
    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject json = (JSONObject) list.get(i);
                WechatData data = new WechatData();
                data.setFirstImg(json.getString("firstImg"));
                data.setTitle(json.getString("title"));
                data.setSource(json.getString("source"));
                data.setUrl(json.getString("url"));
                mDataList.add(data);
            }

            mAdapter = new WechatAdapter(getActivity(), mDataList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
