package com.zhuorui.securities.base2app.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.zhuorui.securities.base2app.rxbus.RxBus;
import okhttp3.ResponseBody;

/**
 * Created by xieyingwu on 2017/10/30.
 * 文件操作的工具类
 */

public class FileUtil {

    private static final String TAG = FileUtil.class.getName();

    /**
     * 保存网络文件到本地
     */
    private static boolean writeResponseBodyToDisk(ResponseBody body, File targetFile, boolean withProgress) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                String name = targetFile.getName();
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(targetFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    if (withProgress) {
                        fileSizeDownloaded += read;
                        ProgressEvent.sendProgressEvent(fileSize, fileSizeDownloaded, name);
                    }
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean writeResponseBodyToDiskWithProgress(ResponseBody body, File targetFile) {
        return writeResponseBodyToDisk(body, targetFile, true);
    }

    public static boolean writeResponseBodyToDiskWithoutProgress(ResponseBody body, File targetFile) {
        return writeResponseBodyToDisk(body, targetFile, false);
    }

    /**
     * 通知下载进度的Event
     */
    public static class ProgressEvent {
        public long allSize;
        public long downloadedSize;
        public String fileName;

        private ProgressEvent(long allSize, long downloadedSize, String fileName) {
            this.allSize = allSize;
            this.downloadedSize = downloadedSize;
            this.fileName = fileName;
        }

        public static void sendProgressEvent(long allSize, long downloadedSize, String fileName) {
            RxBus.getDefault().post(new ProgressEvent(allSize, downloadedSize, fileName));
        }
    }

    /**
     *
     * 将asset 下
     * @param context
     * @param fileName
     * @return
     */
    public static String readAssets(Context context, String fileName) {
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
