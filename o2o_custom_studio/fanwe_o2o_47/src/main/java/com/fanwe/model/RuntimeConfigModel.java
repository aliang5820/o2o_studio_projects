package com.fanwe.model;

import com.fanwe.app.App;
import com.fanwe.constant.Constant.LoadImageType;
import com.fanwe.dao.SettingModelDao;
import com.fanwe.library.utils.SDViewBinder;
import com.ta.util.netstate.TANetWorkUtil;

/**
 * 用于保存app运行期间的一些配置信息
 * 
 * @author js02
 * 
 */
public class RuntimeConfigModel
{

	/** MainActivity是否已经被启动 */
	private boolean isMainActivityStarted = false;
	/** 是否可以推送 */
	private boolean isCanPushMessage = true;
	/** 是否可以加载图片 */
	private boolean isCanLoadImage = true;

	// -------------update

	public void updateIsCanLoadImage()
	{
		switch (SettingModelDao.getLoadImageType())
		{
		case LoadImageType.ONLY_WIFI:
			switch (TANetWorkUtil.getAPNType(App.getApplication()))
			{
			case wifi:
				setCanLoadImage(true);
				break;
			default:
				setCanLoadImage(false);
				break;
			}
			break;
		case LoadImageType.ALL:
			setCanLoadImage(true);
			break;

		default:
			break;
		}
		SDViewBinder.mCanLoadImageFromUrl = isCanLoadImage();
	}

	public void updateIsCanPushMessage()
	{
		switch (SettingModelDao.canPushMessage())
		{
		case 0:
			setCanPushMessage(false);
			break;
		case 1:
			setCanPushMessage(true);
			break;

		default:
			break;
		}
	}

	// -------------getter setter

	public boolean isCanPushMessage()
	{
		return isCanPushMessage;
	}

	public void setCanPushMessage(boolean isCanPushMessage)
	{
		this.isCanPushMessage = isCanPushMessage;
	}

	public boolean isCanLoadImage()
	{
		return isCanLoadImage;
	}

	public void setCanLoadImage(boolean isCanLoadImage)
	{
		this.isCanLoadImage = isCanLoadImage;
	}

	public boolean isMainActivityStarted()
	{
		return isMainActivityStarted;
	}

	public void setMainActivityStarted(boolean isMainActivityStarted)
	{
		this.isMainActivityStarted = isMainActivityStarted;
	}

}
