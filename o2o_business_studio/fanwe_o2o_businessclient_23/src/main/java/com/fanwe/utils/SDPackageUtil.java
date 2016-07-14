package com.fanwe.utils;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import com.fanwe.application.App;

/**
 * package包工具类
 * 
 * @author zhengjun
 * 
 */
public class SDPackageUtil
{

	/**
	 * 手机上是否已经安装了某个应用
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static Boolean isPackageExist(Context context, String packageName)
	{
		if (context == null || TextUtils.isEmpty(packageName))
		{
			return null;
		}

		PackageManager manager = context.getPackageManager();
		List<PackageInfo> pkgList = manager.getInstalledPackages(0);
		for (PackageInfo pi : pkgList)
		{
			if (pi.packageName.equalsIgnoreCase(packageName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取未按转的apk的信息
	 * 
	 * @param context
	 * @param apkFilePath
	 *            apk绝对路径
	 * @return
	 */
	public static PackageInfo getApkPackageInfo(Context context, String apkFilePath)
	{
		if (context == null || TextUtils.isEmpty(apkFilePath) || !new File(apkFilePath).exists())
		{
			return null;
		}
		PackageManager pm = context.getPackageManager();
		PackageInfo apkInfo = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_META_DATA);
		return apkInfo;
	}

	/**
	 * 得到当前应用的信息
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static PackageInfo getCurrentAppPackageInfo(Context context, String packageName)
	{
		if (context == null || TextUtils.isEmpty(packageName))
		{
			return null;
		}
		PackageManager pm = context.getPackageManager();
		PackageInfo apkInfo = null;
		try
		{
			apkInfo = pm.getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apkInfo;
	}

	/**
	 * 安装apk
	 * 
	 * @param context
	 * @param apkFilePath
	 *            apk绝对路径
	 * @return
	 */
	public static Boolean installApkPackage(Context context, String apkFilePath)
	{
		if (context == null || TextUtils.isEmpty(apkFilePath) || !new File(apkFilePath).exists())
		{
			return null;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + apkFilePath), "application/vnd.android.package-archive");
		context.startActivity(intent);
		return true;
	}

	public static void startAPP(String appPackageName)
	{
		try
		{
			Intent intent = App.getApp().getPackageManager().getLaunchIntentForPackage(appPackageName);
			App.getApp().startActivity(intent);
		} catch (Exception e)
		{

		}
	}

}
