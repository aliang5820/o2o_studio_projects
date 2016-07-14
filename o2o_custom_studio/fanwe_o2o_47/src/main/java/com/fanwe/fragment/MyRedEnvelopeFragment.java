package com.fanwe.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的红包
 * 
 * @author Administrator
 * 
 */
public class MyRedEnvelopeFragment extends MyRedEnvelopeInvalidFragment
{

	@ViewInject(R.id.iv_user_head)
	private ImageView mIv_user_head;

	@ViewInject(R.id.tv_count)
	private TextView mTv_count;

	@ViewInject(R.id.tv_total_money)
	private TextView mTv_total_money;

	@Override
	protected void init()
	{
		n_valid = 0;
		super.init();
	}

	@Override
	protected void findViews()
	{
		super.findViews();
		View view = getActivity().getLayoutInflater().inflate(R.layout.view_my_red_envelope_header, null);
		mPtrlv_content.getRefreshableView().addHeaderView(view);
		ViewUtils.inject(this, view);
	}

	protected void bindData(boolean isLoadMore)
	{
		SDViewBinder.setImageView(mActModel.getUser_avatar(), mIv_user_head);
		SDViewBinder.setTextView(mTv_count, String.valueOf(mActModel.getEcv_count()));
		SDViewBinder.setTextView(mTv_total_money, SDFormatUtil.formatMoneyChina(mActModel.getEcv_total()));
		super.bindData(isLoadMore);
	}

}
