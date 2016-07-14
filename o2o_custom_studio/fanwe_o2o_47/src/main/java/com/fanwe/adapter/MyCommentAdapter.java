package com.fanwe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.AlbumActivity;
import com.fanwe.EventDetailActivity;
import com.fanwe.StoreDetailActivity;
import com.fanwe.TuanDetailActivity;
import com.fanwe.YouHuiDetailActivity;
import com.fanwe.constant.Constant.CommentType;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.CommentModel;
import com.fanwe.o2o.newo2o.R;

public class MyCommentAdapter extends SDSimpleAdapter<CommentModel>
{

	private int mImageViewWidth = 150;

	public MyCommentAdapter(List<CommentModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_comment;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final CommentModel model)
	{
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_content = ViewHolder.get(R.id.tv_content, convertView);
		LinearLayout ll_replay = ViewHolder.get(R.id.ll_replay, convertView);
		TextView tv_reply_content = ViewHolder.get(R.id.tv_reply_content, convertView);
		TextView tv_time = ViewHolder.get(R.id.tv_time, convertView);
		RatingBar rb_star = ViewHolder.get(R.id.rb_star, convertView);
		View viewDiv = ViewHolder.get(R.id.view_div, convertView);

		FlowLayout flow_images = ViewHolder.get(R.id.flow_images, convertView);
		flow_images.removeAllViews();
		if (position == 0)
		{
			SDViewUtil.hide(viewDiv);
		} else
		{
			SDViewUtil.show(viewDiv);
		}
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_content, model.getContent());
		SDViewBinder.setTextView(tv_reply_content, model.getReply_content());
		SDViewBinder.setTextView(tv_time, model.getCreate_time());
		SDViewBinder.setRatingBar(rb_star, model.getPointFloat());

		String replyContent = model.getReply_content();
		if (TextUtils.isEmpty(replyContent))
		{
			ll_replay.setVisibility(View.GONE);
		} else
		{
			ll_replay.setVisibility(View.VISIBLE);
		}

		bindCommentImages(model.getImages(), model.getOimages(), flow_images);

		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = null;
				String type = model.getType();
				if (CommentType.DEAL.equals(type))
				{
					intent = new Intent(mActivity, TuanDetailActivity.class);
					intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, model.getData_id());
				} else if (CommentType.YOUHUI.equals(type))
				{
					intent = new Intent(mActivity, YouHuiDetailActivity.class);
					intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, model.getData_id());
				} else if (CommentType.EVENT.equals(type))
				{
					intent = new Intent(mActivity, EventDetailActivity.class);
					intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, model.getData_id());
				} else if (CommentType.STORE.equals(type))
				{
					intent = new Intent(mActivity, StoreDetailActivity.class);
					intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, model.getData_id());
				}
				if (intent != null)
				{
					mActivity.startActivity(intent);
				}
			}
		});
	}

	private void bindCommentImages(List<String> listImage, List<String> listOimage, FlowLayout flow_images)
	{
		if (listImage != null && !listImage.isEmpty())
		{
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mImageViewWidth, mImageViewWidth);
			for (int i = 0; i < listImage.size(); i++)
			{
				ImageView iv = new ImageView(mActivity);
				iv.setOnClickListener(new ImageViewClickListener(listOimage, i));
				SDViewBinder.setImageView(listImage.get(i), iv);
				flow_images.addView(iv, params);
			}
		}
	}

	class ImageViewClickListener implements View.OnClickListener
	{

		private List<String> nListOimage;
		private int nIndex;

		public ImageViewClickListener(List<String> nListOimage, int nIndex)
		{
			this.nListOimage = nListOimage;
			this.nIndex = nIndex;
		}

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(mActivity, AlbumActivity.class);
			intent.putExtra(AlbumActivity.EXTRA_IMAGES_INDEX, nIndex);
			intent.putStringArrayListExtra(AlbumActivity.EXTRA_LIST_IMAGES, (ArrayList<String>) nListOimage);
			mActivity.startActivity(intent);
		}
	}

}
