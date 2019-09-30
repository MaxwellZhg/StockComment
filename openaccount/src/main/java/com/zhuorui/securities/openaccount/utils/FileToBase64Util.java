package com.zhuorui.securities.openaccount.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/29
 * Desc: base64转码工具
 */
public class FileToBase64Util {

//    /**
//     * 图片文件转Base64字符串
//     *
//     * @param path 文件所在的绝对路径加文件名
//     */
//    public static String fileBase64String(String path) {
//        try {
//            FileInputStream fis = new FileInputStream(path);//转换成输入流
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int count = 0;
//            while ((count = fis.read(buffer)) >= 0) {
//                baos.write(buffer, 0, count);//读取输入流并写入输出字节流中
//            }
//            fis.close();//关闭文件输入流
//            String uploadBuffer = "data:video/avi;base64," + Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);  //进行Base64编码
//            return uploadBuffer;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * BitMap转Base64字符串
     *
     * @param bitmap 位图
     */
    public static String bitMapBase64String(Base64Enum base64Enum, Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //进行Base64编码;
            return base64Enum.getCode() + Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * byte 转Base64字符串
     *
     * @param bytes 字节
     */
    public static String getBase64String(Base64Enum base64Enum, byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        try {
            //进行Base64编码;
            return base64Enum.getCode() + Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }
}