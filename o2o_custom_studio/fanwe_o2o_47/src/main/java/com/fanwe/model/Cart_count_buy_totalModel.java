package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fanwe.library.utils.SDTypeParseUtil;

public class Cart_count_buy_totalModel extends BaseActModel
{

	private String pay_price; // 当前要付的余额，如为0表示不需要使用在线支付，则支付方式不让选中
	private List<FeeinfoModel> feeinfo;
	private Map<String, String> delivery_fee_supplier;
	private Delivery_infoModel delivery_info;
	private int is_pick; // 1:上门自提

	public Delivery_infoModel getDelivery_info()
	{
		return delivery_info;
	}

	public void setDelivery_info(Delivery_infoModel delivery_info)
	{
		this.delivery_info = delivery_info;
	}

	public Map<String, String> getDelivery_fee_supplier()
	{
		return delivery_fee_supplier;
	}

	public void setDelivery_fee_supplier(Map<String, String> delivery_fee_supplier)
	{
		this.delivery_fee_supplier = delivery_fee_supplier;
		if (delivery_fee_supplier != null)
		{
			List<SupplierDeliveryFeeModel> listModel = new ArrayList<SupplierDeliveryFeeModel>();
			for (Entry<String, String> item : delivery_fee_supplier.entrySet())
			{
				SupplierDeliveryFeeModel model = new SupplierDeliveryFeeModel();
				model.setId(SDTypeParseUtil.getInt(item.getKey()));
				model.setFee(SDTypeParseUtil.getInt(item.getValue(), -1));
				listModel.add(model);
			}
		}
	}

	public String getPay_price()
	{
		return pay_price;
	}

	public void setPay_price(String pay_price)
	{
		this.pay_price = pay_price;
	}

	public List<FeeinfoModel> getFeeinfo()
	{
		return feeinfo;
	}

	public void setFeeinfo(List<FeeinfoModel> feeinfo)
	{
		this.feeinfo = feeinfo;
	}

	public int getIs_pick()
	{
		return is_pick;
	}

	public void setIs_pick(int is_pick)
	{
		this.is_pick = is_pick;
	}

}
