package com.fanwe.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.CommentListActivity;
import com.fanwe.adapter.TuanDetailCommentAdapter;
import com.fanwe.app.App;
import com.fanwe.constant.Constant.CommentType;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.CommentModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 活动详细页（评价fragment）
 * 
 * @author js02
 * 
 */
public class EventDetailCommentFragment extends EventDetailBaseFragment
{
	@ViewInject(R.id.ll_all)
	private LinearLayout mLl_all;

	@ViewInject(R.id.ll_comment)
	private LinearLayout mLl_comment;

	@ViewInject(R.id.ll_more_comment)
	private LinearLayout mLl_more_comment;

	@ViewInject(R.id.tv_comment)
	private TextView mTv_comment;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_tuan_detail_comment);
	}

	@Override
	protected void init()
	{
		bindData();
		registeClick();
	}

	private void registeClick()
	{
		mLl_more_comment.setOnClickListener(this);
		mTv_comment.setOnClickListener(this);
	}

	private void bindData()
	{
		if (!toggleFragmentView(mEventModel))
		{
			return;
		}

		List<CommentModel> listModel = mEventModel.getDp_list();
		changeViewState(listModel);
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			mLl_comment.removeAllViews();
			TuanDetailCommentAdapter adapter = new TuanDetailCommentAdapter(listModel, getActivity());
			for (int i = 0; i < listModel.size(); i++)
			{
				mLl_comment.addView(adapter.getView(i, null, null));
			}
		}
	}

	private void changeViewState(List<CommentModel> listModel)
	{
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			SDViewUtil.show(mLl_all);
			SDViewUtil.hide(mTv_comment);
		} else
		{
			SDViewUtil.show(mTv_comment);
			SDViewUtil.hide(mLl_all);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.ll_more_comment:
			clickMoreComment();
			break;
		case R.id.tv_comment:
			clickMoreComment();
			break;

		default:
			break;
		}
	}

	private void clickMoreComment()
	{
		Intent intent = new Intent(App.getApplication(), CommentListActivity.class);
		intent.putExtra(CommentListActivity.EXTRA_ID, mInfoModel.getId());
		intent.putExtra(CommentListActivity.EXTRA_TYPE, CommentType.EVENT);
		startActivity(intent);
	}

}