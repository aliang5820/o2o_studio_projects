package com.fanwe;

import android.os.Bundle;

import com.fanwe.constant.ApkConstant;
import com.fanwe.fragment.AppWebViewFragment;
import com.fanwe.library.fragment.WebViewFragment.EnumProgressMode;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.o2o.newo2o.R;

/**
 * 分销小店
 * 
 * @author Administrator
 * 
 */
public class DistributionStoreWapActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_distribution_store_wap);
		init();
	}

	private void init()
	{
		AppWebViewFragment frag = new AppWebViewFragment();

		String url = ApkConstant.SERVER_API_URL_PRE + ApkConstant.SERVER_API_URL_MID + ApkConstant.URL_PART_WAP;
		UrlLinkBuilder urlBuilder = new UrlLinkBuilder(url);
		urlBuilder.add("ctl", "uc_fx");
		urlBuilder.add("act", "mall");

		frag.setShowTitle(true);
		frag.setUrl(urlBuilder.build());
		frag.setmProgressMode(EnumProgressMode.HORIZONTAL);
		getSDFragmentManager().replace(R.id.act_distribution_store_wap_fl_all, frag);
	}

}
