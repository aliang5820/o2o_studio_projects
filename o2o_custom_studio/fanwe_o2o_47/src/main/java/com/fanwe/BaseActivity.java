package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import com.fanwe.app.App;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.title.SDTitleSimple.SDTitleSimpleListener;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.work.ScanResultHandler;
import com.lidroid.xutils.ViewUtils;
import com.sunday.eventbus.SDBaseEvent;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.sso.UMSsoHandler;

public class BaseActivity extends SDBaseActivity implements SDTitleSimpleListener
{

	/** 是否是当作广告页面启动 (boolean) */
	public static final String EXTRA_IS_ADVS = "extra_is_advs";

	private boolean mIsStartByAdvs = false;
	protected boolean mIsNeedShopcart = false;

	private TitleType mTitleType = TitleType.TITLE_NONE;
	protected SDTitleSimple mTitle;

	private ScanResultHandler mScanResultHandler;

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

//		SDViewUtil.setStatusBarTintResource(this, R.color.bg_title_bar);

		mScanResultHandler = new ScanResultHandler(this);
		UmengPushManager.getPushAgent().onAppStart();
	}

	@Override
	protected void baseGetIntentData()
	{
		mIsStartByAdvs = getIntent().getBooleanExtra(EXTRA_IS_ADVS, false);
	}

	@Override
	protected View onCreateTitleView()
	{
		View viewTitle = null;
		switch (getmTitleType())
		{
		case TITLE:
			mTitle = new SDTitleSimple(App.getApplication());
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
	protected void onDestroy()
	{
		UmengSocialManager.getUMShake().unregisterShakeListener(this);
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		MobclickAgent.onPause(this);
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		MobclickAgent.onResume(this);
		super.onResume();
	}

	@Override
	public void finish()
	{
		if (mIsStartByAdvs)
		{
			mIsStartByAdvs = false;
			startActivity(new Intent(this, MainActivity.class));
		}
		super.finish();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case EXIT_APP:
			finish();
			break;
		case LOGOUT:
			if (!App.getApplication().mListClassNotFinishWhenLoginState0.contains(this.getClass()))
			{
				finish();
			}
			break;
		case CART_NUMBER_CHANGE:
			break;
		case START_SCAN_QRCODE:
			if (this.getClass() == event.getData())
			{
				Intent intent = new Intent(getApplicationContext(), MyCaptureActivity.class);
				mScanResultHandler.startScan(intent);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		UMSsoHandler ssoHandler = UmengSocialManager.getUMShare().getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null)
		{
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		mScanResultHandler.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
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
