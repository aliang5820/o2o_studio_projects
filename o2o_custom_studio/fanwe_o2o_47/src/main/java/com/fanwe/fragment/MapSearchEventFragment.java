package com.fanwe.fragment;

import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.model.Events_indexActModel;
import com.fanwe.model.RequestModel;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;

public class MapSearchEventFragment extends MapSearchFragment
{

	@Override
	protected void init()
	{
		super.init();
		mTitle.setMiddleTextTop("附近活动");
	}

	@Override
	protected HttpHandler<String> requestMapsearch()
	{
		stopRequest();

		RequestModel model = new RequestModel();
		model.putCtl("events");
		putScreenLatLng(model);
		SDRequestCallBack<Events_indexActModel> handler = new SDRequestCallBack<Events_indexActModel>()
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
