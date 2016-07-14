package com.fanwe.fragment;

import com.fanwe.model.Youhui_indexActModel;
import com.fanwe.model.Youhui_infoModel;

public class YouhuiDetailBaseFragment extends BaseFragment
{

	protected Youhui_indexActModel mYouhuiModel;
	protected Youhui_infoModel mInfoModel;

	public void setmYouhuiModel(Youhui_indexActModel mYouhuiModel)
	{
		this.mYouhuiModel = mYouhuiModel;
		if (this.mYouhuiModel != null)
		{
			this.mInfoModel = this.mYouhuiModel.getYouhui_info();
		}
	}

}
