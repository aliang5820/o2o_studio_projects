package com.fanwe.model;

import java.util.List;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-7-2 上午10:40:48 类说明
 */
public class BizGoodSoCtlDeliveryFormActModel extends BaseCtlActModel
{
	private String express_name;
	private int rel_deal_id;
	private List<LocationModel> location_list;
	private List<ExpressModel> express_list;
	private AddressDataModel address_data;
	private List<DoiModel> doi_list;
	
	public List<DoiModel> getDoi_list()
	{
		return doi_list;
	}

	public void setDoi_list(List<DoiModel> doi_list)
	{
		this.doi_list = doi_list;
	}

	public List<LocationModel> getLocation_list()
	{
		return location_list;
	}

	public void setLocation_list(List<LocationModel> location_list)
	{
		this.location_list = location_list;
	}

	public List<ExpressModel> getExpress_list()
	{
		return express_list;
	}

	public void setExpress_list(List<ExpressModel> express_list)
	{
		this.express_list = express_list;
	}

	public AddressDataModel getAddress_data()
	{
		return address_data;
	}

	public void setAddress_data(AddressDataModel address_data)
	{
		this.address_data = address_data;
	}

	public int getRel_deal_id()
	{
		return rel_deal_id;
	}

	public void setRel_deal_id(int rel_deal_id)
	{
		this.rel_deal_id = rel_deal_id;
	}

	public String getExpress_name()
	{
		return express_name;
	}

	public void setExpress_name(String express_name)
	{
		this.express_name = express_name;
	}

}
