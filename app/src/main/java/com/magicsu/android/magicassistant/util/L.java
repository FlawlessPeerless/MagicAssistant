package com.magicsu.android.magicassistant.util;

import android.util.Log;

/**
 * project: MagicAssistant
 * package: com.magicsu.android.magicassistant.util
 * file: L
 * author: admin
 * date: 2018/1/26
 * description: log工具类
 */

public class L {
    public static final boolean DEBUG = true;

    public static final String TAG = "magic_assistant";

    public static void d(String text) {
        if (DEBUG) Log.d(TAG,  text);
    }
    public static void i(String text) {
        if (DEBUG) Log.i(TAG, text);
    }
    public static void w(String text) {
        if (DEBUG) Log.w(TAG, text);
    }
    public static void e(String text) {
        if (DEBUG) Log.e(TAG, text);
    }
    public static void f(String text) {
        if (DEBUG) Log.wtf(TAG, text);
    }

}
