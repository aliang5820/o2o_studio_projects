package com.fanwe.fragment;

import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Stores_indexActModel;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;

public class MapSearchStoreFragment extends MapSearchFragment
{

	@Override
	protected void init()
	{
		super.init();
		mTitle.setMiddleTextTop(SDResourcesUtil.getString(R.string.nearby_store));
	}

	@Override
	protected HttpHandler<String> requestMapsearch()
	{
		stopRequest();

		RequestModel model = new RequestModel();
		model.putCtl("stores");
		putScreenLatLng(model);
		SDRequestCallBack<Stores_indexActModel> handler = new SDRequestCallBack<Stores_indexActModel>()
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
