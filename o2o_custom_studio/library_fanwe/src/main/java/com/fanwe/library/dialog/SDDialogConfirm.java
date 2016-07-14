package com.fanwe.library.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 带标题，内容，确定按钮和取消按钮的窗口
 * 
 * @author js02
 * 
 */
public class SDDialogConfirm extends SDDialogCustom
{

	public TextView mTvContent;

	public SDDialogConfirm()
	{
		super();
	}

	public SDDialogConfirm(Activity activity)
	{
		super(activity);
	}

	@Override
	protected void init()
	{
		super.init();
		addTextView();
	}

	public SDDialogConfirm setTextContent(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mTvContent.setVisibility(View.GONE);
		} else
		{
			mTvContent.setVisibility(View.VISIBLE);
			mTvContent.setText(text);
		}
		return this;
	}

	private void addTextView()
	{
		View view = SDViewUtil.inflate(R.layout.dialog_confirm, null);

		mTvContent = (TextView) view.findViewById(R.id.dialog_confirm_tv_content);

		LinearLayout.LayoutParams params = SDViewUtil.getLayoutParamsLinearLayoutWW();
		setCustomView(view, params);
	}

}
