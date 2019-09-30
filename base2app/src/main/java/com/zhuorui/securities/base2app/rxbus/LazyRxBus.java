package com.zhuorui.securities.base2app.rxbus;

public class LazyRxBus {
    private volatile RxBus RxBus;

    RxBus getRxBus() {
        if (RxBus == null) {
            synchronized (this) {
                if (RxBus == null) {
                    RxBus = new RxBus();
                }
            }
        }
        return RxBus;
    }
}