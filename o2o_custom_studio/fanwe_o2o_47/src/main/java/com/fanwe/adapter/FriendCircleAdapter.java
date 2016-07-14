package com.fanwe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.AlbumActivity;
import com.fanwe.DynamicDetailActivity;
import com.fanwe.EventDetailActivity;
import com.fanwe.FriendCircleActivity;
import com.fanwe.StoreDetailActivity;
import com.fanwe.TuanDetailActivity;
import com.fanwe.YouHuiDetailActivity;
import com.fanwe.adapter.DynamicCommentAdapter.DynamicCommentAdapter_onClick;
import com.fanwe.common.CommonInterface;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.dialog.SimpleInputDialog;
import com.fanwe.dialog.SimpleInputDialog.InputPopListener;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Do_replyDataModel;
import com.fanwe.model.DynamicModel;
import com.fanwe.model.DynamicReplyModel;
import com.fanwe.model.DynamicShare_objModel;
import com.fanwe.model.Uc_home_do_replyActModel;
import com.fanwe.model.Uc_home_showActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.pop.DynamicCommentActionPop;
import com.fanwe.pop.DynamicCommentActionPop.DynamicCommentActionPopListener;
import com.fanwe.span.SDNetImageTextView;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;

public class FriendCircleAdapter extends SDSimpleAdapter<DynamicModel> implements SDEventObserver
{

	private int mUserId;
	private DynamicModel mShowDetailDynamicModel;

	public FriendCircleAdapter(List<DynamicModel> listModel, Activity activity)
	{
		super(listModel, activity);
		SDEventManager.register(this);
	}

	public void setUserId(int userId)
	{
		this.mUserId = userId;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent)
	{
		return R.layout.item_friend_circle_normal;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final DynamicModel model)
	{
		ImageView iv_user = get(R.id.iv_user, convertView);
		TextView tv_user = get(R.id.tv_user, convertView);
		TextView tv_type_text = get(R.id.tv_type_text, convertView);
		SDNetImageTextView tv_content = get(R.id.tv_content, convertView);

		// images
		ImageView iv_0 = get(R.id.iv_0, convertView);
		ImageView iv_1 = get(R.id.iv_1, convertView);
		ImageView iv_2 = get(R.id.iv_2, convertView);
		ImageView iv_3 = get(R.id.iv_3, convertView);
		ImageView iv_4 = get(R.id.iv_4, convertView);
		ImageView iv_5 = get(R.id.iv_5, convertView);
		ImageView iv_6 = get(R.id.iv_6, convertView);
		ImageView iv_7 = get(R.id.iv_7, convertView);
		ImageView iv_8 = get(R.id.iv_8, convertView);

		LinearLayout ll_share_action = get(R.id.ll_share_action, convertView);

		SDGridLinearLayout ll_comment = get(R.id.ll_comment, convertView);
		TextView tv_more_comment = get(R.id.tv_more_comment, convertView);

		ImageView iv_goods_image = get(R.id.iv_goods_image, convertView);
		TextView tv_goods_name = get(R.id.tv_goods_name, convertView);

		TextView tv_time = get(R.id.tv_time, convertView);
		ImageView iv_comment_action = get(R.id.iv_comment_action, convertView);

		// 数据

		setImagesOnclickListener(iv_0, 0, model);
		setImagesOnclickListener(iv_1, 1, model);
		setImagesOnclickListener(iv_2, 2, model);
		setImagesOnclickListener(iv_3, 3, model);
		setImagesOnclickListener(iv_4, 4, model);
		setImagesOnclickListener(iv_5, 5, model);
		setImagesOnclickListener(iv_6, 6, model);
		setImagesOnclickListener(iv_7, 7, model);
		setImagesOnclickListener(iv_8, 8, model);

		tv_user.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onClickUser(model.getUser_id());
			}
		});

		iv_user.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onClickUser(model.getUser_id());
			}
		});

		iv_comment_action.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onClickCommentAction(model, v);
			}
		});

		SDViewBinder.setImageView(model.getUser_avatar(), iv_user);
		SDViewBinder.setTextView(tv_user, model.getUser_name());
		SDViewBinder.setTextView(tv_type_text, model.getType_txt());

		// 设置默认状态
		tv_content.setMapData(model.getMapKeyUrl());
		SDViewUtil.hide(tv_content);
		SDViewUtil.hide(ll_share_action);
		bindImages(null, iv_0, iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8);
		ll_share_action.setOnClickListener(null);

		final DynamicShare_objModel shareObj = model.getShare_obj();
		switch (model.getType_format_enum())
		{
		case SHARE:
			SDViewUtil.show(tv_content);
			tv_content.setTextContent(model.getContent());
			bindImages(model.getB_img(), iv_0, iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8);
			break;
		case SHAREDEAL:
			SDViewUtil.show(ll_share_action);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, TuanDetailActivity.class);
						intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;
		case SHAREYOUHUI:
			SDViewUtil.show(ll_share_action);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, YouHuiDetailActivity.class);
						intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;
		case SHAREEVENT:
			SDViewUtil.show(ll_share_action);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, EventDetailActivity.class);
						intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;
		case DEALCOMMENT:
			SDViewUtil.show(tv_content);
			tv_content.setTextContent(model.getContent());
			SDViewUtil.show(ll_share_action);
			bindImages(model.getB_img(), iv_0, iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, TuanDetailActivity.class);
						intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;
		case YOUHUICOMMENT:
			SDViewUtil.show(tv_content);
			tv_content.setTextContent(model.getContent());
			SDViewUtil.show(ll_share_action);
			bindImages(model.getB_img(), iv_0, iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, YouHuiDetailActivity.class);
						intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;
		case EVENTCOMMENT:
			SDViewUtil.show(tv_content);
			tv_content.setTextContent(model.getContent());
			SDViewUtil.show(ll_share_action);
			bindImages(model.getB_img(), iv_0, iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, EventDetailActivity.class);
						intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;
		case SLOCATIONCOMMENT:
			SDViewUtil.show(tv_content);
			tv_content.setTextContent(model.getContent());
			SDViewUtil.show(ll_share_action);
			bindImages(model.getB_img(), iv_0, iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, StoreDetailActivity.class);
						intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;

		case EVENTSUBMIT:
			SDViewUtil.show(tv_content);
			tv_content.setTextContent(model.getContent());
			SDViewUtil.show(ll_share_action);
			if (shareObj != null)
			{
				SDViewBinder.setImageView(shareObj.getImage(), iv_goods_image);
				SDViewBinder.setTextView(tv_goods_name, shareObj.getContent());
				ll_share_action.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, EventDetailActivity.class);
						intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, shareObj.getId());
						mActivity.startActivity(intent);
					}
				});
			}
			break;

		default:
			break;
		}
		SDViewBinder.setTextView(tv_time, model.getShow_time());

		if (model.getReply_is_move() == 1)
		{
			SDViewUtil.show(tv_more_comment);
			tv_more_comment.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					int id = model.getId();
					if (id > 0)
					{
						mShowDetailDynamicModel = model;
						Intent intent = new Intent(mActivity, DynamicDetailActivity.class);
						intent.putExtra(DynamicDetailActivity.EXTRA_ID, model.getId());
						mActivity.startActivity(intent);
					}
				}
			});
		} else
		{
			SDViewUtil.hide(tv_more_comment);
		}

		List<DynamicReplyModel> listReply = model.getReply_list();
		if (listReply == null)
		{
			SDViewUtil.hide(ll_comment);
		} else
		{
			DynamicCommentAdapter adapter = new DynamicCommentAdapter(listReply, mActivity);
			adapter.setListener(new DynamicCommentAdapter_onClick()
			{

				@Override
				public void onClickCommentUsername(DynamicReplyModel replyModel, View v)
				{
					if (mListenerOnClickComment != null)
					{
						mListenerOnClickComment.onClickCommentUsername(model, replyModel, v);
					}
				}

				@Override
				public void onClickComment(DynamicReplyModel replyModel, View v)
				{
					if (mListenerOnClickComment != null)
					{
						mListenerOnClickComment.onClickComment(model, replyModel, v);
					}
				}
			});
			ll_comment.setAdapter(adapter);
			SDViewUtil.show(ll_comment);
		}
	}

	private void bindImages(List<String> listUrl, ImageView... imgs)
	{
		if (imgs != null)
		{
			ImageView iv = null;
			String url = null;
			for (int i = 0; i < imgs.length; i++)
			{
				iv = imgs[i];
				url = SDCollectionUtil.get(listUrl, i);
				if (url != null)
				{
					SDViewUtil.show(iv);
					SDViewBinder.setImageView(url, iv);
				} else
				{
					SDViewUtil.hide(iv);
				}
			}
		}
	}

	private void setImagesOnclickListener(ImageView imageView, final int imageIndex, final DynamicModel model)
	{
		imageView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (model != null)
				{
					List<String> listImage = model.getB_img();
					Intent intent = new Intent(mActivity, AlbumActivity.class);
					intent.putExtra(AlbumActivity.EXTRA_IMAGES_INDEX, imageIndex);
					intent.putExtra(AlbumActivity.EXTRA_LIST_IMAGES, (ArrayList<String>) listImage);
					mActivity.startActivity(intent);
				}
			}
		});
	}

	// actions
	protected void onClickCommentAction(final DynamicModel model, View v)
	{
		final DynamicCommentActionPop pop = new DynamicCommentActionPop();
		pop.setmListener(new DynamicCommentActionPopListener()
		{

			@Override
			public void onClickReply(View v)
			{
				pop.dismiss();
				clickReply(model, null);
			}

			@Override
			public void onClickFav(View v)
			{
				pop.dismiss();
				clickFav(model);
			}

		});
		SDViewUtil.showPopLeft(pop, v, SDViewUtil.dp2px(20));
	}

	private void clickFav(DynamicModel model)
	{
		if (!AppRuntimeWorker.isLogin(mActivity))
		{
			return;
		}
		CommonInterface.requestDoFavTopic(model.getId(), new SDRequestCallBack<BaseActModel>()
		{

		});
	}

	private void clickReply(final DynamicModel model, final DynamicReplyModel replyModel)
	{
		if (!AppRuntimeWorker.isLogin(mActivity))
		{
			return;
		}
		if (model == null)
		{
			return;
		}
		final SimpleInputDialog dialog = new SimpleInputDialog();
		if (replyModel != null)
		{
			dialog.mEt_content.setHint("回复@" + replyModel.getUser_name());
		} else
		{
			dialog.mEt_content.setHint("评论");
		}
		dialog.setmListener(new InputPopListener()
		{

			@Override
			public void onClickSend(String content, View v)
			{
				if (TextUtils.isEmpty(content))
				{
					SDToast.showToast("内容不能为空");
					return;
				}

				int id = model.getId();
				int replyId = 0;
				if (replyModel != null)
				{
					replyId = replyModel.getId();
					content = "回复@" + replyModel.getUser_name() + ":" + content;
				}

				CommonInterface.requestDoReply(id, content, replyId, new SDRequestCallBack<Uc_home_do_replyActModel>()
				{
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo)
					{
						if (actModel.getStatus() == 1)
						{
							Do_replyDataModel data = actModel.getReply_data();
							if (data != null)
							{
								DynamicReplyModel replyModelNew = new DynamicReplyModel();
								replyModelNew.setId(data.getReply_id());
								replyModelNew.setUser_id(LocalUserModelDao.queryModel().getUser_id());
								replyModelNew.setContent(data.getContent());
								replyModelNew.setUser_name(data.getUser_name());

								List<DynamicReplyModel> listReply = model.getReply_list();
								if (listReply == null)
								{
									listReply = new ArrayList<DynamicReplyModel>();
									model.setReply_list(listReply);
								}
								listReply.add(replyModelNew);
								int index = indexOf(model);
								updateItem(index);
							}
						}
					}

					@Override
					public void onFinish()
					{
						dialog.dismiss();
					}
				});
			}
		});
		dialog.showBottom();
		int position = indexOf(model);
		if (getItemParent(position) instanceof ListView)
		{
			ListView lv = (ListView) getItemParent(position);
			lv.setSelection(position + lv.getHeaderViewsCount());
		}
	}

	private void onClickUser(int uid)
	{
		if (uid > 0)
		{
			if (uid != mUserId)
			{
				Intent intent = new Intent(mActivity, FriendCircleActivity.class);
				intent.putExtra(FriendCircleActivity.EXTRA_ID, uid);
				mActivity.startActivity(intent);
			}
		} else
		{
			SDToast.showToast("uid<=0");
		}
	}

	private FriendCircleAdapter_onClickComment mListenerOnClickComment = new FriendCircleAdapter_onClickComment()
	{

		@Override
		public void onClickCommentUsername(DynamicModel model, DynamicReplyModel replyModel, View v)
		{
			onClickUser(replyModel.getUser_id());
		}

		@Override
		public void onClickComment(DynamicModel model, DynamicReplyModel replyModel, View v)
		{
			FriendCircleAdapter.this.onClickComment(model, replyModel, v);
		}
	};

	protected void showDeleteReplyDialog(final DynamicModel model, final DynamicReplyModel replyModel, View v)
	{
		SDDialogMenu dialog = new SDDialogMenu();
		dialog.setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				if (index == 0)
				{
					CommonInterface.requestDeleteReply(replyModel.getId(), new SDRequestCallBack<BaseActModel>()
					{
						public void onSuccess(ResponseInfo<String> responseInfo)
						{
							if (actModel.getStatus() > 0)
							{
								if (model != null)
								{
									List<DynamicReplyModel> listReply = model.getReply_list();
									if (listReply != null)
									{
										listReply.remove(replyModel);
										updateItem(indexOf(model));
									}
								}
							}
						};
					});
				}
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
		dialog.setItems(new String[] { "删除" });
		dialog.showBottom();
	}

	public void onClickComment(DynamicModel model, DynamicReplyModel replyModel, View v)
	{
		if (!AppRuntimeWorker.isLogin(mActivity))
		{
			return;
		}
		int uid = replyModel.getUser_id();
		if (uid > 0)
		{
			if (uid == LocalUserModelDao.queryModel().getUser_id())
			{
				showDeleteReplyDialog(model, replyModel, v);
			} else
			{
				clickReply(model, replyModel);
			}
		}
	}

	public interface FriendCircleAdapter_onClickComment
	{
		public void onClickCommentUsername(DynamicModel model, DynamicReplyModel replyModel, View v);

		public void onClickComment(DynamicModel model, DynamicReplyModel replyModel, View v);
	}

	public void destroy()
	{
		SDEventManager.unregister(this);
	}

	@Override
	public void onEvent(SDBaseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case DYNAMIC_DETAIL_CLOSED:
			updateItem();
			break;

		default:
			break;
		}

	}

	private void updateItem()
	{
		if (mShowDetailDynamicModel != null)
		{
			CommonInterface.requestDynamicDetail(mShowDetailDynamicModel.getId(), new SDRequestCallBack<Uc_home_showActModel>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					if (actModel.getStatus() > 0)
					{
						DynamicModel newModel = actModel.getData();
						int index = indexOf(mShowDetailDynamicModel);
						mListModel.remove(mShowDetailDynamicModel);
						mListModel.add(index, newModel);
						mShowDetailDynamicModel = null;
						updateItem(index);
					}
					super.onSuccess(responseInfo);
				}
			});
		}
	}

	@Override
	public void onEventBackgroundThread(SDBaseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onEventAsync(SDBaseEvent event)
	{
		// TODO Auto-generated method stub

	}

}
