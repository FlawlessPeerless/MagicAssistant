package com.magicsu.android.magicassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.WechatData;
import com.magicsu.android.magicassistant.ui.WebViewActivity;
import com.magicsu.android.magicassistant.util.PicassoUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.adapter
 * file: WechatAdapter
 * author: admin
 * date: 2018/2/5
 * description:
 */

public class WechatAdapter extends RecyclerView.Adapter<WechatAdapter.WechatViewHolder> {
    private Context mContext;
    private List<WechatData> mDataList;

    public WechatAdapter(Context context, List<WechatData> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public WechatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wechat_list, parent, false);
        return new WechatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WechatViewHolder holder, int position) {
        holder.bindView(position);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class WechatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_group_root)
        LinearLayout mLinearLayout;
        @BindView(R.id.image_first)
        ImageView mImageView;
        @BindView(R.id.text_view_title)
        TextView mTextViewTitle;
        @BindView(R.id.text_view_description)
        TextView mTextViewDescription;

        WechatData mData;

        WechatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {
            mData = mDataList.get(position);
            mTextViewTitle.setText(mData.getTitle());
            mTextViewDescription.setText(mData.getSource());
            // 设置图片
            if (TextUtils.isEmpty(mData.getFirstImg())) {
                mImageView.setImageResource(R.mipmap.ic_launcher);
            } else {
                PicassoUtil.loadImageViewSize(mContext,mData.getFirstImg(), 300, 150,mImageView);
            }
        }

        @OnClick(R.id.view_group_root)
        void jumpLink() {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("title", mData.getTitle());
            intent.putExtra("url", mData.getUrl());
            mContext.startActivity(intent);
        }
    }
}
