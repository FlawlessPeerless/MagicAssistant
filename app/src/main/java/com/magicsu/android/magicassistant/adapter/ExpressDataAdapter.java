package com.magicsu.android.magicassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.ExpressData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.adapter
 * file: ExpressDataAdapter
 * author: admin
 * date: 2018/1/31
 * description: 快递list适配器
 */

public class ExpressDataAdapter extends RecyclerView.Adapter {
    private List<ExpressData> mDataList;
    private LayoutInflater mLayoutInflater;

    public ExpressDataAdapter(Context context, List<ExpressData> list) {
        mDataList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpressDataViewHolder(mLayoutInflater.inflate(R.layout.item_express_data_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ExpressDataViewHolder viewHolder = (ExpressDataViewHolder) holder;
        viewHolder.bindHolder(mDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ExpressDataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_description)
        TextView mTextViewDescription;
        @BindView(R.id.text_view_zone)
        TextView mTextViewZone;
        @BindView(R.id.text_view_datetime)
        TextView mTextViewDatetime;

        ExpressDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindHolder(ExpressData data) {
            mTextViewDescription.setText(data.getRemark());
            mTextViewZone.setText(data.getZone());
            mTextViewDatetime.setText(data.getDatetime());
        }


    }
}
