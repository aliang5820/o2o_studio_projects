package com.fanwe.library.handler;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDImageUtil;
import com.fanwe.library.utils.SDIntentUtil;

public class PhotoHandler extends OnActivityResultHandler
{
	public static final String TAKE_PHOTO_FILE_DIR_NAME = "take_photo";
	public static final int REQUEST_CODE_GET_PHOTO_FROM_CAMERA = 16542;
	public static final int REQUEST_CODE_GET_PHOTO_FROM_ALBUM = REQUEST_CODE_GET_PHOTO_FROM_CAMERA + 1;

	private PhotoHandlerListener mListener;
	private File mTakePhotoFile;
	private File mTakePhotoDir;

	public void setmListener(PhotoHandlerListener mListener)
	{
		this.mListener = mListener;
	}

	public PhotoHandler(Fragment mFragment)
	{
		super(mFragment);
		init();
	}

	public PhotoHandler(FragmentActivity mActivity)
	{
		super(mActivity);
		init();
	}

	private void init()
	{
		File saveFileDir = SDLibrary.getInstance().getApplication().getExternalCacheDir();
		if (saveFileDir != null)
		{
			mTakePhotoDir = new File(saveFileDir, TAKE_PHOTO_FILE_DIR_NAME);
			mTakePhotoDir.mkdirs();
		}
	}

	public void getPhotoFromAlbum()
	{
		Intent intent = SDIntentUtil.getIntentSelectLocalImage();
		startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_ALBUM);
	}

	public void getPhotoFromCamera()
	{
		if (mTakePhotoDir == null)
		{
			if (mListener != null)
			{
				mListener.onFailure("获取SD卡缓存目录失败");
			}
		} else
		{
			File takePhotoFile = createTakePhotoFile();
			getPhotoFromCamera(takePhotoFile);
		}
	}

	private File createTakePhotoFile()
	{
		long current = System.currentTimeMillis();
		File takePhotoFile = new File(mTakePhotoDir, current + ".jpg");
		try
		{
			while (takePhotoFile.exists())
			{
				current++;
				takePhotoFile = new File(mTakePhotoDir, current + ".jpg");
			}
		} catch (Exception e)
		{
			if (mListener != null)
			{
				mListener.onFailure("创建照片文件失败:" + e.toString());
			}
		}
		return takePhotoFile;
	}

	public void getPhotoFromCamera(File saveFile)
	{
		mTakePhotoFile = saveFile;
		Intent intent = SDIntentUtil.getIntentTakePhoto(saveFile);
		startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_CAMERA);
	}

	public void deleteTakePhotoFiles()
	{
		if (mTakePhotoDir != null)
		{
			SDFileUtil.deleteFile(mTakePhotoDir);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case REQUEST_CODE_GET_PHOTO_FROM_CAMERA:
			if (resultCode == Activity.RESULT_OK)
			{
				if (mListener != null)
				{
					mListener.onResultFromCamera(mTakePhotoFile);
				}
			}
			break;
		case REQUEST_CODE_GET_PHOTO_FROM_ALBUM:
			if (resultCode == Activity.RESULT_OK)
			{
				String path = SDImageUtil.getImageFilePathFromIntent(data, mActivity);
				if (mListener != null)
				{
					if (TextUtils.isEmpty(path))
					{
						mListener.onFailure("从相册获取图片失败");
					} else
					{
						mListener.onResultFromAlbum(new File(path));
					}
				}
			}
			break;

		default:
			break;
		}
	}

	public interface PhotoHandlerListener
	{
		public void onResultFromAlbum(File file);

		public void onResultFromCamera(File file);

		public void onFailure(String msg);
	}

}
