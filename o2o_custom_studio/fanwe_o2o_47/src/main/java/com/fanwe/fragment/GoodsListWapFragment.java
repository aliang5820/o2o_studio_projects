package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.HomeSearchActivity;
import com.fanwe.MainActivity;
import com.fanwe.constant.ApkConstant;
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

public class GoodsListWapFragment extends BaseFragment
{

	/** 关键词(String) */
	public static String EXTRA_KEY_WORD = "extra_key_word";

	/** 分类id(int) */
	public static final String EXTRA_CATE_ID = "extra_cate_id";

	/** 品牌id(int) */
	public static final String EXTRA_BID = "extra_bid";

	/** 商品分类ID */
	private int cate_id;
	/** 品牌ID */
	private int bid;
	/** 关键字 */
	private String keyword;

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
		Intent i = getActivity().getIntent();

		keyword = i.getStringExtra(EXTRA_KEY_WORD);
		cate_id = i.getIntExtra(EXTRA_CATE_ID, 0);
		bid = i.getIntExtra(EXTRA_BID, 0);
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
		urlBuilder.add("ctl", "goods");
		urlBuilder.add("cate_id", String.valueOf(cate_id));
		urlBuilder.add("bid", String.valueOf(bid));
		urlBuilder.add("keyword", keyword);

		frag.setUrl(urlBuilder.build());
		getSDFragmentManager().replace(R.id.view_container_fl_content, frag);
	}

	private void initTitle()
	{
		String title = SDResourcesUtil.getString(R.string.mall);
		String city = AppRuntimeWorker.getCity_name();
		if (!TextUtils.isEmpty(city))
		{
			title = title + " - " + city;
		}

		mTitle.setMiddleTextTop(title);

		if (getActivity() instanceof MainActivity)
		{
			mTitle.setLeftImageLeft(0);
		} else
		{
			mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
		}

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setImageLeft(R.drawable.ic_search_home_top);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(HomeSearchActivity.EXTRA_SEARCH_TYPE, SearchTypeNormal.SHOP);
		startActivity(intent);
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
