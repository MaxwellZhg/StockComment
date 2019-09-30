package com.zhuorui.securities.base2app.util;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Json解析器
 */
public class JsonUtil {
    private JsonUtil() {
    }

    private static Gson mGson = new GsonBuilder().disableHtmlEscaping().create();

    public static String toJson(Object model) {
        if (model == null) {
            return null;
        }
        return mGson.toJson(model);
    }

    /**
     * 根据json key排序
     *
     * @param json
     * @return
     */
    public static String sortJson(String json) {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        JsonParser p = new JsonParser();
        JsonElement e = p.parse(json);
        sort(e);
        return g.toJson(e);
    }

    /**
     * 排序
     *
     * @param e
     */
    private static void sort(JsonElement e) {
        if (e.isJsonNull() || e.isJsonPrimitive()) {
            return;
        }

        if (e.isJsonArray()) {
            JsonArray a = e.getAsJsonArray();
            for (JsonElement jsonElement : a) {
                sort(jsonElement);
            }
            return;
        }

        if (e.isJsonObject()) {
            Map<String, JsonElement> tm = new TreeMap<>(getComparator());
            for (Map.Entry<String, JsonElement> en : e.getAsJsonObject().entrySet()) {
                tm.put(en.getKey(), en.getValue());
            }

            String key;
            JsonElement val;
            for (Map.Entry<String, JsonElement> en : tm.entrySet()) {
                key = en.getKey();
                val = en.getValue();
                e.getAsJsonObject().remove(key);
                e.getAsJsonObject().add(key, val);
                sort(val);
            }
        }
    }

    /**
     * 定义比较规则
     *
     * @return
     */
    private static Comparator<String> getComparator() {
        return String::compareTo;
    }

    public static JSONObject toJSONObject(@NonNull String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(@NonNull String json, Class<T> t) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            return mGson.fromJson(json, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(@NonNull String json, Type typeOfT) {
        if (TextUtils.isEmpty(json)) return null;

        try {
            return mGson.fromJson(json, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
