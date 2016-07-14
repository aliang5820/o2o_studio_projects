package com.fanwe.event;

public enum EnumEventTag
{
	/** 退出app事件 */
	EXIT_APP,

	/** 扫描二维码成功事件 */
	SCAN_SUCCESS,

	/** 登录成功事件 */
	LOGIN_SUCCESS,

	/** 验证码输入完成确认 */
	CONFIRM_IMAGE_CODE,
	
	/** 未登录状态 */
	UN_LOGIN;

	public static EnumEventTag valueOf(int index)
	{
		if (index >= 0 && index < values().length)
		{
			return values()[index];
		} else
		{
			return null;
		}
	}
}
