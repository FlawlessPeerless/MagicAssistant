package com.magicsu.android.magicassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.ExpressData;

import java.util.List;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.adapter
 * file: ExpressDataAdapter
 * author: admin
 * date: 2018/1/31
 * description: 快递list适配器
 */

public class ExpressDataAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ExpressData> mDataList;
    private LayoutInflater mLayoutInflater;


    public ExpressDataAdapter(Context context, List<ExpressData> list) {
        mContext = context;
        mDataList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpressDataViewHolder(mLayoutInflater.inflate(R.layout.item_express_data_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO 完成数据绑定渲染
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ExpressDataViewHolder extends RecyclerView.ViewHolder {

        public ExpressDataViewHolder(View itemView) {
            super(itemView);
        }
    }
}
