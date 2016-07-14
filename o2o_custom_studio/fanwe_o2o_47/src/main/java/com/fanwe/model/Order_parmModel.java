package com.fanwe.model;

import java.util.List;
import java.util.Map;

public class Order_parmModel
{
	private int has_delivery_time;
	private int has_ecv;
	private int has_moblie;
	private int has_invoice;
	private int has_message;
	private int has_delivery;

	private String select_payment_id;
	private String select_delivery_time_id;

	private List<Payment_listModel> payment_list;
	private List<Delivery_time_listModel> delivery_time_list;

	private List<Delivery_listModel> delivery_list;

	private List<Map<String, Object>> invoice_list;
	private int has_mcod;

	private String delivery_id;
	private String payment_id;

	public String getDelivery_id()
	{
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id)
	{
		this.delivery_id = delivery_id;
	}

	public String getPayment_id()
	{
		return payment_id;
	}

	public void setPayment_id(String payment_id)
	{
		this.payment_id = payment_id;
	}

	public List<Delivery_time_listModel> getDelivery_time_list()
	{
		return delivery_time_list;
	}

	public void setDelivery_time_list(List<Delivery_time_listModel> delivery_time_list)
	{
		this.delivery_time_list = delivery_time_list;
	}

	public List<Delivery_listModel> getDelivery_list()
	{
		return delivery_list;
	}

	public void setDelivery_list(List<Delivery_listModel> delivery_list)
	{
		this.delivery_list = delivery_list;
	}

	public List<Map<String, Object>> getInvoice_list()
	{
		return invoice_list;
	}

	public void setInvoice_list(List<Map<String, Object>> invoice_list)
	{
		this.invoice_list = invoice_list;
	}

	public int getHas_mcod()
	{
		return has_mcod;
	}

	public void setHas_mcod(int has_mcod)
	{
		this.has_mcod = has_mcod;
	}

	public int getHas_delivery_time()
	{
		return has_delivery_time;
	}

	public void setHas_delivery_time(int has_delivery_time)
	{
		this.has_delivery_time = has_delivery_time;
	}

	public int getHas_ecv()
	{
		return has_ecv;
	}

	public void setHas_ecv(int has_ecv)
	{
		this.has_ecv = has_ecv;
	}

	public int getHas_moblie()
	{
		return has_moblie;
	}

	public void setHas_moblie(int has_moblie)
	{
		this.has_moblie = has_moblie;
	}

	public int getHas_invoice()
	{
		return has_invoice;
	}

	public void setHas_invoice(int has_invoice)
	{
		this.has_invoice = has_invoice;
	}

	public int getHas_message()
	{
		return has_message;
	}

	public void setHas_message(int has_message)
	{
		this.has_message = has_message;
	}

	public int getHas_delivery()
	{
		return has_delivery;
	}

	public void setHas_delivery(int has_delivery)
	{
		this.has_delivery = has_delivery;
	}

	public String getSelect_payment_id()
	{
		return select_payment_id;
	}

	public void setSelect_payment_id(String select_payment_id)
	{
		this.select_payment_id = select_payment_id;
	}

	public String getSelect_delivery_time_id()
	{
		return select_delivery_time_id;
	}

	public void setSelect_delivery_time_id(String select_delivery_time_id)
	{
		this.select_delivery_time_id = select_delivery_time_id;
	}

	public List<Payment_listModel> getPayment_list()
	{
		return payment_list;
	}

	public void setPayment_list(List<Payment_listModel> payment_list)
	{
		this.payment_list = payment_list;
	}

}
