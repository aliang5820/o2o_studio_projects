package com.fanwe.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.common.CommonInterface;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.CartGoodsModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

public class ShopCartAdapter extends SDAdapter<CartGoodsModel>
{

	private boolean mIsScore;

	public ShopCartAdapter(List<CartGoodsModel> listModel, Activity activity, boolean isScore)
	{
		super(listModel, activity);
		this.mIsScore = isScore;
	}

	public Map<String, Integer> getMapNumber()
	{
		Map<String, Integer> mapNumber = new HashMap<String, Integer>();
		if (mListModel != null)
		{
			for (CartGoodsModel model : mListModel)
			{
				mapNumber.put(String.valueOf(model.getId()), model.getNumber());
			}
		}
		return mapNumber;
	}

	@Override
	protected View onGetView(int position, View convertView, ViewGroup parent)
	{
		convertView = mInflater.inflate(R.layout.item_shop_cart, null);
		ImageView iv_image = ViewHolder.get(R.id.iv_image, convertView);
		TextView tv_delete = ViewHolder.get(R.id.tv_delete, convertView);
		TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
		TextView tv_add_number = ViewHolder.get(R.id.tv_add_number, convertView);
		TextView tv_minus_number = ViewHolder.get(R.id.tv_minus_number, convertView);
		final EditText et_number = ViewHolder.get(R.id.et_number, convertView);
		final TextView tv_single_price = ViewHolder.get(R.id.tv_single_price, convertView);
		final TextView tv_total_price = ViewHolder.get(R.id.tv_total_price, convertView);

		final CartGoodsModel model = getItem(position);

		SDViewBinder.setImageView(model.getIcon(), iv_image);
		SDViewBinder.setTextView(tv_name, model.getSub_name());
		et_number.setText(String.valueOf(model.getNumber()));

		setPrice(tv_single_price, tv_total_price, model);

		tv_delete.setOnClickListener(new DeleteOnClickListener(position));

		tv_add_number.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int curNumber = getNumberFromEditText(et_number);
				curNumber++;
				et_number.setText(String.valueOf(curNumber));
			}
		});

		tv_minus_number.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int curNumber = getNumberFromEditText(et_number);
				curNumber--;
				et_number.setText(String.valueOf(curNumber));
			}
		});

		et_number.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				int maxNum = model.getMax();
				int curNum = getNumberFromEditText(et_number);
				if (curNum < 1)
				{
					SDToast.showToast("数量不能小于1");
					et_number.setText(String.valueOf(1));
				} else if (curNum <= maxNum)
				{
					// 正常数量范围，不做处理
				} else
				{
					SDToast.showToast("数量不能大于" + maxNum);
					et_number.setText(String.valueOf(maxNum));
				}

				model.setNumber(getNumberFromEditText(et_number));
				setPrice(tv_single_price, tv_total_price, model);
			}
		});
		return convertView;
	}

	private void setPrice(TextView tvSinglePrice, TextView tvTotalPrice, CartGoodsModel model)
	{
		if (model != null && tvSinglePrice != null && tvTotalPrice != null)
		{
			if (mIsScore)
			{
				SDViewBinder.setTextView(tvSinglePrice, model.getReturn_scoreFormat());
				SDViewBinder.setTextView(tvTotalPrice, model.getReturn_total_scoreFormat());
			} else
			{
				SDViewBinder.setTextView(tvSinglePrice, model.getUnit_priceFormat());
				SDViewBinder.setTextView(tvTotalPrice, model.getTotal_priceFormat());
			}
		}
	}

	private int getNumberFromEditText(EditText et)
	{
		int num = 0;
		if (et != null)
		{
			num = SDTypeParseUtil.getInt(et.getText().toString(), 0);
		}
		return num;
	}

	class DeleteOnClickListener implements OnClickListener
	{
		private int nPosition;

		public DeleteOnClickListener(int position)
		{
			this.nPosition = position;
		}

		@Override
		public void onClick(View v)
		{
			// TODO 删除商品
			final CartGoodsModel model = getItem(nPosition);
			if (model != null)
			{
				RequestModel request = new RequestModel();
				request.putCtl("cart");
				request.putAct("del");
				request.putUser();
				request.put("id", model.getId());
				SDRequestCallBack<BaseActModel> handler = new SDRequestCallBack<BaseActModel>()
				{

					@Override
					public void onStart()
					{
						SDDialogManager.showProgressDialog("正在删除");
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo)
					{
						if (actModel.getStatus() == 1)
						{
							// 删除成功
							removeItem(nPosition);
							CommonInterface.updateCartNumber();
							SDEventManager.post(EnumEventTag.DELETE_CART_GOODS_SUCCESS.ordinal());
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
				};
				InterfaceServer.getInstance().requestInterface(request, handler);
			}
		}

	}

}
