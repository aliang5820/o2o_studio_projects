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
import com.fanwe.model.BizEventrCtlIndexActItemModel;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-15 下午5:05:25 类说明
 */
public class BizEventrCtlAdapter extends SDSimpleAdapter<BizEventrCtlIndexActItemModel>
{
	public BizEventrCtlAdapter(List<BizEventrCtlIndexActItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.frag_tab1_biz_tuan_type0_listitem_type2;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final BizEventrCtlIndexActItemModel model)
	{
		TextView avg_point = ViewHolder.get(R.id.fragtab1_listitem_tv_buy_dp_avg, convertView);
		TextView sub_name = ViewHolder.get(R.id.fragtab1_listitem_tv_sub_name, convertView);
		TextView current_pric = ViewHolder.get(R.id.fragtab1_listitem_tv_current_price_format, convertView);
		TextView dp_count = ViewHolder.get(R.id.fragtab1_listitem_tv_buy_dp_no_read_count, convertView);

		SDViewBinder.setTextView(avg_point, model.getAvg_point() + "");
		SDViewBinder.setTextView(sub_name, model.getName());
		SDViewBinder.setTextView(current_pric, "还剩余 : " + model.getTotal_count() + "名额");
		SDViewBinder.setTextView(dp_count, model.getSubmit_count());
	}
}
