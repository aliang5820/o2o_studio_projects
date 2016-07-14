package com.fanwe;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.fragment.CommodityFragment;
import com.fanwe.fragment.GroupListFragment;
import com.fanwe.library.utils.SDViewBinder;

/**
 * 
 * 消费统计
 */
public class ConsumeStatisticsActivity extends BaseActivity implements OnClickListener
{
	private TextView mTvTitle;
	private LinearLayout mLlMode;
	private LinearLayout mLlBack;
	private PopupWindow mPopupwindow;

	private CommodityFragment mCommodityFragment;
	private GroupListFragment mGroupListFragment;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_consume_statistics);
		init();
	}

	private void init()
	{
		register();
		clickTvGroupList();
	}

	private void register()
	{
		mTvTitle = (TextView) findViewById(R.id.tv_title);
		SDViewBinder.setTextView(mTvTitle, "团购列表");

		mLlMode = (LinearLayout) findViewById(R.id.ll_mode);
		mLlMode.setOnClickListener(this);
		mLlBack = (LinearLayout) findViewById(R.id.ll_back);
		mLlBack.setOnClickListener(this);
	}

	public void initmPopupWindowView(View v)
	{
		View view = getLayoutInflater().inflate(R.layout.popupview_act_consume_statistics, null, false);
		mPopupwindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPopupwindow.setOutsideTouchable(true);
		mPopupwindow.setFocusable(true);
		mPopupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		mPopupwindow.showAsDropDown(v, 0, 10);

		LinearLayout mLlGroupList = (LinearLayout) view.findViewById(R.id.ll_group_list);
		mLlGroupList.setOnClickListener(this);
		LinearLayout mLlConsumeStatistics = (LinearLayout) view.findViewById(R.id.ll_consume_statistics);
		mLlConsumeStatistics.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ll_back:
			clickLlBack();
			break;
		case R.id.ll_mode:
			clickLlModel(v);
			break;
		case R.id.ll_group_list:
			clickTvGroupList();
			break;
		case R.id.ll_consume_statistics:
			clickTvConsumeStatistics();
			break;
		}
	}

	private void clickLlBack()
	{
		finish();
	}

	private void clickLlModel(View v)
	{
		if (mPopupwindow == null)
		{
			initmPopupWindowView(v);
		} else
		{
			if (!mPopupwindow.isShowing())
			{
				mPopupwindow.showAsDropDown(v, 0, 10);
			} else
			{
				closePopupView();
			}
		}
	}

	private void closePopupView()
	{
		if (mPopupwindow != null && mPopupwindow.isShowing())
		{
			mPopupwindow.dismiss();
		}
	}

	private void clickTvGroupList()
	{
		closePopupView();
		SDViewBinder.setTextView(mTvTitle, "团购列表");
		if (mGroupListFragment == null)
		{
			mGroupListFragment = new GroupListFragment();
		}
		getSDFragmentManager().replace(R.id.ll_content, mGroupListFragment);
	}

	private void clickTvConsumeStatistics()
	{
		closePopupView();
		SDViewBinder.setTextView(mTvTitle, "商品列表");
		if (mCommodityFragment == null)
		{
			mCommodityFragment = new CommodityFragment();
		}
		getSDFragmentManager().replace(R.id.ll_content, mCommodityFragment);
	}

}
