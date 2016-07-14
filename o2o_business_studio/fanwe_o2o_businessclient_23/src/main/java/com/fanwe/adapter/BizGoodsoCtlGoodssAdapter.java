package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.DeliverGoodsActivity;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.constant.Constant.RequestCodeActicity;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.ItemBizGoodsoActGoodss;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-9 下午2:11:45 类说明
 */
public class BizGoodsoCtlGoodssAdapter extends SDSimpleAdapter<ItemBizGoodsoActGoodss>
{

	private int mNow_time, mOrder_delivery_expire;

	public void setmNow_time(int mNow_time)
	{
		this.mNow_time = mNow_time;
	}

	public void setmOrder_delivery_expire(int mOrder_delivery_expire)
	{
		this.mOrder_delivery_expire = mOrder_delivery_expire;
	}

	public BizGoodsoCtlGoodssAdapter(List<ItemBizGoodsoActGoodss> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_bizgoodsoctl_goodssact_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final ItemBizGoodsoActGoodss model)
	{
		ImageView iv_icon = ViewHolder.get(R.id.iv_icon, convertView);
		TextView tv_order_sn = ViewHolder.get(R.id.tv_order_sn, convertView);
		TextView tv_create_time = ViewHolder.get(R.id.tv_create_time, convertView);
		TextView tv_sub_name = ViewHolder.get(R.id.tv_sub_name, convertView);
		TextView tv_user_name = ViewHolder.get(R.id.tv_user_name, convertView);
		TextView tv_number = ViewHolder.get(R.id.tv_number, convertView);
		TextView tv_s_total_price = ViewHolder.get(R.id.tv_s_total_price, convertView);

		// 地址
		LinearLayout ll_address = ViewHolder.get(R.id.ll_address, convertView);
		ll_address.setVisibility(View.GONE);
		TextView tv_consignee = ViewHolder.get(R.id.tv_consignee, convertView);
		TextView tv_mobile = ViewHolder.get(R.id.tv_mobile, convertView);
		TextView tv_address_str = ViewHolder.get(R.id.tv_address_str, convertView);

		TextView tv_status1 = ViewHolder.get(R.id.tv_status1, convertView);
		TextView tv_status1_and_status2 = ViewHolder.get(R.id.tv_status1_and_status2, convertView);
		TextView tv_send = ViewHolder.get(R.id.tv_send, convertView);

		SDViewBinder.setImageView(model.getIcon(), iv_icon);
		SDViewBinder.setTextView(tv_order_sn, model.getOrder_sn());
		SDViewBinder.setTextView(tv_create_time, model.getCreate_time());
		SDViewBinder.setTextView(tv_sub_name, model.getSub_name());
		SDViewBinder.setTextView(tv_user_name, model.getUser_name());
		SDViewBinder.setTextView(tv_number, model.getNumber());
		SDViewBinder.setTextView(tv_s_total_price, model.getS_total_price());

		SDViewBinder.setTextView(tv_consignee, model.getConsignee());
		SDViewBinder.setTextView(tv_mobile, model.getMobile());
		SDViewBinder.setTextView(tv_address_str, model.getAddress_str());

		SDViewBinder.setTextView(tv_status1, model.getStatusText1());
		SDViewUtil.setBackgroundResource(tv_status1, model.getStatus1Color());

		String str1 = null;
		if (model.getDelivery_notice() != null)
		{
			str1 = model.getStatusText2(mNow_time, model.getDelivery_notice().getDelivery_time(), mOrder_delivery_expire);
		} else
		{
			str1 = model.getStatusText2(mNow_time, 0, mOrder_delivery_expire);
		}
		String str2 = model.getStatusText3();
		SDViewBinder.setTextView(tv_status1_and_status2, str1 + " " + str2);

		if (model.getOrder_status() != 1)
		{
			if (model.getDelivery_status() == 0)
			{
				tv_send.setVisibility(View.VISIBLE);
				tv_send.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, DeliverGoodsActivity.class);
						intent.putExtra(ExtraConstant.EXTRA_ID, model.getId());
						mActivity.startActivityForResult(intent, RequestCodeActicity.REQUESTCODENORMAL);
					}
				});
			} else
			{
				if (model.getDelivery_status() == 1)
				{
					ll_address.setVisibility(View.VISIBLE);
				}

				tv_send.setVisibility(View.GONE);
			}
		} else
		{
			tv_send.setVisibility(View.GONE);
		}
	}
}
