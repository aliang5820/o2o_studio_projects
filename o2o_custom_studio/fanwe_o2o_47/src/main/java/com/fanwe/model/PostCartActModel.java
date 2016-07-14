package com.fanwe.model;

public class PostCartActModel extends BaseActModel
{
	private String is_binding;
	private String user_id;
	private String mobile;
	private String order_has_bind_mobile;

	public String getIs_binding()
	{
		return is_binding;
	}

	public void setIs_binding(String is_binding)
	{
		this.is_binding = is_binding;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getOrder_has_bind_mobile()
	{
		return order_has_bind_mobile;
	}

	public void setOrder_has_bind_mobile(String order_has_bind_mobile)
	{
		this.order_has_bind_mobile = order_has_bind_mobile;
	}

}
