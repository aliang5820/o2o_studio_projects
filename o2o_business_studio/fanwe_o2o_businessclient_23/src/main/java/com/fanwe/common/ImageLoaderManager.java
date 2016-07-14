package com.fanwe.common;

import android.graphics.Bitmap;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderManager
{

	private static ImageLoader mImageLoader = ImageLoader.getInstance();
	private static DisplayImageOptions options = null;

	private ImageLoaderManager()
	{
	}

	public static ImageLoader getImageLoader()
	{
		initImageLoader();
		return mImageLoader;
	}

	private static DisplayImageOptions getOptions()
	{
		if (options == null)
		{
			options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.nopic).showImageOnFail(R.drawable.nopic).resetViewBeforeLoading(true).cacheOnDisc(true)
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();
		}
		return options;
	}

	private static void initImageLoader()
	{
		if (!mImageLoader.isInited())
		{
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(App.getApp()).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(getOptions()).build();
			mImageLoader.init(config);
		}
	}
	
	private static DisplayImageOptions.Builder getBuilderDefault()
	{
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.nopic)
				.showImageOnFail(R.drawable.nopic).resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(false)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true);
		return builder;
	}
	
	public static DisplayImageOptions getOptionsNoCache()
	{
		DisplayImageOptions options = getBuilderDefault().cacheOnDisk(false).cacheInMemory(false).build();
		return options;
	}

}
