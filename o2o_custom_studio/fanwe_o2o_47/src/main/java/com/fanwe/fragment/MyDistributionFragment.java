package com.fanwe.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.DistributionStoreWapActivity;
import com.fanwe.MyAccountActivity;
import com.fanwe.adapter.MyDistributionAdapter;
import com.fanwe.adapter.MyDistributionAdapter.MyDistributionAdapterListener;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dialog.MyDistributionStoreQrcodeDialog;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.handler.PhotoHandler.PhotoHandlerListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.ImageFileCompresser.ImageFileCompresserListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.DistributionGoodsModel;
import com.fanwe.model.MyDistributionUser_dataModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_fx_my_fxActModel;
import com.fanwe.model.Uc_fx_upload_bgActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.utils.JsonUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.work.AppRuntimeWorker;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

public class MyDistributionFragment extends BaseFragment
{

	private ImageView mSiv_image;
	private CircularImageView mIv_user_avatar;
	private TextView mTv_username;
	private TextView mTv_commission;
	private LinearLayout mLl_my_distribution_store;

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private Uc_fx_my_fxActModel mActModel;

	private List<DistributionGoodsModel> mListModel = new ArrayList<DistributionGoodsModel>();
	private MyDistributionAdapter mAdapter;

	private PhotoHandler mPhotoHandler;
	private ImageFileCompresser mCompresser;

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		setmTitleType(TitleType.TITLE);
		return setContentView(R.layout.frag_my_distribution);
	}

	@Override
	protected void init()
	{
		initPhotoHandler();
		initImageFileCompresser();
		initTitle();
		addTopView();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void initImageFileCompresser()
	{
		mCompresser = new ImageFileCompresser();
		mCompresser.setmListener(new ImageFileCompresserListener()
		{

			@Override
			public void onSuccess(File fileCompressed)
			{
				requestUploadFile(fileCompressed);
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在处理图片");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onFailure(String msg)
			{
				SDToast.showToast(msg);
			}
		});
	}

	protected void requestUploadFile(File file)
	{
		if (file == null)
		{
			return;
		}

		RequestModel model = new RequestModel();
		model.putCtl("uc_fx");
		model.putAct("upload_bg");
		model.putFile("file", file);
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_fx_upload_bgActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在上传");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					SDViewBinder.setImageView(mSiv_image, actModel.getFx_mall_bg());
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

		});

	}

	private void initPhotoHandler()
	{
		mPhotoHandler = new PhotoHandler(this);
		mPhotoHandler.setmListener(new PhotoHandlerListener()
		{

			@Override
			public void onResultFromCamera(File file)
			{
				mCompresser.compressImageFile(file);
			}

			@Override
			public void onResultFromAlbum(File file)
			{
				mCompresser.compressImageFile(file);
			}

			@Override
			public void onFailure(String msg)
			{
				SDToast.showToast(msg);
			}
		});
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("我的分销");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setImageLeft(R.drawable.ic_tuan_detail_share);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		if (mActModel == null)
		{
			return;
		}

		MyDistributionUser_dataModel userData = mActModel.getUser_data();
		if (userData == null)
		{
			return;
		}

		String title = userData.getUserStoreName();
		String content = title + userData.getShare_mall_url();
		String imageUrl = userData.getShare_mall_qrcode();
		String clickUrl = userData.getShare_mall_url();

		UmengSocialManager.openShare(title, content, imageUrl, clickUrl, getActivity(), null);
	}

	private void addTopView()
	{
		View viewHead = View.inflate(getActivity(), R.layout.view_my_distribution_head, null);

		mSiv_image = (ImageView) viewHead.findViewById(R.id.siv_image);
		mIv_user_avatar = (CircularImageView) viewHead.findViewById(R.id.iv_user_avatar);
		mTv_username = (TextView) viewHead.findViewById(R.id.tv_username);
		LinearLayout ll_username = (LinearLayout) viewHead.findViewById(R.id.ll_username);
		mTv_commission = (TextView) viewHead.findViewById(R.id.tv_commission);
		mLl_my_distribution_store = (LinearLayout) viewHead.findViewById(R.id.ll_my_distribution_store);

		ll_username.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (AppRuntimeWorker.isLogin())
				{
					Intent intent = new Intent(getActivity(), MyAccountActivity.class);
					startActivity(intent);
				}
			}
		});

		mLl_my_distribution_store.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showQrcodeDialog();
			}
		});
		mSiv_image.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				showSelectImageDialog();
			}
		});

		mPtrlv_content.getRefreshableView().addHeaderView(viewHead);
	}

	protected void showQrcodeDialog()
	{
		if (mActModel == null)
		{
			return;
		}

		MyDistributionStoreQrcodeDialog dialog = new MyDistributionStoreQrcodeDialog(getActivity());
		dialog.setmActModel(mActModel);
		dialog.setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				Intent intent = new Intent(getActivity(), DistributionStoreWapActivity.class);
				startActivity(intent);
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		});
		dialog.show();
	}

	protected void showSelectImageDialog()
	{
		SDDialogMenu dialog = new SDDialogMenu(getActivity());

		String[] arrItem = new String[] { "拍照", "从手机相册选择" };
		SDSimpleTextAdapter<String> adapter = new SDSimpleTextAdapter<String>(Arrays.asList(arrItem), getActivity());
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

	private void bindDefaultData()
	{
		mAdapter = new MyDistributionAdapter(mListModel, getActivity());
		mAdapter.setmListener(new MyDistributionAdapterListener()
		{

			@Override
			public void onStateChange()
			{

			}
		});
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
				pullRefresh();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				pullLoadMore();
			}
		});
		mPtrlv_content.setRefreshing();
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_fx");
		model.putAct("my_fx");
		model.putPage(mPage.getPage());

		InterfaceServer.getInstance().requestInterface(model, new RequestCallBack<String>()
		{

			@Override
			public void onStart()
			{
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Uc_fx_my_fxActModel actModel = JsonUtil.json2Object(responseInfo.result, Uc_fx_my_fxActModel.class);
				if (!SDInterfaceUtil.isActModelNull(actModel))
				{
					if (actModel.getStatus() == 1)
					{
						mActModel = actModel;
						mPage.update(actModel.getPage());
						bindData();
						SDViewUtil.updateAdapterByList(mListModel, actModel.getItem(), mAdapter, isLoadMore);
					}
				}
			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
			}
		});
	}

	protected void bindData()
	{
		if (mActModel == null)
		{
			return;
		}

		String title = mActModel.getPage_title();
		if (!isEmpty(title))
		{
			mTitle.setMiddleTextTop(title);
		}

		MyDistributionUser_dataModel userData = mActModel.getUser_data();
		if (userData == null)
		{
			return;
		}

		SDViewBinder.setImageView(mSiv_image, userData.getFx_mall_bg());
		SDViewBinder.setImageView(mIv_user_avatar, userData.getUser_avatar());
		SDViewBinder.setTextView(mTv_username, userData.getUser_name());
		SDViewBinder.setTextView(mTv_commission, userData.getFx_moneyFormat());
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case ADD_DISTRIBUTION_GOODS_SUCCESS:
			pullRefresh();
			break;
		case LOGIN_SUCCESS:
			pullRefresh();
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

	private void pullRefresh()
	{
		mPage.resetPage();
		requestData(false);
	}

	private void pullLoadMore()
	{
		if (mPage.increment())
		{
			requestData(true);
		} else
		{
			SDToast.showToast("没有更多数据了");
			mPtrlv_content.onRefreshComplete();
		}
	}

}
