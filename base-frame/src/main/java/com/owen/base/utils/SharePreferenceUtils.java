package com.owen.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceUtils {
    private static SharedPreferences settings;

    public static void init(Context context) {
        init(context, (String) null);
    }

    public static void init(Context context, String sharePreFileName) {
        if (sharePreFileName == null) {
            sharePreFileName = context.getPackageName();
        }

        settings = context.getSharedPreferences(sharePreFileName, MODE_PRIVATE);
    }

    public static String getStringValue(String key, String defValue) {
        return settings.getString(key, defValue);
    }

    public static boolean getBooleanValue(String key, boolean defValue) {
        return settings.getBoolean(key, defValue);
    }

    public static float getFloatValue(String key, float defValue) {
        return settings.getFloat(key, defValue);
    }

    public static int getIntValue(String key, int defValue) {
        return settings.getInt(key, defValue);
    }

    public static long getLongValue(String key, long defValue) {
        return settings.getLong(key, defValue);
    }

    public static boolean putBoolean(String key, boolean value) {
        return settings.edit().putBoolean(key, value).commit();
    }

    public static boolean putString(String key, String value) {
        return settings.edit().putString(key, value).commit();
    }

    public static boolean putFloat(String key, float value) {
        return settings.edit().putFloat(key, value).commit();
    }

    public static boolean putLong(String key, long value) {
        return settings.edit().putLong(key, value).commit();
    }

    public static boolean putInt(String key, int value) {
        return settings.edit().putInt(key, value).commit();
    }

    public static Map getAll() {
        return settings.getAll();
    }

    public static boolean contains(String key) {
        return settings.contains(key);
    }

    public static boolean delete(String key) {
        return settings.edit().remove(key).commit();
    }

    public static boolean clear() {
        return settings.edit().clear().commit();
    }
}
