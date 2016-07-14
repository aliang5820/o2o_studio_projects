package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fanwe.AlbumActivity;
import com.fanwe.LoginActivity;
import com.fanwe.ShopCartActivity;
import com.fanwe.common.CommonInterface;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDPagerAdapter.SDBasePagerAdapterOnItemClickListener;
import com.fanwe.library.adapter.SDSimpleAdvsAdapter;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.customview.SDSlidingPlayView.SDSlidingPlayViewOnPageChangeListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Deal_attrModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

public class TuanDetailImagePriceFragment extends TuanDetailBaseFragment
{
	@ViewInject(R.id.frag_tuan_detail_first_spv_image)
	private SDSlidingPlayView mSpvImage = null;

	@ViewInject(R.id.frag_tuan_detail_first_tv_current_price)
	private TextView mTvCurrentPrice = null;

	@ViewInject(R.id.frag_tuan_detail_first_tv_original_price)
	private TextView mTvOriginalPrice = null;

	@ViewInject(R.id.frag_tuan_detail_first_btn_buy_goods)
	private Button mBtnBuyGoods = null;

	private SDSimpleAdvsAdapter<String> mAdapter;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_tuan_detail_image_price);
	}

	@Override
	protected void init()
	{
		mTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		initSlidingPlayView();
		registeClick();
		bindDataByGoodsModel();
	}

	private void bindDataByGoodsModel()
	{
		if (!toggleFragmentView(mDealModel))
		{
			return;
		}

		bindGoodsImage(mDealModel.getImages());

		String curPriceFormat = mDealModel.getCurrent_priceFormat();
		String oriPriceFormat = mDealModel.getOrigin_priceFormat();

		switch (mDealModel.getBuy_type())
		{
		case 1:
			SDViewBinder.setTextView(mTvOriginalPrice, null);
			SDViewBinder.setTextView(mTvCurrentPrice, mDealModel.getReturn_score_show() + "积分");
			mBtnBuyGoods.setText("立即兑换");
			break;
		default:
			SDViewBinder.setTextView(mTvCurrentPrice, curPriceFormat, "未找到");
			SDViewBinder.setTextView(mTvOriginalPrice, oriPriceFormat, "未找到");
			mBtnBuyGoods.setText("立即购买");
			break;
		}
	}

	/**
	 * 绑定轮播图片
	 * 
	 * @param listUrl
	 */
	private void bindGoodsImage(List<String> listUrl)
	{
		if (SDCollectionUtil.isEmpty(listUrl))
		{
			String icon = mDealModel.getIcon();
			if (!TextUtils.isEmpty(icon))
			{
				listUrl = new ArrayList<String>();
				listUrl.add(mDealModel.getIcon());
			}
		}

		mAdapter = new SDSimpleAdvsAdapter<String>(listUrl, getActivity());
		mAdapter.setmView(mSpvImage);
		mAdapter.setmListenerOnItemClick(new SDBasePagerAdapterOnItemClickListener()
		{
			@Override
			public void onItemClick(View v, int position)
			{
				if (mDealModel != null)
				{
					List<String> listOimage = mDealModel.getOimages();
					if (!SDCollectionUtil.isEmpty(listOimage))
					{
						Intent intent = new Intent(getActivity(), AlbumActivity.class);
						intent.putExtra(AlbumActivity.EXTRA_IMAGES_INDEX, position);
						intent.putStringArrayListExtra(AlbumActivity.EXTRA_LIST_IMAGES, (ArrayList<String>) listOimage);
						startActivity(intent);
					}
				}
			}
		});
		mSpvImage.setAdapter(mAdapter);
	}

	@Override
	public void onResume()
	{
		if (mSpvImage != null)
		{
			mSpvImage.startPlay();
		}
		super.onResume();
	}

	@Override
	public void onPause()
	{
		if (mSpvImage != null)
		{
			mSpvImage.stopPlay();
		}
		super.onPause();
	}

	private void initSlidingPlayView()
	{
		mSpvImage.setmImageNormalResId(R.drawable.ic_main_dot2_normal);
		mSpvImage.setmImageSelectedResId(R.drawable.ic_main_dot2_foused);

		mSpvImage.setmListenerOnPageChange(new SDSlidingPlayViewOnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{
			}
		});
	}

	private void registeClick()
	{
		mBtnBuyGoods.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.frag_tuan_detail_first_btn_buy_goods:
			clickBuyGoods();
			break;

		default:
			break;
		}
	}

	private void clickBuyGoods()
	{

		// TODO 添加购物车
		requestAddCart();

	}

	/**
	 * 添加商品到购物车
	 */
	private void requestAddCart()
	{
		if (mDealModel == null)
		{
			return;
		}

		Map<String, Integer> mapAttrIds = null;
		if (mDealModel.hasAttr()) // 有属性
		{
			List<Deal_attrModel> listUnSelected = mDealModel.getUnSelectedAttr();
			if (!SDCollectionUtil.isEmpty(listUnSelected)) // 有属性未被选中
			{
				SDToast.showToast("请选择" + listUnSelected.get(0).getName());
				scrollToAttr();
				return;
			} else
			{
				mapAttrIds = mDealModel.getSelectedAttrId();
			}
		}

		// TODO 请求接口
		RequestModel model = new RequestModel();
		model.putCtl("cart");
		model.putAct("addcart");
		model.putUser();
		model.put("id", mDealModel.getId());
		model.put("deal_attr", mapAttrIds);
		SDRequestCallBack<BaseActModel> handler = new SDRequestCallBack<BaseActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				SDDialogManager.dismissProgressDialog();
				Intent intent = null;
				switch (actModel.getStatus())
				{
				case -1:
					intent = new Intent(getActivity(), LoginActivity.class);
					startActivity(intent);
					break;
				case 0:

					break;
				case 1:
					CommonInterface.updateCartNumber();
					SDEventManager.post(EnumEventTag.ADD_CART_SUCCESS.ordinal());
					intent = new Intent(getActivity(), ShopCartActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onFinish()
			{

			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	@Override
	public void onDestroy()
	{
		if (mSpvImage != null)
		{
			mSpvImage.stopPlay();
		}
		super.onDestroy();
	}

}