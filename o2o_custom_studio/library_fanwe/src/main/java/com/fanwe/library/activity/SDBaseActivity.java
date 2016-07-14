package com.fanwe.library.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.receiver.SDBroadcastReceiver;
import com.fanwe.library.receiver.SDBroadcastUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;

public class SDBaseActivity extends FragmentActivity implements SDEventObserver, OnClickListener
{

	private SDFragmentManager mFragmentManager;
	private SDActivityLifeCircleListener mListenerLifeCircle;
	private SDActivityOnActivityResultListener mListenerOnActivityResult;
	private boolean mIsNeedRefreshOnResume = false;
	protected SDBaseActivity mActivity;
	private View mTitleView;
	private ViewGroup mContentView;

	/**
	 * 用public void setIsNeedRefreshOnResume(boolean isNeedRefreshOnResume)替代
	 * 
	 * @param mIsNeedRefreshOnResume
	 */
	@Deprecated
	public void setmIsNeedRefreshOnResume(boolean mIsNeedRefreshOnResume)
	{
		this.mIsNeedRefreshOnResume = mIsNeedRefreshOnResume;
	}

	/**
	 * 设置是否下次onResume被调用的时候调用onNeedRefreshOnResume方法
	 * 
	 * @param mIsNeedRefreshOnResume
	 */
	public void setIsNeedRefreshOnResume(boolean isNeedRefreshOnResume)
	{
		this.mIsNeedRefreshOnResume = isNeedRefreshOnResume;
	}

	public void setListenerLifeCircle(SDActivityLifeCircleListener listenerLifeCircle)
	{
		this.mListenerLifeCircle = listenerLifeCircle;
	}

	public SDActivityLifeCircleListener getListenerLifeCircle()
	{
		return mListenerLifeCircle;
	}

	public void setListenerOnActivityResult(SDActivityOnActivityResultListener listenerOnActivityResult)
	{
		this.mListenerOnActivityResult = listenerOnActivityResult;
	}

	public SDActivityOnActivityResultListener getListenerOnActivityResult()
	{
		return mListenerOnActivityResult;
	}

	/** 默认广播接收者 */
	private SDBroadcastReceiver mDefaultSDBroadcastReceiver = new SDBroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent, String tagString)
		{
			SDBaseActivity.this.onReceive(context, intent, tagString);
		}
	};

	/**
	 * 默认广播接收者回调
	 * 
	 * @param context
	 * @param intent
	 * @param tagString
	 */
	public void onReceive(Context context, Intent intent, String tagString)
	{

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		mActivity = this;
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onCreate(savedInstanceState, this);
		}
		SDActivityManager.getInstance().onCreate(this);
		SDEventManager.register(this);
		SDBroadcastUtil.registerReceiver(mDefaultSDBroadcastReceiver);
		super.onCreate(savedInstanceState);
		afterOnCreater(savedInstanceState);
	}

	private void afterOnCreater(Bundle savedInstanceState)
	{
		baseGetIntentData();
		baseInit();
	}

	protected void baseGetIntentData()
	{

	}

	protected void baseInit()
	{

	}

	@SuppressWarnings("unchecked")
	public <V extends View> V find(int id)
	{
		View view = findViewById(id);
		return (V) view;
	}

	@Override
	public void setContentView(int layoutResID)
	{
		View contentView = getLayoutInflater().inflate(layoutResID, null);
		setContentView(contentView);
	}

	@Override
	public void setContentView(View view)
	{
		mTitleView = createTitleView();
		if (mTitleView != null)
		{
			LinearLayout linAll = new LinearLayout(this);
			linAll.setOrientation(LinearLayout.VERTICAL);
			linAll.addView(mTitleView, generateTitleViewLayoutParams());
			linAll.addView(view, generateContentViewLayoutParams());
			mContentView = linAll;
		} else
		{
			mContentView = (ViewGroup) view;
		}
		super.setContentView(mContentView);
	}

	/**
	 * 此方法用于被重写，创建标题布局
	 * 
	 * @return 标题view
	 */
	protected View onCreateTitleView()
	{
		return null;
	}

	protected int onCreateTitleViewResId()
	{
		return 0;
	}

	private View createTitleView()
	{
		View view = null;
		int resId = onCreateTitleViewResId();
		if (resId != 0)
		{
			view = LayoutInflater.from(this).inflate(resId, null);
		} else
		{
			view = onCreateTitleView();
		}
		return view;
	}

	protected LinearLayout.LayoutParams generateTitleViewLayoutParams()
	{
		return new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	protected LinearLayout.LayoutParams generateContentViewLayoutParams()
	{
		return new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	/**
	 * 获得activity布局
	 * 
	 * @return
	 */
	public View getContentView()
	{
		return mContentView;
	}

	/**
	 * 获得标题布局(onCreateTitleView方法有被重写时有效)
	 * 
	 * @return
	 */
	public View getTitleView()
	{
		return mTitleView;
	}

	/**
	 * 移除标题布局(onCreateTitleView方法有被重写时且返回不为null时有效)
	 */
	public void removeTileView()
	{
		if (mTitleView != null && mContentView != null)
		{
			mContentView.removeView(mTitleView);
		}
	}

	/**
	 * 改变标题布局(onCreateTitleView方法有被重写且返回不为null时有效)
	 * 
	 * @param view
	 */
	public void changeTitleView(View view)
	{
		if (mTitleView != null && view != null && mContentView != null)
		{
			mContentView.removeView(mTitleView);
			mContentView.addView(view, 0, generateTitleViewLayoutParams());
			mTitleView = view;
		}
	}

	@Override
	public void setContentView(View view, LayoutParams params)
	{
		super.setContentView(view, params);
	}

	public SDFragmentManager getSDFragmentManager()
	{
		if (mFragmentManager == null)
		{
			mFragmentManager = new SDFragmentManager(getSupportFragmentManager());
		}
		return mFragmentManager;
	}

	public boolean isEmpty(CharSequence content)
	{
		return TextUtils.isEmpty(content);
	}

	public boolean isEmpty(List<?> list)
	{
		return SDCollectionUtil.isEmpty(list);
	}

	@Override
	protected void onStart()
	{
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onStart(this);
		}
		super.onStart();
	}

	@Override
	protected void onRestart()
	{
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onRestart(this);
		}
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onResume(this);
		}
		SDActivityManager.getInstance().onResume(this);
		if (mIsNeedRefreshOnResume)
		{
			mIsNeedRefreshOnResume = false;
			onNeedRefreshOnResume();
		}
		super.onResume();
	}

	protected void onNeedRefreshOnResume()
	{

	}

	@Override
	protected void onPause()
	{
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onPause(this);
		}
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onStop(this);
		}
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		if (mListenerLifeCircle != null)
		{
			mListenerLifeCircle.onDestroy(this);
		}
		SDActivityManager.getInstance().onDestroy(this);
		SDEventManager.unregister(this);
		SDBroadcastUtil.unRegisterReceiver(mDefaultSDBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void finish()
	{
		SDActivityManager.getInstance().onDestroy(this);
		super.finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		try
		{
			super.onSaveInstanceState(outState);
			if (outState != null)
			{
				outState.remove("android:support:fragments");
			}
		} catch (Exception e)
		{
			onSaveInstanceStateException(e);
		}
	}

	protected void onSaveInstanceStateException(Exception e)
	{

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		try
		{
			super.onRestoreInstanceState(savedInstanceState);
		} catch (Exception e)
		{
			onRestoreInstanceStateException(e);
		}
	}

	protected void onRestoreInstanceStateException(Exception e)
	{

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (mListenerOnActivityResult != null)
		{
			mListenerOnActivityResult.onActivityResult(requestCode, resultCode, data, this);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed()
	{
		finish();
		super.onBackPressed();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onEvent(SDBaseEvent event)
	{

	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{

	}

	@Override
	public void onEventBackgroundThread(SDBaseEvent event)
	{

	}

	@Override
	public void onEventAsync(SDBaseEvent event)
	{

	}

	@Override
	public void onClick(View v)
	{

	}

	public interface SDActivityOnActivityResultListener
	{
		public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity);
	}

	public interface SDActivityLifeCircleListener
	{
		public void onCreate(Bundle savedInstanceState, Activity activity);

		public void onStart(Activity activity);

		public void onRestart(Activity activity);

		public void onResume(Activity activity);

		public void onPause(Activity activity);

		public void onStop(Activity activity);

		public void onDestroy(Activity activity);
	}
}
