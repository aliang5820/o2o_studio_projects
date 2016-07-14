package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.AppWebViewActivity;
import com.fanwe.library.customview.SDScaleImageView;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EventDetailTopFragment extends EventDetailBaseFragment
{

	@ViewInject(R.id.siv_image)
	private SDScaleImageView mSiv_image;

	@ViewInject(R.id.tv_name)
	private TextView mTv_name;

	@ViewInject(R.id.rb_star)
	private RatingBar mRb_star;

	@ViewInject(R.id.tv_star_number)
	private TextView mTv_star_number;

	@ViewInject(R.id.tv_total_count)
	private TextView mTv_total_count; // 活动名额

	@ViewInject(R.id.tv_score_limit)
	private TextView mTv_score_limit; // 消耗积分

	@ViewInject(R.id.tv_point_limit)
	private TextView mTv_point_limit; // 所需经验

	@ViewInject(R.id.tv_submit_count)
	private TextView mTv_submit_count; // 报名人数

	@ViewInject(R.id.tv_event_begin_time)
	private TextView mTv_event_begin_time; // 活动开始时间

	@ViewInject(R.id.tv_event_end_time)
	private TextView mTv_event_end_time; // 活动结束时间

	@ViewInject(R.id.tv_submit_left_time)
	private TextView mTv_submit_left_time; // 报名截止时间

	@ViewInject(R.id.tv_event_address)
	private TextView mTv_event_address; // 活动地点

	@ViewInject(R.id.tv_more_detail)
	private TextView mTv_more_detail; // 图文详情

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_event_detail_top);
	}

	@Override
	protected void init()
	{
		registerClick();
		bindData();
	}

	private void registerClick()
	{
		mTv_more_detail.setOnClickListener(this);
	}

	private void bindData()
	{
		if (!toggleFragmentView(mInfoModel))
		{
			return;
		}

		SDViewBinder.setImageView(mInfoModel.getIcon(), mSiv_image);
		SDViewBinder.setTextView(mTv_name, mInfoModel.getName());
		float ratingStar = SDTypeParseUtil.getFloat(mInfoModel.getAvg_point());
		SDViewBinder.setRatingBar(mRb_star, ratingStar);
		SDViewBinder.setTextView(mTv_star_number, String.valueOf(mInfoModel.getAvg_point()));
		SDViewBinder.setTextView(mTv_total_count, String.valueOf(mInfoModel.getTotal_count()));
		SDViewBinder.setTextView(mTv_score_limit, String.valueOf(mInfoModel.getScore_limit()));
		SDViewBinder.setTextView(mTv_point_limit, String.valueOf(mInfoModel.getPoint_limit()));
		SDViewBinder.setTextView(mTv_submit_count, String.valueOf(mInfoModel.getSubmit_count()));
		SDViewBinder.setTextView(mTv_event_begin_time, mInfoModel.getEvent_begin_time_format());
		SDViewBinder.setTextView(mTv_event_end_time, mInfoModel.getEvent_end_time_format());
		SDViewBinder.setTextView(mTv_event_address, mInfoModel.getAddress());
		SDViewBinder.setTextView(mTv_submit_left_time, mInfoModel.getSubmit_end_time_format());

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_more_detail:
			clickMoreDetail();
			break;

		default:
			break;
		}
	}

	private void clickMoreDetail()
	{
		String content = mInfoModel.getContent();
		if (!TextUtils.isEmpty(content))
		{
			Intent intent = new Intent(getActivity(), AppWebViewActivity.class);
			intent.putExtra(AppWebViewActivity.EXTRA_TITLE, "详情");
			intent.putExtra(AppWebViewActivity.EXTRA_HTML_CONTENT, content);
			startActivity(intent);
		}
	}

}