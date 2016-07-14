package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.AppWebViewActivity;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 团购详情（查看图文详情）
 * 
 * @author js02
 * 
 */
public class TuanDetailMoreDetailFragment extends TuanDetailBaseFragment
{

	@ViewInject(R.id.ll_more_detail)
	private LinearLayout mLl_more_detail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.frag_tuan_detail_more_detail, container, false);
		return view;
	}

	@Override
	protected void init()
	{
		changeViewState();
		registerClick();
	}

	private void changeViewState()
	{
		if (!toggleFragmentView(mDealModel))
		{
			return;
		}
		if (!toggleFragmentView(mDealModel.getDescription()))
		{
			return;
		}
	}

	private void registerClick()
	{
		mLl_more_detail.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				clickMoreDetail();
			}
		});
	}

	private void clickMoreDetail()
	{
		if (mDealModel != null && !TextUtils.isEmpty(mDealModel.getDescription()))
		{
			Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
			intent.putExtra(AppWebViewActivity.EXTRA_TITLE, "详情");
			intent.putExtra(AppWebViewActivity.EXTRA_HTML_CONTENT, mDealModel.getDescription());
			startActivity(intent);
		} else
		{
			hideFragmentView();
		}
	}
}