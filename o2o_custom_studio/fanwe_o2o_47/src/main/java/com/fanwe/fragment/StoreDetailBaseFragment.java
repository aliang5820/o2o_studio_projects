package com.fanwe.fragment;

import com.fanwe.model.StoreActModel;
import com.fanwe.model.Store_infoModel;

public class StoreDetailBaseFragment extends BaseFragment
{

	protected StoreActModel mStoreModel;
	protected Store_infoModel mInfoModel;

	public StoreActModel getmStoreModel()
	{
		return mStoreModel;
	}

	public void setmStoreModel(StoreActModel mStoreModel)
	{
		this.mStoreModel = mStoreModel;
		if (this.mStoreModel != null)
		{
			mInfoModel = this.mStoreModel.getStore_info();
		}
	}

}
