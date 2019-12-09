package com.owen.base.utils;

import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2019-07-15
 */
public class UtilsManager {
    public static void init(Context context) {
        Utils.init(context);
        SharePreferenceUtils.init(context);
        ScreenUtils.init(context);
    }
}
