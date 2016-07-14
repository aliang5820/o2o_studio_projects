package com.fanwe.fragment;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.EventDetailActivity;
import com.fanwe.LoginActivity;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Event_fieldsModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 活动详细页（报名表单fragment）
 * 
 * @author js02
 * 
 */
public class EventDetailFieldFragment extends EventDetailBaseFragment
{
	public static final String SELECT_BTN_STRING = "请选择范围";

	@ViewInject(R.id.ll_content)
	private LinearLayout mLl_content;

	@ViewInject(R.id.tv_apply)
	private TextView mTv_apply; // 报名

	private int mId;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_event_detail_field);
	}

	@Override
	protected void init()
	{
		getIntentData();
		registerClick();
		bindData();
	}

	private void getIntentData()
	{
		Intent intent = getActivity().getIntent();
		mId = intent.getIntExtra(EventDetailActivity.EXTRA_EVENT_ID, -1);
		if (mId <= 0)
		{
			hideFragmentView();
		}
	}

	private void registerClick()
	{
		mTv_apply.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickApply();
			}
		});

	}

	private void clickApply()
	{
		if (!AppRuntimeWorker.isLogin())
		{
			startActivity(new Intent(getActivity(), LoginActivity.class));
			return;
		}

		if (!fillUserInfo())
		{
			scrollToFields();
			return;
		}
		requestSubmitField();
	}

	private boolean fillUserInfo()
	{
		List<Event_fieldsModel> listField = mInfoModel.getEvent_fields();

		if (SDCollectionUtil.isEmpty(listField))
		{
			return true;
		}

		for (Event_fieldsModel fieldModel : listField)
		{
			if (fieldModel == null)
			{
				continue;
			}
			View view = fieldModel.getView();
			if (view == null)
			{
				continue;
			}

			try
			{
				if (view instanceof Button) // 如果是button
				{
					Button btnScope = (Button) view;
					String strScope = btnScope.getText().toString();
					if (EventDetailFieldFragment.SELECT_BTN_STRING.equals(strScope))
					{
						SDToast.showToast("请选择" + fieldModel.getField_show_name());
						return false;
					} else
					{
						fieldModel.setFieldValue(strScope);
					}
				} else if (view instanceof EditText) // 如果是edittext
				{
					EditText etInfo = (EditText) view;
					String strInfo = etInfo.getText().toString();
					if (TextUtils.isEmpty(strInfo))
					{
						SDToast.showToast("请输入" + fieldModel.getField_show_name());
						return false;
					} else
					{
						fieldModel.setFieldValue(strInfo);
					}
				}
			} catch (Exception e)
			{
				return false;
			}
		}
		return true;
	}

	private void requestSubmitField()
	{
		RequestModel model = new RequestModel();
		model.putCtl("event");
		model.putAct("do_submit");
		model.put("event_id", mId);
		model.put("field_id", mInfoModel.getFieldsIds());
		model.put("result", mInfoModel.getFieldsValues());
		model.putUser();
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
				if (actModel.getStatus() == 1)
				{
					requestDetail();
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
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	private void changeViewState()
	{
		int isVerify = mInfoModel.getIs_verify();
		switch (isVerify)
		{
		case 0: // 未审核
			mTv_apply.setText("修改报名");
			mTv_apply.setEnabled(true);
			SDViewUtil.show(mLl_content);
			break;
		case 1: // 审核已通过
			mTv_apply.setText("已报名");
			mTv_apply.setEnabled(false);
			SDViewUtil.hide(mLl_content);
			break;
		case 2: // 审核未通过
			mTv_apply.setText("审核未通过");
			mTv_apply.setEnabled(false);
			SDViewUtil.hide(mLl_content);
			break;
		default:
			mTv_apply.setText("马上报名");
			mTv_apply.setEnabled(true);
			SDViewUtil.show(mLl_content);
			break;
		}

		if (mTv_apply.isEnabled())
		{
			mTv_apply.setBackgroundResource(R.drawable.selector_main_color_corner);
		} else
		{
			mTv_apply.setBackgroundResource(R.drawable.layer_gray_stroke_corner_item_single);
		}

	}

	private void bindData()
	{
		if (!toggleFragmentView(mInfoModel))
		{
			return;
		}

		changeViewState();

		List<Event_fieldsModel> listField = mInfoModel.getEvent_fields();
		if (SDCollectionUtil.isEmpty(listField))
		{
			return;
		}

		Event_fieldsModel model = null;
		for (int i = 0; i < listField.size(); i++)
		{
			model = listField.get(i);
			if (model == null)
			{
				continue;
			}

			// start
			String fieldValue = model.getResult();

			LinearLayout llItem = new LinearLayout(getActivity());
			llItem.setOrientation(LinearLayout.HORIZONTAL);
			llItem.setGravity(Gravity.CENTER_VERTICAL);

			TextView tvLabel = new TextView(getActivity());
			tvLabel.setText(model.getField_show_name() + ":　");
			tvLabel.setTextSize(15);
			tvLabel.setTextColor(Color.BLACK);
			tvLabel.setGravity(Gravity.CENTER);

			if (model.getField_type() == 0) // edittext
			{
				EditText etInfo = new EditText(getActivity());
				if (!TextUtils.isEmpty(fieldValue))
				{
					etInfo.setText(fieldValue);
				}
				model.setView(etInfo);
				etInfo.setTag(model);
				etInfo.setPadding(SDViewUtil.dp2px(5), 0, 0, 0);
				etInfo.setSingleLine(true);
				if ("手机号".equals(model.getField_show_name()))
				{
					etInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
					etInfo.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
				}
				etInfo.setTextColor(getResources().getColor(R.color.black));
				etInfo.setBackgroundResource(R.drawable.layer_white_stroke_all);
				llItem.addView(tvLabel, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				llItem.addView(etInfo, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, SDViewUtil.dp2px(40)));
			} else if (model.getField_type() == 1)// button
			{
				final Button btnScope = new Button(getActivity());
				model.setView(btnScope);
				btnScope.setTag(model);
				btnScope.setText(SELECT_BTN_STRING);
				btnScope.setTextColor(getResources().getColor(R.color.main_color));
				btnScope.setBackgroundResource(R.drawable.layer_goods_attr_selected);

				btnScope.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						final Event_fieldsModel list = (Event_fieldsModel) v.getTag();

						List<String> listValue = list.getValue_scope();
						if (SDCollectionUtil.isEmpty(listValue))
						{
							return;
						}

						final SDSimpleTextAdapter<String> adatper = new SDSimpleTextAdapter<String>(listValue, getActivity());
						SDDialogMenu dialog = new SDDialogMenu(getActivity());
						dialog.setAdapter(adatper);
						dialog.setmListener(new SDDialogMenuListener()
						{

							@Override
							public void onItemClick(View v, int index, SDDialogMenu dialog)
							{
								btnScope.setText(adatper.getItem(index));
							}

							@Override
							public void onDismiss(SDDialogMenu dialog)
							{
							}

							@Override
							public void onCancelClick(View v, SDDialogMenu dialog)
							{
							}
						});
						dialog.showBottom();
					}
				});
				llItem.addView(tvLabel, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				llItem.addView(btnScope, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}

			LinearLayout.LayoutParams paramsItem = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			paramsItem.bottomMargin = SDViewUtil.dp2px(5);
			paramsItem.topMargin = SDViewUtil.dp2px(5);
			mLl_content.addView(llItem, paramsItem);
		}
	}

	@Override
	public void onClick(View v)
	{

	}

}