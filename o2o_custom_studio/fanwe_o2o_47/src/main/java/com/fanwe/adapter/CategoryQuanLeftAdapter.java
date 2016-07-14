package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SD2LvCategoryViewHelper.SD2LvCategoryViewHelperAdapterInterface;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.Quan_listModel;
import com.fanwe.o2o.newo2o.R;

public class CategoryQuanLeftAdapter extends SDSimpleAdapter<Quan_listModel> implements SD2LvCategoryViewHelperAdapterInterface
{

	private int mDefaultIndex;

	public CategoryQuanLeftAdapter(List<Quan_listModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	public void setmDefaultIndex(int rightIndex)
	{
		this.mDefaultIndex = rightIndex;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_category_left;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, Quan_listModel model)
	{
		TextView tvTitle = ViewHolder.get(R.id.item_category_left_tv_title, convertView);
		TextView tvArrowRight = ViewHolder.get(R.id.item_category_left_tv_arrow_right, convertView);

		SDViewBinder.setTextView(tvTitle, model.getName());

		if (model.isSelect())
		{
			convertView.setBackgroundColor(SDResourcesUtil.getColor(R.color.bg_gray_categoryview_item_select));
		} else
		{
			convertView.setBackgroundColor(SDResourcesUtil.getColor(R.color.white));
		}
		if (model.isHasChild())
		{
			tvArrowRight.setVisibility(View.VISIBLE);
		} else
		{
			tvArrowRight.setVisibility(View.GONE);
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
		return mDefaultIndex;
	}

	@Override
	public Object getRightListModelFromPosition_left(int position)
	{
		return getItem(position).getQuan_sub();
	}

	@Override
	public void updateRightListModel_right(Object rightListModel)
	{

	}

	@Override
	public void setPositionSelectState_left(int positionLeft, int positionRight, boolean select)
	{
		List<Quan_listModel> listRight = getItem(positionLeft).getQuan_sub();
		if (listRight != null && positionRight >= 0 && positionRight < listRight.size())
		{
			listRight.get(positionRight).setSelect(select);
		}
	}

}
