package com.fanwe.model;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-8-31 上午10:41:45 类说明
 */
public class Biz_withdrawal_bindbank_Model extends BaseCtlActModel
{
	private String mobile;
	private int sms_on;

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public int getSms_on()
	{
		return sms_on;
	}

	public void setSms_on(int sms_on)
	{
		this.sms_on = sms_on;
	}

}
