package com.fanwe.library.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class SDPagerAdapter<T> extends PagerAdapter
{

	protected List<T> mListModel = new ArrayList<T>();
	protected LayoutInflater mInflater;
	protected Activity mActivity;
	protected SDBasePagerAdapterOnItemClickListener mListenerOnItemClick;
	protected View mView;

	public void setmView(View mView)
	{
		this.mView = mView;
	}

	public void setmListenerOnItemClick(SDBasePagerAdapterOnItemClickListener mListenerOnItemClick)
	{
		this.mListenerOnItemClick = mListenerOnItemClick;
	}

	public SDPagerAdapter(List<T> listModel, Activity activity)
	{
		setData(listModel);
		this.mActivity = activity;
		this.mInflater = mActivity.getLayoutInflater();
	}

	@Override
	public int getCount()
	{
		if (mListModel != null)
		{
			return mListModel.size();
		} else
		{
			return 0;
		}
	}

	public T getItemModel(int position)
	{
		if (mListModel != null && position >= 0 && mListModel.size() > position)
		{
			return mListModel.get(position);
		} else
		{
			return null;
		}
	}

	public void updateData(List<T> listModel)
	{
		setData(listModel);
		this.notifyDataSetChanged();
	}

	public List<T> getData()
	{
		return mListModel;
	}

	public void setData(List<T> listModel)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		} else
		{
			this.mListModel = new ArrayList<T>();
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, final int position)
	{
		View v = getView(container, position);
		if (mListenerOnItemClick != null)
		{
			v.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (mListenerOnItemClick != null)
					{
						mListenerOnItemClick.onItemClick(v, position);
					}
				}
			});
		}
		((ViewPager) container).addView(v);
		return v;
	}

	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(View container, int position, Object object)
	{
		((ViewPager) container).removeView((View) object);
	}

	public View getView(View container, int position)
	{
		return null;
	}

	public interface SDBasePagerAdapterOnItemClickListener
	{
		public void onItemClick(View v, int position);
	}

}
