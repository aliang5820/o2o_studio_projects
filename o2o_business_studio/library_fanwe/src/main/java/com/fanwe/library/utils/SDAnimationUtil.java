package com.fanwe.library.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class SDAnimationUtil
{

	public static String getPropertyAlpha()
	{
		return "alpha";
	}

	public static String getPropertyScaleX()
	{
		return "scaleX";
	}

	public static String getPropertyScaleY()
	{
		return "scaleY";
	}

	public static String getPropertyTranslationY()
	{
		return "translationY";
	}

	public static String getPropertyTranslationX()
	{
		return "translationX";
	}
	
	public static AnimatorSet scale(View view, float... values)
	{
		AnimatorSet animator = null;
		if (view != null)
		{
			animator = new AnimatorSet();
			
			ObjectAnimator animatorX = scaleX(view, values);
			ObjectAnimator animatorY = scaleY(view, values);
			
			animator.playTogether(animatorX, animatorY);
		}
		return animator;
	}

	public static ObjectAnimator scaleX(View view, float... values)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyScaleX(), values);
		}
		return animator;
	}

	public static ObjectAnimator scaleY(View view, float... values)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyScaleY(), values);
		}
		return animator;
	}

	public static ObjectAnimator alpha(View view, float... values)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyAlpha(), values);
		}
		return animator;
	}

	public static ObjectAnimator alphaIn(View view)
	{
		return alpha(view, 0.0f, 1.0f);
	}

	public static ObjectAnimator alphaOut(View view)
	{
		return alpha(view, 1.0f, 0.0f);
	}

	public static ObjectAnimator translateInLeft(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationX(), -view.getWidth(), 0);
		}
		return animator;
	}

	public static ObjectAnimator translateOutLeft(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationX(), 0, -view.getWidth());
		}
		return animator;
	}

	public static ObjectAnimator translateInRight(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationX(), view.getWidth(), 0);
		}
		return animator;
	}

	public static ObjectAnimator translateOutRight(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationX(), 0, view.getWidth());
		}
		return animator;
	}

	public static ObjectAnimator translateInTop(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationY(), -view.getHeight(), 0);
		}
		return animator;
	}

	public static ObjectAnimator translateOutTop(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationY(), 0, -view.getHeight());
		}
		return animator;
	}

	public static ObjectAnimator translateInBottom(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationY(), view.getHeight(), 0);
		}
		return animator;
	}

	public static ObjectAnimator translateOutBottom(View view)
	{
		ObjectAnimator animator = null;
		if (view != null)
		{
			animator = ObjectAnimator.ofFloat(view, getPropertyTranslationY(), 0, view.getHeight());
		}
		return animator;
	}

}
