package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.YouHuiListFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 优惠列表界面
 * 
 * @author js02
 * 
 */
public class YouHuiListActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.act_tuan_list_fl_container, YouHuiListFragment.class);
	}

}