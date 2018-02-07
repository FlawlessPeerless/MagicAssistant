package com.magicsu.android.magicassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.magicsu.android.magicassistant.R;
import com.magicsu.android.magicassistant.entity.GirlData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.adapter
 * file: GirlAdapter
 * author: admin
 * date: 2018/2/6
 * description:
 */

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> {
    private OnItemClickListener mOnItemClickListener = null;
    private final Context mContext;
    private final List<GirlData> mGirlDataList;

    public GirlAdapter(Context context, List<GirlData> list) {
        mContext = context;
        mGirlDataList = list;
    }

    /**
     * 对外暴露点击事件接口
     * @param listener 点击监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_girl_list, parent, false);
        return new GirlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GirlViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mGirlDataList.size();
    }

    class GirlViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.image_view)
        ImageView mImageView;
        private int mPosition;

        GirlViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        void bindView(int pos) {
            mPosition = pos;
            Picasso.with(mContext)
                    .load(mGirlDataList.get(pos).getImageUrl())
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, mPosition);
            }
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
