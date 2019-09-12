package com.owen.base.utils;

import android.content.Context;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2019-07-15
 */
public class UtilsManager {
    public static void init(Context context) {
        ToastUtils.initToast(context);
        SharePreferenceUtils.init(context);
        ScreenUtils.init(context);
    }
}
