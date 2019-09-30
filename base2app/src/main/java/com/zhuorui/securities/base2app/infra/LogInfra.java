package com.zhuorui.securities.base2app.infra;

import com.zhuorui.securities.base2app.util.JsonUtil;

/**
 * 日志打印处理
 */
public class LogInfra {
    private static String TAG = LogInfra.class.getSimpleName();
    private static int LEVEL = android.util.Log.ERROR;
    private static boolean OPEN_LOG = false;

    /***
     * 传入App级别TAG,级别高于level才允许输出日志；初始化调用在Application
     * @param tag 日志TAG
     * @param openLog 是否开启日志打印
     * @param level 日志Level；用于判断日志是否打印
     */
    public static void init(String tag, boolean openLog, int level) {
        TAG = tag;
        OPEN_LOG = openLog;
        LEVEL = level;
    }

    /***
     * V和D只进行android输出
     * 其他会依据设置的LEVEL进行输出和存储文件
     */
    public static class Log {
        private static boolean log(int level) {
            return LEVEL > level;
        }

        public static void v(String tag, String msg) {
            if (!OPEN_LOG || log(android.util.Log.VERBOSE)) return;
            android.util.Log.v(TAG + " " + tag, msg);
        }

        public static void v(String tag, String msg, Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.VERBOSE)) return;
            android.util.Log.v(TAG + " " + tag, msg, tr);
        }

        public static void d(String tag, String msg) {
            if (!OPEN_LOG || log(android.util.Log.DEBUG)) return;
            android.util.Log.d(TAG + " " + tag, msg);
        }

        public static void d(String tag, String msg, Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.DEBUG)) return;
            android.util.Log.d(TAG + " " + tag, msg, tr);
        }

        public static void i(String tag, String msg) {
            if (!OPEN_LOG || log(android.util.Log.INFO)) return;
            android.util.Log.i(TAG + " " + tag, msg);
        }

        public static void i(String tag, Object msg) {
            if (!OPEN_LOG || log(android.util.Log.INFO)) return;
            android.util.Log.i(TAG + " " + tag, msg.getClass().getSimpleName() + ":" + JsonUtil.toJson(msg));
        }

        public static void i(String tag, String msg, Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.INFO)) return;
            android.util.Log.i(TAG + " " + tag, msg, tr);
        }

        public static void w(String tag, String msg) {
            if (!OPEN_LOG || log(android.util.Log.WARN)) return;
            android.util.Log.w(TAG + " " + tag, msg);
        }

        public static void w(String tag, Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.WARN)) return;
            android.util.Log.w(TAG + " " + tag, tr);
        }

        public static void w(String tag, String msg, Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.WARN)) return;
            android.util.Log.w(TAG + " " + tag, msg, tr);
        }

        public static void e(final String tag, final String msg) {
            if (!OPEN_LOG || log(android.util.Log.ERROR)) return;
            android.util.Log.e(TAG + " " + tag, msg);
        }


        public static void e(final String tag, final Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.ERROR)) return;
            android.util.Log.e(TAG + " " + tag, "", tr);
        }

        public static void e(final String tag, final String msg, final Throwable tr) {
            if (!OPEN_LOG || log(android.util.Log.ERROR)) return;
            android.util.Log.e(TAG + " " + tag, msg, tr);
        }
    }
}
