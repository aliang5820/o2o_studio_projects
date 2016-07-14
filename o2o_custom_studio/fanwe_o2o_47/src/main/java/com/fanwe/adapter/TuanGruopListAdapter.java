package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.StoreDetailActivity;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDMoreLinearLayout;
import com.fanwe.library.customview.SDMoreLinearLayout.OnOpenCloseListener;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.GoodsGroupModel;
import com.fanwe.model.GoodsModel;
import com.fanwe.o2o.newo2o.R;

public class TuanGruopListAdapter extends SDSimpleAdapter<GoodsGroupModel>
{

	public TuanGruopListAdapter(List<GoodsGroupModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_tuan_group_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final GoodsGroupModel model)
	{
		View ll_store = get(R.id.ll_store, convertView);
		TextView tv_store_name = get(R.id.tv_store_name, convertView);
		ImageView iv_tag_tuan = get(R.id.iv_tag_tuan, convertView);
		ImageView iv_tag_quan = get(R.id.iv_tag_quan, convertView);
		TextView tv_store_type = get(R.id.tv_store_type, convertView);
		RatingBar rb_point = get(R.id.rb_point, convertView);
		TextView tv_point = get(R.id.tv_point, convertView);
		TextView tv_comment_count = get(R.id.tv_comment_count, convertView);
		TextView tv_location = get(R.id.tv_location, convertView);
		TextView tv_distance = get(R.id.tv_distance, convertView);
		SDMoreLinearLayout ll_goods = get(R.id.ll_goods, convertView);

		SDViewBinder.setTextView(tv_store_name, model.getName());
		SDViewBinder.setTextView(tv_store_type, null);
		SDViewBinder.setTextView(tv_comment_count, null);
		SDViewBinder.setTextView(tv_location, null);
		rb_point.setRating(model.getAvg_point());
		SDViewBinder.setTextView(tv_point, model.getAvg_pointFormat());
		SDViewBinder.setTextView(tv_distance, model.getDistanceFormat());
		if (model.getIs_youhui() == 1)
		{
			SDViewUtil.show(iv_tag_quan);
		} else
		{
			SDViewUtil.hide(iv_tag_quan);
		}

		ll_store.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(mActivity, StoreDetailActivity.class);
				intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, model.getId());
				mActivity.startActivity(intent);
			}
		});

		final List<GoodsModel> listGood = model.getDeal_data();
		if (listGood == null)
		{
			ll_goods.removeAllViews();
		} else
		{
			ll_goods.setmMaxShowCount(2);
			ll_goods.setmViewMoreLayoutId(R.layout.view_more_switch);
			ll_goods.setmIsOpen(model.isOpen());
			ll_goods.setmListenerOnOpenClose(new OnOpenCloseListener()
			{

				@Override
				public void onOpen(List<View> listView, View viewMore)
				{
					model.setOpen(true);
					TextView tvMore = find(R.id.tv_more, viewMore);
					tvMore.setText(R.string.click_close);
				}

				@Override
				public void onClose(List<View> listView, final View viewMore)
				{
					model.setOpen(false);
					int leftCount = listGood.size() - 2;
					if (leftCount > 0)
					{
						TextView tvMore = find(R.id.tv_more, viewMore);
						tvMore.setText("查看其他" + (leftCount) + "个团购");
					}
				}
			});

			TuanListAdapter adapter = new TuanListAdapter(listGood, mActivity);
			ll_goods.setAdapter(adapter);
		}
	}

}
