package com.fanwe.fragment;

import android.webkit.JavascriptInterface;

import com.fanwe.config.AppConfig;
import com.fanwe.constant.ApkConstant;
import com.fanwe.jshandler.AppJsHandler;
import com.fanwe.library.fragment.WebViewFragment;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.library.utils.SDHandlerUtil;
import com.fanwe.library.utils.UrlLinkBuilder;

public class AppWebViewFragment extends WebViewFragment
{

	@Override
	public void setUrl(String url)
	{
		this.mStrUrl = wrapperUrl(url);
	}

	@Override
	protected void addJavascriptInterface()
	{
		// 详情回调处理
		AppJsHandler detailHandler = new AppJsHandler(getActivity())
		{
			@Override
			@JavascriptInterface
			public void page_title(final String title)
			{
				SDHandlerUtil.runOnUiThread(new Runnable()
				{

					@Override
					public void run()
					{
						setTitle(title);
					}
				});
			}
		};
		mWeb.addJavascriptInterface(detailHandler, detailHandler.getName());
	}

	private String wrapperUrl(String url)
	{
		String resultUrl = putSessionId(url);
		resultUrl = putPageType(resultUrl);
		resultUrl = putRefId(resultUrl);
		return resultUrl;
	}

	private String putPageType(String url)
	{
		if (!isEmpty(url))
		{
			url = new UrlLinkBuilder(url).add("page_type", "app").build();
		}
		return url;
	}

	private String putRefId(String url)
	{
		String refId = AppConfig.getRefId();
		if (!isEmpty(url) && !isEmpty(refId))
		{
			url = new UrlLinkBuilder(url).add("r", SDBase64.encodeToString(refId)).build();
		}
		return url;
	}

	private String putSessionId(String url)
	{
		// 如果是域名的url，添加session
		if (url != null && url.contains(ApkConstant.SERVER_API_URL_MID))
		{
			String sessionId = AppConfig.getSessionId();
			if (sessionId != null)
			{
				url = new UrlLinkBuilder(url).add("sess_id", sessionId).build();
			}
		}
		return url;
	}

}
