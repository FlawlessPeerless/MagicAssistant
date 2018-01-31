package com.magicsu.android.magicassistant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.util
 * file: UtilTool
 * author: admin
 * date: 2018/1/26
 * description: 工具类
 */

public class UtilTool {
    /**
     * 设置字体
     * @param context 上下文
     * @param fontName 字体名称
     * @return Typeface 需要的字体
     */
    public static Typeface getFontType(Context context, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/"+ fontName);
    }

    /**
     * 将image view的图片转换为base64保存
     * @param context 上下文环境
     * @param imageView 视图
     */
    public static void putImageToSP(Context context, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
        byte[] bytes = outputStream.toByteArray();
        String imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
        SP.putString(context, Constant.PORTRAIT_PNG, imageString);
    }

    /**
     * 获取本地存储图片置入image view
     * @param context 上下文环境
     * @param imageView 视图
     */
    public static void getImageFromSP(Context context, ImageView imageView) {
        String imageString = SP.getString(context, Constant.PORTRAIT_PNG, "");
        if (imageString.equals("")) return;
        byte[] bytes = Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        imageView.setImageBitmap(bitmap);
    }
}
