package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.ScoresListFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 积分商城
 * 
 * @author js02
 * 
 */
public class ScoresListActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.act_tuan_list_fl_container, ScoresListFragment.class);
	}

}