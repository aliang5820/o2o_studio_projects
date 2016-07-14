package com.fanwe;

import android.os.Bundle;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.StorePayListFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 到店付门店列表
 * 
 * @author js02
 * 
 */
public class StorePayListActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.view_container);
		init();

	}

	private void init()
	{
		mTitle.setMiddleTextTop("优惠买单");

		getSDFragmentManager().replace(R.id.view_container_fl_content, StorePayListFragment.class);
	}

}