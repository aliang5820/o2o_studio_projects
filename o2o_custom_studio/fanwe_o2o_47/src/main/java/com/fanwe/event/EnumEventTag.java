package com.fanwe.event;

public enum EnumEventTag
{
	/** 退出app事件 */
	EXIT_APP,

	/** 登录成功事件 */
	LOGIN_SUCCESS,

	/** 退出登录事件 */
	LOGOUT,

	/** 购物车下单成功 */
	DONE_CART_SUCCESS,

	/** 添加购物车成功 */
	ADD_CART_SUCCESS,

	/** 删除购物商品车成功 */
	DELETE_CART_GOODS_SUCCESS,

	/** 购物车数量变化 */
	CART_NUMBER_CHANGE,

	/** 订单支付成功 */
	PAY_ORDER_SUCCESS,

	/** 刷新订单列表 */
	REFRESH_ORDER_LIST,

	/** 城市改变事件 */
	CITY_CHANGE,

	/** 定位成功事件 */
	LOCATION_SUCCESS,

	/** 评论成功 */
	COMMENT_SUCCESS,

	/** 用户配送地址改变（删除，修改等） */
	USER_DELIVERY_CHANGE,

	/** 临时登录状态 */
	TEMP_LOGIN,

	/** 未登录状态 */
	UN_LOGIN,

	/** 启动二维码扫描 */
	START_SCAN_QRCODE,

	/** 添加分销商品成功 */
	ADD_DISTRIBUTION_GOODS_SUCCESS,

	/** 删除分销商品成功 */
	DELETE_DISTRIBUTION_GOODS_SUCCESS,

	/** 上传头像成功 */
	UPLOAD_USER_HEAD_SUCCESS,

	/** 发表动态成功 */
	PUBLISH_DYNAMIC_SUCCESS,

	/** 兑换红包成功 */
	EXCHANGE_RED_ENVELOPE_SUCCESS,

	/** 领红包成功 */
	GET_RED_ENVELOPE_SUCCESS,

	/** 动态详情页面关闭 */
	DYNAMIC_DETAIL_CLOSED,

	/** 重试初始化成功 */
	RETRY_INIT_SUCCESS,

	/** 手机号设置成功 */
	BIND_MOBILE_SUCCESS,

	/** 验证码输入完成确认 */
	CONFIRM_IMAGE_CODE;

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
