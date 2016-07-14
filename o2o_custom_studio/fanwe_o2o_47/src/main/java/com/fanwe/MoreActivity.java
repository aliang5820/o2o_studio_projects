package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.MoreFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 更多
 * 
 * @author Administrator
 * 
 */
public class MoreActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.view_container_fl_content, MoreFragment.class);
	}
}
