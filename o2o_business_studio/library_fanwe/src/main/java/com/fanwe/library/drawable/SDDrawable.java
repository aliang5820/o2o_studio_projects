package com.fanwe.library.drawable;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

public class SDDrawable extends LayerDrawable
{

	private static final int DEFAULT_COLOR = Color.parseColor("#ffffff");

	private GradientDrawable mDrawableFirst;
	private GradientDrawable mDrawableSecond;

	private int mStrokeWidthLeft;
	private int mStrokeWidthTop;
	private int mStrokeWidthRight;
	private int mStrokeWidthBottom;

	private float mCornerTopLeft;
	private float mCornerTopRight;
	private float mCornerBottomLeft;
	private float mCornerBottomRight;

	public SDDrawable()
	{
		this(new GradientDrawable[] { new GradientDrawable(), new GradientDrawable() });
	}

	public SDDrawable(Drawable[] layers)
	{
		super(layers);
		init();
	}

	private void init()
	{
		mDrawableFirst = (GradientDrawable) this.getDrawable(0);
		mDrawableSecond = (GradientDrawable) this.getDrawable(1);

		mDrawableFirst.setShape(GradientDrawable.RECTANGLE);
		mDrawableSecond.setShape(GradientDrawable.RECTANGLE);
		color(DEFAULT_COLOR);
	}

	/**
	 * 透明度
	 * 
	 * @param alpha
	 * @return
	 */
	public SDDrawable alpha(int alpha)
	{
		setAlpha(alpha);
		return this;
	}

	/**
	 * 图片颜色
	 * 
	 * @param color
	 * @return
	 */
	public SDDrawable color(int color)
	{
		mDrawableSecond.setColor(color);
		return this;
	}

	/**
	 * 边框颜色
	 * 
	 * @param color
	 * @return
	 */
	public SDDrawable strokeColor(int color)
	{
		mDrawableFirst.setColor(color);
		return this;
	}

	/**
	 * 设置圆角
	 * 
	 * @param topLeft
	 * @param topRight
	 * @param bottomLeft
	 * @param bottomRight
	 * @return
	 */
	public SDDrawable corner(float topLeft, float topRight, float bottomLeft, float bottomRight)
	{
		mCornerTopLeft = topLeft;
		mCornerTopRight = topRight;
		mCornerBottomLeft = bottomLeft;
		mCornerBottomRight = bottomRight;

		mDrawableFirst.setCornerRadii(new float[] { mCornerTopLeft, mCornerTopLeft, mCornerTopRight, mCornerTopRight, mCornerBottomRight,
				mCornerBottomRight, mCornerBottomLeft, mCornerBottomLeft });
		mDrawableSecond.setCornerRadii(new float[] { mCornerTopLeft, mCornerTopLeft, mCornerTopRight, mCornerTopRight, mCornerBottomRight,
				mCornerBottomRight, mCornerBottomLeft, mCornerBottomLeft });
		return this;
	}

	public SDDrawable cornerAll(float radius)
	{
		corner(radius, radius, radius, radius);
		return this;
	}

	public SDDrawable cornerTopLeft(float radius)
	{
		corner(radius, mCornerTopRight, mCornerBottomLeft, mCornerBottomRight);
		return this;
	}

	public SDDrawable cornerTopRight(float radius)
	{
		corner(mCornerTopLeft, radius, mCornerBottomLeft, mCornerBottomRight);
		return this;
	}

	public SDDrawable cornerBottomLeft(float radius)
	{
		corner(mCornerTopLeft, mCornerTopRight, radius, mCornerBottomRight);
		return this;
	}

	public SDDrawable cornerBottomRight(float radius)
	{
		corner(mCornerTopLeft, mCornerTopRight, mCornerBottomLeft, radius);
		return this;
	}

	/**
	 * 边框宽度
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public SDDrawable strokeWidth(int left, int top, int right, int bottom)
	{
		mStrokeWidthLeft = left;
		mStrokeWidthTop = top;
		mStrokeWidthRight = right;
		mStrokeWidthBottom = bottom;
		setLayerInset(1, mStrokeWidthLeft, mStrokeWidthTop, mStrokeWidthRight, mStrokeWidthBottom);
		return this;
	}

	public SDDrawable strokeWidthAll(int width)
	{
		strokeWidth(width, width, width, width);
		return this;
	}

	public SDDrawable strokeWidthLeft(int width)
	{
		strokeWidth(width, mStrokeWidthTop, mStrokeWidthRight, mStrokeWidthBottom);
		return this;
	}

	public SDDrawable strokeWidthTop(int width)
	{
		strokeWidth(mStrokeWidthLeft, width, mStrokeWidthRight, mStrokeWidthBottom);
		return this;
	}

	public SDDrawable strokeWidthRight(int width)
	{
		strokeWidth(mStrokeWidthLeft, mStrokeWidthTop, width, mStrokeWidthBottom);
		return this;
	}

	public SDDrawable strokeWidthBottom(int width)
	{
		strokeWidth(mStrokeWidthLeft, mStrokeWidthTop, mStrokeWidthRight, width);
		return this;
	}

	// -------------------------------StateListDrawable

	/**
	 * 获得可以根据状态变化的drawable背景
	 * 
	 * @param none
	 *            正常状态时候背景
	 * @param focus
	 *            获得焦点时候的背景
	 * @param selected
	 *            选中时候的背景
	 * @param pressed
	 *            按下时候的背景
	 * @return
	 */
	public static StateListDrawable getStateListDrawable(Drawable none, Drawable focus, Drawable selected, Drawable pressed)
	{
		StateListDrawable drawable = new StateListDrawable();
		if (none != null)
		{
			drawable.addState(getStateNone(), none);
		}
		if (focus != null)
		{
			drawable.addState(getStateFocus(), focus);
		}
		if (selected != null)
		{
			drawable.addState(getStateSelected(), selected);
		}
		if (pressed != null)
		{
			drawable.addState(getStatePressed(), pressed);
		}
		return drawable;
	}

	// -------------------------------States
	public static int[] getStateNone()
	{
		return new int[] { -android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed };
	}

	public static int[] getStateFocus()
	{
		return new int[] { android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed };
	}

	public static int[] getStateSelected()
	{
		return new int[] { -android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed };
	}

	public static int[] getStatePressed()
	{
		return new int[] { -android.R.attr.state_focused, -android.R.attr.state_selected, android.R.attr.state_pressed };
	}

}
