package com.fanwe.library.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fanwe.library.SDLibrary;

public class ImageFileCompresser
{

	public static final String COMPRESSED_IMAGE_FILE_DIR_NAME = "compressed_image";
	private long mMaxLength = 1024 * 1024;

	private File mCompressedFileDir;

	public ImageFileCompresser()
	{
		File saveFileDir = SDLibrary.getInstance().getApplication().getExternalCacheDir();
		if (saveFileDir != null)
		{
			mCompressedFileDir = new File(saveFileDir, COMPRESSED_IMAGE_FILE_DIR_NAME);
			mCompressedFileDir.mkdirs();
		}
	}

	private ImageFileCompresserListener mListener;

	public void setmListener(ImageFileCompresserListener mListener)
	{
		this.mListener = mListener;
	}

	public long getmMaxLength()
	{
		return mMaxLength;
	}

	public void setmMaxLength(long mMaxLength)
	{
		this.mMaxLength = mMaxLength;
	}

	public void compressImageFile(final List<File> listImageFile)
	{
		if (listImageFile == null)
		{
			return;
		}

		if (listImageFile.size() <= 0)
		{
			return;
		}

		if (mCompressedFileDir == null)
		{
			notifyFailure("获取SD卡缓存目录失败");
			return;
		}

		// 开线程执行
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				notifyStart();
				for (int i = 0; i < listImageFile.size(); i++)
				{
					File imageFile = listImageFile.get(i);
					if (imageFile != null && imageFile.exists())
					{
						final File fileCompressed = createCompressedImageFile();
						if (mMaxLength > 0 && imageFile.length() > mMaxLength)
						{
							SDImageUtil.compressImageFileToNewFileSize(imageFile, fileCompressed, mMaxLength);
						} else
						{
							SDFileUtil.copy(imageFile.getAbsolutePath(), fileCompressed.getAbsolutePath());
						}
						notifySuccess(fileCompressed);
					} else
					{
						notifyFailure("第" + i + "张图片不存在，已跳过");
					}
				}
				notifyFinish();
			}
		}).start();

	}

	public void compressImageFile(File imageFile)
	{
		List<File> listImageFile = new ArrayList<File>();
		listImageFile.add(imageFile);
		compressImageFile(listImageFile);
	}

	private File createCompressedImageFile()
	{
		long current = System.currentTimeMillis();
		File compressedImageFile = new File(mCompressedFileDir, current + ".jpg");
		try
		{
			while (compressedImageFile.exists())
			{
				current++;
				compressedImageFile = new File(mCompressedFileDir, current + ".jpg");
			}
		} catch (Exception e)
		{
			notifyFailure("创建照片文件失败:" + e.toString());
		}
		return compressedImageFile;
	}

	public void deleteCompressedImageFile()
	{
		if (mCompressedFileDir != null)
		{
			SDFileUtil.deleteFile(mCompressedFileDir);
		}
	}

	protected void notifyStart()
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onStart();
				}
			});
		}
	}

	protected void notifySuccess(final File file)
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onSuccess(file);
				}
			});
		}
	}

	protected void notifyFailure(final String msg)
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onFailure(msg);
				}
			});
		}
	}

	protected void notifyFinish()
	{
		if (mListener != null)
		{
			SDHandlerUtil.runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					mListener.onFinish();
				}
			});
		}
	}

	public interface ImageFileCompresserListener
	{
		public void onStart();

		public void onSuccess(File fileCompressed);

		public void onFailure(String msg);

		public void onFinish();
	}

}
