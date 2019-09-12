package com.owen.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2019-08-15
 */
public class Utils {

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Object object) {
        if (object instanceof Collection)
            return ((Collection) object).isEmpty();
        if (object instanceof Map)
            return ((Map) object).isEmpty();
        if (object instanceof CharSequence)
            return TextUtils.isEmpty((CharSequence) object);
        return null == object;
    }

    public static boolean isNoEmpty(Object object) {
        return !isEmpty(object);
    }


    public static boolean isTopActivity(Activity activity) {
        return activity != null && isTopActivity(activity, activity.getClass().getName());
    }

    public static boolean isTopActivity(Context context, String activityName) {
        return isForeground(context, activityName);
    }

    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
