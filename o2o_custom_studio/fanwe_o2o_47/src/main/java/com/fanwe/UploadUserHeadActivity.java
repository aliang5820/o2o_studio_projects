package com.fanwe;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView.ScaleType;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.ImageFileCompresser.ImageFileCompresserListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_account_upload_avatarActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

public class UploadUserHeadActivity extends BaseActivity
{

	public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

	@ViewInject(R.id.iv_image)
	private PhotoView mIv_image;

	private String mStrUrl;
	private File mFileOriginal;
	private ImageFileCompresser mCompresser;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_upload_user_head);
		init();
	}

	private void init()
	{
		initTitle();
		initImageView();
		initImageFileCompresser();
		getIntentData();
	}

	private void initImageFileCompresser()
	{
		mCompresser = new ImageFileCompresser();
		mCompresser.setmListener(new ImageFileCompresserListener()
		{

			@Override
			public void onSuccess(File fileCompressed)
			{
				requestUpload(fileCompressed);
			}

			@Override
			public void onFailure(String msg)
			{
				if (!TextUtils.isEmpty(msg))
				{
					SDToast.showToast(msg);
				}
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
		});
	}

	protected void requestUpload(File fileCompressed)
	{
		if (fileCompressed == null)
		{
			return;
		}

		if (!fileCompressed.exists())
		{
			return;
		}

		RequestModel model = new RequestModel();
		model.putUser();
		model.putCtl("uc_account");
		model.putAct("upload_avatar");
		model.putFile("file", fileCompressed);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_account_upload_avatarActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在上传");
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					SDEventManager.post(EnumEventTag.UPLOAD_USER_HEAD_SUCCESS.ordinal());
					finish();
				}
			}
		});
	}

	private void initImageView()
	{
		mIv_image.setScaleType(ScaleType.FIT_CENTER);
	}

	private void getIntentData()
	{
		mStrUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
		if (isEmpty(mStrUrl))
		{
			SDToast.showToast("图片不存在");
			finish();
		}

		mFileOriginal = new File(mStrUrl);
		if (!mFileOriginal.exists())
		{
			SDToast.showToast("图片不存在");
			finish();
		}

		SDViewBinder.setImageView(mIv_image, "file://" + mStrUrl);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("上传头像");

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("上传");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		clickUpload();
	}

	private void clickUpload()
	{
		mCompresser.compressImageFile(mFileOriginal);
	}

	@Override
	protected void onDestroy()
	{
		mCompresser.deleteCompressedImageFile();
		super.onDestroy();
	}

}
