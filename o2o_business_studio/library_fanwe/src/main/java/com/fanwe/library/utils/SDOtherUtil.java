package com.fanwe.library.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.fanwe.library.SDLibrary;

public class SDOtherUtil
{

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void copyText(CharSequence content)
	{
		if (getBuildVersion() < 11)
		{
			android.text.ClipboardManager clip = getOldClipboardManager();
			clip.setText(content);
		} else
		{
			android.content.ClipboardManager clip = getNewClipboardManager();
			clip.setPrimaryClip(ClipData.newPlainText(null, content));
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static CharSequence pasteText()
	{
		CharSequence content = null;
		if (getBuildVersion() < 11)
		{
			android.text.ClipboardManager clip = getOldClipboardManager();
			if (clip.hasText())
			{
				content = clip.getText();
			}
		} else
		{
			android.content.ClipboardManager clip = getNewClipboardManager();
			if (clip.hasPrimaryClip())
			{
				content = clip.getPrimaryClip().getItemAt(0).getText();
			}
		}
		return content;
	}

	@SuppressWarnings("deprecation")
	public static android.text.ClipboardManager getOldClipboardManager()
	{
		android.text.ClipboardManager clip = (android.text.ClipboardManager) SDLibrary.getInstance().getApplication()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return clip;
	}

	public static android.content.ClipboardManager getNewClipboardManager()
	{
		android.content.ClipboardManager clip = (android.content.ClipboardManager) SDLibrary.getInstance().getApplication()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		return clip;
	}

	public static int getBuildVersion()
	{
		return Build.VERSION.SDK_INT;
	}

	public static String hideMobile(String mobile)
	{
		String result = null;
		if (!TextUtils.isEmpty(mobile) && TextUtils.isDigitsOnly(mobile) && mobile.length() == 11)
		{
			result = mobile.substring(0, 3) + "****" + mobile.substring(7);
		}
		return result;
	}

	public static Type[] getType(Class<?> clazz)
	{
		Type[] types = null;
		if (clazz != null)
		{
			Type type = clazz.getGenericSuperclass();
			ParameterizedType parameterizedType = (ParameterizedType) type;
			types = parameterizedType.getActualTypeArguments();
		}
		return types;
	}

	public static Type getType(Class<?> clazz, int index)
	{
		Type type = null;
		Type[] types = getType(clazz);
		if (types != null && index >= 0 && types.length > index)
		{
			type = types[index];
		}
		return type;
	}

}
