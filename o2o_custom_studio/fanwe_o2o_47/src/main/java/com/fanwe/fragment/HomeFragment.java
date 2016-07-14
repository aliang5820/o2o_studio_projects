package com.fanwe.fragment;

import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.IndexActAdvsModel;
import com.fanwe.model.Index_indexActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 首页fragment
 * 
 * @author js02
 * 
 */
public class HomeFragment extends BaseFragment
{
	@ViewInject(R.id.frag_home_new_ptrsv_all)
	private PullToRefreshScrollView mPtrsvAll;

	private HomeTitleBarFragment mFragTitle;

	private HomeAdvsFragment mFragAdvs;
	private HomeIndexFragment mFragIndex;
	private HomeZtFragment mFragZt;
	private HomeRecommendStoreFragment mFragRecommendSupplier;
	private HomeRecommendEvnetFragment mFragRecommendEvent;
	private HomeRecommendTuanFragment mFragRecommendDeals;
	private HomeRecommendGoodsFragment mFragRecommendGoods;
	private HomeRecommendYouhuiFragment mFragRecommendCoupon;

	// 米果
	private HomeForenoticeYouhui mFragForenoticeYouhui;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home);
	}

	@Override
	protected void init()
	{
		addTitleBarFragment();
		initPullToRefreshScrollView();
		locationCity();
	}

	private void locationCity()
	{
		if (BaiduMapManager.getInstance().hasLocationSuccess())
		{
			dealChangeLocation();
			requestIndex();
		} else
		{
			BaiduMapManager.getInstance().startLocation(new BDLocationListener()
			{

				@Override
				public void onReceiveLocation(BDLocation location)
				{
					dealChangeLocation();
					requestIndex();
				}
			});
		}
	}

	public static void dealChangeLocation()
	{
		String defaultCity = AppRuntimeWorker.getCity_name();
		if (TextUtils.isEmpty(defaultCity))
		{
			return;
		}
		if (!BaiduMapManager.getInstance().hasLocationSuccess())
		{
			return;
		}

		String dist = BaiduMapManager.getInstance().getDistrict();
		String distShort = BaiduMapManager.getInstance().getDistrictShort();
		String city = BaiduMapManager.getInstance().getCity();
		String cityShort = BaiduMapManager.getInstance().getCityShort();

		if (!dealLocation(dist))
		{
			if (!dealLocation(distShort))
			{
				if (!dealLocation(city))
				{
					if (!dealLocation(cityShort))
					{

					}
				}
			}
		}
	}

	private static boolean dealLocation(String locationName)
	{
		String defaultCity = AppRuntimeWorker.getCity_name();
		int cityId = AppRuntimeWorker.getCityIdByCityName(locationName);
		if (cityId > 0) // 地点存在于城市列表
		{
			if (!locationName.equals(defaultCity)) // 地点不是默认的
			{
				showChangeLocationDialog(locationName);
			}
			return true;
		} else
		{
			return false;
		}
	}

	private static void showChangeLocationDialog(final String location)
	{
		new SDDialogConfirm().setTextContent("当前定位位置为：" + location + "\n" + "是否切换到" + location + "?").setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				AppRuntimeWorker.setCity_name(location);
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		}).show();
	}

	public void scrollToTop()
	{
		if (isResumed() && mPtrsvAll != null)
		{
			SDViewUtil.scrollToViewY(mPtrsvAll.getRefreshableView(), 0, 200);
		}
	}

	private void initPullToRefreshScrollView()
	{
		mPtrsvAll.setMode(Mode.PULL_FROM_START);
		mPtrsvAll.setOnRefreshListener(new OnRefreshListener2<ScrollView>()
		{
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
				requestIndex();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
			}
		});
	}

	private void requestIndex()
	{
		RequestModel model = new RequestModel();
		model.putCtl("index");
		SDRequestCallBack<Index_indexActModel> handler = new SDRequestCallBack<Index_indexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					addFragmentsByActModel(actModel);
				}
			}

			@Override
			public void onFinish()
			{
				mPtrsvAll.onRefreshComplete();
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	protected void addFragmentsByActModel(Index_indexActModel actModel)
	{
		if (actModel == null)
		{
			return;
		}

		// 首页广告
		mFragAdvs = new HomeAdvsFragment();
		List<IndexActAdvsModel> listAdvs = actModel.getAdvs();
		mFragAdvs.setListIndexActAdvsModel(listAdvs);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_advs, mFragAdvs);

		// 首页分类
		mFragIndex = new HomeIndexFragment();
		mFragIndex.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_index, mFragIndex);

		// 米果优惠预告
		mFragForenoticeYouhui = new HomeForenoticeYouhui();
		mFragForenoticeYouhui.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_forenotice_youhui, mFragForenoticeYouhui);

		// 首页专题
		mFragZt = new HomeZtFragment();
		mFragZt.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_zt, mFragZt);

		// 推荐商家
		mFragRecommendSupplier = new HomeRecommendStoreFragment();
		mFragRecommendSupplier.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_recommend_supplier, mFragRecommendSupplier);

		// 推荐活动
		mFragRecommendEvent = new HomeRecommendEvnetFragment();
		mFragRecommendEvent.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_recommend_event, mFragRecommendEvent);

		// 推荐团购
		mFragRecommendDeals = new HomeRecommendTuanFragment();
		mFragRecommendDeals.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_recommend_deals, mFragRecommendDeals);

		// 推荐商品
		mFragRecommendGoods = new HomeRecommendGoodsFragment();
		mFragRecommendGoods.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_recommend_goods, mFragRecommendGoods);

		// 推荐优惠券
		mFragRecommendCoupon = new HomeRecommendYouhuiFragment();
		mFragRecommendCoupon.setmIndexModel(actModel);
		getSDFragmentManager().replace(R.id.frag_home_new_fl_recommend_coupon, mFragRecommendCoupon);

	}

	private void addTitleBarFragment()
	{
		mFragTitle = new HomeTitleBarFragment();
		getSDFragmentManager().replace(R.id.frag_home_new_fl_title_bar, mFragTitle);
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case CITY_CHANGE:
			requestIndex();
			break;
		case RETRY_INIT_SUCCESS:
			dealChangeLocation();
			break;
		default:
			break;
		}
	}

}