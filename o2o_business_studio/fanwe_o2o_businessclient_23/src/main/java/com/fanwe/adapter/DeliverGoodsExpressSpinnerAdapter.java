package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.ExpressModel;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-7-2 上午11:39:37 类说明
 */
public class DeliverGoodsExpressSpinnerAdapter extends SDSimpleAdapter<ExpressModel>
{
	public DeliverGoodsExpressSpinnerAdapter(List<ExpressModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_frag_tab0;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, ExpressModel model)
	{
		TextView tv_name = ViewHolder.get(R.id.tv_name,convertView);
		SDViewBinder.setTextView(tv_name, model.getName());
	}
}
