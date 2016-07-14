package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.AppWebViewActivity;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 商家描述 （详情fragment）
 * 
 * @author js02
 * 
 */
public class StoreDetailBriefFragment extends StoreDetailBaseFragment
{

	@ViewInject(R.id.ll_more)
	private LinearLayout mLl_more;

	private AppWebViewFragment mFragWeb;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_store_detail_brief);
	}

	@Override
	protected void init()
	{
		bindData();
		registerClick();
	}

	private void registerClick()
	{
		mLl_more.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String brief = mInfoModel.getBrief();
				Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
				intent.putExtra(AppWebViewActivity.EXTRA_HTML_CONTENT, brief);
				intent.putExtra(AppWebViewActivity.EXTRA_TITLE, SDResourcesUtil.getString(R.string.store_brief));
				startActivity(intent);
			}
		});
	}

	private void bindData()
	{
		if (!toggleFragmentView(mInfoModel))
		{
			return;
		}

		String brief = mInfoModel.getBrief();
		if (!toggleFragmentView(brief))
		{
			return;
		}

		mFragWeb = new AppWebViewFragment();
		mFragWeb.setHtmlContent(mInfoModel.getBrief());
		getSDFragmentManager().replace(R.id.frag_youhui_detail_detail_fl_webview, mFragWeb);
	}
}