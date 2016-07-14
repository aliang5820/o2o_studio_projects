package com.fanwe;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.fragment.ShopCartFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 购物车
 * 
 * @author js02
 * 
 */
public class ShopCartActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_container);
		init();
	}

	private void init()
	{
		getSDFragmentManager().replace(R.id.view_container_fl_content, new ShopCartFragment());
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		init();
		super.onNewIntent(intent);
	}

}