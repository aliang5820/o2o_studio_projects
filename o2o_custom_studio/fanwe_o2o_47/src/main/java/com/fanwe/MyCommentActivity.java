package com.fanwe;

import android.os.Bundle;

import com.fanwe.fragment.MyCommentFragment;
import com.fanwe.o2o.newo2o.R;

/**
 * 我的点评
 * 
 * @author js02
 * 
 */
public class MyCommentActivity extends BaseActivity
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
		getSDFragmentManager().replace(R.id.view_container_fl_content, MyCommentFragment.class);
	}

}