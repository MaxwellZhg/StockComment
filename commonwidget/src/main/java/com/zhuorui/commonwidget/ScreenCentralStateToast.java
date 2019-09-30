package com.zhuorui.commonwidget;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.zhuorui.securities.base2app.BaseApplication;
import com.zhuorui.securities.base2app.util.ResUtil;

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/8/21 10:10
 * desc   : 屏幕中央带有图标提示的Toast
 */
public class ScreenCentralStateToast {

    public static void show(String message) {
        //加载Toast布局
        @SuppressLint("InflateParams") View toastRoot = LayoutInflater.from(BaseApplication.Companion.getContext()).inflate(R.layout.view_toast, null);
        //初始化布局控件
        TextView tv_message = toastRoot.findViewById(R.id.tv_message);
        //为控件设置属性
        tv_message.setText(message);
        //Toast的初始化
        Toast toastStart = new Toast(BaseApplication.Companion.getContext());
        toastStart.setGravity(Gravity.CENTER, 0, -ResUtil.INSTANCE.getDimensionDp2Px(70));
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}