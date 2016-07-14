package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.customview.SDGridLinearLayout.OnItemClickListener;
import com.fanwe.model.DistributionMarketCateModel;

public class DistributionMarketCatePageAdapter extends SDPagerAdapter<List<DistributionMarketCateModel>>
{

	private OnClickCateItemListener mListenerOnClickCateItem;

	public void setmListenerOnClickCateItem(OnClickCateItemListener listenerOnClickCateItem)
	{
		this.mListenerOnClickCateItem = listenerOnClickCateItem;
	}

	public DistributionMarketCatePageAdapter(List<List<DistributionMarketCateModel>> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(View container, int position)
	{
		final SDGridLinearLayout ll = new SDGridLinearLayout(mActivity);
		ll.setmColNumber(4);
		final DistributionMarketCateAdapter adapter = new DistributionMarketCateAdapter(getItemModel(position), mActivity);
		ll.setmListenerOnItemClick(new OnItemClickListener()
		{

			@Override
			public void onItemClick(int position, View view, ViewGroup parent)
			{
				if (mListenerOnClickCateItem != null)
				{
					mListenerOnClickCateItem.onClickItem(position, view, adapter.getItem(position));
				}
			}
		});
		ll.setAdapter(adapter);
		return ll;
	}

	public interface OnClickCateItemListener
	{
		public void onClickItem(int position, View view, DistributionMarketCateModel model);
	}

}
