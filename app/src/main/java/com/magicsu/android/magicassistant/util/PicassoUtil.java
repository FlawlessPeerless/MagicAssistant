package com.magicsu.android.magicassistant.util;

import android.content.Context;
import android.widget.ImageView;

import com.magicsu.android.magicassistant.R;
import com.squareup.picasso.Picasso;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.util
 * file: PicassoUtil
 * author: admin
 * date: 2018/2/6
 * description: Picasso 工具类
 */

public class PicassoUtil {
    public static void loadImageView(Context context, String url, ImageView view) {
        Picasso.with(context).load(url).into(view);
    }

    /**
     * 加载图片并指定宽高
     * @param context 上下文
     * @param url  图片地址
     * @param width  指定宽
     * @param height  指定高
     * @param view view控件
     */
    public static void loadImageViewSize(Context context, String url,int width, int height, ImageView view) {
        Picasso.with(context)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(view);
    }

    /**
     * 加载中 加载错误的占位图设置
     * @param context 上下文环境
     * @param url 图片地址
     * @param loadRes 加载中资源
     * @param errorRes  加载错误资源
     * @param view 视图
     */
    public static void loadImageViewHolder(Context context, String url, int loadRes, int errorRes, ImageView view) {
        Picasso.with(context).load(url)
                .placeholder(loadRes)
                .error(errorRes)
                .into(view);
    }

}
