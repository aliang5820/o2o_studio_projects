package com.fanwe.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.FlowLayout;
import com.fanwe.library.dialog.SDDialogInput;
import com.fanwe.library.dialog.SDDialogInput.SDDialogInputListener;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.listener.RefreshActListener;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.Biz_tuan_msgModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDTypeParseUtil;

/**
 * 
 * @author yhz
 * @create time 2014-8-5
 */
public class Biz_tuan_msgAdapter extends SDSimpleAdapter<Biz_tuan_msgModel>
{
	private RefreshActListener mRefreshActListener = null;
	private String mDeal_id = null;
	private int mCurrentType = 0;

	public Biz_tuan_msgAdapter(List<Biz_tuan_msgModel> listModel, Activity activity, String deal_id, int currentType,
			RefreshActListener refreshActListener)
	{
		super(listModel, activity);
		this.mDeal_id = deal_id;
		this.mRefreshActListener = refreshActListener;
		this.mCurrentType = currentType;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.frag_biz_tuan_msg_listitem;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final Biz_tuan_msgModel model)
	{
		TextView user_name = get(R.id.frag_biztuanmsglistitem_tv_user_name, convertView);
		TextView create_time_format = ViewHolder.get(R.id.frag_biztuanmsglistitem_create_time_format, convertView);
		RatingBar rb_star = ViewHolder.get(R.id.frag_biztuanmsglistitem_rb_star, convertView);
		TextView content = ViewHolder.get(R.id.frag_biztuanmsglistitem_tv_content, convertView);
		LinearLayout ll_reply = ViewHolder.get(R.id.frag_biztuanmsglistitem_ll_reply, convertView);
		ll_reply.setVisibility(View.GONE);
		TextView update_time_format = ViewHolder.get(R.id.frag_biztuanmsglistitem_tv_update_time_format, convertView);
		TextView admin_reply = ViewHolder.get(R.id.frag_biztuanmsglistitem_tv_admin_reply, convertView);
		TextView modify = ViewHolder.get(R.id.frag_biztuanmsglistitem_tv_modify, convertView);
		TextView answer = ViewHolder.get(R.id.frag_biztuanmsglistitem_tv_answer, convertView);
		FlowLayout fl_image = ViewHolder.get(R.id.fl_image, convertView);

		SDViewBinder.setTextView(user_name, "会员:" + model.getUser_name());
		SDViewBinder.setTextView(create_time_format, model.getCreate_time());
		rb_star.setRating(SDTypeParseUtil.getFloatFromString(model.getPoint(), 0));
		SDViewBinder.setTextView(content, model.getContent());
		if (!TextUtils.isEmpty(model.getReply_content()))
		{
			ll_reply.setVisibility(View.VISIBLE);
			answer.setVisibility(View.GONE);
			SDViewBinder.setTextView(update_time_format, model.getCreate_time());
			SDViewBinder.setTextView(admin_reply, model.getReply_content());
		}

		if (model.getImages() != null && model.getImages().size() > 0)
		{
			fl_image.removeAllViews();
			int size = model.getImages().size();
			for (int i = 0; i < size; i++)
			{
				ImageView imageview = new ImageView(mActivity);
				fl_image.addView(imageview);
				FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(150, 150);
				imageview.setLayoutParams(lp);
				SDViewBinder.setImageView(model.getImages().get(i), imageview);
			}
		}
		modify.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showReplyDialog(model);
			}
		});
		answer.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showReplyDialog(model);
			}
		});
	}

	private void showReplyDialog(final Biz_tuan_msgModel biz_tuan_msgModel)
	{

		final SDDialogInput dialogInput = new SDDialogInput(mActivity);
		if (TextUtils.isEmpty(biz_tuan_msgModel.getReply_content()))
		{
			dialogInput.setTextTitle("评论回复");
			dialogInput.mEtContent.setHint("您的回复会被公开展示，请注意措辞");
		} else
		{
			dialogInput.setTextTitle("评论修改");
			dialogInput.mEtContent.setText(biz_tuan_msgModel.getReply_content());
		}
		dialogInput.setmListener(new SDDialogInputListener()
		{

			@Override
			public void onDismiss(SDDialogInput dialog)
			{

			}

			@Override
			public void onClickConfirm(View v, String content, SDDialogInput dialog)
			{
				requestBiz_tuan_msg_read(biz_tuan_msgModel, dialogInput.mEtContent.getText().toString());
			}

			@Override
			public void onClickCancel(View v, SDDialogInput dialog)
			{

			}
		}).show();

	}

	private void requestBiz_tuan_msg_read(Biz_tuan_msgModel biz_tuan_msgModel, String admin_replay)
	{
		LocalUserModel localUserModel = App.getApp().getmLocalUser();
		if (localUserModel == null || TextUtils.isEmpty(mDeal_id) || TextUtils.isEmpty(admin_replay))
		{
			return;
		}
		RequestModel model = new RequestModel();
		if (mCurrentType == 0)
		{
			model.putCtlAct("biz_dealr", "do_reply_dp");
		} else if (mCurrentType == 1)
		{
			model.putCtlAct("biz_youhuir", "do_reply_dp");
		} else if (mCurrentType == 2)
		{
			model.putCtlAct("biz_eventr", "do_reply_dp");
		} else if (mCurrentType == 3)
		{
			model.putCtlAct("biz_storer", "do_reply_dp");
		}
		model.put("data_id", biz_tuan_msgModel.getId());
		model.put("reply_content", admin_replay);
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
					if (mRefreshActListener != null)
					{
						mRefreshActListener.refreshActivity();
					}
				}
			}

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("回复提交中...");
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

}
