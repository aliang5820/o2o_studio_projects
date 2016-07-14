package com.fanwe.model;

import java.util.List;

import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.utils.SDFormatUtil;

/**
 * 会员中心订单实体
 * 
 * @author Administrator
 * 
 */
public class Uc_orderModel
{
	private int id;
	private String order_sn;
	private int order_status; // 订单状态 0:未结单 1:结单(将出现删除订单按钮)
	private int pay_status; // 支付状态 0:未支付(出现取消订单按钮) 1:已支付
	private String create_time; // 下单时间
	private String pay_amount; // 已付金额
	private String total_price; // 应付金额
	private int c; // 商品总量
	private List<Uc_orderGoodsModel> deal_order_item;
	private String status; // 未支付 string 订单状态

	// add
	private String total_priceFormat;
	private String pay_amountFormat;

	public boolean hasCancelButton()
	{
		boolean has = false;
		if (order_status == 1)
		{
			has = true;
		} else
		{
			if (pay_status == 0)
			{
				has = true;
			}
		}
		return has;
	}

	public String getCancelButtonText()
	{
		String text = null;
		if (order_status == 1)
		{
			text = "删除订单";
		} else
		{
			if (pay_status == 0)
			{
				text = "取消订单";
			}
		}
		return text;
	}

	public String getPay_amountFormat()
	{
		return pay_amountFormat;
	}

	public void setPay_amountFormat(String pay_amountFormat)
	{
		this.pay_amountFormat = pay_amountFormat;
	}

	public String getTotal_priceFormat()
	{
		return total_priceFormat;
	}

	public void setTotal_priceFormat(String total_priceFormat)
	{
		this.total_priceFormat = total_priceFormat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOrder_sn()
	{
		return order_sn;
	}

	public void setOrder_sn(String order_sn)
	{
		this.order_sn = order_sn;
	}

	public int getOrder_status()
	{
		return order_status;
	}

	public void setOrder_status(int order_status)
	{
		this.order_status = order_status;
		updateGoodsStatus();
	}

	public int getPay_status()
	{
		return pay_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
		updateGoodsStatus();
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public String getPay_amount()
	{
		return pay_amount;
	}

	public void setPay_amount(String pay_amount)
	{
		this.pay_amount = pay_amount;
		double payAmount = SDTypeParseUtil.getDouble(pay_amount);
		if (payAmount > 0)
		{
			this.pay_amountFormat = ",已支付" + SDFormatUtil.formatMoneyChina(pay_amount);
		}
	}

	public String getTotal_price()
	{
		return total_price;
	}

	public void setTotal_price(String total_price)
	{
		this.total_price = total_price;
		this.total_priceFormat = SDFormatUtil.formatMoneyChina(total_price);
	}

	public int getC()
	{
		return c;
	}

	public void setC(int c)
	{
		this.c = c;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<Uc_orderGoodsModel> getDeal_order_item()
	{
		return deal_order_item;
	}

	public void setDeal_order_item(List<Uc_orderGoodsModel> deal_order_item)
	{
		this.deal_order_item = deal_order_item;
		updateGoodsStatus();
	}

	private void updateGoodsStatus()
	{
		if (!SDCollectionUtil.isEmpty(deal_order_item))
		{
			for (Uc_orderGoodsModel model : deal_order_item)
			{
				model.setOrder_status(order_status);
				model.setPay_status(pay_status);
			}
		}
	}

}
