package com.fanwe.model;

import com.fanwe.utils.SDFormatUtil;

/**
 * 会员中心订单商品实体
 * 
 * @author Administrator
 * 
 */
public class Uc_orderGoodsModel
{

	public static final int EMPTY_STATE = -999;

	private int id; // 订单表中的商品ID
	private int deal_id; // 商品ID，用于跳到商品页
	private String deal_icon; // 图片
	private String name; // 名字
	private String sub_name; // 简短名字
	private String number; // 购买数量
	private String unit_price; // 单价
	private String total_price; // 总价
	private int dp_id; // ID大于0表示已点评
	private int consume_count; // 消费数 大于0表示可以点评
	private int delivery_status; // 0:未发货 1:已发货 5.无需发货
	private int is_arrival; // 0:未收货1:已收货2:没收到货(维权)
	private int is_refund; // 是否支持退款，由商品表同步而来，0不支持 1支持
	private int refund_status; // 0未退款 1退款中 2已退款 3退款被拒

	// add
	private String unit_priceFormat;
	private String total_priceFormat;
	private int order_status; // 订单状态
	private int pay_status;
	private int commentState = EMPTY_STATE;
	private int refundState = EMPTY_STATE;
	private int deliveryState = EMPTY_STATE;

	/**
	 * 
	 * @return true:实体商品，false:团购商品
	 */
	public boolean isShop()
	{
		boolean isShop = true;
		if (delivery_status == 5)
		{
			isShop = false;
		}
		return isShop;
	}

	public void setOrder_status(int order_status)
	{
		this.order_status = order_status;
	}

	public void setPay_status(int pay_status)
	{
		this.pay_status = pay_status;
	}

	/**
	 * 
	 * @return 0:不可点评，1:可以点评，2:已点评
	 */
	public int getCommentState()
	{
		if (commentState == EMPTY_STATE)
		{
			if (order_status == 1)
			{
				if (consume_count > 0) // 有消费
				{
					if (dp_id > 0)
					{
						commentState = 2;
					} else
					{
						commentState = 1;
					}
				}
			} else if (order_status == 0)
			{
				if (delivery_status != 5)
				{
					if (is_arrival == 1)
					{
						if (dp_id > 0)
						{
							commentState = 2;
						} else
						{
							commentState = 1;
						}
					}
				}
			}
		}
		return commentState;
	}

	/**
	 * 
	 * @return 0:可以退款，1:退款中，2:已退款，3:退款被拒
	 */
	public int getRefundState()
	{
		if (refundState == EMPTY_STATE)
		{
			if (delivery_status == 0)
			{
				if (pay_status == 2)
				{
					if (is_refund == 1)
					{
						if (refund_status == 0)
						{
							refundState = 0;
						} else if (refund_status == 1)
						{
							refundState = 1;
						} else if (refund_status == 2)
						{
							refundState = 2;
						} else if (refund_status == 3)
						{
							refundState = 3;
						}
					}
				}
			} else if (delivery_status == 5)
			{
				if (pay_status == 2)
				{
					if (is_refund == 1)
					{
						if (order_status == 0)
						{
							refundState = 0;
						} else if (order_status == 1)
						{
							if (refund_status == 1)
							{
								refundState = 1;
							} else if (refund_status == 2)
							{
								refundState = 2;
							} else if (refund_status == 3)
							{
								refundState = 3;
							}
						}
					}
				}
			}
		}
		return refundState;
	}

	/**
	 * 
	 * @return 0:未发货， 1:已发货，但是未收到货(查询物流操作,确认收货操作，没收到货操作)，2:已收货，3:维权中
	 */
	public int getDeliveryState()
	{
		if (deliveryState == EMPTY_STATE)
		{
			if (order_status == 0)
			{
				if (delivery_status != 5)
				{
					if (delivery_status == 0)
					{
						deliveryState = 0;
					} else if (delivery_status == 1)
					{
						if (is_arrival == 0)
						{
							deliveryState = 1;
						} else if (is_arrival == 1)
						{
							deliveryState = 2;
						} else if (is_arrival == 2)
						{
							deliveryState = 3;
						}
					}
				}
			}
		}
		return deliveryState;
	}

	public String getUnit_priceFormat()
	{
		return unit_priceFormat;
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

	public int getDeal_id()
	{
		return deal_id;
	}

	public void setDeal_id(int deal_id)
	{
		this.deal_id = deal_id;
	}

	public String getDeal_icon()
	{
		return deal_icon;
	}

	public void setDeal_icon(String deal_icon)
	{
		this.deal_icon = deal_icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getUnit_price()
	{
		return unit_price;
	}

	public void setUnit_price(String unit_price)
	{
		this.unit_price = unit_price;
		this.unit_priceFormat = SDFormatUtil.formatMoneyChina(unit_price);
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

	public int getDp_id()
	{
		return dp_id;
	}

	public void setDp_id(int dp_id)
	{
		this.dp_id = dp_id;
	}

	public int getConsume_count()
	{
		return consume_count;
	}

	public void setConsume_count(int consume_count)
	{
		this.consume_count = consume_count;
	}

	public int getDelivery_status()
	{
		return delivery_status;
	}

	public void setDelivery_status(int delivery_status)
	{
		this.delivery_status = delivery_status;
	}

	public int getIs_arrival()
	{
		return is_arrival;
	}

	public void setIs_arrival(int is_arrival)
	{
		this.is_arrival = is_arrival;
	}

	public int getIs_refund()
	{
		return is_refund;
	}

	public void setIs_refund(int is_refund)
	{
		this.is_refund = is_refund;
	}

	public int getRefund_status()
	{
		return refund_status;
	}

	public void setRefund_status(int refund_status)
	{
		this.refund_status = refund_status;
	}

}
