package com.zhuorui.securities.base2app.util;

import java.util.Calendar;

/**
 * Create by xieyingwu on 2018/8/29
 * 时区util
 */
public class TimeZoneUtil {
    private TimeZoneUtil() {
    }

    public static int getTimeOffset() {
        Calendar instance = Calendar.getInstance();
        int zoneOffset = instance.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = instance.get(java.util.Calendar.DST_OFFSET);
        int offsetMills = zoneOffset + dstOffset;
        return offsetMills / 1000 / 60 / 60;
    }
}
