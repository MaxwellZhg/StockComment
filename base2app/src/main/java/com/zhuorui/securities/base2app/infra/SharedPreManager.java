package com.zhuorui.securities.base2app.infra;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * SharedPreferences控制器
 */
public class SharedPreManager {
    private static class Builder {
        private static SharedPreManager instance = new SharedPreManager();
    }

    public static SharedPreManager getInstance() {
        return Builder.instance;
    }

    private Map<String, SharedPreferences> sharedPreMap = new HashMap<String, SharedPreferences>();
    private SharedPreferences defaultSharedPreferences;
    private Context mContext;
    private String defaultSpName;

    private SharedPreManager() {
    }

    /**
     * 初始化SharedPreManager；调用在Application中
     *
     * @param context       context
     * @param defaultSpName 默认的Sp文件名称
     */
    public void init(Context context, String defaultSpName) {
        sharedPreMap.clear();
        mContext = context;
        this.defaultSpName = defaultSpName;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getBoolean(null, key, defValue);
    }

    public boolean getBoolean(String fileName, String key, boolean defValue) {
        return getSharedPreferences(fileName).getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return getInt(null, key, defValue);
    }

    public int getInt(String fileName, String key, int defValue) {
        return getSharedPreferences(fileName).getInt(key, defValue);
    }

    public float getFloat(String fileName, String key, float defValue) {
        return getSharedPreferences(fileName).getFloat(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return getFloat(null, key, defValue);
    }

    public String getString(String key) {
        return getString(null, key, null);
    }

    public String getString(String key, String defValue) {
        return getString(null, key, defValue);
    }

    public String getString(String fileName, String key, String defValue) {
        String data = getSharedPreferences(fileName).getString(key, "");
        if (TextUtils.isEmpty(data)) {
            data = defValue;
        }
        return data;
    }

    public long getLong(String fileName, String key, long defValue) {
        return getSharedPreferences(fileName).getLong(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return getLong(null, key, defValue);
    }

    public void saveBoolean(String key, boolean value) {
        saveBoolean(null, key, value);
    }

    public void saveBoolean(String fileName, String key, boolean value) {
        boolean commit = getSharedPreferences(fileName).edit().putBoolean(key, value).commit();
    }

    public void saveInt(String key, int value) {
        saveInt(null, key, value);
    }

    public void saveInt(String fileName, String key, int value) {
        boolean commit = getSharedPreferences(fileName).edit().putInt(key, value).commit();
    }

    public void saveLong(String key, Long value) {
        saveLong(null, key, value);
    }

    public void saveLong(String fileName, String key, Long value) {
        boolean commit = getSharedPreferences(fileName).edit().putLong(key, value).commit();
    }

    public void saveFloat(String key, Float value) {
        saveFloat(null, key, value);
    }

    public void saveFloat(String fileName, String key, Float value) {
        boolean commit = getSharedPreferences(fileName).edit().putFloat(key, value).commit();
    }

    public void saveString(String key, String value) {
        saveString(null, key, value);
    }

    public void saveString(String fileName, String key, String value) {
        boolean commit = getSharedPreferences(fileName).edit().putString(key, value).commit();
    }

    public boolean remove(String key) {
        return remove(null, key);
    }

    public boolean remove(String fileName, String key) {
        return getSharedPreferences(fileName).edit().remove(key).commit();
    }

    private SharedPreferences getSharedPreferences(String fileName) {
        /*若未指定SpName；则采用默认SP*/
        if (TextUtils.isEmpty(fileName)) {
            /*若未设置defaultSpName；则采用默认的系统SpName*/
            if (TextUtils.isEmpty(defaultSpName)) {
                if (defaultSharedPreferences == null) {
                    defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                }
                return defaultSharedPreferences;
            }
            /*若设置了默认的SpName；则默认未传入指定的SpName采用默认的Sp读取*/
            fileName = defaultSpName;
        }
        SharedPreferences pref = sharedPreMap.get(fileName);
        if (pref == null) {
            pref = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            sharedPreMap.put(fileName, pref);
        }
        return pref;
    }


    public void removeFile(String file) {
        boolean commit = getSharedPreferences(file).edit().clear().commit();
    }
}
