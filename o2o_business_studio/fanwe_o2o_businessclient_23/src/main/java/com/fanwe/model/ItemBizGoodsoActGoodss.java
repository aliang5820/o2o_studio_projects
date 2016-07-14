package com.fanwe.model;

import com.fanwe.businessclient.R;

/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-9 上午11:08:09 类说明
 * 
 *          1.状态的显示 【完成的情况】order_status=1(订单已经完结) 或 delivery_status=5(无需配送) 或者
 *          delivery_status=1 && is_arrival=1(已发货且会员已经收到货) 【未完成】order_status!=1
 *          (订单未完结) 且不存上上面的状态 2.状态文字的显示 判断order_status =1 已经完结 ， 否则
 *          判断delivery_status = 0 （未发货） 在判断 delivery_status=1 已发货{ is_arrival=1
 *          (已收货) is_arrival=2(维权中) 判断时间 $data.nowtime -
 *          $row.delivery_notice.delivery_time >
 *          3600*24*$data.ORDER_DELIVERY_EXPIRE} (超期收货) } 3.退款状态显示 refund_status
 *          退款状态 1退款中 2已退款
 */
public class ItemBizGoodsoActGoodss
{
	private int id;
	private String icon;
	private String order_sn;
	private String create_time;
	private String sub_name;
	private String user_name;
	private String number;
	private String total_price;
	private String balance_total_price;
	private String s_total_price;
	private int order_status;
	private int delivery_status;
	private int is_arrival;
	private int refund_status;
	private String consignee;
	private String mobile;
	private String address_str;
	private DeliveryNoticeModel delivery_notice;

	public int getStatus1Color()
	{
		int colorID = R.drawable.layer_red_corner_normal;
		if (order_status == 1)
		{
			colorID = R.drawable.layer_green_corner;
		} else
		{
			if (delivery_status == 5)
			{
				colorID = R.color.green;
			} else if (delivery_status == 1 && is_arrival == 1)
			{
				colorID = R.drawable.layer_green_corner;
			} else
			{
				colorID = R.drawable.layer_red_corner_normal;
			}
		}
		return colorID;
	}

	public String getStatusText1()
	{
		String str = "";
		if (order_status == 1)
		{
			str = "已完结";
		} else
		{
			if (delivery_status == 5)
			{
				str = "已成功";
			} else if (delivery_status == 1 && is_arrival == 1)
			{
				str = "已成功";
			} else
			{
				str = "未完成";
			}
		}
		return str;

	}

	public String getStatusText2(int now_time, int delivery_time, int order_delivery_expire)
	{
		String str = "";
		if (order_status == 1)
		{
			str = "已完结";
		} else
		{
			if (delivery_status == 0)
			{
				str = "未发货";
			} else if (delivery_status == 1)
			{
				str = "已发货";
				if (is_arrival == 1)
				{
					str = str + "(已收货)";
				} else if (is_arrival == 2)
				{
					str = str + "(维权中)";
				} else if ((now_time - delivery_time) > order_delivery_expire * 3600 * 24)
				{
					str = str + "(超期收货)";
				}
			}
		}
		return str;

	}

	public String getStatusText3()
	{
		String str = "";
		if (refund_status == 1)
		{
			str = "[退款中]";
			return str;
		} else if (refund_status == 2)
		{
			str = "[已退款]";
			return str;
		}
		return str;

	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getConsignee()
	{
		return consignee;
	}

	public void setConsignee(String consignee)
	{
		this.consignee = consignee;
	}

	public String getAddress_str()
	{
		return address_str;
	}

	public void setAddress_str(String address_str)
	{
		this.address_str = address_str;
	}

	public String getS_total_price()
	{
		return s_total_price;
	}

	public void setS_total_price(String s_total_price)
	{
		this.s_total_price = s_total_price;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getBalance_total_price()
	{
		return balance_total_price;
	}

	public void setBalance_total_price(String balance_total_price)
	{
		this.balance_total_price = balance_total_price;
	}

	public int getIs_arrival()
	{
		return is_arrival;
	}

	public void setIs_arrival(int is_arrival)
	{
		this.is_arrival = is_arrival;
	}

	public int getRefund_status()
	{
		return refund_status;
	}

	public void setRefund_status(int refund_status)
	{
		this.refund_status = refund_status;
	}

	public DeliveryNoticeModel getDelivery_notice()
	{
		return delivery_notice;
	}

	public void setDelivery_notice(DeliveryNoticeModel delivery_notice)
	{
		this.delivery_notice = delivery_notice;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getOrder_sn()
	{
		return order_sn;
	}

	public void setOrder_sn(String order_sn)
	{
		this.order_sn = order_sn;
	}

	public String getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(String create_time)
	{
		this.create_time = create_time;
	}

	public String getSub_name()
	{
		return sub_name;
	}

	public void setSub_name(String sub_name)
	{
		this.sub_name = sub_name;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getTotal_price()
	{
		return total_price;
	}

	public void setTotal_price(String total_price)
	{
		this.total_price = total_price;
	}

	public int getOrder_status()
	{
		return order_status;
	}

	public void setOrder_status(int order_status)
	{
		this.order_status = order_status;
	}

	public int getDelivery_status()
	{
		return delivery_status;
	}

	public void setDelivery_status(int delivery_status)
	{
		this.delivery_status = delivery_status;
	}

}
