package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.HomeSearchActivity;
import com.fanwe.MainActivity;
import com.fanwe.MapSearchActivity;
import com.fanwe.constant.ApkConstant;
import com.fanwe.constant.Constant.SearchTypeMap;
import com.fanwe.constant.Constant.SearchTypeNormal;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.fragment.WebViewFragment.EnumProgressMode;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.sunday.eventbus.SDBaseEvent;

public class TuanListWapFragment extends BaseFragment
{

	/** 大分类id(int) */
	public static final String EXTRA_CATE_ID = "extra_cate_id";

	/** 小分类id(int) */
	public static final String EXTRA_TID = "extra_tid";

	/** 商圈id(int) */
	public static final String EXTRA_QID = "extra_qid";

	/** 关键字(String) */
	public static final String EXTRA_KEY_WORD = "extra_key_word";

	/** 大分类id */
	private int cate_id;
	/** 小分类id */
	private int tid;
	/** 关键词 */
	private String keyword;
	/** 商圈id */
	private int qid;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.view_container);
	}

	/**
	 * 从intent获取数据
	 */
	private void getIntentData()
	{
		Intent intent = getActivity().getIntent();
		cate_id = intent.getIntExtra(EXTRA_CATE_ID, 0);
		tid = intent.getIntExtra(EXTRA_TID, 0);
		qid = intent.getIntExtra(EXTRA_QID, 0);
		keyword = intent.getStringExtra(EXTRA_KEY_WORD);
	}

	@Override
	protected void init()
	{
		getIntentData();
		initTitle();
		AppWebViewFragment frag = new AppWebViewFragment();
		frag.setmProgressMode(EnumProgressMode.HORIZONTAL);
		String url = ApkConstant.SERVER_API_URL_PRE + ApkConstant.SERVER_API_URL_MID + ApkConstant.URL_PART_WAP;

		UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
		urlBuilder.add("ctl", "tuan");
		urlBuilder.add("cate_id", String.valueOf(cate_id));
		urlBuilder.add("tid", String.valueOf(tid));
		urlBuilder.add("qid", String.valueOf(qid));
		urlBuilder.add("keyword", keyword);

		frag.setUrl(urlBuilder.build());
		getSDFragmentManager().replace(R.id.view_container_fl_content, frag);
	}

	private void initTitle()
	{
		String title = SDResourcesUtil.getString(R.string.tuan_gou);
		String cityName = AppRuntimeWorker.getCity_name();
		if (!TextUtils.isEmpty(cityName))
		{
			title = title + " - " + cityName;
		}
		mTitle.setMiddleTextTop(title);

		if (getActivity() instanceof MainActivity)
		{
			mTitle.setLeftImageLeft(0);
		} else
		{
			mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
		}

		mTitle.initRightItem(2);
		mTitle.getItemRight(0).setImageLeft(R.drawable.ic_location_home_top);
		mTitle.getItemRight(1).setImageLeft(R.drawable.ic_search_home_top);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		Intent intent = null;
		switch (index)
		{
		case 0:
			intent = new Intent(getActivity(), MapSearchActivity.class);
			intent.putExtra(MapSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeMap.TUAN);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(getActivity(), HomeSearchActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(HomeSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeNormal.TUAN);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case CITY_CHANGE:
			init();
			break;
		default:
			break;
		}
	}

}
