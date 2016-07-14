package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fanwe.adapter.DeliverGoodsDoiAdapter;
import com.fanwe.adapter.DeliverGoodsExpressSpinnerAdapter;
import com.fanwe.adapter.DeliverGoodsLocationSpinnerAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.BizGoodSoCtlDeliveryFormActModel;
import com.fanwe.model.DoiModel;
import com.fanwe.model.ExpressModel;
import com.fanwe.model.LocationModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.fanwe.utils.SDUIUtil;

/**
 * 
 * 商品发货
 */
public class DeliverGoodsActivity extends TitleBaseActivity implements OnClickListener
{
	private TextView mTvConsignee;
	private TextView mTvMobile;
	private TextView mTvAddress;
	private Spinner mSpinnerLocationList;
	private TextView mTvExpressName;
	private Spinner mSpinnerExpressList;
	private EditText mEtNo;
	private EditText mEtRemark;
	private Button mBtnSubmit;
	private ListView mListView;
	private ScrollView mScrollView;

	private DeliverGoodsDoiAdapter mDeliverGoodsDoiAdapter;
	private DeliverGoodsLocationSpinnerAdapter mDeliverGoodsLocationSpinnerAdapter;
	private DeliverGoodsExpressSpinnerAdapter mDeliverGoodsExpressSpinnerAdapter;

	private int mData_id;

	private int mRel_deal_id;
	private ArrayList<String> mDoi_ids = new ArrayList<String>();
	private String mDelivery_sn;
	private String mMemo;
	private int mExpress_id;
	private int mLocation_id;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_deliver_goods);
		init();
	}

	private void init()
	{
		register();
		initIntent();
		initTitle();
		requestInterface();
	}

	private void register()
	{
		mTvConsignee = (TextView) findViewById(R.id.tv_consignee);
		mTvMobile = (TextView) findViewById(R.id.tv_mobile);
		mTvAddress = (TextView) findViewById(R.id.tv_address);
		mSpinnerLocationList = (Spinner) findViewById(R.id.spinner_location_list);
		mTvExpressName = (TextView) findViewById(R.id.tv_express_name);
		mSpinnerExpressList = (Spinner) findViewById(R.id.spinner_express_list);
		mEtNo = (EditText) findViewById(R.id.et_no);
		mEtRemark = (EditText) findViewById(R.id.et_remark);
		mBtnSubmit = (Button) findViewById(R.id.btn_submit);
		mBtnSubmit.setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.listview);
		mScrollView = (ScrollView) findViewById(R.id.scrollview);
	}

	private void initIntent()
	{
		mData_id = getIntent().getExtras().getInt(ExtraConstant.EXTRA_ID, 0);
	}

	private void initTitle()
	{
		mTitle.setText("商品发货");
	}

	private void requestInterface()
	{
		if (mData_id <= 0)
		{
			return;
		}

		RequestModel model = new RequestModel();
		model.putCtlAct("biz_goodso", "delivery_form");
		model.put("data_id", mData_id);

		SDRequestCallBack<BizGoodSoCtlDeliveryFormActModel> handler = new SDRequestCallBack<BizGoodSoCtlDeliveryFormActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("请稍等...");
			}

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
			}

			@Override
			public void onSuccess(BizGoodSoCtlDeliveryFormActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, null))
				{
					switch (actModel.getStatus())
					{
					case 0:
						SDToast.showToast(actModel.getInfo());
						break;
					case 1:
						addInfo(actModel);
						break;
					}
				}
			}

		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	@SuppressLint("NewApi")
	private void addInfo(BizGoodSoCtlDeliveryFormActModel actModel)
	{
		mRel_deal_id = actModel.getRel_deal_id();

		List<DoiModel> listDoi = actModel.getDoi_list();
		if (listDoi != null && listDoi.size() > 0)
		{
			mDeliverGoodsDoiAdapter = new DeliverGoodsDoiAdapter(actModel.getDoi_list(), this);
			mListView.setAdapter(mDeliverGoodsDoiAdapter);
			SDUIUtil.setListViewHeightBasedOnChildren(mListView);
		}

		final List<LocationModel> listLocation = actModel.getLocation_list();
		if (listLocation != null && listLocation.size() > 0)
		{
			mDeliverGoodsLocationSpinnerAdapter = new DeliverGoodsLocationSpinnerAdapter(actModel.getLocation_list(), this);
			mSpinnerLocationList.setDropDownVerticalOffset(SDUIUtil.dp2px(this, 1));
			mSpinnerLocationList.setAdapter(mDeliverGoodsLocationSpinnerAdapter);
			mSpinnerLocationList.setOnItemSelectedListener(new OnItemSelectedListener()
			{

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
				{
					mLocation_id = listLocation.get((int) id).getId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent)
				{
				}
			});
		}
		final List<ExpressModel> listExpress = actModel.getExpress_list();
		if (listExpress != null && listExpress.size() > 0)
		{
			ExpressModel model = new ExpressModel();
			model.setId(0);
			model.setName("其他");
			listExpress.add(model);

			mDeliverGoodsExpressSpinnerAdapter = new DeliverGoodsExpressSpinnerAdapter(actModel.getExpress_list(), this);
			mSpinnerExpressList.setDropDownVerticalOffset(SDUIUtil.dp2px(this, 1));
			mSpinnerExpressList.setAdapter(mDeliverGoodsExpressSpinnerAdapter);
			mSpinnerExpressList.setOnItemSelectedListener(new OnItemSelectedListener()
			{

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
				{
					mExpress_id = listExpress.get((int) id).getId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent)
				{
				}
			});

			int tag = 0;
			for (int i = 0; i < listExpress.size(); i++)
			{
				if (listExpress.get(i).getName().equals(actModel.getExpress_name()))
				{
					tag = i;
					break;
				}
			}

			mSpinnerExpressList.setSelection(tag);

		}

		if (actModel.getAddress_data() != null)
		{
			SDViewBinder.setTextView(mTvConsignee, actModel.getAddress_data().getConsignee());
			SDViewBinder.setTextView(mTvMobile, actModel.getAddress_data().getMobile());
			SDViewBinder.setTextView(mTvAddress, actModel.getAddress_data().getAddress());
		}

		SDViewBinder.setTextView(mTvExpressName, actModel.getExpress_name());

		mScrollView.scrollTo(10, 10);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_submit:
			clickBtnSubmit();
			break;
		}
	}

	private void clickBtnSubmit()
	{
		if (verifyRequestParams())
		{
			requestDoDelivery();
		}
	}

	private boolean verifyRequestParams()
	{
		mDelivery_sn = mEtNo.getText().toString();
		mMemo = mEtRemark.getText().toString();
		if (mDeliverGoodsDoiAdapter != null)
		{
			ArrayList<DoiModel> list = mDeliverGoodsDoiAdapter.getmSelectList();
			if (list.size() <= 0)
			{
				SDToast.showToast("亲!请选择发货商品!");
				return false;
			} else
			{
				for (DoiModel nlist : list)
				{
					mDoi_ids.add(nlist.getId());
				}

			}
		} else
		{
			SDToast.showToast("亲！发货商品列表为空了啊！");
			return false;
		}
		if (TextUtils.isEmpty(mDelivery_sn))
		{
			SDToast.showToast("亲!请填写发货单号");
			return false;
		}

		return true;
	}

	private void requestDoDelivery()
	{
		RequestModel model = new RequestModel();
		model.putCtlAct("biz_goodso", "do_delivery");
		model.put("rel_deal_id", mRel_deal_id);
		model.put("doi_ids", mDoi_ids);
		model.put("delivery_sn", mDelivery_sn);
		model.put("memo", mMemo);
		model.put("express_id", mExpress_id);
		model.put("location_id", mLocation_id);

		SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("请稍等...");
			}

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
			}

			@Override
			public void onSuccess(BaseCtlActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, null))
				{
					SDToast.showToast(actModel.getInfo());
					switch (actModel.getStatus())
					{
					case 0:
						break;
					case 1:
						setResult(Activity.RESULT_OK);
						finish();
						break;
					}
				}
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);

	}

}
