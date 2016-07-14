package com.fanwe.utils;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

public class SDSystemServiceUtil
{

	/**
	 * 获得音频管理器
	 * 
	 * @param context
	 * @return
	 */
	public static AudioManager getAudioManager(Context context)
	{
		if (context == null)
		{
			return null;
		}
		AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		return manager;
	}

	/**
	 * 获得电话管理器
	 * 
	 * @param context
	 * @return
	 */
	public static TelephonyManager getTelephonyManager(Context context)
	{
		if (context == null)
		{
			return null;
		}
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return manager;
	}

	/**
	 * 获得连接管理器
	 * 
	 * @param context
	 * @return
	 */
	public static ConnectivityManager getConnectivityManager(Context context)
	{
		if (context == null)
		{
			return null;
		}
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager;
	}

	/**
	 * 获得震动管理器
	 * 
	 * @param context
	 * @return
	 */
	public static Vibrator getVibrator(Context context)
	{
		if (context == null)
		{
			return null;
		}
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		return vibrator;
	}

}
