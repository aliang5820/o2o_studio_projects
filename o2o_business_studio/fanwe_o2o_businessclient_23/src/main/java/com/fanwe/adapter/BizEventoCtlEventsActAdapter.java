package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.BizEventoCtlEventsActActivity;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.ItemBizEventoCtlEventsActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-5 上午11:07:33 类说明
 */
public class BizEventoCtlEventsActAdapter extends SDSimpleAdapter<ItemBizEventoCtlEventsActModel>
{

	public BizEventoCtlEventsActAdapter(List<ItemBizEventoCtlEventsActModel> listModel, Activity activity)
	{
		super(listModel, activity);
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_bizeventoctl_eventsact_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final ItemBizEventoCtlEventsActModel model)
	{
		TextView tv_user_name = ViewHolder.get(R.id.tv_user_name, convertView);
		TextView tv_f_create_time = ViewHolder.get(R.id.tv_f_create_time, convertView);
		TextView tv_location_name = ViewHolder.get(R.id.tv_location_name, convertView);

		TextView tv_is_verify_status = ViewHolder.get(R.id.tv_is_verify_status, convertView);
		tv_is_verify_status.setVisibility(View.GONE);

		TextView tv_is_verify_1 = ViewHolder.get(R.id.tv_is_verify_1, convertView);
		tv_is_verify_1.setVisibility(View.GONE);
		tv_is_verify_1.setOnClickListener(null);

		TextView tv_is_verify_2 = ViewHolder.get(R.id.tv_is_verify_2, convertView);
		tv_is_verify_2.setVisibility(View.GONE);
		tv_is_verify_2.setOnClickListener(null);

		SDViewBinder.setTextView(tv_user_name, model.getUser_name());
		SDViewBinder.setTextView(tv_f_create_time, model.getF_create_time());
		SDViewBinder.setTextView(tv_location_name, model.getLocation_name());

		switch (model.getIs_verify())
		{
		case 0:

			tv_is_verify_1.setVisibility(View.VISIBLE);
			tv_is_verify_2.setVisibility(View.VISIBLE);
			SDViewBinder.setTextView(tv_is_verify_1, "同意审核");
			SDViewBinder.setTextView(tv_is_verify_2, "拒绝");

			tv_is_verify_1.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (!TextUtils.isEmpty(model.getId()))
					{
						final Dialog dialog = SDDialogUtil.showView("确定通过审核?", null, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								requestBizEventoApprovalAct(model.getId());
								dialog.dismiss();

							}
						}, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.dismiss();
							}
						});
						dialog.show();

					}
				}
			});
			tv_is_verify_2.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (!TextUtils.isEmpty(model.getId()))
					{
						final Dialog dialog = SDDialogUtil.showView("确定拒绝吗?", null, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								requestBizEventoRefuseAct(model.getId());
								dialog.dismiss();

							}
						}, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.dismiss();
							}
						});
						dialog.show();
					}
				}
			});

			break;
		case 1:
			tv_is_verify_status.setVisibility(View.VISIBLE);
			SDViewBinder.setTextView(tv_is_verify_status, "通过审核");
			break;
		case 2:
			tv_is_verify_status.setVisibility(View.VISIBLE);
			SDViewBinder.setTextView(tv_is_verify_status, "已拒绝");
			break;
		}
	}

	private void requestBizEventoApprovalAct(String data_id)
	{
		RequestModel model = new RequestModel();
		model.putCtlAct("biz_evento", "approval");
		model.put("data_id", data_id);

		SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>()
		{
			private Dialog nDialog;

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
				if (!SDInterfaceUtil.dealactModel(actModel, mActivity))
				{
					SDToast.showToast(actModel.getInfo());
					switch (actModel.getStatus())
					{
					case 0:
						break;
					case 1:
						BizEventoCtlEventsActActivity activity = (BizEventoCtlEventsActActivity) mActivity;
						activity.refreshData();
						break;
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

	private void requestBizEventoRefuseAct(String data_id)
	{
		RequestModel model = new RequestModel();
		model.putCtlAct("biz_evento", "refuse");
		model.put("data_id", data_id);

		SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>()
		{
			private Dialog nDialog;

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
				if (!SDInterfaceUtil.dealactModel(actModel, mActivity))
				{
					SDToast.showToast(actModel.getInfo());
					switch (actModel.getStatus())
					{
					case 0:
						break;
					case 1:
						BizEventoCtlEventsActActivity activity = (BizEventoCtlEventsActActivity) mActivity;
						activity.refreshData();
						break;
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

}
