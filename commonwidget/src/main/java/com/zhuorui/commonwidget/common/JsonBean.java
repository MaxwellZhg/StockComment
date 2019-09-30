package com.zhuorui.commonwidget.common;

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/19
 * Desc:
 */
public class JsonBean {

    /**
     * cn_py : hanguo
     * hant : 韓國
     * hant_py : hanguo
     * used : false
     * number : +82
     * en : Korea
     * cn : 韩国
     */

    private String cn_py;
    private String hant;
    private String hant_py;
    private boolean used;
    private String number;
    private String en;
    private String cn;
    private String sortLetters;//显示数据拼音的首字母
    private String sortEnletters;

    public String getSortEnletters() {
        return sortEnletters;
    }

    public void setSortEnletters(String sortEnletters) {
        this.sortEnletters = sortEnletters;
    }

    public String getCn_py() {
        return cn_py;
    }

    public void setCn_py(String cn_py) {
        this.cn_py = cn_py;
    }

    public String getHant() {
        return hant;
    }

    public void setHant(String hant) {
        this.hant = hant;
    }

    public String getHant_py() {
        return hant_py;
    }

    public void setHant_py(String hant_py) {
        this.hant_py = hant_py;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public JsonBean(String cn_py, String hant, String hant_py, boolean used, String number, String en, String cn, String sortLetters, String sortEnletters) {
        this.cn_py = cn_py;
        this.hant = hant;
        this.hant_py = hant_py;
        this.used = used;
        this.number = number;
        this.en = en;
        this.cn = cn;
        this.sortLetters = sortLetters;
        this.sortEnletters = sortEnletters;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "cn_py='" + cn_py + '\'' +
                ", hant='" + hant + '\'' +
                ", hant_py='" + hant_py + '\'' +
                ", used=" + used +
                ", number='" + number + '\'' +
                ", en='" + en + '\'' +
                ", cn='" + cn + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                ", sortEnletters='" + sortEnletters + '\'' +
                '}';
    }
}
