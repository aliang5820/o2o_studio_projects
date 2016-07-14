package com.fanwe.http;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.BaseActModel;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public abstract class SDRequestCallBack<E> extends RequestCallBack<String>
{
	public ResponseInfo<String> responseInfo;
	public E actModel;
	public boolean showToast = true;

	public SDRequestCallBack()
	{
		this(true);
	}

	public SDRequestCallBack(boolean showToast)
	{
		this.showToast = showToast;
	}

	@Override
	public void onSuccessBack(ResponseInfo<String> responseInfo)
	{
		this.responseInfo = responseInfo;
		parseActModel();
		showToast();
		super.onSuccessBack(responseInfo);
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo)
	{
		onSuccess(actModel);
	}

	protected void onSuccess(E actModel)
	{

	}

	@SuppressWarnings("unchecked")
	protected void parseActModel()
	{
		Class<E> clazz = null;
		Type type = SDOtherUtil.getType(getClass(), 0);
		if (type instanceof Class)
		{
			clazz = (Class<E>) type;
			actModel = JSON.parseObject(this.responseInfo.result, clazz);
		}
	}

	public void showToast()
	{
		if (showToast)
		{
			if (actModel instanceof BaseActModel)
			{
				BaseActModel baseActModel = (BaseActModel) actModel;
				SDToast.showToast(baseActModel.getInfo());
			}
		}
	}
}
