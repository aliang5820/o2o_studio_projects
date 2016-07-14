package com.fanwe.library.pager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.fanwe.library.adapter.SDBaseAdapter;

/**
 * 用com.fanwe.library.view.HorizontalScrollViewPageIndicator替代
 * 
 * @author Administrator
 * 
 */
@Deprecated
public class HorizontalScrollViewPageIndicator extends HorizontalScrollView implements OnPageChangeListener
{

	private ViewPager mViewPager;
	private OnPageChangeListener mListenerOnPageChange;
	private Runnable mIconSelector;
	private LinearLayout mLlTabLayout;
	private SDBaseAdapter<?> mAdapter;
	private OnTabItemSelectedListener mListenerOnTabItemSelected;

	public void setmListenerOnTabItemSelected(OnTabItemSelectedListener listenerOnTabItemSelected)
	{
		this.mListenerOnTabItemSelected = listenerOnTabItemSelected;
	}

	public void setmListenerOnPageChange(OnPageChangeListener listenerOnPageChange)
	{
		this.mListenerOnPageChange = listenerOnPageChange;
	}

	public HorizontalScrollViewPageIndicator(Context context)
	{
		this(context, null);
	}

	public HorizontalScrollViewPageIndicator(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setHorizontalScrollBarEnabled(false);
		mLlTabLayout = new LinearLayout(context);
		mLlTabLayout.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(mLlTabLayout);
	}

	public void setAdapter(SDBaseAdapter<?> adapter)
	{
		this.mAdapter = adapter;
		mAdapter.registerDataSetObserver(new DataSetObserver()
		{
			@Override
			public void onChanged()
			{
				notifyDataSetChanged();
				super.onChanged();
			}
		});
		notifyDataSetChanged();
	}

	private void animateToTab(final int position)
	{
		final View iconView = mLlTabLayout.getChildAt(position);
		if (mIconSelector != null)
		{
			removeCallbacks(mIconSelector);
		}
		mIconSelector = new Runnable()
		{
			public void run()
			{
				final int scrollPos = iconView.getLeft() - (getWidth() - iconView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mIconSelector = null;
			}
		};
		post(mIconSelector);
	}

	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		if (mIconSelector != null)
		{
			post(mIconSelector);
		}
	}

	@Override
	public void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		if (mIconSelector != null)
		{
			removeCallbacks(mIconSelector);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
		if (mListenerOnPageChange != null)
		{
			mListenerOnPageChange.onPageScrollStateChanged(state);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{
		if (mListenerOnPageChange != null)
		{
			mListenerOnPageChange.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position)
	{
		setCurrentItemTab(position);
		if (mListenerOnPageChange != null)
		{
			mListenerOnPageChange.onPageSelected(position);
		}
	}

	public void setViewPager(ViewPager view)
	{
		setViewPager(view, 0);
	}

	public void setViewPager(ViewPager view, int position)
	{
		if (mViewPager == view)
		{
			return;
		}
		if (mViewPager != null)
		{
			mViewPager.setOnPageChangeListener(null);
		}
		mViewPager = view;
		mViewPager.setOnPageChangeListener(this);

		setCurrentItem(position);
		if (mViewPager.getCurrentItem() == position)
		{
			setCurrentItemTab(position);
		}
	}

	public void notifyDataSetChanged()
	{
		mLlTabLayout.removeAllViews();
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++)
		{
			View view = mAdapter.getView(i, null, mLlTabLayout);
			view.setOnClickListener(new DefaultOnClickTabItemListener(i));
			mLlTabLayout.addView(view);
		}
		// requestLayout();
	}

	private class DefaultOnClickTabItemListener implements View.OnClickListener
	{
		private int nIndex;

		public DefaultOnClickTabItemListener(int index)
		{
			nIndex = index;
		}

		@Override
		public void onClick(View v)
		{
			setCurrentItem(nIndex);
		}
	}

	public void setCurrentItem(int position)
	{
		if (!mAdapter.isPositionLegal(position))
		{
			return;
		}
		if (mViewPager != null)
		{
			mViewPager.setCurrentItem(position);
		} else
		{
			setCurrentItemTab(position);
		}
	}

	private void setCurrentItemTab(int position)
	{
		if (!mAdapter.isPositionLegal(position))
		{
			return;
		}
		mAdapter.setmSelectedPosition(position, true);
		animateToTab(position);
		if (mListenerOnTabItemSelected != null)
		{
			mListenerOnTabItemSelected.onSelected(mLlTabLayout.getChildAt(position), position);
		}
	}

	public interface OnTabItemSelectedListener
	{
		public void onSelected(View v, int index);
	}

}
