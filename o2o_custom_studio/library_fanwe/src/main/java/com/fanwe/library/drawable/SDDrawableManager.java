package com.fanwe.library.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.config.SDLibraryConfig;

public class SDDrawableManager
{

	private int mColorMain;
	private int mColorStroke;
	private int mColorMainPress;
	private int mColorGrayPress;

	private float mCorner;
	private int mStrokeWidth;

	public SDDrawableManager()
	{
		SDLibraryConfig config = SDLibrary.getInstance().getConfig();
		if (config != null)
		{
			setmColorMain(config.getmMainColor());
			setmColorMainPress(config.getmMainColorPress());
			setmColorStroke(config.getmStrokeColor());
			setmColorGrayPress(config.getmGrayPressColor());
			setmCorner(config.getmCornerRadius());
			setmStrokeWidth(config.getmStrokeWidth());
		}
	}

	// -------------------------------mainColor
	public Drawable getLayerMainColor(boolean corner)
	{
		SDDrawable drawable = new SDDrawable();
		drawable.color(getmColorMain()).strokeColor(getmColorMain()).strokeWidthAll(getmStrokeWidth());
		if (corner)
		{
			drawable.cornerAll(getmCorner());
		}
		return drawable;
	}

	// --------------------------------white
	public Drawable getLayerWhiteStrokeItemSingle(boolean corner)
	{
		SDDrawable drawable = new SDDrawable();
		drawable.strokeColor(getmColorStroke()).strokeWidthAll(getmStrokeWidth());
		if (corner)
		{
			drawable.cornerAll(getmCorner());
		}
		return drawable;
	}

	public Drawable getLayerWhiteStrokeItemTop(boolean corner)
	{
		SDDrawable drawable = new SDDrawable();
		drawable.strokeColor(getmColorStroke()).strokeWidth(getmStrokeWidth(), getmStrokeWidth(), getmStrokeWidth(), 0);
		if (corner)
		{
			drawable.corner(getmCorner(), getmCorner(), 0, 0);
		}
		return drawable;
	}

	public Drawable getLayerWhiteStrokeItemMiddle()
	{
		SDDrawable drawable = new SDDrawable();
		drawable.strokeColor(getmColorStroke()).strokeWidth(getmStrokeWidth(), getmStrokeWidth(), getmStrokeWidth(), 0);
		return drawable;
	}

	public Drawable getLayerWhiteStrokeItemBottom(boolean corner)
	{
		SDDrawable drawable = new SDDrawable();
		drawable.strokeColor(getmColorStroke()).strokeWidthAll(getmStrokeWidth());
		if (corner)
		{
			drawable.corner(0, 0, getmCorner(), getmCorner());
		}
		return drawable;
	}

	// --------------------------mainColorPress
	public Drawable getLayerMainColorPress(boolean corner)
	{
		SDDrawable drawable = new SDDrawable();
		drawable.color(getmColorMainPress()).strokeColor(getmColorMainPress()).strokeWidthAll(getmStrokeWidth());
		if (corner)
		{
			drawable.cornerAll(getmCorner());
		}
		return drawable;
	}

	// ---------------------------------gray
	public Drawable getLayerGrayStrokeItemSingle(boolean corner)
	{
		SDDrawable drawable = (SDDrawable) getLayerWhiteStrokeItemSingle(corner);
		drawable.color(getmColorGrayPress());
		return drawable;
	}

	public Drawable getLayerGrayStrokeItemTop(boolean corner)
	{
		SDDrawable drawable = (SDDrawable) getLayerWhiteStrokeItemTop(corner);
		drawable.color(getmColorGrayPress());
		return drawable;
	}

	public Drawable getLayerGrayStrokeItemMiddle()
	{
		SDDrawable drawable = (SDDrawable) getLayerWhiteStrokeItemMiddle();
		drawable.color(getmColorGrayPress());
		return drawable;
	}

	public Drawable getLayerGrayStrokeItemBottom(boolean corner)
	{
		SDDrawable drawable = (SDDrawable) getLayerWhiteStrokeItemBottom(corner);
		drawable.color(getmColorGrayPress());
		return drawable;
	}

	// ----------------------------selector
	public Drawable getSelectorWhiteGray(boolean corner)
	{
		SDDrawable white = new SDDrawable();
		if (corner)
		{
			white.cornerAll(getmCorner());
		}

		SDDrawable gray = new SDDrawable();
		gray.color(getmColorGrayPress());
		if (corner)
		{
			gray.cornerAll(getmCorner());
		}
		StateListDrawable drawable = SDDrawable.getStateListDrawable(white, null, null, gray);
		return drawable;
	}

	public Drawable getSelectorMainColor(boolean corner)
	{
		StateListDrawable drawable = SDDrawable.getStateListDrawable(getLayerMainColor(corner), null, null, getLayerMainColorPress(corner));
		return drawable;
	}

	public Drawable getSelectorWhiteGrayStrokeItemSingle(boolean corner)
	{
		StateListDrawable drawable = SDDrawable.getStateListDrawable(getLayerWhiteStrokeItemSingle(corner), null, null,
				getLayerGrayStrokeItemSingle(corner));
		return drawable;
	}

	public Drawable getSelectorWhiteGrayStrokeItemTop(boolean corner)
	{
		StateListDrawable drawable = SDDrawable.getStateListDrawable(getLayerWhiteStrokeItemTop(corner), null, null,
				getLayerGrayStrokeItemTop(corner));
		return drawable;
	}

	public Drawable getSelectorWhiteGrayStrokeItemMiddle()
	{
		StateListDrawable drawable = SDDrawable.getStateListDrawable(getLayerWhiteStrokeItemMiddle(), null, null, getLayerGrayStrokeItemMiddle());
		return drawable;
	}

	public Drawable getSelectorWhiteGrayStrokeItemBottom(boolean corner)
	{
		StateListDrawable drawable = SDDrawable.getStateListDrawable(getLayerWhiteStrokeItemBottom(corner), null, null,
				getLayerGrayStrokeItemBottom(corner));
		return drawable;
	}

	// ----------------------getter setter-----------------------

	public int getmColorMain()
	{
		return mColorMain;
	}

	public int getmStrokeWidth()
	{
		return mStrokeWidth;
	}

	public void setmStrokeWidth(int mStrokeWidth)
	{
		this.mStrokeWidth = mStrokeWidth;
	}

	public float getmCorner()
	{
		return mCorner;
	}

	public void setmCorner(float mCorner)
	{
		this.mCorner = mCorner;
	}

	public void setmColorMain(int mColorMain)
	{
		this.mColorMain = mColorMain;
	}

	public int getmColorStroke()
	{
		return mColorStroke;
	}

	public void setmColorStroke(int mColorStroke)
	{
		this.mColorStroke = mColorStroke;
	}

	public int getmColorMainPress()
	{
		return mColorMainPress;
	}

	public void setmColorMainPress(int mColorMainPress)
	{
		this.mColorMainPress = mColorMainPress;
	}

	public int getmColorGrayPress()
	{
		return mColorGrayPress;
	}

	public void setmColorGrayPress(int mColorGrayPress)
	{
		this.mColorGrayPress = mColorGrayPress;
	}

}
