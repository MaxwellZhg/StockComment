package com.zhuorui.securities.base2app.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xieyingwu on 2018/1/4.
 * 流处理的工具类
 */

public final class StreamUtil {
    private StreamUtil() {
    }

    /**
     * 关闭流
     *
     * @param closeable closeable
     */
    public static void closeStream(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件字节流
     *
     * @param filePath 文件路径
     * @return byte[]字节数组，null表明读取出现错误
     */
    public static byte[] readFileStream(String filePath) {
        File f;
        try {
            if (TextUtils.isEmpty(filePath)) return null;
            f = new File(filePath);
            if (!f.exists() || !f.canRead()) {
                return new byte[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*关闭流*/
            StreamUtil.closeStream(in);
            StreamUtil.closeStream(bos);
        }
        return null;
    }

    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(bos);
            closeStream(is);
        }
        return null;
    }
}
