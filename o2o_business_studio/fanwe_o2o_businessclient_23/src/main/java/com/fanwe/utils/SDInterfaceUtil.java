package com.fanwe.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.fanwe.model.BaseCtlActModel;

public class SDInterfaceUtil {

    public static boolean dealactModel(BaseCtlActModel actModel, Activity activity) {
        if (actModel != null) {
            if (!TextUtils.isEmpty(actModel.getInfo())) {
                SDToast.showToast(actModel.getInfo());
            }
            return false;
        } else {
            SDToast.showToast("出错了！!");
            return true;
        }
    }

}
