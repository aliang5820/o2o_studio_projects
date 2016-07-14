package com.fanwe;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.FriendCircleAdapter;
import com.fanwe.common.CommonInterface;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.handler.PhotoHandler.PhotoHandlerListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DynamicModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_home_focusActModel;
import com.fanwe.model.Uc_home_indexActModel;
import com.fanwe.model.User_dataModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

public class FriendCircleActivity extends BaseActivity
{

	/** 要查看的朋友圈id (int) */
	public static final String EXTRA_ID = "extra_id";

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private ImageView mIvHomeBg;
	private ImageView mIvUserHead;
	private TextView mTvUsername;
	private Button mBtnFocus;

	private PageModel mPage = new PageModel();
	private int mUserId;

	private FriendCircleAdapter mAdapter;
	private List<DynamicModel> mListModel = new ArrayList<DynamicModel>();
	private PhotoHandler mPhotoHandler;
	private Uc_home_indexActModel mActModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_friend_circle);
		init();
	}

	private void init()
	{
		getIntentData();
		if (mUserId <= 0)
		{
			SDToast.showToast("id<=0");
			return;
		}
		initTitle();
		addHeader();
		bindDefaultData();
		initPullToRefreshListView();
		initPhotoHandler();
	}

	private void addHeader()
	{
		View view = getLayoutInflater().inflate(R.layout.view_friend_circle_header, null);
		mIvHomeBg = (ImageView) view.findViewById(R.id.iv_home_bg);
		mIvUserHead = (ImageView) view.findViewById(R.id.iv_user_head);
		mTvUsername = (TextView) view.findViewById(R.id.tv_user_name);
		mBtnFocus = (Button) view.findViewById(R.id.btn_focus);

		mIvUserHead.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (AppRuntimeWorker.isLogin(mActivity) && LocalUserModelDao.queryModel().getUser_id() == mUserId)
				{
					clickUserAvatar();
				}
			}
		});

		mBtnFocus.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (AppRuntimeWorker.isLogin(mActivity))
				{
					requestFocus();
				}
			}
		});
		changeFocusBtn();
		mPtrlv_content.getRefreshableView().addHeaderView(view);
	}

	private void initPhotoHandler()
	{
		mPhotoHandler = new PhotoHandler(this);
		mPhotoHandler.setmListener(new PhotoHandlerListener()
		{

			@Override
			public void onResultFromCamera(File file)
			{
				if (file != null && file.exists())
				{
					Intent intent = new Intent(mActivity, UploadUserHeadActivity.class);
					intent.putExtra(UploadUserHeadActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
					startActivity(intent);
				}
			}

			@Override
			public void onResultFromAlbum(File file)
			{
				if (file != null && file.exists())
				{
					Intent intent = new Intent(mActivity, UploadUserHeadActivity.class);
					intent.putExtra(UploadUserHeadActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
					startActivity(intent);
				}
			}

			@Override
			public void onFailure(String msg)
			{
				SDToast.showToast(msg);
			}
		});
	}

	protected void requestFocus()
	{
		CommonInterface.requestFocus(mUserId, new SDRequestCallBack<Uc_home_focusActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					switch (actModel.getTag())
					{
					case 1: // 已关注
						mBtnFocus.setText("取消关注");
						break;
					case 2: // 已经取消关注
						mBtnFocus.setText("关注TA");
						break;
					case 3:
						SDToast.showToast("不能关注自己");
						break;
					case 4:
						AppRuntimeWorker.isLogin(mActivity);
						break;

					default:
						break;
					}
				}
			}
		});
	}

	private void changeFocusBtn()
	{
		SDViewUtil.show(mBtnFocus);
		SDViewUtil.setViewMarginBottom(mIvHomeBg, SDViewUtil.dp2px(50));
		LocalUserModel user = LocalUserModelDao.queryModel();

		String btnFocusText = null;
		if (user == null)
		{
			btnFocusText = "关注";
		} else
		{
			if (user.getUser_id() == mUserId)
			{
				SDViewUtil.invisible(mBtnFocus);
				SDViewUtil.setViewMarginBottom(mIvHomeBg, SDViewUtil.dp2px(25));
				return;
			} else
			{
				if (mActModel != null)
				{
					int isFav = mActModel.getIs_fav();
					if (isFav == 1)
					{
						btnFocusText = "取消关注";
					} else
					{
						btnFocusText = "关注TA";
					}
				} else
				{
					SDViewUtil.invisible(mBtnFocus);
					SDViewUtil.setViewMarginBottom(mIvHomeBg, SDViewUtil.dp2px(25));
					return;
				}
			}
		}
		mBtnFocus.setText(btnFocusText);
	}

	private void bindDefaultData()
	{
		mAdapter = new FriendCircleAdapter(mListModel, mActivity);
		mAdapter.setUserId(mUserId);
		mPtrlv_content.setAdapter(mAdapter);
	}

	private void initPullToRefreshListView()
	{
		mPtrlv_content.setMode(Mode.BOTH);
		mPtrlv_content.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mPage.increment())
				{
					requestData(true);
				} else
				{
					mPtrlv_content.onRefreshComplete();
					SDToast.showToast("没有更多数据了");
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	private void getIntentData()
	{
		mUserId = getIntent().getIntExtra(EXTRA_ID, 0);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("朋友圈");

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("发表");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		Intent intent = new Intent(getApplicationContext(), AddDynamicActivity.class);
		startActivity(intent);
	}

	private void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_home");
		model.put("id", mUserId);
		model.putPage(mPage.getPage());

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_home_indexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					mPage.update(mActModel.getPage());
					bindData(isLoadMore);

				}
			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
			}
		});
	}

	protected void bindData(boolean isLoadMore)
	{
		if (mActModel == null)
		{
			return;
		}

		User_dataModel userModel = mActModel.getUser_data();
		if (userModel != null)
		{
			SDViewBinder.setImageView(userModel.getUc_home_bg(), mIvHomeBg);
			SDViewBinder.setImageView(userModel.getUser_avatar(), mIvUserHead, ImageLoaderManager.getOptionsNoCacheNoResetViewBeforeLoading());
			SDViewBinder.setTextView(mTvUsername, userModel.getUser_name());
		}
		changeFocusBtn();
		SDViewUtil.updateAdapterByList(mListModel, mActModel.getData_list(), mAdapter, isLoadMore);
	}

	/**
	 * 头像被点击
	 */
	private void clickUserAvatar()
	{
		SDDialogMenu dialog = new SDDialogMenu(this);

		String[] arrItem = new String[] { "拍照", "从手机相册选择" };
		SDSimpleTextAdapter<String> adapter = new SDSimpleTextAdapter<String>(Arrays.asList(arrItem), this);
		dialog.setAdapter(adapter);
		dialog.setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				switch (index)
				{
				case 0:
					mPhotoHandler.getPhotoFromCamera();
					break;
				case 1:
					mPhotoHandler.getPhotoFromAlbum();
					break;

				default:
					break;
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
		dialog.showBottom();
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case UPLOAD_USER_HEAD_SUCCESS:
			requestData(false);
			break;
		case PUBLISH_DYNAMIC_SUCCESS:
			requestData(false);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mPhotoHandler.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy()
	{
		if (mPhotoHandler != null)
		{
			mPhotoHandler.deleteTakePhotoFiles();
		}
		if (mAdapter != null)
		{
			mAdapter.destroy();
		}
		super.onDestroy();
	}

}
