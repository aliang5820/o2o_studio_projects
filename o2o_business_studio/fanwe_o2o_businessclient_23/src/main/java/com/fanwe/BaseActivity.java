package com.fanwe;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.title.SDTitleSimple.SDTitleSimpleListener;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.umeng.UmengPushManager;
import com.lidroid.xutils.ViewUtils;
import com.sunday.eventbus.SDBaseEvent;

public class BaseActivity extends SDBaseActivity implements SDTitleSimpleListener
{

	private TitleType mTitleType = TitleType.TITLE_NONE;
	protected SDTitleSimple mTitle;

	public TitleType getmTitleType()
	{
		return mTitleType;
	}

	public void setmTitleType(TitleType mTitleType)
	{
		this.mTitleType = mTitleType;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void baseInit()
	{
		UmengPushManager.getPushAgent().onAppStart();
	}

	@Override
	protected void baseGetIntentData()
	{
	}

	@Override
	protected View onCreateTitleView()
	{
		View viewTitle = null;
		switch (getmTitleType())
		{
		case TITLE:
			mTitle = new SDTitleSimple(this);
			mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
			mTitle.setmListener(this);
			viewTitle = mTitle;
			break;
		default:
			break;
		}
		return viewTitle;
	}

	@Override
	protected LayoutParams generateTitleViewLayoutParams()
	{
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, SDResourcesUtil.getDimensionPixelSize(R.dimen.height_title_bar));
		return params;
	}

	@Override
	public void setContentView(View view)
	{
		super.setContentView(view);
		ViewUtils.inject(this);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case EXIT_APP:
			finish();
			break;
		case UN_LOGIN:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCLickLeft_SDTitleSimple(SDTitleItem v)
	{
		finish();
	}

	@Override
	public void onCLickMiddle_SDTitleSimple(SDTitleItem v)
	{
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
	}

}
