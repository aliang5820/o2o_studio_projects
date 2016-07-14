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
import com.fanwe.model.BizDealrCtlItemModel;

/**
 * 
 * @author yhz
 * @create time 2014-8-4
 */
public class Biz_tuanType0Adapter extends SDSimpleAdapter<BizDealrCtlItemModel>
{

	private int mCurrentType;

	public int getmCurrentType()
	{
		return mCurrentType;
	}

	public void setmCurrentType(int mCurrentType)
	{
		this.mCurrentType = mCurrentType;
	}

	public Biz_tuanType0Adapter(List<BizDealrCtlItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		int layoutId = 0;
		switch (mCurrentType)
		{
		case 0:
			layoutId = R.layout.frag_tab1_biz_tuan_type0_listitem;
			break;
		case 1:
			layoutId = R.layout.frag_tab1_biz_tuan_type0_listitem_type1;
			break;
		case 2:
			layoutId = R.layout.frag_tab1_biz_tuan_type0_listitem_type2;
			break;
		case 3:
			layoutId = R.layout.frag_tab1_biz_tuan_type0_listitem_type3;
			break;

		default:
			break;
		}

		return layoutId;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, BizDealrCtlItemModel model)
	{
		TextView avg_point = ViewHolder.get(R.id.fragtab1_listitem_tv_buy_dp_avg, convertView);
		TextView sub_name = ViewHolder.get(R.id.fragtab1_listitem_tv_sub_name, convertView);
		TextView current_pric = ViewHolder.get(R.id.fragtab1_listitem_tv_current_price_format, convertView);
		TextView dp_count = ViewHolder.get(R.id.fragtab1_listitem_tv_buy_dp_no_read_count, convertView);

		switch (mCurrentType)
		{
		case 0:
			SDViewBinder.setTextView(avg_point, model.getAvg_point() + "");
			SDViewBinder.setTextView(sub_name, model.getSub_name());
			SDViewBinder.setTextView(current_pric, "￥" + model.getCurrent_price());
			SDViewBinder.setTextView(dp_count, model.getDp_count());
			break;
		case 1:
			SDViewBinder.setTextView(avg_point, model.getAvg_point() + "");
			SDViewBinder.setTextView(sub_name, model.getName());
			SDViewBinder.setTextView(current_pric, "还剩余 : " + model.getTotal_num() + "张");
			SDViewBinder.setTextView(dp_count, model.getUser_count());
			break;
		case 2:
			SDViewBinder.setTextView(avg_point, model.getAvg_point() + "");
			SDViewBinder.setTextView(sub_name, model.getName());
			SDViewBinder.setTextView(current_pric, "还剩余 : " + model.getSubmit_count() + "名额");
			SDViewBinder.setTextView(dp_count, model.getSubmit_count());
			break;
		case 3:
			SDViewBinder.setTextView(avg_point, model.getAvg_point() + "");
			SDViewBinder.setTextView(sub_name, model.getName());
			SDViewBinder.setTextView(current_pric, "好评率 : " + model.getGood_rate() + "%");
			SDViewBinder.setTextView(dp_count, model.getRef_avg_price());
			break;
		}
	}

}
