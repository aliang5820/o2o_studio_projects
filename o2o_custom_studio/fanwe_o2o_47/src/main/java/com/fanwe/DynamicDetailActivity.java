package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanwe.adapter.DynamicCommentDetailAdapter;
import com.fanwe.adapter.DynamicCommentDetailAdapter.DynamicCommentDetailAdapter_onClick;
import com.fanwe.common.CommonInterface;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.dialog.SimpleInputDialog;
import com.fanwe.dialog.SimpleInputDialog.InputPopListener;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDBaseAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.customview.SDGridLinearLayout.StrokeCreater;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.Do_replyDataModel;
import com.fanwe.model.DynamicModel;
import com.fanwe.model.DynamicReplyModel;
import com.fanwe.model.DynamicShare_objModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_home_do_replyActModel;
import com.fanwe.model.Uc_home_load_move_replyActModel;
import com.fanwe.model.Uc_home_showActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.pop.DynamicCommentActionPop;
import com.fanwe.pop.DynamicCommentActionPop.DynamicCommentActionPopListener;
import com.fanwe.span.SDNetImageTextView;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

/**
 * 动态详情
 * 
 * @author Administrator
 * 
 */
public class DynamicDetailActivity extends BaseActivity
{
	/** 要查看的动态id (int) */
	public static final String EXTRA_ID = "EXTRA_ID";

	@ViewInject(R.id.ptrsv_content)
	private PullToRefreshScrollView mPtrsv_content;

	@ViewInject(R.id.lv_content)
	private ListView mLv_content;

	@ViewInject(R.id.iv_user)
	private ImageView mIv_user;

	@ViewInject(R.id.tv_user)
	private TextView mTv_user;

	@ViewInject(R.id.tv_type_text)
	private TextView mTv_type_text;

	@ViewInject(R.id.tv_content)
	private SDNetImageTextView mTv_content;

	// share deal
	@ViewInject(R.id.ll_share_goods_content)
	private LinearLayout mLl_share_goods_content;

	@ViewInject(R.id.iv_goods_image)
	private ImageView mIv_goods_image;

	@ViewInject(R.id.tv_goods_name)
	private TextView mTv_goods_name;

	@ViewInject(R.id.ll_images)
	private SDGridLinearLayout mLl_images;

	@ViewInject(R.id.tv_time)
	private TextView mTv_time;

	@ViewInject(R.id.iv_comment_action)
	private ImageView mIv_comment_action;

	private DynamicCommentDetailAdapter mAdapter;
	private List<DynamicReplyModel> mListModel = new ArrayList<DynamicReplyModel>();

	private Uc_home_showActModel mActModel;
	private PageModel mPage = new PageModel();
	private int mId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_dynamic_detail);
		init();
	}

	private void init()
	{
		getIntentData();
		if (mId <= 0)
		{
			finish();
			return;
		}
		initTitle();
		bindDefault();
		initPullToRefreshListView();
	}

	private void bindDefault()
	{
		mAdapter = new DynamicCommentDetailAdapter(mListModel, mActivity);
		mAdapter.setListener(new DynamicCommentDetailAdapter_onClick()
		{

			@Override
			public void onClickCommentUsername(DynamicReplyModel model, View v)
			{
				onClickUser(model.getUser_id());
			}

			@Override
			public void onClickComment(DynamicReplyModel model, View v)
			{
				if (mActModel != null)
				{
					DynamicModel dynamicModel = mActModel.getData();
					if (dynamicModel != null)
					{
						DynamicDetailActivity.this.onClickComment(dynamicModel, model, v);
					}
				}

			}
		});
		mLv_content.setAdapter(mAdapter);
	}

	private void getIntentData()
	{
		mId = getIntent().getIntExtra(EXTRA_ID, -1);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("详情");
	}

	private void initPullToRefreshListView()
	{
		mPtrsv_content.setMode(Mode.BOTH);
		mPtrsv_content.setOnRefreshListener(new OnRefreshListener2<ScrollView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
				mPage.resetPage();
				requestDetail();
				requestComment(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView)
			{
				if (mPage.increment())
				{
					requestComment(true);
				} else
				{
					mPtrsv_content.onRefreshComplete();
					SDToast.showToast("没有更多数据了");
				}
			}
		});
		mPtrsv_content.setRefreshing();
	}

	protected void requestComment(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_home");
		model.putAct("load_move_reply");
		model.put("id", mId);
		model.putPage(mPage.getPage());
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_home_load_move_replyActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mPage.update(actModel.getPage());
					SDViewUtil.updateAdapterByList(mListModel, actModel.getReply_data(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				mPtrsv_content.onRefreshComplete();
			}
		});
	}

	public void onClickCommentAction(final DynamicModel model, View v)
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

								mListModel.add(replyModelNew);
								mAdapter.notifyDataSetChanged();
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
	}

	public void onClickUser(int uid)
	{
		if (uid > 0)
		{
			if (uid != mId)
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

	public void showDeleteReplyDialog(final DynamicModel model, final DynamicReplyModel replyModel, View v)
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
										mAdapter.notifyDataSetChanged();
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

	public void requestDetail()
	{
		CommonInterface.requestDynamicDetail(mId, new SDRequestCallBack<Uc_home_showActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					mActModel = actModel;
					bindData();
				}
			}
		});
	}

	private void bindData()
	{
		if (mActModel == null)
		{
			return;
		}

		final DynamicModel model = mActModel.getData();
		if (model == null)
		{
			return;
		}

		SDViewBinder.setImageView(mIv_user, model.getUser_avatar());
		SDViewBinder.setTextView(mTv_type_text, model.getType_txt());
		SDViewBinder.setTextView(mTv_user, model.getUser_name());
		SDViewBinder.setTextView(mTv_time, model.getShow_time());

		mTv_user.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onClickUser(model.getUser_id());
			}
		});

		mIv_user.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onClickUser(model.getUser_id());
			}
		});

		mIv_comment_action.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onClickCommentAction(model, v);
			}
		});

		mLl_share_goods_content.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DynamicShare_objModel shareDeal = model.getShare_obj();
				if (shareDeal != null)
				{
					Intent intent = new Intent(mActivity, TuanDetailActivity.class);
					intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, shareDeal.getId());
					mActivity.startActivity(intent);
				}
			}
		});

		DynamicShare_objModel shareDeal = null;
		switch (model.getType_format_enum())
		{
		case SHARE:
			SDViewUtil.hide(mLl_share_goods_content);
			SDViewUtil.show(mTv_content);
			mTv_content.setMapData(model.getMapKeyUrl());
			mTv_content.setTextContent(model.getContent());
			bindImages(model.getB_img(), mLl_images);
			break;
		case SHAREDEAL:
			SDViewUtil.show(mLl_share_goods_content);
			SDViewUtil.hide(mTv_content);
			bindImages(null, mLl_images);
			shareDeal = model.getShare_obj();
			if (shareDeal != null)
			{
				SDViewBinder.setImageView(mIv_goods_image, shareDeal.getImage());
				SDViewBinder.setTextView(mTv_goods_name, shareDeal.getContent());
			}
			break;
		case DEALCOMMENT:
			SDViewUtil.show(mLl_share_goods_content);
			SDViewUtil.hide(mTv_content);
			bindImages(null, mLl_images);
			shareDeal = model.getShare_obj();
			if (shareDeal != null)
			{
				SDViewBinder.setImageView(mIv_goods_image, shareDeal.getImage());
				SDViewBinder.setTextView(mTv_goods_name, model.getContent());
			}
			mLl_share_goods_content.setOnClickListener(null);
			break;

		default:
			break;
		}

	}

	private void bindImages(final List<String> listUrl, SDGridLinearLayout llImages)
	{
		final int strokeWidth = SDViewUtil.dp2px(10);
		llImages.setmColNumber(3);
		llImages.setmWidthStrokeVer(strokeWidth);
		llImages.setmSquare(true);
		llImages.setmCreaterStroke(new StrokeCreater()
		{

			@Override
			public View createVer()
			{
				View view = new View(mActivity);
				view.setLayoutParams(new LinearLayout.LayoutParams(strokeWidth, LinearLayout.LayoutParams.MATCH_PARENT));
				return view;
			}

			@Override
			public View createHor()
			{
				View view = new View(mActivity);
				view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, strokeWidth));
				return view;
			}
		});
		llImages.setAdapter(new SDBaseAdapter<String>(listUrl, mActivity)
		{
			@Override
			public View getView(final int position, View convertView, ViewGroup parent, String model)
			{
				ImageView iv = new ImageView(mActivity);
				iv.setScaleType(ScaleType.CENTER_CROP);
				iv.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(mActivity, AlbumActivity.class);
						intent.putExtra(AlbumActivity.EXTRA_IMAGES_INDEX, position);
						intent.putExtra(AlbumActivity.EXTRA_LIST_IMAGES, (ArrayList<String>) listUrl);
						startActivity(intent);
					}
				});
				SDViewBinder.setImageView(iv, model);
				return iv;
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		SDEventManager.post(EnumEventTag.DYNAMIC_DETAIL_CLOSED.ordinal());
		super.onDestroy();
	}

}
