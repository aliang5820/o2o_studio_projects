package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.fanwe.AppWebViewActivity;
import com.fanwe.library.fragment.WebViewFragment.WebViewFragmentListener;
import com.fanwe.o2o.newo2o.R;

/**
 * 首页专题fragment
 * 
 * @author Administrator
 * 
 */
public class HomeZtFragment extends HomeBaseFragment
{

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_home_zt);
	}

	@Override
	protected void init()
	{
		bindData();
	}

	private void bindData()
	{
		if (!toggleFragmentView(mIndexModel))
		{
			return;
		}

		String ztHtml = mIndexModel.getZt_html();
		if (!toggleFragmentView(ztHtml))
		{
			return;
		}

		AppWebViewFragment frag = new AppWebViewFragment();
		frag.setHtmlContent(ztHtml);
		frag.setmListener(new WebViewFragmentListener()
		{
			@Override
			public boolean onLoadUrl(WebView webView, String url)
			{
				if (!isEmpty(url))
				{
					if ("about:blank".equals(url))
					{

					} else
					{
						Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
						intent.putExtra(AppWebViewActivity.EXTRA_URL, url);
						startActivity(intent);
					}
				}
				return true;
			}

			@Override
			public void onInitFinish(WebView webView)
			{
			}
		});
		getSDFragmentManager().replace(R.id.frag_home_zt_fl_content, frag);
	}

	@Override
	protected void onRefreshData()
	{
		// TODO Auto-generated method stub
		super.onRefreshData();
	}

}
