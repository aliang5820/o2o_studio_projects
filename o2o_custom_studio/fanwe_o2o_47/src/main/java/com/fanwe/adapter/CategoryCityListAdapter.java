package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDLvCategoryViewHelper.SDLvCategoryViewHelperAdapterInterface;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Quan_listModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;

public class CategoryCityListAdapter extends SDSimpleAdapter<Quan_listModel> implements SDLvCategoryViewHelperAdapterInterface
{

	public CategoryCityListAdapter(List<Quan_listModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_category_single;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Quan_listModel model)
	{
		TextView tvTitle = ViewHolder.get(R.id.item_category_single_tv_title, convertView);

		SDViewBinder.setTextView(tvTitle, model.getName());
		if (model.isSelect())
		{
			convertView.setBackgroundColor(SDResourcesUtil.getColor(R.color.bg_gray_categoryview_item_select));
		} else
		{
			convertView.setBackgroundColor(SDResourcesUtil.getColor(R.color.white));
		}
	}

	@Override
	public void setPositionSelectState(int position, boolean select, boolean notify)
	{
		getItem(position).setSelect(select);
		if (notify)
		{
			notifyDataSetChanged();
		}
	}

	@Override
	public String getTitleNameFromPosition(int position)
	{
		return getItem(position).getName();
	}

	@Override
	public BaseAdapter getAdapter()
	{
		return this;
	}

	@Override
	public Object getSelectModelFromPosition(int position)
	{
		return getItem(position);
	}

	@Override
	public int getTitleIndex()
	{
		String city = AppRuntimeWorker.getCity_name();
		if (!TextUtils.isEmpty(city))
		{
			if (mListModel != null && mListModel.size() > 0)
			{
				Quan_listModel model = null;
				for (int i = 0; i < mListModel.size(); i++)
				{
					model = mListModel.get(i);
					if (model != null && city.equals(model.getName()))
					{
						return i;
					}
				}
			}
		}
		return -1;
	}

}
