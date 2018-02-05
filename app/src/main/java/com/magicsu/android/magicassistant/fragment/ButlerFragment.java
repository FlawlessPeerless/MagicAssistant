package com.magicsu.android.magicassistant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.adapter.ChatListAdapter;
import com.magicsu.android.magicassistant.entity.ChatListData;
import com.magicsu.android.magicassistant.util.Constant;
import com.magicsu.android.magicassistant.util.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.fragment
 * file: ButlerFragment
 * author: admin
 * date: 2018/1/26
 * description: 服务管家 fragment
 */

public class ButlerFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.edit_text_message)
    EditText mEditMessage;
    @BindView(R.id.button_send)
    Button mButtonSendMessage;

    private ChatListAdapter mAdapter;
    private List<ChatListData> mDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 初始化内容
     */
    private void initView() {
        mAdapter = new ChatListAdapter(getContext(), mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addItem("你好，我是小管家", ChatListAdapter.VALUE_LEFT_TEXT);
    }

    /**
     * 发送内容
     */
    @OnClick(R.id.button_send)
    void leftButton() {
        String message = mEditMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), "请输入有效内容", Toast.LENGTH_SHORT).show();
            return;
        }
        addItem(message, ChatListAdapter.VALUE_RIGHT_TEXT);
        mEditMessage.setText("");
        String url = "http://op.juhe.cn/robot/code?info="+ message +"&key="+ Constant.JUEH_CHAT_ROBORT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i(t);
                parseJson(t);
            }
        });
    }

    /**
     * 解析json
     * @param t json 字符串
     */
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String text = jsonResult.getString("50000");
            addItem(text, ChatListAdapter.VALUE_LEFT_TEXT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void addItem(String text, int position) {
        ChatListData data = new ChatListData();
        data.setType(position);
        data.setText(text);
        mDataList.add(data);
        mAdapter.notifyItemInserted(mDataList.size());
        mRecyclerView.scrollToPosition(mDataList.size() - 1);
    }

}
