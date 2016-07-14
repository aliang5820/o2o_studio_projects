package com.fanwe.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fanwe.businessclient.R;

/**
 * 
 * 评价
 */
public class Tab_1_Fragment extends BaseFragment implements OnClickListener
{
	private TextView mTvConsume, mTvGroupon, mTvCoupons, mTvActivity;
	private TextView mTvTitle;
	private LinearLayout mLlMode;

	private PopupWindow mPopupwindow;

	private BizDealrCtlFragment mBizDealrCtlFragment;
	private BizYouhuirCtlFragment mBizYouhuirCtlFragment;
	private BizEventrCtlFragment mBizEventrCtlFragment;
	private BizStorerCtlFragment mBizStorerCtlFragment;

	@Override
	protected int onCreateContentView()
	{
		return R.layout.m_frag_tab_1;
	}

	private void register(View view)
	{
		mTvTitle = (TextView) view.findViewById(R.id.tv_title);
		mLlMode = (LinearLayout) view.findViewById(R.id.ll_mode);
		mLlMode.setOnClickListener(this);
	}

	@Override
	protected void init()
	{
		register(getView());
		clickTvConsume();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ll_mode:
			clickLlMode(v);
			break;
		case R.id.tv_consume:
			clickTvConsume();
			break;
		case R.id.tv_groupon:
			clickTvGroupon();
			break;
		case R.id.tv_coupons:
			clickTvCoupons();
			break;
		case R.id.tv_activity:
			clickTvActivity();
			break;
		}
	}

	private void clickTvConsume()
	{
		mTvTitle.setText("消费评价");
		if (mBizDealrCtlFragment == null)
		{
			mBizDealrCtlFragment = new BizDealrCtlFragment();
		}
		getSDFragmentManager().replace(R.id.ll_content, mBizDealrCtlFragment);
		closePopupView();
	}

	private void clickTvGroupon()
	{
		mTvTitle.setText("优惠券评价");
		if (mBizYouhuirCtlFragment == null)
		{
			mBizYouhuirCtlFragment = new BizYouhuirCtlFragment();
		}
		getSDFragmentManager().replace(R.id.ll_content, mBizYouhuirCtlFragment);
		closePopupView();
	}

	private void clickTvCoupons()
	{
		mTvTitle.setText("活动评价");
		if (mBizEventrCtlFragment == null)
		{
			mBizEventrCtlFragment = new BizEventrCtlFragment();
		}
		getSDFragmentManager().replace(R.id.ll_content, mBizEventrCtlFragment);
		closePopupView();
	}

	private void clickTvActivity()
	{
		mTvTitle.setText("门店评价");
		if (mBizStorerCtlFragment == null)
		{
			mBizStorerCtlFragment = new BizStorerCtlFragment();
		}
		getSDFragmentManager().replace(R.id.ll_content, mBizStorerCtlFragment);
		closePopupView();
	}

	private void clickLlMode(View v)
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

	public void initmPopupWindowView(View v)
	{
		View view = getActivity().getLayoutInflater().inflate(R.layout.popupview_frag_tab1, null, false);
		mPopupwindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mPopupwindow.setOutsideTouchable(true);
		mPopupwindow.setFocusable(true);
		mPopupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		mPopupwindow.showAsDropDown(v, 0, 10);

		mTvConsume = (TextView) view.findViewById(R.id.tv_consume);
		mTvConsume.setOnClickListener(this);
		mTvGroupon = (TextView) view.findViewById(R.id.tv_groupon);
		mTvGroupon.setOnClickListener(this);
		mTvCoupons = (TextView) view.findViewById(R.id.tv_coupons);
		mTvCoupons.setOnClickListener(this);
		mTvActivity = (TextView) view.findViewById(R.id.tv_activity);
		mTvActivity.setOnClickListener(this);

	}

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		closePopupView();
	}

}
