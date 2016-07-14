package com.fanwe.library.customview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class SDGridLinearLayout extends LinearLayout
{
	private BaseAdapter mAdapter;
	private int mColNumber = 1;
	private int mMaxWidth;
	private int mRowNumbr;
	/** 是否以正方形布局 */
	private boolean mSquare = false;
	private OnItemClickListener mListenerOnItemClick;
	private int mWidthStrokeVer;
	private int mWidthStrokeHor;

	public void setmWidthStrokeHor(int mWidthStrokeHor)
	{
		this.mWidthStrokeHor = mWidthStrokeHor;
	}

	public void setmWidthStrokeVer(int mWidthStrokeVer)
	{
		this.mWidthStrokeVer = mWidthStrokeVer;
	}

	public SDGridLinearLayout(Context context)
	{
		this(context, null);
	}

	public SDGridLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		setOrientation(VERTICAL);
	}

	/**
	 * 是否以正方形布局
	 * 
	 * @param square
	 */
	public void setmSquare(boolean square)
	{
		this.mSquare = square;
	}

	public boolean ismSquare()
	{
		return mSquare;
	}

	public void setmListenerOnItemClick(OnItemClickListener listenerOnItemClick)
	{
		this.mListenerOnItemClick = listenerOnItemClick;
	}

	public int getColWidth()
	{
		return (mMaxWidth - ((mColNumber - 1) * mWidthStrokeVer)) / mColNumber;
	}

	private RowItemLayoutParamsCreater mCreaterRowItemLayoutParams = new RowItemLayoutParamsCreater()
	{

		@Override
		public LayoutParams create(View itemView, int index)
		{
			int width = getColWidth();
			int height = LayoutParams.MATCH_PARENT;
			if (mSquare)
			{
				height = width;
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
			return params;
		}
	};

	private StrokeCreater mCreaterStroke = new StrokeCreater()
	{

		@Override
		public View createVer()
		{
			return null;
		}

		@Override
		public View createHor()
		{
			return null;
		}
	};

	public void setmCreaterRowItemLayoutParams(RowItemLayoutParamsCreater createrRowItemLayoutParams)
	{
		if (createrRowItemLayoutParams != null)
		{
			this.mCreaterRowItemLayoutParams = createrRowItemLayoutParams;
		}
	}

	public void setmCreaterStroke(StrokeCreater createrStroke)
	{
		if (createrStroke != null)
		{
			this.mCreaterStroke = createrStroke;
		}
	}

	public void setmMaxWidth(int maxWidth)
	{
		if (maxWidth > 0)
		{
			this.mMaxWidth = maxWidth;
		}
	}

	public int getmMaxWidth()
	{
		return mMaxWidth;
	}

	public void setmColNumber(int colNumber)
	{
		if (colNumber >= 1)
		{
			this.mColNumber = colNumber;
		}
	}

	public int getmColNumber()
	{
		return mColNumber;
	}

	public int getmRowNumbr()
	{
		return mRowNumbr;
	}

	public void setAdapter(BaseAdapter adapter)
	{
		this.mAdapter = adapter;
		mAdapter.registerDataSetObserver(new DataSetObserver()
		{
			@Override
			public void onChanged()
			{
				notifyDataSetChanged();
			}
		});
		notifyDataSetChanged();
	}

	public BaseAdapter getAdapter()
	{
		return mAdapter;
	}

	public void notifyDataSetChanged()
	{
		post(new Runnable()
		{
			@Override
			public void run()
			{
				mMaxWidth = getWidth();
				create();
			}
		});
	}

	protected void create()
	{
		if (mMaxWidth <= 0)
		{
			return;
		}
		if (mAdapter == null)
		{
			return;
		}

		this.removeAllViews();

		int count = mAdapter.getCount();
		if (count <= 0)
		{
			return;
		}

		if (count % mColNumber == 0)
		{
			mRowNumbr = count / mColNumber;
		} else
		{
			mRowNumbr = count / mColNumber + 1;
		}

		LinearLayout llRow = null;
		int currentCol = -1;
		int currentRow = -1;
		for (int i = 0; i < count; i++)
		{
			if (i % mColNumber == 0)
			{
				currentCol = 0;
				currentRow++;

				// 添加横分割线
				if (currentRow > 0 && currentRow < mRowNumbr)
				{
					if (mCreaterStroke != null)
					{
						View strokeHor = mCreaterStroke.createHor();
						if (strokeHor != null)
						{
							this.addView(strokeHor);
						}
					}
				}

				if (mColNumber == 1) // 如果只有一列，不用新建LinearLayout，直接用当前LinearLayout
				{
					llRow = this;
				} else
				{
					llRow = createRowLinearLayout();
					this.addView(llRow);
				}
			}

			// 添加竖直分割线
			if (currentCol > 0 && currentCol < mColNumber)
			{
				if (mCreaterStroke != null)
				{
					View strokeVer = mCreaterStroke.createVer();
					if (strokeVer != null)
					{
						llRow.addView(strokeVer);
					}
				}
			}

			View itemView = mAdapter.getView(i, null, this);
			whetherRegisterClick(i, itemView);
			wrapperItemView(i, itemView);
			LinearLayout.LayoutParams params = mCreaterRowItemLayoutParams.create(itemView, i);
			llRow.addView(itemView, params);
			currentCol++;
		}
	}

	protected void wrapperItemView(int i, View itemView)
	{

	}

	private void whetherRegisterClick(final int i, final View itemView)
	{
		if (mListenerOnItemClick != null)
		{
			itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mListenerOnItemClick.onItemClick(i, itemView, SDGridLinearLayout.this);
				}
			});
		}
	}

	private LinearLayout createRowLinearLayout()
	{
		LinearLayout llRow = new LinearLayout(getContext());
		llRow.setOrientation(LinearLayout.HORIZONTAL);
		llRow.setGravity(Gravity.CENTER_VERTICAL);
		return llRow;
	}

	public View getStrokeHorColor(int color, int strokeWidth)
	{
		View view = new View(getContext());
		view.setBackgroundColor(color);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, strokeWidth);
		view.setLayoutParams(params);
		return view;
	}

	public View getStrokeHorColorResId(int resId, int strokeWidth)
	{
		return getStrokeHorColor(getContext().getResources().getColor(resId), strokeWidth);
	}

	public View getStrokeVerColor(int color, int strokeWidth)
	{
		View view = new View(getContext());
		view.setBackgroundColor(color);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(strokeWidth, LinearLayout.LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		return view;
	}

	public View getStrokeVerColorResId(int resId, int strokeWidth)
	{
		return getStrokeVerColor(getContext().getResources().getColor(resId), strokeWidth);
	}

	public interface RowItemLayoutParamsCreater
	{
		public LinearLayout.LayoutParams create(View itemView, int index);
	}

	public interface StrokeCreater
	{
		public View createHor();

		public View createVer();
	}

	public interface OnItemClickListener
	{
		public void onItemClick(int position, View view, ViewGroup parent);
	}

}
