package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.LoginActivity;
import com.fanwe.NearbyVipActivity;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.NearbyuserActItemModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public class NearbyVipAdapter extends SDSimpleAdapter<NearbyuserActItemModel>
{

	public NearbyVipAdapter(List<NearbyuserActItemModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_lv_nearby_vip;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, NearbyuserActItemModel model)
	{
		ImageView ivAvater = ViewHolder.get(R.id.item_nearby_vip_iv_avater, convertView);
		TextView tvUsername = ViewHolder.get(R.id.item_nearby_vip_tv_username, convertView);
		TextView tvDistance = ViewHolder.get(R.id.item_nearby_vip_tv_distance, convertView);
		TextView tvTime = ViewHolder.get(R.id.item_nearby_vip_tv_time, convertView);
		TextView tvFansFollow = ViewHolder.get(R.id.item_nearby_vip_tv_fans_follow, convertView);

		// 头像
		SDViewBinder.setImageView(model.getUser_avatar(), ivAvater);
		// 名字
		SDViewBinder.setTextView(tvUsername, model.getUser_name());
		// 距离
		SDViewBinder.setTextView(tvDistance, model.getDistance_format());
		// 时间
		SDViewBinder.setTextView(tvTime, model.getLocate_time_format());
		// 关注
		SDViewBinder.setTextView(tvFansFollow, model.getIs_follow_format());
		if (LocalUserModelDao.queryModel() == null) // 未登录
		{
			tvFansFollow.setBackgroundResource(R.drawable.ico_gz_a);
		} else
		{
			switch (model.getIs_follow())
			{
			case 0:
				tvFansFollow.setBackgroundResource(R.drawable.ico_gz_a);
				break;
			case 1:
				tvFansFollow.setBackgroundResource(R.drawable.ico_gz_c);
				break;

			default:
				tvFansFollow.setBackgroundResource(R.drawable.ico_gz_a);
				break;
			}
		}
		tvFansFollow.setOnClickListener(new NearbyVipAdapter_FollowClickListener(model, tvFansFollow));
	}

	class NearbyVipAdapter_FollowClickListener implements OnClickListener
	{

		private NearbyuserActItemModel nModel;
		private TextView tvFansFollow;

		public NearbyVipAdapter_FollowClickListener(NearbyuserActItemModel nModel, TextView tvFansFollow)
		{
			this.nModel = nModel;
			this.tvFansFollow = tvFansFollow;
		}

		@Override
		public void onClick(View v)
		{
			LocalUserModel user = LocalUserModelDao.queryModel();
			if (user == null)
			{
				mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class), NearbyVipActivity.REQUEST_CODE_LOGIN_FOR_FOLLOW_FANS);
			} else
			{
				// TODO 请求关注接口
				requestFollowFans(user, tvFansFollow);
			}
		}

		private void requestFollowFans(LocalUserModel user, final TextView tvFansFollow)
		{
			RequestModel model = new RequestModel();
			model.putCtl("followuser");
			model.put("uid", nModel.getUid());
			model.put("email", user.getUser_name());
			model.put("pwd", user.getUser_pwd());
			SDRequestCallBack<BaseActModel> handler = new SDRequestCallBack<BaseActModel>()
			{

				@Override
				public void onStart()
				{

				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					int status = actModel.getStatus();
					if (status == 1)
					{
						switch (nModel.getIs_follow())
						{
						case 0:
							nModel.setIs_follow(1);
							tvFansFollow.setText("取消关注");
							tvFansFollow.setBackgroundResource(R.drawable.ico_gz_c);
							break;
						case 1:
							nModel.setIs_follow(0);
							tvFansFollow.setText("加关注");
							tvFansFollow.setBackgroundResource(R.drawable.ico_gz_a);
							break;

						default:
							tvFansFollow.setBackgroundResource(R.drawable.ico_gz_a);
							break;
						}
						Toast.makeText(mActivity, "操作成功", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{

				}

				@Override
				public void onFinish()
				{

				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);

		}
	}

}
