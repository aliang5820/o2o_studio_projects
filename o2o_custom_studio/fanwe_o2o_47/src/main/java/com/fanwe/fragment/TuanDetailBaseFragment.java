package com.fanwe.fragment;

import android.app.Activity;
import android.widget.TextView;

import com.fanwe.TuanDetailActivity;
import com.fanwe.model.Deal_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;

public class TuanDetailBaseFragment extends BaseFragment
{

	protected Deal_indexActModel mDealModel;
	private TextView mTvCurrentPrice;
	private TextView mTvOriginalPrice;

	public void setmDealModel(Deal_indexActModel mDealModel)
	{
		this.mDealModel = mDealModel;
	}

	public void updateGoodsPrice()
	{
		if (mDealModel != null)
		{
			double currentPrice = mDealModel.getCurrentPriceTotal();
			double originalPrice = mDealModel.getOriginalPriceTotal();

			getTextViewCurrentPrice().setText(SDFormatUtil.formatMoneyChina(currentPrice));
			// getTextViewOriginalPrice().setText(SDFormatUtil.formatMoneyChina(originalPrice));
		}
	}

	public TextView getTextViewCurrentPrice()
	{
		if (mTvCurrentPrice == null)
		{
			mTvCurrentPrice = (TextView) getActivity().findViewById(R.id.frag_tuan_detail_first_tv_current_price);
		}
		return mTvCurrentPrice;
	}

	public TextView getTextViewOriginalPrice()
	{
		if (mTvOriginalPrice == null)
		{
			mTvOriginalPrice = (TextView) getActivity().findViewById(R.id.frag_tuan_detail_first_tv_original_price);
		}
		return mTvOriginalPrice;
	}

	public TuanDetailAttrsFragment getTuanDetailAttrsFragment()
	{
		TuanDetailAttrsFragment fragment = null;
		TuanDetailActivity activity = getTuanDetailActivity();
		if (activity != null)
		{
			fragment = activity.getTuanDetailAttrsFragment();
		}
		return fragment;
	}

	public TuanDetailActivity getTuanDetailActivity()
	{
		TuanDetailActivity tuanDetailActivity = null;

		Activity activity = getActivity();
		if (activity != null && activity instanceof TuanDetailActivity)
		{
			tuanDetailActivity = (TuanDetailActivity) activity;
		}
		return tuanDetailActivity;
	}

	public void scrollToAttr()
	{
		TuanDetailActivity activity = getTuanDetailActivity();
		if (activity != null)
		{
			activity.scrollToAttr();
		}
	}

}