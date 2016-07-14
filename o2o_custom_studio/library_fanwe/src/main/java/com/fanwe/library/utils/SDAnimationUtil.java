package com.fanwe.library.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class SDAnimationUtil
{

	/**
	 * 左侧滑入
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateInLeft(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 左侧滑出
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateOutLeft(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 右侧滑入
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateInRight(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 右侧滑出
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateOutRight(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 顶部滑入
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateInTop(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 顶部滑出
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateOutTop(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 底部滑入
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateInBottom(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 底部滑出
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static TranslateAnimation newTranslateOutBottom(long durationMillis, AnimationListener listener)
	{
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 淡入
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static AlphaAnimation newAlphaIn(long durationMillis, AnimationListener listener)
	{
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	/**
	 * 淡出
	 * 
	 * @param durationMillis
	 * @param listener
	 * @return
	 */
	public static AlphaAnimation newAlphaOut(long durationMillis, AnimationListener listener)
	{
		AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(listener);
		return animation;
	}

	public static void show(View view, Animation animation)
	{
		if (view.getVisibility() != View.VISIBLE)
		{
			if (animation != null)
			{
				view.startAnimation(animation);
			}
			view.setVisibility(View.VISIBLE);
		}
	}

	public static void gone(View view, Animation animation)
	{
		if (view.getVisibility() != View.GONE)
		{
			if (animation != null)
			{
				view.startAnimation(animation);
			}
			view.setVisibility(View.GONE);
		}
	}

}
