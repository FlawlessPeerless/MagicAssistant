package com.magicsu.android.magicassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicsu.android.magicassistant.R;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.fragment
 * file: WeChatFragment
 * author: admin
 * date: 2018/1/26
 * description: 微信精选fragment
 */

public class WeChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null, false);
        return view;
    }
}
