package com.fanwe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Show_articleActModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;

/**
 * 项目详情web界面
 * 
 */
public class DetailWebviewActivity extends TitleBaseActivity
{
	/** webview 要加载的链接 */
	public static final String EXTRA_URL = "extra_url";
	/** webview 界面标题 */
	public static final String EXTRA_TITLE = "extra_title";
	/** 要显示的文章的ID */
	public static final String EXTRA_ARTICLE_ID = "extra_article_id";
	/** 要显示的HTML内容 */
	public static final String EXTRA_HTML_CONTENT = "extra_html_content";

	private ProgressBar mPgbProgress;
	private WebView mWeb;

	private String mStrUrl;
	private String mStrTitle;
	private String mStrArticleId;
	private String mStrHtmlContent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_detail_webview);
		init();
	}

	private void init()
	{
		initIntentData();
		register();
		initWebView();
		startLoadData();
	}

	private void initIntentData()
	{
		mStrUrl = getIntent().getStringExtra(EXTRA_URL);
		mStrTitle = getIntent().getStringExtra(EXTRA_TITLE);
		mStrArticleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);
		mStrHtmlContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
	}

	private void register()
	{
		mWeb = (WebView) findViewById(R.id.act_detail_webview);
		mPgbProgress = (ProgressBar) findViewById(R.id.act_detailwebview_pgb);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView()
	{
		WebSettings settings = mWeb.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		// settings.setUseWideViewPort(true);
		// settings.setLoadWithOverviewMode(true);
		mWeb.setWebViewClient(new ProjectDetailWebviewActivity_WebViewClient());
		mWeb.setWebChromeClient(new ProjectDetailWebviewActivity_WebChromeClient());
	}

	private void startLoadData()
	{
		if (mStrHtmlContent != null)
		{
			loadHtmlContent(mStrHtmlContent);
			return;
		}

		if (mStrArticleId != null)
		{
			loadArticleDetail(mStrArticleId);
			return;
		}

		if (mStrUrl != null)
		{
			loadUrl(mStrUrl);
			return;
		}

	}

	private void loadHtmlContent(String htmlContent)
	{
		if (htmlContent != null)
		{
			mWeb.loadDataWithBaseURL("about:blank", htmlContent, "text/html", "utf-8", null);
		}
	}

	private void loadUrl(String url)
	{
		if (!TextUtils.isEmpty(url))
		{
			Log.i("ProjectDetailWebviewActivity", url);
			mWeb.loadUrl(mStrUrl);
		}
	}

	protected void loadArticleDetail(String articleId)
	{
		RequestModel model = new RequestModel();
		model.putCtl("show_article");
		model.put("id", articleId);

		SDRequestCallBack<Show_articleActModel> handler = new SDRequestCallBack<Show_articleActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
			}

			@Override
			public void onSuccess(Show_articleActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, DetailWebviewActivity.this))
				{
					if (actModel.getStatus() == 1)
					{
						if (TextUtils.isEmpty(actModel.getContent()))
						{
							SDToast.showToast("没有消息详情!");
						} else
						{
							loadHtmlContent(actModel.getContent());
						}

						if (!TextUtils.isEmpty(mStrTitle))
						{
							mTitle.setText(actModel.getTitle());
						} else
						{
							if (!TextUtils.isEmpty(actModel.getTitle()))
							{
								mTitle.setText(actModel.getTitle());
							}
						}
					} else
					{
						SDToast.showToast("获取消息详情失败!");
					}
				}
			}

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
			}

		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	class ProjectDetailWebviewActivity_WebViewClient extends WebViewClient
	{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{

			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url)
		{

			super.onPageFinished(view, url);
		}
	}

	class ProjectDetailWebviewActivity_WebChromeClient extends WebChromeClient
	{

		@Override
		public void onProgressChanged(WebView view, int newProgress)
		{
			if (newProgress == 100)
			{
				mPgbProgress.setVisibility(View.GONE);
			} else
			{
				if (mPgbProgress.getVisibility() == View.GONE)
				{
					mPgbProgress.setVisibility(View.VISIBLE);
				}
				mPgbProgress.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWeb.canGoBack())
		{
			mWeb.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}