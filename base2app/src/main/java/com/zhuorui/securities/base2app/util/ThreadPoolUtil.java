package com.zhuorui.securities.base2app.util;

import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xieyingwu on 2017/7/14.
 * 构建线程池；来处理需要运行在后台的子任务；子任务与当前界面操作无关;子任务是短时性的
 */

public final class ThreadPoolUtil {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private ThreadPoolUtil() {

    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static boolean isRunInUIThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
