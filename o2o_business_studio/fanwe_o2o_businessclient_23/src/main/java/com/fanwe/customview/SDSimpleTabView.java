package com.fanwe.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.businessclient.R;

public class SDSimpleTabView extends SDBottomNavigatorBaseItem
{

	public TextView mTxtTabName = null;

	private int mTextColorNormal = 0;
	private int mTextColorSelect = 0;

	private int mBackgroundImageNormal = 0;
	private int mBackgroundImageSelect = 0;

	public TextView getmTxtTabName()
	{
		return mTxtTabName;
	}

	public void setmTxtTabName(TextView mTxtTabName)
	{
		this.mTxtTabName = mTxtTabName;
	}

	public int getmBackgroundImageNormal()
	{
		return mBackgroundImageNormal;
	}

	public void setmBackgroundImageNormal(int mBackgroundImageNormal)
	{
		this.mBackgroundImageNormal = mBackgroundImageNormal;
	}

	public int getmBackgroundImageSelect()
	{
		return mBackgroundImageSelect;
	}

	public void setmBackgroundImageSelect(int mBackgroundImageSelect)
	{
		this.mBackgroundImageSelect = mBackgroundImageSelect;
	}

	public int getmTextColorNormal()
	{
		return mTextColorNormal;
	}

	public void setmTextColorNormal(int mTextColorNormal)
	{
		this.mTextColorNormal = mTextColorNormal;
	}

	public int getmTextColorSelect()
	{
		return mTextColorSelect;
	}

	public void setmTextColorSelect(int mTextColorSelect)
	{
		this.mTextColorSelect = mTextColorSelect;
	}

	public SDSimpleTabView(Context context)
	{
		super(context);
		init();
	}

	public SDSimpleTabView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mTxtTabName = new TextView(getContext());
		mTxtTabName.setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		this.addView(mTxtTabName, params);
		this.setGravity(Gravity.CENTER);
		setSelectedState(false);
	}

	public void setTabName(String name)
	{
		if (name != null)
		{
			this.mTxtTabName.setText(name);
		}
	}

	@Override
	public void setSelectedState(boolean select)
	{
		if (select)
		{
			this.setSelected(true);
			if (mTextColorSelect != 0)
			{
				this.mTxtTabName.setTextColor(mTextColorSelect);
			}
			if (mBackgroundImageSelect != 0)
			{
				this.setBackgroundResource(mBackgroundImageSelect);
			} else
			{
				this.setBackgroundResource(R.drawable.bg_transparent);
			}

		} else
		{
			this.setSelected(false);
			if (mTextColorNormal != 0)
			{
				this.mTxtTabName.setTextColor(mTextColorNormal);
			}
			if (mBackgroundImageNormal != 0)
			{
				this.setBackgroundResource(mBackgroundImageNormal);
			} else
			{
				this.setBackgroundResource(R.drawable.bg_transparent);
			}
		}
		mSelected = select;
	}

	@Override
	public boolean getSelectedState()
	{
		// TODO Auto-generated method stub
		return mSelected;
	}

}
