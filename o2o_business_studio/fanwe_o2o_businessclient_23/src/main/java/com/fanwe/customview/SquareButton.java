package com.fanwe.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author yhz
 * @create time 2014-7-30
 */
public class SquareButton extends Button
{
	private OnGetTextOnClick mOnGetTextOnClick = null;
	private Button mBtton = null;

	public SquareButton(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.setOnClickListener(onClickListener);
		mBtton = this;
	}

	public SquareButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setOnClickListener(onClickListener);
		mBtton = this;
	}

	public SquareButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setOnClickListener(onClickListener);
		mBtton = this;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// int x = this.getMeasuredWidth();
		// this.setHeight(x);
		super.onDraw(canvas);
	}

	private OnClickListener onClickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if (mOnGetTextOnClick != null)
			{
				mOnGetTextOnClick.getText(mBtton.getText().toString());
			}
		}
	};

	public void setOnGetTextOnClick(OnGetTextOnClick onGetTextOnClick)
	{
		this.mOnGetTextOnClick = onGetTextOnClick;
	}

	public interface OnGetTextOnClick
	{
		void getText(String text);
	}
}
