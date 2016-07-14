package com.fanwe.umeng;

import java.util.Map;

import android.content.Context;
import android.content.Intent;

import com.fanwe.AppWebViewActivity;
import com.fanwe.EventDetailActivity;
import com.fanwe.NoticeDetailActivity;
import com.fanwe.StoreDetailActivity;
import com.fanwe.TuanDetailActivity;
import com.fanwe.YouHuiDetailActivity;
import com.fanwe.app.App;
import com.fanwe.constant.Constant.PushType;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.model.JpushDataModel;
import com.fanwe.utils.JsonUtil;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * 友盟推送通知栏点击事件处理
 * 
 * @author Administrator
 * 
 */
public class AppUmengNotificationClickHandler extends UmengNotificationClickHandler
{
	@Override
	public void dealWithCustomAction(Context context, UMessage msg)
	{
		clickNotification(context, msg);
	}

	protected void clickNotification(Context context, UMessage msg)
	{
		JpushDataModel model = parseData2Model(context, msg);
		if (model == null)
		{
			super.launchApp(context, msg);
			return;
		}

		int type = SDTypeParseUtil.getInt(model.getType());
		int data = SDTypeParseUtil.getInt(model.getData());
		Intent intent = null;
		switch (type)
		{
		case PushType.NORMAL:

			break;
		case PushType.URL:
			intent = new Intent(App.getApplication(), AppWebViewActivity.class);
			intent.putExtra(AppWebViewActivity.EXTRA_URL, model.getData());
			break;
		case PushType.TUAN_DETAIL:
			intent = new Intent(App.getApplication(), TuanDetailActivity.class);
			intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, data);
			break;
		case PushType.GOODS_DETAIL:
			intent = new Intent(App.getApplication(), TuanDetailActivity.class);
			intent.putExtra(TuanDetailActivity.EXTRA_GOODS_ID, data);
			break;
		case PushType.EVENT_DETAIL:
			intent = new Intent(App.getApplication(), EventDetailActivity.class);
			intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, data);
			break;
		case PushType.YOUHUI_DETAIL:
			intent = new Intent(App.getApplication(), YouHuiDetailActivity.class);
			intent.putExtra(YouHuiDetailActivity.EXTRA_YOUHUI_ID, data);
			break;
		case PushType.STORE_DETAIL:
			intent = new Intent(App.getApplication(), StoreDetailActivity.class);
			intent.putExtra(StoreDetailActivity.EXTRA_MERCHANT_ID, data);
			break;
		case PushType.NOTICE_DETAIL:
			intent = new Intent(App.getApplication(), NoticeDetailActivity.class);
			intent.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, data);
			break;
		default:

			break;
		}

		if (intent != null)
		{
			if (SDActivityManager.getInstance().isEmpty())
			{
				App.getApplication().mPushIntent = intent;
				super.launchApp(context, msg);
			} else
			{
				SDActivityManager.getInstance().getLastActivity().startActivity(intent);
			}
		} else
		{
			super.launchApp(context, msg);
		}
	}

	private JpushDataModel parseData2Model(Context context, UMessage msg)
	{
		JpushDataModel model = null;
		if (msg != null)
		{
			Map<String, String> mapData = msg.extra;
			if (mapData != null)
			{
				model = JsonUtil.map2Object(mapData, JpushDataModel.class);
			}
		}
		return model;
	}

	@Override
	public void autoUpdate(Context context, UMessage msg)
	{
		super.autoUpdate(context, msg);
	}

	@Override
	public void dismissNotification(Context context, UMessage msg)
	{
		super.dismissNotification(context, msg);
	}

	@Override
	public void handleMessage(Context context, UMessage msg)
	{
		super.handleMessage(context, msg);
	}

	@Override
	public void launchApp(Context context, UMessage msg)
	{
		clickNotification(context, msg);
	}

	@Override
	public void openActivity(Context context, UMessage msg)
	{
		super.openActivity(context, msg);
	}

	@Override
	public void openUrl(Context context, UMessage msg)
	{
		super.openUrl(context, msg);
	}
}
