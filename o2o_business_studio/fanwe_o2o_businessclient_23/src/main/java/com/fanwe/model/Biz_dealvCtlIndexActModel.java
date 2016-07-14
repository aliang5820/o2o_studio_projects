package com.fanwe.model;

import java.util.ArrayList;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-5-11 下午2:33:40 类说明
 */
public class Biz_dealvCtlIndexActModel extends BaseCtlActModel
{
	private ArrayList<LocationModel> location_list;

	private int is_auth;

	public ArrayList<LocationModel> getLocation_list()
	{
		return location_list;
	}

	public void setLocation_list(ArrayList<LocationModel> location_list)
	{
		this.location_list = location_list;
	}

	public int getIs_auth()
	{
		return is_auth;
	}

	public void setIs_auth(int is_auth)
	{
		this.is_auth = is_auth;
	}

}
