package com.fanwe.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.fanwe.businessclient.R;

public class SDBottomTabItem extends SDBottomNavigatorBaseItem {

	private ImageView mIvTitle = null;
	private int mBackgroundNormalId = 0;
	private int mBackgroundPressId = 0;

	public int getmBackgroundNormalId() {
		return mBackgroundNormalId;
	}

	public void setmBackgroundNormalId(int mBackgroundNormalId) {
		this.mBackgroundNormalId = mBackgroundNormalId;
	}

	public int getmBackgroundPressId() {
		return mBackgroundPressId;
	}

	public void setmBackgroundPressId(int mBackgroundPressId) {
		this.mBackgroundPressId = mBackgroundPressId;
	}

	public SDBottomTabItem(Context context) {
		super(context);
		init();
	}

	public SDBottomTabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_tab_item, this,
				true);
		mIvTitle = (ImageView) findViewById(R.id.view_tab_item_iv_title);
		this.setSelected(mSelected);
		this.setFocusable(true);
	}

	@Override
	public void setSelectedState(boolean select) {
		if (select) {
			mSelected = true;
			if (mBackgroundPressId != 0) {
				mIvTitle.setImageResource(mBackgroundPressId);
			}
		} else {
			mSelected = false;
			if (mBackgroundNormalId != 0) {
				mIvTitle.setImageResource(mBackgroundNormalId);
			}
		}

	}

	@Override
	public boolean getSelectedState() {
		// TODO Auto-generated method stub
		return mSelected;
	}

}
