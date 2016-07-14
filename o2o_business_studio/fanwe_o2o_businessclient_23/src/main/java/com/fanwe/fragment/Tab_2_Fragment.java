package com.fanwe.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.fanwe.ConsumeStatisticsActivity;
import com.fanwe.EventoCtlActivity;
import com.fanwe.Jygl_couponActivity;
import com.fanwe.OrderListActivity;
import com.fanwe.businessclient.R;

/**
 * 
 * 经营
 */
public class Tab_2_Fragment extends BaseFragment implements OnClickListener
{

	@Override
	protected int onCreateContentView()
	{
		return R.layout.m_frag_tab_2;
	}

	private void register(View view)
	{
		// 优惠劵下载
		LinearLayout ll_jygl_coupon = (LinearLayout) view.findViewById(R.id.ll_jygl_coupon);
		ll_jygl_coupon.setOnClickListener(this);
		// 活动验证
		LinearLayout ll_jygl_activity = (LinearLayout) view.findViewById(R.id.ll_jygl_activity);
		ll_jygl_activity.setOnClickListener(this);
		// 消费统计
		LinearLayout ll_jygl_consumption = (LinearLayout) view.findViewById(R.id.ll_jygl_consumption);
		ll_jygl_consumption.setOnClickListener(this);
		// 订单列表
		LinearLayout ll_jygl_orderList = (LinearLayout) view.findViewById(R.id.ll_jygl_orderList);
		ll_jygl_orderList.setOnClickListener(this);
		// 外卖预定
		LinearLayout ll_jygl_wmyd = (LinearLayout) view.findViewById(R.id.ll_jygl_wmyd);
		ll_jygl_wmyd.setOnClickListener(this);

		// 隐藏外卖预定，插件版时显示
		ll_jygl_wmyd.setVisibility(View.GONE);
	}

	@Override
	protected void init()
	{
		register(getView());
	}

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
		case R.id.ll_jygl_coupon:
			clickCoupon();
			break;
		case R.id.ll_jygl_activity:
			clickActivity();
			break;
		case R.id.ll_jygl_consumption:
			clickConsumption();
			break;
		case R.id.ll_jygl_orderList:
			clickOrderList();
			break;
		case R.id.ll_jygl_wmyd:
			clickWmyd();
			break;
		default:
			break;
		}

	}

	private void clickWmyd()
	{
		// startActivity(new Intent(getActivity(), DcActivity_dc.class));
	}

	private void clickOrderList()
	{
		startActivity(new Intent(getActivity(), OrderListActivity.class));
	}

	private void clickConsumption()
	{
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), ConsumeStatisticsActivity.class));
	}

	private void clickActivity()
	{

		startActivity(new Intent(getActivity(), EventoCtlActivity.class));
	}

	private void clickCoupon()
	{
		startActivity(new Intent(getActivity(), Jygl_couponActivity.class));
	}

}
