package com.zhuorui.securities.market.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByteBufferUtil {

    private static final String ENCODE = "UTF-8";

    public static String byteBuffer2String(ByteBuffer bytes) {
        Charset charset = Charset.forName(ENCODE);
        return charset.decode(bytes).toString();
    }

    public static byte[] byteBuffer2Byte(ByteBuffer byteBuffer) {
        int len = byteBuffer.limit() - byteBuffer.position();
        byte[] bytes = new byte[len];
        byteBuffer.get(bytes);
        return bytes;
    }

    public static ByteBuffer string2ByteBuffer(String key) {
        try {
            return ByteBuffer.wrap(key.getBytes(ENCODE));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(key.getBytes());
    }
}
