package com.zhuorui.securities.applib3.ui.contract;

import java.lang.System;

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/14
 * Desc:
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/zhuorui/securities/applib3/ui/contract/InfomationTabContract;", "", "Presenter", "View", "ViewWrapper", "applib3_debug"})
public abstract interface InfomationTabContract {
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/zhuorui/securities/applib3/ui/contract/InfomationTabContract$View;", "Lcom/zhuorui/securities/base2app/mvp/IBaseView;", "applib3_debug"})
    public static abstract interface View extends com.zhuorui.securities.base2app.mvp.IBaseView {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lcom/zhuorui/securities/applib3/ui/contract/InfomationTabContract$Presenter;", "", "fetchData", "", "applib3_debug"})
    public static abstract interface Presenter {
        
        public abstract void fetchData();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lcom/zhuorui/securities/applib3/ui/contract/InfomationTabContract$ViewWrapper;", "", "setData", "", "applib3_debug"})
    public static abstract interface ViewWrapper {
        
        public abstract void setData();
    }
}