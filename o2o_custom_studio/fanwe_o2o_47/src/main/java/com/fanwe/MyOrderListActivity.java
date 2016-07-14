package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.MyOrderListFragment;
import com.fanwe.o2o.newo2o.R;

public class MyOrderListActivity extends BaseActivity
{
	/** 订单状态，1:已支付，0:未支付 */
	public static final String EXTRA_PAY_STATUS = "extra_pay_status";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_container);
		init();
	}

	private void init()
	{
		getSDFragmentManager().replace(R.id.view_container_fl_content, MyOrderListFragment.class);
	}

}
