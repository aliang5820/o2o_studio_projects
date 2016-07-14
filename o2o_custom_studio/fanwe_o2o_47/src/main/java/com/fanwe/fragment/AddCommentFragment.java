package com.fanwe.fragment;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.fanwe.LocalImageActivity;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.handler.PhotoHandler.PhotoHandlerListener;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.ImageFileCompresser.ImageFileCompresserListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.LocalImageModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 评论界面(可添加图片)
 * 
 * @author Administrator
 * 
 */

public class AddCommentFragment extends BaseFragment implements View.OnClickListener
{

	private static final int MAX_UPLOAD_IMAGE_COUNT = 9;

	public static final int REQUEST_CODE_GET_PHOTO_FROM_ALBUM = 1;

	@ViewInject(R.id.tv_title)
	private TextView mTv_title;

	@ViewInject(R.id.rb_star)
	private RatingBar mRb_star;

	@ViewInject(R.id.tv_star_text)
	private TextView mTv_star_text;

	@ViewInject(R.id.et_comment_content)
	private EditText mEt_comment_content;

	@ViewInject(R.id.tv_text_number)
	private TextView mTv_text_number;

	@ViewInject(R.id.ll_images)
	private LinearLayout mLl_images;

	private PhotoHandler mProcesser;
	private ImageFileCompresser mCompresser;
	private List<LocalImageModel> mListModel = new ArrayList<LocalImageModel>();
	private LocalImageModel mClickModel;
	private String mStrName;
	private AddCommentFragmentListener mListener;

	public void setmListener(AddCommentFragmentListener mListener)
	{
		this.mListener = mListener;
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_add_comment);
	}

	// getter

	public float getStarPoint()
	{
		return mRb_star.getRating();
	}

	public String getCommentContent()
	{
		return mEt_comment_content.getText().toString();
	}

	// setter

	public void setName(String name)
	{
		this.mStrName = name;
	}

	@Override
	protected void init()
	{
		bindData();
		addProcesser();
		addCompresser();
		addEdittextListener();
		addRatingbarListener();
		bindUploadImageData();
	}

	private void bindData()
	{
		if (mStrName != null)
		{
			mTv_title.setText(mStrName);
		}
	}

	private void addRatingbarListener()
	{
		mRb_star.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{
				int intRating = (int) rating;
				switch (intRating)
				{
				case 1:
					mTv_star_text.setText(SDResourcesUtil.getString(R.string.text_one_star));
					break;
				case 2:
					mTv_star_text.setText(SDResourcesUtil.getString(R.string.text_two_star));
					break;
				case 3:
					mTv_star_text.setText(SDResourcesUtil.getString(R.string.text_three_star));
					break;
				case 4:
					mTv_star_text.setText(SDResourcesUtil.getString(R.string.text_four_star));
					break;
				case 5:
					mTv_star_text.setText(SDResourcesUtil.getString(R.string.text_five_star));
					break;

				default:
					break;
				}
			}
		});
		mRb_star.setRating(5.0f);
	}

	private void addEdittextListener()
	{
		mEt_comment_content.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s)
			{
				String content = s.toString();
				mTv_text_number.setText(String.valueOf(content.length()));
			}
		});
	}

	private void addProcesser()
	{
		mProcesser = new PhotoHandler(this);
		mProcesser.setmListener(new AddCommentPhotoProcesserListener());
	}

	private void bindUploadImageData()
	{
		addDefaultImage();
		mLl_images.removeAllViews();
		for (int i = 0; i < mListModel.size(); i++)
		{
			if (i % 3 == 0)
			{
				View itemView = SDViewUtil.inflate(R.layout.item_comment_upload_image, null);
				ImageView iv1 = (ImageView) itemView.findViewById(R.id.iv_1);
				ImageView iv2 = (ImageView) itemView.findViewById(R.id.iv_2);
				ImageView iv3 = (ImageView) itemView.findViewById(R.id.iv_3);

				ImageView ivClose1 = (ImageView) itemView.findViewById(R.id.iv_close_1);
				ImageView ivClose2 = (ImageView) itemView.findViewById(R.id.iv_close_2);
				ImageView ivClose3 = (ImageView) itemView.findViewById(R.id.iv_close_3);

				LocalImageModel model1 = SDCollectionUtil.get(mListModel, i);
				LocalImageModel model2 = SDCollectionUtil.get(mListModel, i + 1);
				LocalImageModel model3 = SDCollectionUtil.get(mListModel, i + 2);

				bindImage(model1, iv1, ivClose1);
				bindImage(model2, iv2, ivClose2);
				bindImage(model3, iv3, ivClose3);

				mLl_images.addView(itemView);
			}
		}
	}

	private void bindImage(final LocalImageModel model, ImageView iv, ImageView ivClose)
	{
		if (iv == null)
		{
			return;
		}
		if (model == null)
		{
			return;
		}

		iv.setOnClickListener(this);
		iv.setTag(model);

		if (!model.isAddImage()) // 选择或者拍照的图片
		{
			ivClose.setVisibility(View.VISIBLE);
			ivClose.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					mListModel.remove(model);
					bindUploadImageData();
				}
			});
			SDViewBinder.setImageView("file://" + model.getPath(), iv, ImageLoaderManager.getOptionsNoCache());
		} else
		{
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.setImageResource(R.drawable.bg_add_comment_image);
		}

	}

	/**
	 * 添加加号图片
	 */
	private void addDefaultImage()
	{
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
	public void onClick(View v)
	{
		Object tag = v.getTag();
		if (tag != null && tag instanceof LocalImageModel)
		{
			LocalImageModel model = (LocalImageModel) tag;
			mClickModel = model;
			showDialogByModel();
		}

	}

	private void showDialogByModel()
	{
		List<String> listOptions = getOptionsByModel();

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
				// TODO Auto-generated method stub

			}

			@Override
			public void onCancelClick(View v, SDDialogMenu dialog)
			{
				// TODO Auto-generated method stub

			}
		});

		dialog.showBottom();
	}

	/**
	 * 拍照获取图片
	 * 
	 * @param model
	 */
	protected void getPhotoFromCamera()
	{
		mProcesser.getPhotoFromCamera();
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

	/**
	 * 拍照照片获取监听
	 * 
	 * @author Administrator
	 * 
	 */
	class AddCommentPhotoProcesserListener implements PhotoHandlerListener
	{

		@Override
		public void onResultFromAlbum(File file)
		{
		}

		@Override
		public void onResultFromCamera(File file)
		{
			dealSelectFile(file.getAbsolutePath());
			bindUploadImageData();
		}

		@Override
		public void onFailure(String msg)
		{
			if (!TextUtils.isEmpty(msg))
			{
				SDToast.showToast(msg);
			}
		}
	}

	private void dealSelectFile(String path)
	{
		if (mClickModel != null)
		{
			mClickModel.setPath(path);
		}
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
				if (!TextUtils.isEmpty(msg))
				{
					SDToast.showToast(msg);
				}
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
				if (mListener != null)
				{
					mListener.onCompressFinish(nListFile);
				}
			}
		});
	}

	private List<String> getOptionsByModel()
	{
		List<String> listOptions = new ArrayList<String>();
		listOptions.add("拍照");
		listOptions.add("相册");
		return listOptions;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mProcesser.onActivityResult(requestCode, resultCode, data);
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
		mProcesser.deleteTakePhotoFiles();
		mCompresser.deleteCompressedImageFile();
		super.onDestroy();
	}

	public interface AddCommentFragmentListener
	{
		public void onCompressFinish(List<File> listFile);
	}

}
