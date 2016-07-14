package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.AlbumActivity;
import com.fanwe.StoreLocationActivity;
import com.fanwe.StorePayActivity;
import com.fanwe.library.customview.SDScaleImageView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.Store_imagesModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 门店详情头部信息fragment
 * 
 * @author js02
 * 
 */
public class StoreDetailInfoFragment extends StoreDetailBaseFragment
{

	@ViewInject(R.id.siv_image)
	private SDScaleImageView mSiv_image;

	@ViewInject(R.id.tv_name)
	private TextView mTv_name;

	@ViewInject(R.id.rb_star)
	private RatingBar mRb_star;

	@ViewInject(R.id.tv_star_number)
	private TextView mTv_star_number;

	@ViewInject(R.id.tv_image_count)
	private TextView mTv_image_count;

	@ViewInject(R.id.tv_address)
	private TextView mTv_address;

	@ViewInject(R.id.ll_address)
	private LinearLayout mLl_address;

	@ViewInject(R.id.ll_phone)
	private LinearLayout mLl_phone;

	@ViewInject(R.id.tv_pay)
	private TextView mTv_pay;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_store_detail_info);
	}

	@Override
	protected void init()
	{
		initViewState();
		bindData();
		registeClick();
	}

	private void initViewState()
	{
		if (!toggleFragmentView(mInfoModel))
		{
			return;
		}

		if (AppRuntimeWorker.getIs_store_pay() == 1)
		{
			if (mInfoModel.getOpen_store_payment() == 1)
			{
				SDViewUtil.show(mTv_pay);
			} else
			{
				SDViewUtil.hide(mTv_pay);
			}
		} else
		{
			SDViewUtil.hide(mTv_pay);
		}
	}

	private void bindData()
	{
		if (!toggleFragmentView(mInfoModel))
		{
			return;
		}
		SDViewBinder.setImageView(mInfoModel.getPreview(), mSiv_image);
		SDViewBinder.setTextView(mTv_name, mInfoModel.getName());
		float ratingStar = SDTypeParseUtil.getFloat(mInfoModel.getAvg_point());
		SDViewBinder.setRatingBar(mRb_star, ratingStar);
		SDViewBinder.setTextView(mTv_star_number, String.valueOf(mInfoModel.getAvg_point()));

		List<Store_imagesModel> listImages = mInfoModel.getStore_images();
		if (isEmpty(listImages))
		{
			SDViewUtil.hide(mTv_image_count);
		} else
		{
			SDViewUtil.show(mTv_image_count);
			mTv_image_count.setText(listImages.size() + "张图片");
		}

		SDViewBinder.setTextView(mTv_address, mInfoModel.getAddress());

		if (mInfoModel.getOpen_store_payment() == 1)
		{
			SDViewUtil.show(mTv_pay);
		} else
		{
			SDViewUtil.hide(mTv_pay);
		}
	}

	private void registeClick()
	{
		mLl_address.setOnClickListener(this);
		mLl_phone.setOnClickListener(this);
		mTv_pay.setOnClickListener(this);
		mTv_image_count.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mLl_address)
		{
			clickAddress();
		} else if (v == mLl_phone)
		{
			clickPhone();
		} else if (v == mTv_pay)
		{
			clickPay();
		} else if (v == mTv_image_count)
		{
			clickImage();
		}
	}

	private void clickImage()
	{
		List<Store_imagesModel> listModel = mInfoModel.getStore_images();
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			List<String> listUrl = new ArrayList<String>();
			for (Store_imagesModel model : listModel)
			{
				listUrl.add(model.getImage());
			}
			Intent intent = new Intent(getActivity(), AlbumActivity.class);
			intent.putExtra(AlbumActivity.EXTRA_IMAGES_INDEX, 0);
			intent.putExtra(AlbumActivity.EXTRA_LIST_IMAGES, (ArrayList<String>) listUrl);
			startActivity(intent);
		}
	}

	private void clickAddress()
	{
		// TODO 跳到商家地图界面
		Intent intent = new Intent(getActivity(), StoreLocationActivity.class);
		intent.putExtra(StoreLocationFragment.EXTRA_MODEL_MERCHANTITEMACTMODEL, mInfoModel);
		startActivity(intent);
	}

	private void clickPhone()
	{
		String tel = mInfoModel.getTel();
		if (!TextUtils.isEmpty(tel))
		{
			Intent intent = SDIntentUtil.getIntentCallPhone(tel);
			startActivity(intent);
		} else
		{
			SDToast.showToast("未找到号码");
		}
	}

	private void clickPay()
	{
		Intent intent = new Intent(getActivity(), StorePayActivity.class);
		intent.putExtra(StorePayActivity.EXTRA_STORE_ID, mInfoModel.getId());
		startActivity(intent);
	}

}