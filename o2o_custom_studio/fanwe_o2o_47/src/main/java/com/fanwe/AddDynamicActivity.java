package com.fanwe;

import java.io.File;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fanwe.adapter.ExpressionAdapter.ExpressionAdapterListener;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.ExpressionContainerFragment;
import com.fanwe.fragment.ExpressionContainerFragment.ExpressionContainerOnClickDelete;
import com.fanwe.fragment.SelectImageFragment;
import com.fanwe.fragment.SelectImageFragment.SelectImageFragment_compressListener;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.span.model.SDSpanInfo;
import com.fanwe.library.span.view.SDSpanEdittext;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.ExpressionContainerModel;
import com.fanwe.model.ExpressionModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_home_publishActModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.span.SDNetImageSpan;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

/**
 * 发表动态
 * 
 * @author Administrator
 * 
 */
public class AddDynamicActivity extends BaseActivity
{

	@ViewInject(R.id.ll_select_image)
	private LinearLayout mLl_select_image;

	// @ViewInject(R.id.ll_select_at)
	// private LinearLayout mLl_select_at;
	// @ViewInject(R.id.ll_select_jing)
	// private LinearLayout mLl_select_jing;

	@ViewInject(R.id.ll_select_expression)
	private LinearLayout mLl_select_expression;

	@ViewInject(R.id.iv_select_expression)
	private ImageView mIv_select_expression;

	@ViewInject(R.id.act_add_dynamic_fl_expression)
	private View mFl_expression;

	@ViewInject(R.id.et_content)
	private SDSpanEdittext mEt_content;

	private Uc_home_publishActModel mActModel;
	private boolean mShowExpression = false;
	private ExpressionContainerFragment mFragmentExpressions;

	private SelectImageFragment mFragImages;

	private String mStrContent;
	private List<File> mListImage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_add_dynamic);
		init();
	}

	private void init()
	{
		initTitle();
		addFragments();
		registerClick();
		requestData();
	}

	private void registerClick()
	{
		mLl_select_image.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mFragImages.showSelectDialog();
			}
		});

		// mLl_select_at.setOnClickListener(new OnClickListener()
		// {
		// @Override
		// public void onClick(View v)
		// {
		//
		// }
		// });
		// mLl_select_jing.setOnClickListener(new OnClickListener()
		// {
		// @Override
		// public void onClick(View v)
		// {
		//
		// }
		// });

		mLl_select_expression.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mShowExpression = !mShowExpression;
				changeExpressionsFragmentVisibility();
			}
		});

		mEt_content.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mShowExpression = false;
				changeExpressionsFragmentVisibility();
			}
		});
	}

	private void changeExpressionsFragmentVisibility()
	{
		if (mShowExpression)
		{
			mIv_select_expression.setImageResource(R.drawable.ic_add_dynamic_expression_selected);
			SDViewUtil.show(mFl_expression);
			SDViewUtil.hideInputMethod(mEt_content);
		} else
		{
			mIv_select_expression.setImageResource(R.drawable.ic_add_dynamic_expression_normal);
			SDViewUtil.hide(mFl_expression);
		}
	}

	private void requestData()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_home");
		model.putAct("publish");
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_home_publishActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					mActModel = actModel;
					addExpressionsFragment();
				}
			}
		});
	}

	private void addExpressionsFragment()
	{
		if (mActModel == null)
		{
			return;
		}
		ExpressionContainerModel expressionModel = mActModel.getExpression();
		if (expressionModel != null)
		{
			mFragmentExpressions = new ExpressionContainerFragment();
			mFragmentExpressions.setmListenerOnClickDelete(new ExpressionContainerOnClickDelete()
			{

				@Override
				public void onClick(View v)
				{
					mEt_content.deleteLastSpan();
				}
			});
			mFragmentExpressions.setmListenerExpressionAdapter(new ExpressionAdapterListener()
			{

				@Override
				public void onItemClick(ExpressionModel model, int position)
				{
					String key = model.getEmotion();
					String url = model.getFilename();
					if (!isEmpty(key) && !isEmpty(url))
					{
						SDNetImageSpan span = new SDNetImageSpan(mEt_content);
						span.setImage(url);
						SDSpanInfo spanInfo = new SDSpanInfo(mEt_content, span);
						spanInfo.getMatcherInfo().setKey(key);

						mEt_content.insertSpan(spanInfo);
					}
				}
			});
			mFragmentExpressions.setListExpressionGroupModel(expressionModel.getListExpressionGruop());
			getSDFragmentManager().replace(R.id.act_add_dynamic_fl_expression, mFragmentExpressions);
		}
	}

	private void addFragments()
	{
		mFragImages = new SelectImageFragment();
		mFragImages.setmNeedAddImage(false);
		mFragImages.setmListenerCompress(new SelectImageFragment_compressListener()
		{

			@Override
			public void onCompressFinish(List<File> listFile)
			{
				mListImage = listFile;
				requestPublish();
			}
		});
		getSDFragmentManager().replace(R.id.act_add_dynamic_fl_images, mFragImages);
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("发表动态");

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot("发表");
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		doPublish();
	}

	private void doPublish()
	{
		mStrContent = mEt_content.getText().toString();
		if (isEmpty(mStrContent))
		{
			SDToast.showToast("请输入内容");
			return;
		}

		if (mFragImages != null && !isEmpty(mFragImages.getSelectedImages()))
		{
			mFragImages.compressSelectedImages();
			return;
		}
		requestPublish();
	}

	private void requestPublish()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_home");
		model.putAct("do_publish");
		model.put("content", mStrContent);
		if (!isEmpty(mListImage))
		{
			for (File image : mListImage)
			{
				model.putMultiFile("file[]", image);
			}
		}

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				SDDialogManager.dismissProgressDialog();
				if (actModel.getStatus() > 0)
				{
					SDEventManager.post(EnumEventTag.PUBLISH_DYNAMIC_SUCCESS.ordinal());
					finish();
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		});

	}

}
