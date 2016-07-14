package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.ItemBizEventoCtlModel;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-5 上午9:11:32 类说明
 */
public class EventoCtlAdapter extends SDSimpleAdapter<ItemBizEventoCtlModel>
{

	public EventoCtlAdapter(List<ItemBizEventoCtlModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_eventoctlact_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, ItemBizEventoCtlModel model)
	{
		ImageView iv_icon = ViewHolder.get(R.id.iv_icon, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_total_time = ViewHolder.get(R.id.tv_total_time, convertView);
		TextView tv_total_coun = ViewHolder.get(R.id.tv_total_coun, convertView);
		TextView tv_submit_count = ViewHolder.get(R.id.tv_submit_count, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_icon);
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_total_time, model.getF_event_end_time());
		SDViewBinder.setTextView(tv_total_coun, model.getTotal_count());
		SDViewBinder.setTextView(tv_submit_count, model.getSubmit_count());
	}

}
