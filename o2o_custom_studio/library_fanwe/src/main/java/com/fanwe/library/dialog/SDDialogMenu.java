package com.fanwe.library.dialog;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 带取消按钮的菜单选择窗口
 * 
 * @author js02
 * 
 */
public class SDDialogMenu extends SDDialogBase
{

	private View mView;
	private TextView mTvTitle;
	private TextView mTvCancel;
	private LinearLayout mLlContent;
	private ListView mLvContent;

	private SDDialogMenuListener mListener;

	public SDDialogMenu setmListener(SDDialogMenuListener mListener)
	{
		this.mListener = mListener;
		return this;
	}

	public SDDialogMenu(Activity activity)
	{
		super(activity);
		init();
	}

	public SDDialogMenu()
	{
		super();
		init();
	}

	private void init()
	{
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_menu, null);
		mTvTitle = (TextView) mView.findViewById(R.id.dialog_menu_tv_title);
		mTvCancel = (TextView) mView.findViewById(R.id.dialog_menu_tv_cancel);
		mLlContent = (LinearLayout) mView.findViewById(R.id.dialog_menu_ll_content);
		mLvContent = (ListView) mView.findViewById(R.id.dialog_menu_lv_content);

		setDialogView(mView, false);
		initViewStates();
	}

	private void initViewStates()
	{
		mTvCancel.setOnClickListener(this);
		setTextTitle(null);
		setTextColorCancel(mConfig.getmMainColor());
		initDrawable();
		initListView();
		paddings(SDViewUtil.dp2px(10));
	}

	private void initListView()
	{
		SDViewUtil.setBackgroundDrawable(mLlContent, mDrawableManager.getLayerWhiteStrokeItemSingle(true));
		setDivierEnable(true);
	}

	private void initDrawable()
	{
		SDViewUtil.setBackgroundDrawable(mTvCancel, mDrawableManager.getSelectorWhiteGrayStrokeItemSingle(true));
		SDViewUtil.setBackgroundDrawable(mTvTitle, mDrawableManager.getSelectorWhiteGrayStrokeItemTop(true));
	}

	public SDDialogMenu setDivierEnable(boolean enable)
	{
		if (enable)
		{
			mLvContent.setDivider(new SDDrawable().color(mConfig.getmStrokeColor()));
			mLvContent.setDividerHeight(mConfig.getmStrokeWidth());
		} else
		{
			mLvContent.setDivider(null);
			mLvContent.setDividerHeight(0);
		}
		return this;
	}

	public SDDialogMenu setAdapter(BaseAdapter adapter)
	{
		mLvContent.setAdapter(adapter);
		mLvContent.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (mListener != null)
				{
					mListener.onItemClick(view, (int) id, SDDialogMenu.this);
				}
				dismissClick();
			}
		});
		return this;
	}

	public SDDialogMenu setItems(String[] arrString)
	{
		if (arrString != null && arrString.length > 0)
		{
			List<String> listString = Arrays.asList(arrString);
			SDSimpleTextAdapter<String> adapter = new SDSimpleTextAdapter<String>(listString, mActivity);
			setAdapter(adapter);
		}
		return this;
	}

	// --------------------------color

	public SDDialogMenu setTextColorCancel(int colors)
	{
		mTvCancel.setTextColor(colors);
		return this;
	}

	// ------------------------text

	public SDDialogMenu setTextTitle(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mTvTitle.setVisibility(View.GONE);
		} else
		{
			mTvTitle.setVisibility(View.VISIBLE);
			mTvTitle.setText(text);
		}
		return this;
	}

	public SDDialogMenu setTextCancel(String text)
	{
		if (TextUtils.isEmpty(text))
		{
			mTvCancel.setVisibility(View.GONE);
		} else
		{
			mTvCancel.setVisibility(View.VISIBLE);
			mTvCancel.setText(text);
		}
		return this;
	}

	@Override
	public void onClick(View v)
	{
		if (v == mTvCancel)
		{
			clickCancel(v);
		}
	}

	private void clickCancel(View v)
	{
		if (mListener != null)
		{
			mListener.onCancelClick(v, SDDialogMenu.this);
		}
		dismissClick();
	}

	@Override
	public void onDismiss(DialogInterface dialog)
	{
		if (mListener != null)
		{
			mListener.onDismiss(SDDialogMenu.this);
		}
	}

	public interface SDDialogMenuListener
	{
		public void onCancelClick(View v, SDDialogMenu dialog);

		public void onDismiss(SDDialogMenu dialog);

		public void onItemClick(View v, int index, SDDialogMenu dialog);
	}

}
