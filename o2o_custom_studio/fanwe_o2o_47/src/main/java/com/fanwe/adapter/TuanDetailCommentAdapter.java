package com.fanwe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.AlbumActivity;
import com.fanwe.library.adapter.SDBaseAdapter;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.CommentModel;
import com.fanwe.o2o.newo2o.R;

public class TuanDetailCommentAdapter extends SDBaseAdapter<CommentModel>
{

	private int mImageViewWidth = 150;

	public TuanDetailCommentAdapter(List<CommentModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.item_lv_tuan_detail_comment, null);
		}
		TextView tv_user_name = ViewHolder.get(convertView, R.id.tv_user_name);
		TextView tv_time = ViewHolder.get(convertView, R.id.tv_time);
		LinearLayout ll_replay = ViewHolder.get(convertView, R.id.ll_replay);
		TextView tv_reply_content = ViewHolder.get(convertView, R.id.tv_reply_content);
		TextView tv_content = ViewHolder.get(convertView, R.id.tv_content);
		RatingBar rb_star = ViewHolder.get(convertView, R.id.rb_star);
		View view_div = ViewHolder.get(convertView, R.id.view_div);

		FlowLayout flow_images = ViewHolder.get(convertView, R.id.flow_images);
		flow_images.removeAllViews();
		if (position == 0)
		{
			view_div.setVisibility(View.GONE);
		}

		CommentModel model = getItem(position);
		if (model != null)
		{
			SDViewBinder.setTextView(tv_user_name, model.getUser_name());
			SDViewBinder.setTextView(tv_time, model.getCreate_time());
			SDViewBinder.setTextView(tv_content, model.getContent());
			SDViewBinder.setTextView(tv_reply_content, model.getReply_content());
			SDViewBinder.setRatingBar(rb_star, model.getPointFloat());

			String replyContent = model.getReply_content();
			if (TextUtils.isEmpty(replyContent))
			{
				SDViewUtil.hide(ll_replay);
			} else
			{
				SDViewUtil.show(ll_replay);
			}

			bindCommentImages(model.getImages(), model.getOimages(), flow_images);
		}

		return convertView;
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
				SDViewBinder.setImageView(iv, listImage.get(i));
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
