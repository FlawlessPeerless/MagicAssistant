package com.magicsu.android.magicassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.ChatListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.adapter
 * file: ChatListAdapter
 * author: admin
 * date: 2018/2/5
 * description: 对话列表adapter
 */

public class ChatListAdapter extends RecyclerView.Adapter {
    public static final int VALUE_LEFT_TEXT = 1;
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private List<ChatListData> mDataList;

    public ChatListAdapter(Context context, List<ChatListData> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VALUE_LEFT_TEXT) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_left_chat, parent, false);
            return new LeftViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_right_chat, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
        chatViewHolder.bindHolder(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatListData data = mDataList.get(position);
        return data.getType();
    }

    // view holder
    class ChatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_chat)
        TextView mTextViewMessage;

        ChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindHolder(ChatListData data) {
            mTextViewMessage.setText(data.getText());
        }
    }

    class LeftViewHolder extends ChatViewHolder {


        LeftViewHolder(View itemView) {
            super(itemView);
        }
    }

    class RightViewHolder extends ChatViewHolder {

        RightViewHolder(View itemView) {
            super(itemView);
        }
    }
}
