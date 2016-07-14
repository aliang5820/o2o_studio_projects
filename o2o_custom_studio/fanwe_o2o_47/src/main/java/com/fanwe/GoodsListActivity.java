package com.fanwe;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.fragment.GoodsListFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 商品列表
 * 
 * @author js02
 * 
 */
public class GoodsListActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_tuan_list);
		init();

	}

	private void init()
	{
		getSDFragmentManager().replace(R.id.act_tuan_list_fl_container, GoodsListFragment.class);
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		init();
		super.onNewIntent(intent);
	}

}