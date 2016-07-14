package com.fanwe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.DoiModel;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-7-2 下午1:03:46 类说明
 */
public class DeliverGoodsDoiAdapter extends SDSimpleAdapter<DoiModel>
{

	public ArrayList<DoiModel> mSelectList = new ArrayList<DoiModel>();

	public DeliverGoodsDoiAdapter(List<DoiModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	public ArrayList<DoiModel> getmSelectList()
	{
		return mSelectList;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_deliver_goods_doi_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final DoiModel model)
	{
		CheckBox check_box = ViewHolder.get(R.id.check_box, convertView);
		ImageView iv_deal_icon = ViewHolder.get(R.id.iv_deal_icon, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_number = ViewHolder.get(R.id.tv_number, convertView);
		TextView tv_total_price = ViewHolder.get(R.id.tv_total_price, convertView);

		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_number, model.getNumber());
		SDViewBinder.setTextView(tv_total_price, model.getTotal_price());
		SDViewBinder.setImageView(model.getDeal_icon(), iv_deal_icon);
		check_box.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					mSelectList.add(model);
				} else
				{
					mSelectList.remove(model);
				}
			}
		});
	}
}
