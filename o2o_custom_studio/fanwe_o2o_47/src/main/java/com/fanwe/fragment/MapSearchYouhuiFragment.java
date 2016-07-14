package com.fanwe.fragment;

import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Youhuis_indexActModel;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;

public class MapSearchYouhuiFragment extends MapSearchFragment
{

	@Override
	protected void init()
	{
		super.init();
		mTitle.setMiddleTextTop("附近优惠");
	}

	@Override
	protected HttpHandler<String> requestMapsearch()
	{
		stopRequest();

		RequestModel model = new RequestModel();
		model.putCtl("youhuis");
		putScreenLatLng(model);
		SDRequestCallBack<Youhuis_indexActModel> handler = new SDRequestCallBack<Youhuis_indexActModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					addOverlays(actModel);
				}
			}
		};
		return InterfaceServer.getInstance().requestInterface(model, handler);
	}
}
