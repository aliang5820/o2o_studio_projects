package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.adapter.InitAdvsPagerAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.common.CommonInterface;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDPagerAdapter.SDBasePagerAdapterOnItemClickListener;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.customview.SDSlidingPlayView.SDSlidingPlayViewOnTouchListener;
import com.fanwe.library.customview.SDViewPager.EnumMeasureMode;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.InitActStart_pageModel;
import com.fanwe.model.Init_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;

/**
 * 初始化Activity
 * 
 */
public class InitAdvsMultiActivity extends BaseActivity
{

	/** 广告图片显示时间 */
	private static final long ADVS_DISPLAY_TIME = 2000;

	/** 正常初始化成功后显示时间 */
	private static final long NORMAL_DISPLAY_TIME = 1000;

	@ViewInject(R.id.btn_skip)
	private Button mBtn_skip;

	@ViewInject(R.id.spv_content)
	private SDSlidingPlayView mSpvAd;

	private InitAdvsPagerAdapter mAdapter;

	private SDTimer mTimer = new SDTimer();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_init_advs_multi);
		init();
	}

	private void init()
	{
		startLocation();
		enableUmengPush();
		MobclickAgent.updateOnlineConfig(this);
		registerClick();
		initSlidingPlayView();
		requestInitInterface();
	}

	private void startLocation()
	{
		BaiduMapManager.getInstance().startLocation(new BDLocationListener()
		{
			@Override
			public void onReceiveLocation(BDLocation arg0)
			{
			}
		});
	}

	private void enableUmengPush()
	{
		LogUtil.i("isenable:" + UmengPushManager.getPushAgent().isEnabled() + "registionId:" + UmengPushManager.getPushAgent().getRegistrationId());
		UmengPushManager.getPushAgent().enable(new IUmengRegisterCallback()
		{

			@Override
			public void onRegistered(String arg0)
			{
				LogUtil.i(arg0);
			}
		});
	}

	private void registerClick()
	{
		mBtn_skip.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startMainActivity();
			}
		});
	}

	private void initSlidingPlayView()
	{
		mSpvAd.mVpgContent.setmMeasureMode(EnumMeasureMode.NORMAL);
		mSpvAd.setmImageNormalResId(R.drawable.ic_main_dot2_normal);
		mSpvAd.setmImageSelectedResId(R.drawable.ic_main_dot2_foused);
		mSpvAd.setmListenerOnTouch(new SDSlidingPlayViewOnTouchListener()
		{

			@Override
			public void onUp(View v, MotionEvent event)
			{

			}

			@Override
			public void onTouch(View v, MotionEvent event)
			{

			}

			@Override
			public void onMove(View v, MotionEvent event)
			{
				if (mAdapter != null && mAdapter.getCount() > 1)
				{
					mTimer.stopWork();
				}
			}

			@Override
			public void onDown(View v, MotionEvent event)
			{

			}
		});
	}

	private void requestInitInterface()
	{
		CommonInterface.requestInit(new SDRequestCallBack<Init_indexActModel>()
		{
			private boolean nSuccess = false;

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					nSuccess = true;
					dealInitSuccess(actModel);
				}
			}

			@Override
			public void onStart()
			{
			}

			@Override
			public void onFinish()
			{
				if (!nSuccess)
				{
					startMainActivity();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				nSuccess = false;
			}
		});
	}

	protected void dealInitSuccess(Init_indexActModel model)
	{
		List<InitActStart_pageModel> listModel = model.getStart_page_new();
		bindAdvsImages(listModel);
	}

	protected void bindAdvsImages(List<InitActStart_pageModel> listModel)
	{
		List<InitActStart_pageModel> listModelCached = findCachedModel(listModel);
		if (!SDCollectionUtil.isEmpty(listModelCached))
		{
			mAdapter = new InitAdvsPagerAdapter(listModelCached, mActivity);
			mAdapter.setmListenerOnItemClick(new SDBasePagerAdapterOnItemClickListener()
			{
				@Override
				public void onItemClick(View v, int position)
				{
					InitActStart_pageModel model = mAdapter.getItemModel(position);
					if (model != null)
					{
						int type = model.getType();
						Intent intent = AppRuntimeWorker.createIntentByType(type, model.getData(), false);
						if (intent != null)
						{
							try
							{
								mTimer.stopWork();
								intent.putExtra(BaseActivity.EXTRA_IS_ADVS, true);
								startActivity(intent);
								finish();
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			});
			mSpvAd.setAdapter(mAdapter);
			startAdvsDisplayTimer();
			SDViewUtil.show(mBtn_skip);
		} else
		{
			startNormalDisplayTimer();
		}
	}

	/**
	 * 找到已经缓存过的实体
	 * 
	 * @param listModel
	 * @return
	 */
	private List<InitActStart_pageModel> findCachedModel(List<InitActStart_pageModel> listModel)
	{
		List<InitActStart_pageModel> listCachedModel = new ArrayList<InitActStart_pageModel>();
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			for (InitActStart_pageModel model : listModel)
			{
				String url = model.getImg();
				if (ImageLoaderManager.isCacheExistOnDisk(url))
				{
					listCachedModel.add(model);
				} else
				{
					ImageLoader.getInstance().loadImage(url, null);
				}
			}
		}
		return listCachedModel;
	}

	private void startAdvsDisplayTimer()
	{
		mTimer.startWork(ADVS_DISPLAY_TIME, Long.MAX_VALUE, new SDTimerListener()
		{
			@Override
			public void onWorkMain()
			{
				startMainActivity();
			}

			@Override
			public void onWork()
			{

			}
		});
	}

	private void startNormalDisplayTimer()
	{
		mTimer.startWork(NORMAL_DISPLAY_TIME, Long.MAX_VALUE, new SDTimerListener()
		{
			@Override
			public void onWorkMain()
			{
				startMainActivity();
			}

			@Override
			public void onWork()
			{

			}
		});
	}

	private void startMainActivity()
	{
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy()
	{
		mTimer.stopWork();
		super.onDestroy();
	}

}
