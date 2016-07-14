package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sunday.eventbus.SDEventManager;

public class DistributionMarketAdapter extends SDSimpleAdapter<DistributionGoodsModel>
{
	public DistributionMarketAdapter(List<DistributionGoodsModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_distribution_market;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final DistributionGoodsModel model)
	{
		final ImageView siv_image = ViewHolder.get(R.id.siv_image, convertView);
		TextView tv_status = ViewHolder.get(R.id.tv_status, convertView);
		TextView tv_commission = ViewHolder.get(R.id.tv_commission, convertView);
		TextView tv_price = ViewHolder.get(R.id.tv_price, convertView);
		TextView tv_add_distribution = ViewHolder.get(R.id.tv_add_distribution, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);

		tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 设置中划线效果

		if (model != null)
		{
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
			SDViewBinder.setTextView(tv_commission, model.getFx_salary_moneyFormat());
			SDViewBinder.setTextView(tv_price, model.getCurrent_priceFormat());
			SDViewBinder.setTextView(tv_status, model.getEnd_statusFormat());

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

			tv_add_distribution.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO 我要分销
					clickAddDistribution(model);
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
	}

	/**
	 * 添加分销商品
	 * 
	 * @param model
	 */
	protected void clickAddDistribution(DistributionGoodsModel model)
	{
		CommonInterface.requestAddDistribution(model.getId(), new SDRequestCallBack<BaseActModel>()
		{

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					// 添加分销商品成功
					SDEventManager.post(EnumEventTag.ADD_DISTRIBUTION_GOODS_SUCCESS.ordinal());
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

}
