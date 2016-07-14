package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.SearchTypeModel;
import com.fanwe.o2o.newo2o.R;

public class SearchTypeAdapter extends SDSimpleAdapter<SearchTypeModel>
{

	public SearchTypeAdapter(List<SearchTypeModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_search_type;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, SearchTypeModel model)
	{
		TextView tvCategory = ViewHolder.get(R.id.item_gv_cate_type_tv_category, convertView);
		SDViewBinder.setTextView(tvCategory, model.getName());
		if (model.isSelect())
		{
			tvCategory.setBackgroundResource(R.drawable.layer_main_color_normal);
			tvCategory.setTextColor(SDResourcesUtil.getColor(R.color.white));
		} else
		{
			tvCategory.setBackgroundResource(R.drawable.layer_white_stroke_all);
			tvCategory.setTextColor(SDResourcesUtil.getColor(R.color.gray));
		}
	}

}
