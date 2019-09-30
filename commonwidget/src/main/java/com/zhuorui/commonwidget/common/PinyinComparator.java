package com.zhuorui.commonwidget.common;

import java.util.Comparator;

public class PinyinComparator implements Comparator<JsonBean> {

    public int compare(JsonBean o1, JsonBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("常用地区")
                || o2.getSortLetters().equals("常用地区")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}