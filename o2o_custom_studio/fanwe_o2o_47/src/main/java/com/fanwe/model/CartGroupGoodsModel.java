package com.fanwe.model;

import java.util.List;

public class CartGroupGoodsModel
{

	private int id; // 商家id
	private String supplier; // 商家名字
	private List<CartGoodsModel> goods_list;
	private String deliveryFeeInfo; // 运费描述

	public String getDeliveryFeeInfo()
	{
		return deliveryFeeInfo;
	}

	public void setDeliveryFeeInfo(String deliveryFeeInfo)
	{
		this.deliveryFeeInfo = deliveryFeeInfo;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSupplier()
	{
		return supplier;
	}

	public void setSupplier(String supplier)
	{
		this.supplier = supplier;
	}

	public List<CartGoodsModel> getGoods_list()
	{
		return goods_list;
	}

	public void setGoods_list(List<CartGoodsModel> goods_list)
	{
		this.goods_list = goods_list;
	}

}
