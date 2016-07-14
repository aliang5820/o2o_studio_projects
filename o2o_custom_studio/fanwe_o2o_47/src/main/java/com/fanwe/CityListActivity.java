package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fanwe.adapter.CityListAdapter;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.customview.SideBar;
import com.fanwe.customview.SideBar.OnTouchingLetterChangedListener;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.CharacterParser;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.CitylistModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

/**
 * 城市列表
 * 
 * @author js02
 * 
 */
public class CityListActivity extends BaseActivity
{

	@ViewInject(R.id.ll_location_city)
	private LinearLayout mLl_location_city;

	@ViewInject(R.id.ll_hot_city)
	private LinearLayout mLl_hot_city;

	@ViewInject(R.id.flow_hot_city)
	private FlowLayout mFlow_hot_city;

	@ViewInject(R.id.tv_location)
	private TextView mTv_location;

	@ViewInject(R.id.act_city_list_et_search)
	private ClearEditText mEtSearch;

	@ViewInject(R.id.act_city_list_lv_citys)
	private ListView mLvCitys;

	@ViewInject(R.id.act_city_list_tv_touched_letter)
	private TextView mTvTouchedLetter;

	@ViewInject(R.id.act_city_list_sb_letters)
	private SideBar mSbLetters;

	private List<CitylistModel> mListModel = new ArrayList<CitylistModel>();
	private List<CitylistModel> mListModelHotCity;
	private List<CitylistModel> mListFilterModel = new ArrayList<CitylistModel>();

	private CityListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_city_list);
		init();
	}

	private void init()
	{
		initTitle();
		bindDefaultData();
		initViewState();
		bindDataFromDb();
		initSlideBar();
		initCurrentLocation();
		registeEtSearchListener();
		registeClick();
	}

	private void initViewState()
	{
		mListModelHotCity = AppRuntimeWorker.getCitylistHot();

		if (!SDCollectionUtil.isEmpty(mListModelHotCity))
		{
			SDViewUtil.show(mLl_hot_city);
			mFlow_hot_city.removeAllViews();
			for (CitylistModel model : mListModelHotCity)
			{
				View cityView = createHotCityButton(model);
				if (cityView != null)
				{
					mFlow_hot_city.addView(cityView);
				}
			}
		} else
		{
			SDViewUtil.hide(mLl_hot_city);
		}
	}

	private TextView createHotCityButton(final CitylistModel model)
	{
		TextView btn = null;
		if (model != null)
		{
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(SDViewUtil.dp2px(50), SDViewUtil.dp2px(40));
			btn = new TextView(getApplicationContext());
			btn.setLayoutParams(params);
			btn.setGravity(Gravity.CENTER);
			btn.setText(model.getName());
			SDViewUtil.setTextSizeSp(btn, 13);
			btn.setTextColor(SDResourcesUtil.getColor(R.color.gray));
			btn.setBackgroundResource(R.drawable.selector_white_gray_stroke_all);
			btn.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (AppRuntimeWorker.setCity_name(model.getName()))
					{
						finish();
					} else
					{
						SDToast.showToast("设置失败");
					}
				}
			});
		}
		return btn;
	}

	private void initCurrentLocation()
	{
		if (!BaiduMapManager.getInstance().hasLocationSuccess())
		{
			locationCity();
		} else
		{
			updateLocationTextView();
		}
	}

	protected void locationCity()
	{
		mTv_location.setText("定位中");
		BaiduMapManager.getInstance().startLocation(new BDLocationListener()
		{

			@Override
			public void onReceiveLocation(BDLocation location)
			{
				updateLocationTextView();
			}
		});
	}

	private void updateLocationTextView()
	{
		if (BaiduMapManager.getInstance().hasLocationSuccess())
		{
			String dist = BaiduMapManager.getInstance().getDistrictShort();
			if (AppRuntimeWorker.getCityIdByCityName(dist) > 0)
			{
				mTv_location.setText(dist);
			} else
			{
				String city = BaiduMapManager.getInstance().getCityShort();
				mTv_location.setText(city);
			}
		} else
		{
			mTv_location.setText("定位失败，点击重试");
		}
	}

	private void registeEtSearchListener()
	{
		mEtSearch.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				filterData(s.toString());
			}
		});
	}

	protected void filterData(String key)
	{
		mListFilterModel.clear();
		if (TextUtils.isEmpty(key))
		{
			mListFilterModel.addAll(mListModel);
		} else
		{
			for (CitylistModel city : mListModel)
			{
				String name = city.getName();
				if (name.indexOf(key) != -1 || CharacterParser.getLetters(name).startsWith(key))
				{
					mListFilterModel.add(city);
				}
			}
		}
		mAdapter.updateData(mListFilterModel);
	}

	private void initSlideBar()
	{
		mSbLetters.setTextView(mTvTouchedLetter);
		mSbLetters.setOnTouchingLetterChangedListener(new CityListActivity_OnTouchingLetterChangedListener());
	}

	private void bindDataFromDb()
	{
		List<CitylistModel> listDbModel = AppRuntimeWorker.getCitylist();
		if (listDbModel != null && listDbModel.size() > 0)
		{
			mListModel.addAll(listDbModel);
		} else
		{
			mListModel.clear();
		}
		mAdapter.updateData(mListModel);
	}

	private void bindDefaultData()
	{
		mAdapter = new CityListAdapter(mListModel, this);
		mLvCitys.setAdapter(mAdapter);
	}

	private void initTitle()
	{
		String title = null;
		String city = AppRuntimeWorker.getCity_name();
		if (TextUtils.isEmpty(city))
		{
			title = "城市列表";
		} else
		{
			title = "当前城市 - " + city;
		}
		mTitle.setMiddleTextTop(title);
	}

	private void registeClick()
	{
		mLl_location_city.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (BaiduMapManager.getInstance().hasLocationSuccess())
				{
					String locationCity = mTv_location.getText().toString();
					int cityId = AppRuntimeWorker.getCityIdByCityName(locationCity);
					if (cityId < 0)
					{
						SDToast.showToast("不支持当前城市:" + locationCity);
					} else
					{
						AppRuntimeWorker.setCity_name(locationCity);
						finish();
					}
				} else
				{
					locationCity();
				}
			}
		});
	}

	@Override
	public void onClick(View v)
	{

	}

	class CityListActivity_OnTouchingLetterChangedListener implements OnTouchingLetterChangedListener
	{
		@Override
		public void onTouchingLetterChanged(String s)
		{
			int position = mAdapter.getLettersAsciisFirstPosition(s.charAt(0));
			if (position != -1)
			{
				mLvCitys.setSelection(position);
			}
		}

	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case CITY_CHANGE:
			initTitle();
			break;

		default:
			break;
		}
	}

}