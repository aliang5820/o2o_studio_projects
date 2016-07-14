package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Uc_couponActItemModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MyCouponDetailActivity extends BaseActivity
{
	/** CouponlistActItemModel 实体 */
	public static final String EXTRA_COUPONLISTACTITEMMODEL = "extra_couponlistactitemmodel";

	@ViewInject(R.id.ll_top)
	private LinearLayout mLlTop = null;

	@ViewInject(R.id.act_my_coupon_detail_iv_coupon_image)
	private ImageView mIvCouponImage = null;

	@ViewInject(R.id.act_my_coupon_detail_tv_coupon_brief)
	private TextView mTvCouponBrief = null;

	@ViewInject(R.id.act_my_coupon_detail_tv_coupon_limit_time)
	private TextView mTvCouponLimitTime = null;

	@ViewInject(R.id.act_my_coupon_detail_tv_coupon_code)
	private TextView mTvCouponCode = null;

	@ViewInject(R.id.act_my_coupon_detail_iv_coupon_code_image)
	private ImageView mIvCouponCodeImage = null;

	@ViewInject(R.id.act_my_coupon_detail_tv_merchant_name)
	private TextView mTvMerchantName = null;

	@ViewInject(R.id.act_my_coupon_detail_tv_merchant_mobile)
	private TextView mTvMerchantMobile = null;

	@ViewInject(R.id.act_my_coupon_detail_tv_merchant_address)
	private TextView mTvMerchantAddress = null;

	private Uc_couponActItemModel mModel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_my_coupon_detail);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		bindData();
		registerClick();
	}

	private void registerClick()
	{
		mLlTop.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mModel != null)
				{
					Intent intent = new Intent(mActivity, TuanDetailActivity.class);
					intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, mModel.getDeal_id());
					startActivity(intent);
				}
			}
		});
	}

	private void getIntentData()
	{
		Intent intent = getIntent();
		mModel = (Uc_couponActItemModel) intent.getSerializableExtra(EXTRA_COUPONLISTACTITEMMODEL);
		if (mModel == null)
		{
			SDToast.showToast("实体为空");
			finish();
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop(SDResourcesUtil.getString(R.string.tuan_gou_coupon_detail));
	}

	private void bindData()
	{
		SDViewBinder.setImageView(mModel.getDealIcon(), mIvCouponImage);
		SDViewBinder.setImageView(mModel.getQrcode(), mIvCouponCodeImage);

		SDViewBinder.setTextView(mTvCouponBrief, mModel.getName());

		SDViewBinder.setTextView(mTvCouponLimitTime, mModel.getEnd_time());
		SDViewBinder.setTextView(mTvCouponCode, mModel.getPassword());

		SDViewBinder.setTextView(mTvMerchantName, mModel.getSpName());
		SDViewBinder.setTextView(mTvMerchantMobile, mModel.getSpTel());
		SDViewBinder.setTextView(mTvMerchantAddress, mModel.getSpAddress());
	}

}