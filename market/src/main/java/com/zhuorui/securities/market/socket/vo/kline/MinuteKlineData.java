package com.zhuorui.securities.market.socket.vo.kline;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 分时数据基础对象
 */
@Entity
public class MinuteKlineData {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 交易量
     */
    private double vol;

    /**
     * 交易金额
     */
    private double amount;

    /**
     * 开盘价格
     */
    private double openPrice;

    /**
     * 均价
     */
    private double avgPrice;

    /**
     * 当前价格
     */
    private double price;

    /**
     * 今日最高价
     */
    private double high;

    /**
     * 今日最低价
     */
    private double low;

    /**
     * 日期时间
     */
    private long dateTime;

    @Generated(hash = 1878482173)
    public MinuteKlineData(Long id, double vol, double amount, double openPrice,
            double avgPrice, double price, double high, double low, long dateTime) {
        this.id = id;
        this.vol = vol;
        this.amount = amount;
        this.openPrice = openPrice;
        this.avgPrice = avgPrice;
        this.price = price;
        this.high = high;
        this.low = low;
        this.dateTime = dateTime;
    }

    @Generated(hash = 1842260906)
    public MinuteKlineData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
