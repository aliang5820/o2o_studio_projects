package com.fanwe;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.AddCommentFragment;
import com.fanwe.fragment.AddCommentFragment.AddCommentFragmentListener;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.sunday.eventbus.SDEventManager;

/**
 * 添加评论
 * 
 * @author js02
 * 
 */
public class AddCommentActivity extends BaseActivity
{

	/** id (int) */
	public static final String EXTRA_ID = "extra_id";

	/** type 评论类型,传进来的值要引用Constant.CommentType的属性 (String) */
	public static final String EXTRA_TYPE = "extra_type";

	/** name */
	public static final String EXTRA_NAME = "extra_name";

	private AddCommentFragment mFragAddComment;

	private int mId;
	private String mStrType;
	private String mStrName;

	private List<File> mListFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_add_comment);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		addFragments();
	}

	private void addFragments()
	{
		mFragAddComment = new AddCommentFragment();
		mFragAddComment.setName(mStrName);
		mFragAddComment.setmListener(new AddCommentFragmentListener()
		{
			@Override
			public void onCompressFinish(List<File> listFile)
			{
				mListFile = listFile;
				requestComments();
			}
		});
		getSDFragmentManager().replace(R.id.act_add_comment_fl_content, mFragAddComment);
	}

	private void getIntentData()
	{
		mId = getIntent().getIntExtra(EXTRA_ID, 0);
		mStrType = getIntent().getStringExtra(EXTRA_TYPE);
		mStrName = getIntent().getStringExtra(EXTRA_NAME);

		if (mId <= 0)
		{
			SDToast.showToast("id为空");
			finish();
		}
		if (TextUtils.isEmpty(mStrType))
		{
			SDToast.showToast("评论类型为空");
			finish();
		}
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("评论");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("发布");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		// TODO 发布评论
		if (SDCollectionUtil.isEmpty(mFragAddComment.getSelectedImages()))
		{
			requestComments();
		} else
		{
			if (validateParam())
			{
				mFragAddComment.compressSelectedImages();
			}
		}
	}

	private boolean validateParam()
	{
		if (TextUtils.isEmpty(mFragAddComment.getCommentContent()))
		{
			SDToast.showToast("评论内容不能为空");
			return false;
		}

		return true;
	}

	protected void requestComments()
	{
		if (!validateParam())
		{
			return;
		}
		if (AppRuntimeWorker.isLogin(mActivity))
		{
			RequestModel model = new RequestModel();
			model.putCtl("dp");
			model.putAct("add_dp");
			model.put("content", mFragAddComment.getCommentContent());
			model.put("point", mFragAddComment.getStarPoint());
			model.put("data_id", mId);
			model.put("type", mStrType);
			model.putUser();

			// 图片上传
			if (mListFile != null && mListFile.size() > 0)
			{
				for (File image : mListFile)
				{
					model.putMultiFile("file[]", image);
				}
			}

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
						SDEventManager.post(EnumEventTag.COMMENT_SUCCESS.ordinal());
						Intent intent = new Intent(mActivity, CommentListActivity.class);
						intent.putExtra(CommentListActivity.EXTRA_ID, mId);
						intent.putExtra(CommentListActivity.EXTRA_TYPE, mStrType);
						startActivity(intent);
						finish();
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
	}

}