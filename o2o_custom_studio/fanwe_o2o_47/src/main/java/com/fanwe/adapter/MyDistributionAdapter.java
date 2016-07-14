package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.TuanDetailActivity;
import com.fanwe.common.CommonInterface;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDImageUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.DistributionGoodsModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sunday.eventbus.SDEventManager;

public class MyDistributionAdapter extends SDSimpleAdapter<DistributionGoodsModel>
{

	private MyDistributionAdapterListener mListener;

	public void setmListener(MyDistributionAdapterListener mListener)
	{
		this.mListener = mListener;
	}

	public MyDistributionAdapter(List<DistributionGoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_my_distribution;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final DistributionGoodsModel model)
	{
		final ImageView siv_image = ViewHolder.get(R.id.siv_image, convertView);
		TextView tv_status = ViewHolder.get(R.id.tv_status, convertView);
		TextView tv_commission = ViewHolder.get(R.id.tv_commission, convertView);
		TextView tv_sale_count = ViewHolder.get(R.id.tv_sale_count, convertView);
		TextView tv_sale_total = ViewHolder.get(R.id.tv_sale_total, convertView);
		TextView tv_cancel = ViewHolder.get(R.id.tv_cancel, convertView);
		TextView tv_share = ViewHolder.get(R.id.tv_share, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);

		SDViewBinder.setImageView(model.getIcon(), siv_image, new ImageLoadingListener()
		{

			@Override
			public void onLoadingStarted(String imageUri, View view)
			{
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				if (model.getEnd_status() == 0)
				{
					Bitmap bmpGray = SDImageUtil.getGrayBitmap(loadedImage);
					if (bmpGray != null)
					{
						siv_image.setImageBitmap(bmpGray);
					}
				}
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{
			}
		});
		SDViewBinder.setTextView(tv_name, model.getName());
		SDViewBinder.setTextView(tv_commission, model.getSale_balanceFormat());
		SDViewBinder.setTextView(tv_sale_count, String.valueOf(model.getSale_count()));
		SDViewBinder.setTextView(tv_sale_total, model.getSale_totalFormat());
		SDViewBinder.setTextView(tv_status, model.getEnd_statusFormat());

		SDViewBinder.setTextView(tv_cancel, model.getCancelText());
		SDViewUtil.setTextViewColorResId(tv_cancel, model.getCancelTextColor());
		tv_cancel.setBackgroundResource(model.getCancelBackground());

		SDViewBinder.setTextView(tv_share, model.getShareText());
		SDViewUtil.setTextViewColorResId(tv_share, model.getShareTextColor());
		tv_share.setBackgroundResource(model.getShareBackground());

		switch (model.getEnd_status())
		{
		case 0: // 已过期
			SDViewUtil.show(tv_status);
			break;
		case 1:
			SDViewUtil.hide(tv_status);
			break;
		case 2: // 预告中
			SDViewUtil.show(tv_status);
			break;

		default:
			break;
		}

		tv_cancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				switch (model.getUd_is_effect())
				{
				case 0:
					// TODO 请求重新上架接口
					clickReShelves(model);
					break;
				case 1:
					// TODO 请求取消分销接口
					clickCancelDistribution(model);
					break;

				default:
					break;
				}
			}
		});

		tv_share.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				switch (model.getUd_is_effect())
				{
				case 0:
					// TODO 删除
					clickDeleteDistribution(model);
					break;
				case 1:
					// TODO 分享
					UmengSocialManager.openShare(model.getSub_name(), model.getName() + model.getShare_url(), model.getIcon(), model.getShare_url(),
							mActivity, null);
					break;

				default:
					break;
				}

			}
		});

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int id = model.getId();
				Intent intent = new Intent(mActivity, TuanDetailActivity.class);
				intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, id);
				mActivity.startActivity(intent);
			}
		});
	}

	protected void clickDeleteDistribution(final DistributionGoodsModel model)
	{
		CommonInterface.requestDeleteDistribution(model.getId(), new SDRequestCallBack<BaseActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					removeItem(indexOf(model));
					SDEventManager.post(EnumEventTag.DELETE_DISTRIBUTION_GOODS_SUCCESS.ordinal());
					if (mListener != null)
					{
						mListener.onStateChange();
					}
				}
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
			}
		});
	}

	protected void clickCancelDistribution(final DistributionGoodsModel model)
	{
		if (model.getUd_type() == 1)
		{
			return;
		}

		CommonInterface.requestDistributionIsEffect(model.getId(), new SDRequestCallBack<BaseActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					model.toggleIsEffect();
					updateItem(indexOf(model));
					if (mListener != null)
					{
						mListener.onStateChange();
					}
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		});
	}

	protected void clickReShelves(DistributionGoodsModel model)
	{
		clickCancelDistribution(model);
	}

	public interface MyDistributionAdapterListener
	{
		public void onStateChange();
	}

}
