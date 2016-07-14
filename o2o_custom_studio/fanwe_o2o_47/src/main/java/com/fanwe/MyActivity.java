package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.MyFragment;
import com.fanwe.o2o.newo2o.R;

public class MyActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.view_container_fl_content, MyFragment.class);
	}

}
