package com.zhuorui.securities.openaccount.utils;

/**
 * base64 类型
 * @author wangjiafang
 * @date 2019/8/6
 */
public enum Base64Enum {

    /**
     * mp4
     */
    MP4("data:video/mp4;","mp4"),

    /**
     * avi
     */
    AVI("data:video/avi;","avi"),

    /**
     * flv
     */
    FLV("data:video/flv;","flv"),

    /**
     * png
     */
    PNG("data:image/png;","png"),

    /**
     * jpeg
     */
    JPEG("data:image/jpeg;","jpeg"),

    /**
     * jpg
     */
    JPG("data:image/jpg;","jpg");



    private String code;
    private String desc;

    public static Base64Enum getByCode(String code) {

        for (Base64Enum ts : values()) {
            if (ts.getCode().equalsIgnoreCase(code)) {
                return ts;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }


    Base64Enum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}