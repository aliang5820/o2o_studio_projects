package com.fanwe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.o2o.newo2o.R;

/**
 * 优惠详情 （详情fragment）
 * 
 * @author js02
 * 
 */
public class YouhuiDetailHtmlDetailFragment extends YouhuiDetailBaseFragment
{

	private AppWebViewFragment mFragWeb;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_youhui_detail_html_detail);
	}

	@Override
	protected void init()
	{
		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mInfoModel))
		{
			return;
		}

		String desc = mInfoModel.getDescription();
		if (!toggleFragmentView(desc))
		{
			return;
		}

		mFragWeb = new AppWebViewFragment();
		mFragWeb.setHtmlContent(desc);
		getSDFragmentManager().replace(R.id.frag_youhui_detail_detail_fl_webview, mFragWeb);

	}
}