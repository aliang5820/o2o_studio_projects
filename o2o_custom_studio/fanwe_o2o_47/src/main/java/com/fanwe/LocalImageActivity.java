package com.fanwe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.fanwe.adapter.LocalImageAdapter;
import com.fanwe.adapter.LocalImageAdapter.LocalImageAdapterListener;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.LocalImageFinder;
import com.fanwe.library.utils.LocalImageFinder.LocalImageFinderListener;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalImageModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LocalImageActivity extends BaseActivity
{

	public static final String EXTRA_MAX_SELECTED_IMAGE_COUNT = "extra_max_selected_image_count";

	public static final String EXTRA_RESULT_SELECTED_IMAGES = "extra_result_selected_images";

	public static final String EXTRA_HAS_SELECTED_IMAGES = "extra_has_selected_images";

	public static final int RESULT_CODE_SELECT_IMAGE_SUCCESS = 10;

	@ViewInject(R.id.gv_content)
	private GridView mGv_content;

	private List<LocalImageModel> mListModel = new ArrayList<LocalImageModel>();

	private List<LocalImageModel> mListHasSelectedModel;;

	private LocalImageAdapter mAdapter;

	private int mMaxSelectImageCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_local_image);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		bindDefaultData();
		loadData();
	}

	private void getIntentData()
	{
		mMaxSelectImageCount = getIntent().getIntExtra(EXTRA_MAX_SELECTED_IMAGE_COUNT, 0);
		mListHasSelectedModel = (List<LocalImageModel>) getIntent().getSerializableExtra(EXTRA_HAS_SELECTED_IMAGES);
	}

	private void loadData()
	{
		LocalImageFinder finder = new LocalImageFinder(mActivity);
		finder.getLocalImage(new LocalImageFinderListener()
		{

			@Override
			public void onFinished(List<String> listPath)
			{
				dealLocalImages(listPath);
			}

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候");
			}

			@Override
			public void onFailure(String msg)
			{
				SDDialogManager.dismissProgressDialog();
			}
		});
	}

	protected void dealLocalImages(List<String> listPath)
	{
		List<LocalImageModel> listModel = parseListPathToListModel(listPath);
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			mListModel.clear();
			// 如果有已经选中的图片
			if (!SDCollectionUtil.isEmpty(mListHasSelectedModel))
			{
				List<LocalImageModel> listModelNew = new ArrayList<LocalImageModel>();
				for (LocalImageModel modelSelected : mListHasSelectedModel)
				{
					boolean isNewModel = true;
					for (LocalImageModel model : listModel)
					{
						// 找到该选中的图片
						if (model.equals(modelSelected))
						{
							isNewModel = false;
							model.setSelected(true);
							break;
						}
					}
					// 是拍照的新图片
					if (isNewModel)
					{
						if (!listModelNew.contains(modelSelected))
						{
							modelSelected.setSelected(true);
							listModelNew.add(modelSelected);
						}
					}
				}
				mListModel.addAll(listModelNew);
			}
			mListModel.addAll(listModel);
			mAdapter.updateData(mListModel);
		}
		SDDialogManager.dismissProgressDialog();
	}

	private void bindDefaultData()
	{
		mAdapter = new LocalImageAdapter(mListModel, mActivity, new LocalImageAdapterListener()
		{

			@Override
			public void onClick(View v, int position, LocalImageModel model)
			{
				if (model.isSelected())
				{
					mAdapter.getSelectManager().performClick(position);
				} else
				{
					if (mAdapter.getSelectManager().getSelectedItems().size() >= mMaxSelectImageCount)
					{
						SDToast.showToast("最多选择" + mMaxSelectImageCount + "张图片");
					} else
					{
						mAdapter.getSelectManager().performClick(position);
					}
				}
			}
		});
		mGv_content.setAdapter(mAdapter);

		mGv_content.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				List<String> listPath = parseListModelToListPath(mListModel, true);

				Intent intent = new Intent(mActivity, AlbumActivity.class);
				intent.putExtra(AlbumActivity.EXTRA_IMAGES_INDEX, (int) id);
				intent.putStringArrayListExtra(AlbumActivity.EXTRA_LIST_IMAGES, (ArrayList<String>) listPath);
				startActivity(intent);
			}
		});
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("相册");
		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("完成");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		List<LocalImageModel> listModel = mAdapter.getSelectManager().getSelectedItems();
		if (listModel.size() <= 0)
		{
			SDToast.showToast("您还未选择图片");
		} else
		{
			Intent data = new Intent();
			data.putExtra(EXTRA_RESULT_SELECTED_IMAGES, (Serializable) listModel);
			setResult(RESULT_CODE_SELECT_IMAGE_SUCCESS, data);
			finish();
		}
	}

	private List<String> parseListModelToListPath(List<LocalImageModel> listModel, boolean pathLoad)
	{
		List<String> listPath = new ArrayList<String>();
		if (!SDCollectionUtil.isEmpty(listModel))
		{
			for (LocalImageModel model : listModel)
			{
				if (pathLoad)
				{
					listPath.add(model.getPathLoad());
				} else
				{
					listPath.add(model.getPath());
				}
			}
		}
		return listPath;
	}

	private List<LocalImageModel> parseListPathToListModel(List<String> listPath)
	{
		List<LocalImageModel> listModel = new ArrayList<LocalImageModel>();
		if (!SDCollectionUtil.isEmpty(listPath))
		{
			for (String path : listPath)
			{
				LocalImageModel model = new LocalImageModel();
				model.setPath(path);
				listModel.add(model);
			}
		}
		return listModel;
	}
}
