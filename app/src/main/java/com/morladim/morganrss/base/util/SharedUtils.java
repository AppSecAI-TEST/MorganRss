package com.morladim.morganrss.base.util;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * SharedPreference工具类
 * <br>创建时间：2017/7/20.
 *
 * @author morladim
 */
@SuppressWarnings("unused")
public class SharedUtils {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String PREFERENCES_NAME = "morladimLibrary";
    private static final Gson gson = new Gson();

    private SharedUtils() {
    }

    public static void init(Application context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        editor = preferences.edit();
        editor.apply();
    }

    public static synchronized void saveObject(String key, Object value) {
        String var2 = gson.toJson(value);
        editor.putString(key, var2);
        editor.apply();
    }

    public static synchronized void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static String loadString(String key) {
        return preferences.getString(key, "");
    }

    public static void clearString(String key) {
        editor.remove(key);
        editor.apply();
    }

    public static <T> T loadObject(String key, Class<T> tClass) {
        String var2 = preferences.getString(key, null);
        return gson.fromJson(var2, tClass);
    }

    public static <T> T loadObject(String key, Type type) {
        String var2 = preferences.getString(key, null);
        return gson.fromJson(var2, type);
    }

    public static int loadInt(String key) {
        return preferences.getInt(key, 0);
    }

    public static synchronized void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static synchronized void saveBolean(String key, boolean b) {
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static synchronized boolean loadBolean(String key) {
        return preferences.getBoolean(key, false);
    }

}

