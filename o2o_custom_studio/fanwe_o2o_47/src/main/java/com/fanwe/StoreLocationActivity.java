package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.StoreLocationFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 商家地图
 * 
 * @author js02
 * 
 */
public class StoreLocationActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.view_container_fl_content, StoreLocationFragment.class);
	}

}