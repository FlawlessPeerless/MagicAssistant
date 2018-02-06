package com.magicsu.android.magicassistant.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.adapter.GirlAdapter;
import com.magicsu.android.magicassistant.entity.GirlData;
import com.magicsu.android.magicassistant.util.L;
import com.magicsu.android.magicassistant.util.PicassoUtil;
import com.magicsu.android.magicassistant.view.CustomDialog;

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
 * file: GirlFragment
 * author: admin
 * date: 2018/1/26
 * description: 美女社区fragment
 */

public class GirlFragment extends Fragment {
    @BindView(R.id.recycler_view_girl)
    RecyclerView mRecyclerView;
    private GirlAdapter mGirlAdapter;
    private List<GirlData> mGirlDataList = new ArrayList<>();

    private CustomDialog.PhotoViewDialog mDialog;
    private ImageView mPhotoView;
    private PhotoViewAttacher mViewAttacher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_preview, null, false);
        mPhotoView = view.findViewById(R.id.image_photo_view);
        mDialog = CustomDialog.createDialog(getContext(), view);

        RxVolley.get("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parseJson(t);
            }
        });
    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                GirlData data = new GirlData();
                data.setImageUrl(url);
                mGirlDataList.add(data);
            }

            mGirlAdapter = new GirlAdapter(getContext(), mGirlDataList);
            mGirlAdapter.setOnItemClickListener((view, position) -> {
                // 设置点击事件
                PicassoUtil.loadImageView(getContext(), mGirlDataList.get(position).getImageUrl(), mPhotoView);
                mViewAttacher = new PhotoViewAttacher(mPhotoView);
                mViewAttacher.update();
                mDialog.show();

            });
            mRecyclerView.setAdapter(mGirlAdapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
