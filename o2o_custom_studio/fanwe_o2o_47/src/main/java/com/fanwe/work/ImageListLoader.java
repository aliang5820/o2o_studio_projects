package com.fanwe.work;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageListLoader
{

	private List<String> mListUrl;
	private List<String> mListUrlSuccess = new ArrayList<String>();
	private ImageListLoaderListener mListener;
	private boolean mIsBusy = false;
	private int mFinishCount = 0;
	private SDTimer mTimer = new SDTimer();

	private long mTimeOut = 0;

	public ImageListLoader(long timeOut)
	{
		super();
		this.mTimeOut = timeOut;
	}

	public void loadImageList(List<String> listUrl, ImageListLoaderListener listener)
	{
		if (mIsBusy)
		{
			return;
		}
		this.mListener = listener;
		this.mListUrl = listUrl;
		notifyStart();
		if (!SDCollectionUtil.isEmpty(mListUrl))
		{
			for (String url : mListUrl)
			{
				if (!TextUtils.isEmpty(url))
				{
					ImageLoader.getInstance().loadImage(url, new DefaultImageLoadingListener());
				}
			}
		} else
		{
			notifyFinish();
		}
	}

	private void startTimer()
	{
		if (mTimeOut > 0)
		{
			mTimer.startWork(mTimeOut, mTimeOut, new SDTimerListener()
			{

				@Override
				public void onWorkMain()
				{
					notifyTimeOut();
				}

				@Override
				public void onWork()
				{

				}
			});
		}
	}

	class DefaultImageLoadingListener implements ImageLoadingListener
	{

		@Override
		public void onLoadingStarted(String imageUri, View view)
		{

		}

		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason)
		{
			loadComplete(imageUri);
		}

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
		{
			loadSuccess(imageUri);
			loadComplete(imageUri);
		}

		@Override
		public void onLoadingCancelled(String imageUri, View view)
		{
			loadComplete(imageUri);
		}
	}

	private void loadSuccess(String url)
	{
		mListUrlSuccess.add(url);
		if (mListener != null)
		{
			mListener.onSuccess(url);
		}
	}

	private void loadComplete(String url)
	{
		mFinishCount++;
		if (mFinishCount == mListUrl.size()) // 全部任务已经完成（包括成功和失败的）
		{
			notifyFinish();
		}
	}

	private void beforeStart()
	{
		mIsBusy = true;
		mFinishCount = 0;
		mListUrlSuccess.clear();
		startTimer();
	}

	private void notifyStart()
	{
		beforeStart();
		if (mListener != null)
		{
			mListener.onStart();
		}
	}

	private void beforeFinish()
	{
		mIsBusy = false;
		mTimer.stopWork();
	}

	private void notifyFinish()
	{
		beforeFinish();
		if (mListener != null)
		{
			mListener.onFinish(mListUrlSuccess);
		}
	}

	protected void notifyTimeOut()
	{
		beforeFinish();
		if (mListener != null)
		{
			mListener.onTimeOut();
		}
	}

	public interface ImageListLoaderListener
	{
		public void onStart();

		public void onSuccess(String urlSuccess);

		public void onFinish(List<String> listUrlSuccess);

		public void onTimeOut();
	}

}
