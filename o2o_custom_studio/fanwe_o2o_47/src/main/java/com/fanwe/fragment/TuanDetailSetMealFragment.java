package com.fanwe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.model.Deal_indexActModel;
import com.fanwe.o2o.newo2o.R;

/**
 * 
 * 
 * @author js02
 * 
 */
public class TuanDetailSetMealFragment extends TuanDetailBaseFragment
{

	private AppWebViewFragment mFragWeb;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_tuan_detail_set_meal);
	}

	@Override
	protected void init()
	{
		bindData(mDealModel);

		registerClick();
	}

	private void registerClick()
	{
	}

	private void bindData(Deal_indexActModel model)
	{
		if (!toggleFragmentView(model))
		{
			return;
		}

		String content = model.getSet_meal();
		if (!toggleFragmentView(content))
		{
			return;
		}

		mFragWeb = new AppWebViewFragment();
		mFragWeb.setHtmlContent(content);
		getSDFragmentManager().replace(R.id.frag_youhui_detail_detail_fl_webview, mFragWeb);
	}
}