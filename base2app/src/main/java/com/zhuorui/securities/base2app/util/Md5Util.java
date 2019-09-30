package com.zhuorui.securities.base2app.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xieyingwu on 2017/3/23.
 * MD5计算工具
 */

public final class Md5Util {

    /**
     * 获取指定字节数组的md5
     *
     * @param str 需要被加密的字符串
     * @return 小写md5值
     */
    public static String getMd5Str(String str) {
        return getMd5Str(str.getBytes());
    }

    /**
     * 获取指定字节数组的md5
     *
     * @param bytes 字节数组
     * @return 小写md5值
     */
    public static String getMd5Str(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] digestValues = digest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digestValues) {
                int temp = b & 255;
                if (temp < 16) {
                    sb.append("0").append(Integer.toHexString(temp));
                } else {
                    sb.append(Integer.toHexString(temp));
                }
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取单个文件的MD5值
     *
     * @param file file
     * @return md5
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            /*生成MD5类的实例*/
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            FileInputStream fs = new FileInputStream(file);
            BufferedInputStream bi = new BufferedInputStream(fs);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            /*将文件以字节方式读到数组buf中*/
            while ((len = bi.read(buf)) != -1) {
                /*执行MD5算法*/
                md5.update(buf, 0, len);
            }
            for (byte by : md5.digest()) {
                /*将生成的字节md5值转换成字符串*/
                sb.append(String.format("%02x", by));
            }
            bo.close();
            bi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getFileMd5(File file, int start, int end) {
        try {
            String fileMD5 = getFileMD5(file);
            if (TextUtils.isEmpty(fileMD5)) return "";
            return fileMD5.substring(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /***
     * 通过文件路径读取文件的MD5
     *
     * @param filepath 文件路径
     * @return md5
     */
    public static String getFileMD5(String filepath) {
        File file = new File(filepath);
        return getFileMD5(file);
    }
}
