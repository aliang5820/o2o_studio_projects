package com.fanwe.utils;

import android.text.TextUtils;

import com.fanwe.library.utils.SDToast;
import com.fanwe.model.BaseActModel;

public class SDInterfaceUtil
{

	public static boolean isActModelNull(BaseActModel actModel)
	{
		if (actModel != null)
		{
			if (!TextUtils.isEmpty(actModel.getInfo()))
			{
				SDToast.showToast(actModel.getInfo());
			}
			return false;
		} else
		{
			SDToast.showToast("接口访问失败或者json解析出错!");
			return true;
		}
	}

}
