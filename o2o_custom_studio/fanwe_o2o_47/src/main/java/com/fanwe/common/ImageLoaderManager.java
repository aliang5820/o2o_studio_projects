package com.fanwe.common;

import java.io.File;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.fanwe.app.App;
import com.fanwe.o2o.newo2o.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoaderManager
{

	public static void initImageLoader()
	{
		if (!ImageLoader.getInstance().isInited())
		{
			ImageLoaderConfiguration config = getConfigurationDefault();
			ImageLoader.getInstance().init(config);
		}
	}

	private static ImageLoaderConfiguration getConfigurationDefault()
	{
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(App.getApplication()).memoryCacheSize(2 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory().defaultDisplayImageOptions(getOptionsDefault()).build();
		return config;
	}

	private static DisplayImageOptions getOptionsDefault()
	{
		DisplayImageOptions options = getBuilderDefault().build();
		return options;
	}

	private static DisplayImageOptions.Builder getBuilderDefault()
	{
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.nopic)
				.showImageOnFail(R.drawable.nopic).resetViewBeforeLoading(true).imageScaleType(ImageScaleType.EXACTLY).cacheOnDisk(true)
				.cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true);
		return builder;
	}

	public static DisplayImageOptions getOptionsNoCache()
	{
		DisplayImageOptions options = getBuilderDefault().cacheOnDisk(false).cacheInMemory(false).build();
		return options;
	}

	public static DisplayImageOptions getOptionsNopicSmall()
	{
		DisplayImageOptions options = getBuilderDefault().showImageForEmptyUri(R.drawable.nopic_expression)
				.showImageOnFail(R.drawable.nopic_expression).build();
		return options;
	}

	public static DisplayImageOptions getOptionsNoCacheNoResetViewBeforeLoading()
	{
		DisplayImageOptions options = getBuilderDefault().cacheOnDisk(false).cacheInMemory(false).resetViewBeforeLoading(false).build();
		return options;
	}

	public static DisplayImageOptions getOptionsNoResetViewBeforeLoading()
	{
		DisplayImageOptions options = getBuilderDefault().resetViewBeforeLoading(false).build();
		return options;
	}

	public static boolean isCacheExistOnDisk(String url)
	{
		if (!TextUtils.isEmpty(url))
		{
			File file = ImageLoader.getInstance().getDiskCache().get(url);
			if (file != null && file.exists()) // 缓存存在
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isCacheExistInMemory(String url)
	{
		if (!TextUtils.isEmpty(url))
		{
			Bitmap bmp = ImageLoader.getInstance().getMemoryCache().get(url);
			if (bmp != null) // 缓存存在
			{
				return true;
			}
		}
		return false;
	}

}
