package com.fanwe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.Xftj_storeItemAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant.ExtraConstant;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Xftj_model;
import com.fanwe.model.Xftj_storeModel;
import com.fanwe.model.Xftj_store_itemModel;
import com.fanwe.utils.SDCollectionUtil;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;

/***
 * 
 * 优惠劵门店列表
 *
 */
public class Jygl_couponStoreActivity extends TitleBaseActivity
{

	private ListView mList;
	private TextView mTv_error;
	private TextView mTv_name;
	private TextView mTv_buy_count;
	private TextView mTv_f_end_time;

	private String mData_id;

	private List<Xftj_store_itemModel> mListModel;
	private Xftj_storeItemAdapter mAdapter;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_xftj_store);
		init();
	}

	private void init()
	{
		register();
		initTitle();
		initIntent();
		bindDefaultData();
		requestBizEventoCtlEventsAct();
	}

	private void requestBizEventoCtlEventsAct()
	{

		RequestModel model = new RequestModel();
		model.putCtlAct("biz_dealo", "locations");
		model.put("data_id", mData_id);

		SDRequestCallBack<Xftj_model> handler = new SDRequestCallBack<Xftj_model>()
		{
			private Dialog nDialog;

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}

				toggleEmptyMsg();
			}

			@Override
			public void onSuccess(Xftj_model actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, Jygl_couponStoreActivity.this))
				{
					if (actModel.getDeal_info() != null)
					{
						addEventInfo(actModel.getDeal_info());
					}

					if (actModel.getLocations() != null && actModel.getLocations().size() > 0)
					{
						mListModel.addAll(actModel.getLocations());
						mAdapter.updateData(mListModel);
					} else
					{
						SDToast.showToast("未找到数据!");
					}
				}

			}

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
			}

		};

		InterfaceServer.getInstance().requestInterface(model, handler);

	}

	protected void toggleEmptyMsg()
	{
		if (SDCollectionUtil.isListHasData(mListModel))
		{
			mTv_error.setVisibility(View.GONE);
		} else
		{
			mTv_error.setVisibility(View.VISIBLE);
		}
	}

	protected void addEventInfo(Xftj_storeModel deal_info)
	{
		SDViewBinder.setTextView(mTv_name, deal_info.getName());
		SDViewBinder.setTextView(mTv_buy_count, "总共消费:" + deal_info.getBuy_count());
		SDViewBinder.setTextView(mTv_f_end_time, "有效期:" + deal_info.getF_end_time());

	}

	private void bindDefaultData()
	{
		mListModel = new ArrayList<Xftj_store_itemModel>();
		mAdapter = new Xftj_storeItemAdapter(mListModel, this);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(Jygl_couponStoreActivity.this, Jygl_couponConsumptionActivity.class);
				intent.putExtra(ExtraConstant.EXTRA_MODEL, (Serializable) mListModel.get((int) id));
				intent.putExtra(ExtraConstant.EXTRA_ID, mData_id);
				startActivity(intent);
			}
		});

	}

	private void initIntent()
	{
		mData_id = getIntent().getExtras().getString(ExtraConstant.EXTRA_ID);

	}

	private void initTitle()
	{
		mTitle.setText("门店销量列表");

	}

	private void register()
	{
		mList = (ListView) findViewById(R.id.list);
		mTv_error = (TextView) findViewById(R.id.tv_error);
		mTv_name = (TextView) findViewById(R.id.tv_xftj_store_name);
		mTv_buy_count = (TextView) findViewById(R.id.tv_xftj_store_buy_count);
		mTv_f_end_time = (TextView) findViewById(R.id.tv_xftj_store_f_end_time);

	}
}
