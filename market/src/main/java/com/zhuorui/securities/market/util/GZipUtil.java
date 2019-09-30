package com.zhuorui.securities.market.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Pengxianglin on 19/7/19.
 * GZip工具类
 */
public class GZipUtil {

    private static final String ENCODE = "UTF-8";

    /**
     * 压缩GZip
     *
     * @return String
     */
    public static byte[] compress(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(ENCODE));
            gzip.close();
        } catch ( Exception e) {
            e.printStackTrace();
            System.out.println("压缩出错：" + e.getMessage());
        }
        return out.toByteArray();
    }

    /**
     * 解压GZip
     *
     * @return String
     */
    public static String uncompressToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解压出错：" + e.getMessage());
        }
        return null;
    }
}