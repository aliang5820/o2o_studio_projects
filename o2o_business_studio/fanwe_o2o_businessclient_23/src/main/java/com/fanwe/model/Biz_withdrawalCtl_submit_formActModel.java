package com.fanwe.model;


public class Biz_withdrawalCtl_submit_formActModel extends BaseCtlActModel
{
	private My_payModel supplier_info;
	private int sms_on;
	private String mobile;
	
	public int getSms_on()
	{
		return sms_on;
	}

	public void setSms_on(int sms_on)
	{
		this.sms_on = sms_on;
	}

	public My_payModel getSupplier_info()
	{
		return supplier_info;
	}

	public void setSupplier_info(My_payModel supplier_info)
	{
		this.supplier_info = supplier_info;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
}
