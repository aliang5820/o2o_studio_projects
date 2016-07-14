package com.fanwe.utils;

import android.content.Context;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SDUIUtil
{

	public static DisplayMetrics getDisplayMetrics(Context context)
	{
		return context.getResources().getDisplayMetrics();
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		DisplayMetrics metrics = getDisplayMetrics(context);
		return metrics.widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		DisplayMetrics metrics = getDisplayMetrics(context);
		return metrics.heightPixels;
	}

	/**
	 * 获取屏幕密度.
	 * 
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 获取字体刻度.
	 * 
	 * @return
	 */
	public static float getScaledDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().scaledDensity;
	}

	/**
	 * sp转px.
	 * 
	 * @param context
	 * @param sp
	 * @return
	 */
	public static int sp2px(Context context, float sp)
	{
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (sp * fontScale + 0.5f);
	}

	/**
	 * dp转px.
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * px转dp.
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int px2dp(Context context, float px)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 计算缩放高度.
	 * 
	 * @param defaultWidth
	 *            默认宽度
	 * @param defaultHeight
	 *            默认高度
	 * @param actualWidth
	 *            实际宽度
	 * @return 实际高度
	 */
	public static int scaleHeight(int defaultWidth, int defaultHeight, int actualWidth)
	{
		return defaultHeight * actualWidth / defaultWidth;
	}

	/**
	 * 计算缩放宽度.
	 * 
	 * @param defaultWidth
	 *            默认宽度
	 * @param defaultHeight
	 *            默认高度
	 * @param actualHeight
	 *            实际高度
	 * @return 实际宽度
	 */
	public static int scaleWidth(int defaultWidth, int defaultHeight, int actualHeight)
	{
		return defaultWidth * actualHeight / defaultHeight;
	}

	/**
	 * 判断当前线程是否是UI线程.
	 * 
	 * @return
	 */
	public static boolean isUIThread()
	{
		return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
	}

	/**
	 * 隐藏输入法
	 * 
	 * @param context
	 */
	public static void hideInputMethod(Context context)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
		{
			imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 显示输入法
	 * 
	 * @param context
	 * @param view
	 * @param requestFocus
	 */
	public static void showInputMethod(Context context, View view, boolean requestFocus)
	{
		if (requestFocus)
		{
			view.requestFocus();
		}
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void measureView(View v)
	{
		if (v == null)
		{
			return;
		}
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
	}

	public static boolean isMainThread()
	{
		return Looper.getMainLooper() == Looper.myLooper();
	}

	public static void setListViewHeightBasedOnChildren(ListView listView)
	{
		if (listView == null)
			return;

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
		{
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++)
		{
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
