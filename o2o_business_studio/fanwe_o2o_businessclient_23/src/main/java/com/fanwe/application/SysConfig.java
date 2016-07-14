package com.fanwe.application;

import com.fanwe.model.RequestModel.RequestDataType;
import com.fanwe.model.RequestModel.ResponseDataType;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-8 下午2:55:09 类说明 系统配置
 */
public class SysConfig
{	
	public static final int REQUEST_DATA_TYPE = RequestDataType.BASE64;
	public static final int RESPONSE_DATA_TYPE = ResponseDataType.JSON;
	public static final boolean REQUEST_CALLBACK_PROXY_ISDEBUG = false;
	private boolean isCheckUpdate = false;

	private static class SysCofigHolder
	{
		private static final SysConfig INSTANCE = new SysConfig();
	}

	private SysConfig()
	{
	}

	public static final SysConfig getInstance()
	{
		return SysCofigHolder.INSTANCE;
	}

	public boolean isCheckUpdate()
	{
		return isCheckUpdate;
	}

	public void setCheckUpdate(boolean isCheckUpdate)
	{
		this.isCheckUpdate = isCheckUpdate;
	}

}
