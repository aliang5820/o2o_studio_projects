package com.fanwe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.CommentListActivity;
import com.fanwe.constant.Constant.CommentType;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 团购详情（评分）
 * 
 * @author js02
 * 
 */
public class TuanDetailRatingFragment extends TuanDetailBaseFragment
{

	@ViewInject(R.id.rl_comment_detail)
	private RelativeLayout mRlCommentDetail = null;

	@ViewInject(value = R.id.rb_star)
	private RatingBar mRbRatingStar = null;

	@ViewInject(value = R.id.tv_star_number)
	private TextView mTvStarNumber = null;

	@ViewInject(value = R.id.tv_comment_count)
	private TextView mTvCommentCount = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.frag_tuan_detail_rating, container, false);
		return view;
	}

	@Override
	protected void init()
	{
		mRbRatingStar.setStepSize(0.5f);
		bindData();
		registerClick();
	}

	private void registerClick()
	{
		mRlCommentDetail.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mDealModel != null)
				{
					Intent intent = new Intent(getActivity(), CommentListActivity.class);
					intent.putExtra(CommentListActivity.EXTRA_ID, mDealModel.getId());
					intent.putExtra(CommentListActivity.EXTRA_TYPE, CommentType.DEAL);
					startActivity(intent);
				}
			}
		});
	}

	private void bindData()
	{
		if (!toggleFragmentView(mDealModel))
		{
			return;
		}

		// 设置ratingBar
		float ratingStar = SDTypeParseUtil.getFloat(mDealModel.getAvg_point());
		SDViewBinder.setRatingBar(mRbRatingStar, ratingStar);
		SDViewBinder.setTextView(mTvStarNumber, String.valueOf(mDealModel.getAvg_point()));
		// 评论人数
		mTvCommentCount.setText(mDealModel.getDp_count() + "人评分");
	}
}