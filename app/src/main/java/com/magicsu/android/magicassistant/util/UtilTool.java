package com.magicsu.android.magicassistant.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

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
}
