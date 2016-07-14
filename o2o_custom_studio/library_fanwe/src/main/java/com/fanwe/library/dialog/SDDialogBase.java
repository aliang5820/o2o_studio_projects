package com.fanwe.library.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fanwe.library.R;
import com.fanwe.library.SDLibrary;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.config.SDLibraryConfig;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.drawable.SDDrawableManager;
import com.fanwe.library.utils.SDViewUtil;

public class SDDialogBase extends Dialog implements View.OnClickListener, OnDismissListener
{

	public static final int DEFAULT_PADDING_LEFT_RIGHT = SDViewUtil.dp2px(20);
	public static final int DEFAULT_PADDING_TOP_BOTTOM = SDViewUtil.dp2px(10);

	private View mView;
	public SDDrawableManager mDrawableManager;
	protected LinearLayout mLlAll;
	protected SDLibraryConfig mConfig = SDLibrary.getInstance().getConfig();
	protected boolean mDismissAfterClick = true;
	protected Activity mActivity;

	public SDDialogBase()
	{
		this(SDActivityManager.getInstance().getLastActivity());
	}

	public SDDialogBase(Activity activity)
	{
		this(activity, R.style.dialogBaseBlur);
	}

	public SDDialogBase(int theme)
	{
		this(SDActivityManager.getInstance().getLastActivity(), theme);
	}

	public SDDialogBase(Activity activity, int theme)
	{
		super(activity, theme);
		this.mActivity = activity;
		baseInit();
	}

	private void initDrawable()
	{
		mDrawableManager = new SDDrawableManager();
	}

	private void baseInit()
	{
		mLlAll = new LinearLayout(getContext());
		mLlAll.setBackgroundColor(Color.parseColor("#00000000"));
		mLlAll.setGravity(Gravity.CENTER);
		this.setOnDismissListener(this);
		initDrawable();
		setCanceledOnTouchOutside(false);
	}

	@Override
	public void onClick(View v)
	{

	}

	// ------------------getter setter-----------------

	/**
	 * 是否点击按钮后自动关闭窗口
	 * 
	 * @return
	 */
	public boolean ismDismissAfterClick()
	{
		return mDismissAfterClick;
	}

	/**
	 * 设置是否点击按钮后自动关闭窗口,默认true(是)
	 * 
	 * @param mDismissAfterClick
	 * @return
	 */
	public SDDialogBase setmDismissAfterClick(boolean mDismissAfterClick)
	{
		this.mDismissAfterClick = mDismissAfterClick;
		return this;
	}

	// ---------------------show gravity

	/**
	 * 设置窗口显示的位置
	 * 
	 * @param gravity
	 * @return
	 */
	public SDDialogBase setGrativity(int gravity)
	{
		getWindow().setGravity(gravity);
		return this;
	}

	public void showTop()
	{
		showTop(true);
	}

	/**
	 * 显示在顶部
	 * 
	 * @param anim
	 *            是否需要动画
	 */
	public void showTop(boolean anim)
	{
		setGrativity(Gravity.TOP);
		if (anim)
		{
			setAnimations(R.style.anim_top_top);
		}
		show();
	}

	public void showBottom()
	{
		showBottom(true);
	}

	/**
	 * 显示在底部
	 * 
	 * @param anim
	 *            是否需要动画
	 */
	public void showBottom(boolean anim)
	{
		setGrativity(Gravity.BOTTOM);
		if (anim)
		{
			setAnimations(R.style.anim_bottom_bottom);
		}
		show();
	}

	public void showCenter()
	{
		setGrativity(Gravity.CENTER);
		show();
	}

	@Override
	public void dismiss()
	{
		if (mActivity != null && !mActivity.isFinishing())
		{
			super.dismiss();
		}
	}

	@Override
	public void show()
	{
		if (mActivity != null && !mActivity.isFinishing())
		{
			super.show();
		}
	}

	/**
	 * 设置窗口的显示和隐藏动画
	 * 
	 * @param resId
	 */
	public void setAnimations(int resId)
	{
		getWindow().setWindowAnimations(resId);
	}

	// -----------------------padding

	public SDDialogBase paddingTop(int top)
	{
		mLlAll.setPadding(mLlAll.getPaddingLeft(), top, mLlAll.getPaddingRight(), mLlAll.getPaddingBottom());
		return this;
	}

	public SDDialogBase paddingBottom(int bottom)
	{
		mLlAll.setPadding(mLlAll.getPaddingLeft(), mLlAll.getPaddingTop(), mLlAll.getPaddingRight(), bottom);
		return this;
	}

	public SDDialogBase paddingLeft(int left)
	{
		mLlAll.setPadding(left, mLlAll.getPaddingTop(), mLlAll.getPaddingRight(), mLlAll.getPaddingBottom());
		return this;
	}

	public SDDialogBase paddingRight(int right)
	{
		mLlAll.setPadding(mLlAll.getPaddingLeft(), mLlAll.getPaddingTop(), right, mLlAll.getPaddingBottom());
		return this;
	}

	public SDDialogBase paddings(int paddings)
	{
		mLlAll.setPadding(paddings, paddings, paddings, paddings);
		return this;
	}

	/**
	 * 设置窗口上下左右的边距
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public SDDialogBase padding(int left, int top, int right, int bottom)
	{
		mLlAll.setPadding(left, top, right, bottom);
		return this;
	}

	// -----------------------------layoutParams

	public WindowManager.LayoutParams getLayoutParams()
	{
		return getWindow().getAttributes();
	}

	public SDDialogBase setLayoutParams(WindowManager.LayoutParams params)
	{
		getWindow().setAttributes(params);
		return this;
	}

	// ----------------------dialogView

	public SDDialogBase setDialogView(View view)
	{
		return setDialogView(view, null, true);
	}

	public SDDialogBase setDialogView(View view, boolean needDefaultBackground)
	{
		return setDialogView(view, null, needDefaultBackground);
	}

	/**
	 * 设置窗口的视图
	 * 
	 * @param view
	 * @param params
	 * @param needDefaultBackground是否需要默认背景
	 *            (白色圆角)
	 * @return
	 */
	public SDDialogBase setDialogView(View view, ViewGroup.LayoutParams params, boolean needDefaultBackground)
	{
		mView = view;
		wrapperView(mView);
		setDefaultBackgroundEnable(needDefaultBackground);
		if (params == null)
		{
			params = new ViewGroup.LayoutParams(SDViewUtil.getScreenWidth(), LayoutParams.WRAP_CONTENT);
		}
		padding(DEFAULT_PADDING_LEFT_RIGHT, DEFAULT_PADDING_TOP_BOTTOM, DEFAULT_PADDING_LEFT_RIGHT, DEFAULT_PADDING_TOP_BOTTOM);
		super.setContentView(mLlAll, params);
		return this;
	}

	public void paddingClear()
	{
		paddings(0);
	}

	/**
	 * 是否需要默认背景
	 * 
	 * @param enable
	 * @return
	 */
	public SDDialogBase setDefaultBackgroundEnable(boolean enable)
	{
		if (enable)
		{
			SDViewUtil.setBackgroundDrawable(mView, new SDDrawable().cornerAll(mConfig.getmCornerRadius()));
		} else
		{

		}
		return this;
	}

	private void wrapperView(View view)
	{
		mLlAll.removeAllViews();
		mLlAll.addView(view, SDViewUtil.getLayoutParamsLinearLayoutMM());
	}

	public View getDialogView()
	{
		return mView;
	}

	protected void dismissClick()
	{
		if (mDismissAfterClick)
		{
			dismiss();
		}
	}

	// ------------------------setContentView

	@Override
	public void setContentView(int layoutResID)
	{
		this.setContentView(SDViewUtil.inflate(layoutResID, null));
	}

	@Override
	public void setContentView(View view)
	{
		this.setContentView(view, null);
	}

	@Override
	public void setContentView(View view, LayoutParams params)
	{
		setDialogView(view, params, false);
	}

	public View getContentView()
	{
		return mView;
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{

	}

	// ------------------------------background

	/**
	 * 边框：top，right 圆角：bottomLeft
	 * 
	 * @return
	 */
	public Drawable getBackgroundBottomLeft()
	{
		SDDrawable drawableCancel = new SDDrawable();
		drawableCancel.strokeColor(mConfig.getmStrokeColor()).strokeWidth(0, mConfig.getmStrokeWidth(), mConfig.getmStrokeWidth(), 0)
				.cornerBottomLeft(mConfig.getmCornerRadius());

		SDDrawable drawableCancelPressed = new SDDrawable();
		drawableCancelPressed.strokeColor(mConfig.getmStrokeColor()).color(mConfig.getmGrayPressColor())
				.strokeWidth(0, mConfig.getmStrokeWidth(), mConfig.getmStrokeWidth(), 0).cornerBottomLeft(mConfig.getmCornerRadius());

		return SDDrawable.getStateListDrawable(drawableCancel, null, null, drawableCancelPressed);
	}

	/**
	 * 边框：top 圆角：bottomRight
	 * 
	 * @return
	 */
	public Drawable getBackgroundBottomRight()
	{
		SDDrawable drawableConfirm = new SDDrawable();
		drawableConfirm.strokeColor(mConfig.getmStrokeColor()).strokeWidth(0, mConfig.getmStrokeWidth(), 0, 0)
				.cornerBottomRight(mConfig.getmCornerRadius());

		SDDrawable drawableConfirmPressed = new SDDrawable();
		drawableConfirmPressed.strokeColor(mConfig.getmStrokeColor()).color(mConfig.getmGrayPressColor())
				.strokeWidth(0, mConfig.getmStrokeWidth(), 0, 0).cornerBottomRight(mConfig.getmCornerRadius());

		return SDDrawable.getStateListDrawable(drawableConfirm, null, null, drawableConfirmPressed);
	}

	/**
	 * 边框：top 圆角：bottomLeft，bottomRight
	 * 
	 * @return
	 */
	public Drawable getBackgroundBottomSingle()
	{
		SDDrawable drawableConfirm = new SDDrawable();
		drawableConfirm.strokeColor(mConfig.getmStrokeColor()).strokeWidth(0, mConfig.getmStrokeWidth(), 0, 0)
				.corner(0, 0, mConfig.getmCornerRadius(), mConfig.getmCornerRadius());

		SDDrawable drawableConfirmPressed = new SDDrawable();
		drawableConfirmPressed.strokeColor(mConfig.getmStrokeColor()).color(mConfig.getmGrayPressColor())
				.strokeWidth(0, mConfig.getmStrokeWidth(), 0, 0).corner(0, 0, mConfig.getmCornerRadius(), mConfig.getmCornerRadius());
		return SDDrawable.getStateListDrawable(drawableConfirm, null, null, drawableConfirmPressed);
	}
}
