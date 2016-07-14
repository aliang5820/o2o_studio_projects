package com.fanwe;

import android.content.Intent;

import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.AppWebViewFragment;
import com.fanwe.library.activity.WebViewActivity;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.fragment.WebViewFragment.EnumProgressMode;
import com.fanwe.library.fragment.WebViewFragment.EnumWebviewHeightMode;
import com.sunday.eventbus.SDBaseEvent;

/**
 * webview界面
 * 
 * @author js02
 * 
 */
public class AppWebViewActivity extends WebViewActivity
{

	/** 缩放到显示全部 (boolean) */
	public static final String EXTRA_SCALE_TO_SHOW_ALL = "extra_scale_to_show_all";

	private boolean mIsStartByAdvs = false;

	private boolean mScaleToShowAll = false;

	@Override
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		init();
		super.onNewIntent(intent);
	}

	@Override
	protected void getIntentData()
	{
		mIsStartByAdvs = getIntent().getBooleanExtra(BaseActivity.EXTRA_IS_ADVS, false);
		mScaleToShowAll = getIntent().getBooleanExtra(EXTRA_SCALE_TO_SHOW_ALL, false);
		mFragWebview.setScaleToShowAll(mScaleToShowAll);
		super.getIntentData();
	}

	@Override
	protected WebViewFragment createFragment()
	{
		AppWebViewFragment fragment = new AppWebViewFragment();
		fragment.setmProgressMode(EnumProgressMode.HORIZONTAL);
		fragment.setmWebviewHeightMode(EnumWebviewHeightMode.MATCH_PARENT);
		return fragment;
	}

	@Override
	public void finish()
	{
		if (mIsStartByAdvs)
		{
			startActivity(new Intent(this, MainActivity.class));
		}
		super.finish();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case LOGIN_SUCCESS:
			if (mFragWebview != null)
			{
				mFragWebview.startLoadData();
			}
			break;

		default:
			break;
		}
		super.onEventMainThread(event);
	}

}