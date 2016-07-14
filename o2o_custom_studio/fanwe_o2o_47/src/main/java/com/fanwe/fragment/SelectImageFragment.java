package com.fanwe.fragment;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.fanwe.LocalImageActivity;
import com.fanwe.adapter.SelectImageAdapter;
import com.fanwe.adapter.SelectImageAdapter.SelectImageAdapterListener;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.customview.SDGridLinearLayout.OnItemClickListener;
import com.fanwe.library.customview.SDGridLinearLayout.RowItemLayoutParamsCreater;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.handler.PhotoHandler.PhotoHandlerListener;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.ImageFileCompresser.ImageFileCompresserListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalImageModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 选择要上传的图片fragment
 * 
 * @author Administrator
 * 
 */
public class SelectImageFragment extends BaseFragment
{

	private static final int MAX_UPLOAD_IMAGE_COUNT = 9;
	public static final int REQUEST_CODE_GET_PHOTO_FROM_ALBUM = 1;

	@ViewInject(R.id.frag_select_image_ll_content)
	SDGridLinearLayout mLlContent;

	private PhotoHandler mPhotoHandler;
	private ImageFileCompresser mCompresser;
	private List<LocalImageModel> mListModel = new ArrayList<LocalImageModel>();
	private LocalImageModel mClickModel;
	private boolean mNeedAddImage = true;
	private SelectImageFragment_compressListener mListenerCompress;

	public void setmListenerCompress(SelectImageFragment_compressListener listenerCompress)
	{
		this.mListenerCompress = listenerCompress;
	}

	public void setmNeedAddImage(boolean mNeedAddImage)
	{
		this.mNeedAddImage = mNeedAddImage;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_select_image);
	}

	@Override
	protected void init()
	{
		initSDGridLinearLayout();
		addProcesser();
		addCompresser();
		bindUploadImageData();
	}

	private void initSDGridLinearLayout()
	{
		mLlContent.setmColNumber(3);
		mLlContent.setmCreaterRowItemLayoutParams(new RowItemLayoutParamsCreater()
		{

			@Override
			public LayoutParams create(View itemView, int index)
			{
				int width = mLlContent.getmMaxWidth() / mLlContent.getmColNumber();
				LayoutParams params = new LayoutParams(width, width);
				return params;
			}
		});
		mLlContent.setmListenerOnItemClick(new OnItemClickListener()
		{

			@Override
			public void onItemClick(int position, View view, ViewGroup parent)
			{
				showSelectDialog();
				mClickModel = (LocalImageModel) mLlContent.getAdapter().getItem(position);
			}
		});
	}

	private void addProcesser()
	{
		mPhotoHandler = new PhotoHandler(this);
		mPhotoHandler.setmListener(new PhotoHandlerListener()
		{

			@Override
			public void onResultFromCamera(File file)
			{
				String path = file.getAbsolutePath();
				if (mClickModel != null)
				{
					mClickModel.setPath(path);
				}
				bindUploadImageData();
			}

			@Override
			public void onResultFromAlbum(File file)
			{

			}

			@Override
			public void onFailure(String msg)
			{
				SDToast.showToast(msg);
			}
		});
	}

	private void addCompresser()
	{
		mCompresser = new ImageFileCompresser();
		mCompresser.setmListener(new ImageFileCompresserListener()
		{
			private List<File> nListFile = new ArrayList<File>();

			@Override
			public void onSuccess(File fileCompressed)
			{
				nListFile.add(fileCompressed);
			}

			@Override
			public void onFailure(String msg)
			{
				SDToast.showToast(msg);
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("正在处理图片");
				nListFile.clear();
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
				if (mListenerCompress != null)
				{
					mListenerCompress.onCompressFinish(nListFile);
				}
			}
		});
	}

	public void compressSelectedImages()
	{
		List<LocalImageModel> listSelectedImages = getSelectedImages();
		List<File> listFile = new ArrayList<File>();
		if (!SDCollectionUtil.isEmpty(listSelectedImages))
		{
			for (LocalImageModel model : listSelectedImages)
			{
				listFile.add(new File(model.getPath()));
			}
			mCompresser.compressImageFile(listFile);
		}
	}

	public void showSelectDialog()
	{
		String[] arrOption = new String[] { "拍照", "相册" };
		List<String> listOptions = Arrays.asList(arrOption);

		SDSimpleTextAdapter<String> adapter = new SDSimpleTextAdapter<String>(listOptions, getActivity());

		SDDialogMenu dialog = new SDDialogMenu();

		dialog.setAdapter(adapter);
		dialog.setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				switch (index)
				{
				case 0: // 拍照
					getPhotoFromCamera();
					break;
				case 1: // 相册
					getPhotoFromAlbum();
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

	/**
	 * 从相册获取图片
	 * 
	 * @param model
	 */
	protected void getPhotoFromAlbum()
	{
		Intent intent = new Intent(getActivity(), LocalImageActivity.class);
		List<LocalImageModel> listModel = getSelectedImages();

		intent.putExtra(LocalImageActivity.EXTRA_MAX_SELECTED_IMAGE_COUNT, MAX_UPLOAD_IMAGE_COUNT);
		intent.putExtra(LocalImageActivity.EXTRA_HAS_SELECTED_IMAGES, (Serializable) listModel);

		startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_ALBUM);
	}

	/**
	 * 拍照获取图片
	 * 
	 * @param model
	 */
	protected void getPhotoFromCamera()
	{
		mPhotoHandler.getPhotoFromCamera();
	}

	public List<LocalImageModel> getSelectedImages()
	{
		List<LocalImageModel> listModel = new ArrayList<LocalImageModel>();
		for (LocalImageModel model : mListModel)
		{
			if (!model.isAddImage())
			{
				listModel.add(model);
			}
		}
		return listModel;
	}

	private void bindUploadImageData()
	{
		addDefaultImage();
		SelectImageAdapter adapter = new SelectImageAdapter(mListModel, getActivity());
		adapter.setmListener(new SelectImageAdapterListener()
		{

			@Override
			public void onClickDelete(int position, View v)
			{
				bindUploadImageData();
			}
		});
		mLlContent.setAdapter(adapter);
	}

	/**
	 * 添加加号图片
	 */
	private void addDefaultImage()
	{
		if (!mNeedAddImage)
		{
			return;
		}

		int size = mListModel.size();
		boolean needAddImage = false;

		if (size >= MAX_UPLOAD_IMAGE_COUNT)
		{
			return;
		} else if (size == 0)
		{
			needAddImage = true;
		} else if (size > 0 && size < MAX_UPLOAD_IMAGE_COUNT)
		{
			LocalImageModel lastModel = SDCollectionUtil.getLast(mListModel, 0);
			if (lastModel != null && !lastModel.isAddImage())
			{
				needAddImage = true;
			}
		}

		if (needAddImage)
		{
			LocalImageModel model = new LocalImageModel();
			mListModel.add(model);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mPhotoHandler.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
		case REQUEST_CODE_GET_PHOTO_FROM_ALBUM:
			if (resultCode == LocalImageActivity.RESULT_CODE_SELECT_IMAGE_SUCCESS)
			{
				List<LocalImageModel> listModel = (List<LocalImageModel>) data
						.getSerializableExtra((LocalImageActivity.EXTRA_RESULT_SELECTED_IMAGES));
				if (listModel != null && listModel.size() > 0)
				{
					mListModel = listModel;
					bindUploadImageData();
				}
			}
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy()
	{
		mPhotoHandler.deleteTakePhotoFiles();
		mCompresser.deleteCompressedImageFile();
		super.onDestroy();
	}

	public interface SelectImageFragment_compressListener
	{
		public void onCompressFinish(List<File> listFile);
	}

}
