package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.StoreListContainerFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 商家列表
 * 
 * @author js02
 * 
 */
public class StoreListActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.view_container_fl_content, StoreListContainerFragment.class);
	}

}