package com.fanwe.http;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public abstract class SDRequestCallBackProxy<E> extends SDRequestCallBack<E>
{

	private SDRequestCallBack<E> mOriginalCallBack;

	public SDRequestCallBackProxy(SDRequestCallBack<E> originalCallBack)
	{
		this.mOriginalCallBack = originalCallBack;
		this.showToast = false;
	}

	public SDRequestCallBack<E> getOriginalCallBack()
	{
		return mOriginalCallBack;
	}

	@Override
	public void onStart()
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onStart();
		}
		super.onStart();
	}

	@Override
	public void onSuccessBack(ResponseInfo<String> responseInfo)
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onSuccessBack(responseInfo);
		}
		super.onSuccessBack(responseInfo);
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo)
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onSuccess(responseInfo);
		}
		super.onSuccess(responseInfo);
	}

	@Override
	public void onLoading(long total, long current, boolean isUploading)
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onLoading(total, current, isUploading);
		}
		super.onLoading(total, current, isUploading);
	}

	@Override
	public void onFailure(HttpException error, String msg)
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onFailure(error, msg);
		}
		super.onFailure(error, msg);
	}

	@Override
	public void onCancelled()
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onCancelled();
		}
		super.onCancelled();
	}

	@Override
	public void onFinish()
	{
		if (mOriginalCallBack != null)
		{
			mOriginalCallBack.onFinish();
		}
		super.onFinish();
	}

}
