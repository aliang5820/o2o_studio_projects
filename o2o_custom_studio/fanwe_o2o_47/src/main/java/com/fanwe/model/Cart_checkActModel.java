package com.fanwe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.utils.SDFormatUtil;

public class Cart_checkActModel extends BaseActModel
{

	private Map<String, CartGroupGoodsModel> cart_list_group; // 商品数据.key:商家id,value:商品数据
	private CartTotal_dataModel total_data;
	private int is_score; // 0:普通商品，1:积分商品
	private int is_coupon; // 是否为发券订单，0否 1:是
	private int consignee_count; // 预设的配送地址数量 0：提示去设置收货地址 1以及以上显示选择其他收货方式
	private Consignee_infoModel consignee_info;
	private List<Delivery_listModel> delivery_list;
	private List<Payment_listModel> payment_list;

	private int is_delivery; // 是否需要配送 0无需 1需要
	private int show_payment; // 是否要显示支付方式 0:否（0元抽奖类） 1:是
	private int has_account; // 是否显示余额支付 0否 1是
	private int has_ecv; // 是否显示代金券支付 0否 1是
	private List<VoucherModel> voucher_list;
	private String account_money; // 余额
	private String order_memo; // 订单备注(用户继续支付时候返回)

	// add
	private List<CartGroupGoodsModel> listCartGroupsGoods;
	private String account_moneyFormat;
	private Cart_count_buy_totalModel calculateModel;

	public Map<String, CartGroupGoodsModel> getCart_list_group()
	{
		return cart_list_group;
	}

	public void setCart_list_group(Map<String, CartGroupGoodsModel> cart_list_group)
	{
		this.cart_list_group = cart_list_group;
		updateListCartGroupsGoods();
	}

	public void updateListCartGroupsGoods()
	{
		if (cart_list_group != null)
		{
			List<CartGroupGoodsModel> listModel = new ArrayList<CartGroupGoodsModel>();
			for (Entry<String, CartGroupGoodsModel> item : cart_list_group.entrySet())
			{
				CartGroupGoodsModel value = item.getValue();
				listModel.add(value);
			}
			setListCartGroupsGoods(listModel);
		}
	}

	public List<CartGroupGoodsModel> getListCartGroupsGoods()
	{
		return listCartGroupsGoods;
	}

	public void setListCartGroupsGoods(List<CartGroupGoodsModel> listCartGroupsGoods)
	{
		this.listCartGroupsGoods = listCartGroupsGoods;
	}

	public List<VoucherModel> getVoucher_list()
	{
		return voucher_list;
	}

	public void setVoucher_list(List<VoucherModel> voucher_list)
	{
		if (!SDCollectionUtil.isEmpty(voucher_list))
		{
			VoucherModel model = new VoucherModel();
			model.setSn("");
			model.setName("不使用红包");
			voucher_list.add(0, model);
		}
		this.voucher_list = voucher_list;
	}

	public String getOrder_memo()
	{
		return order_memo;
	}

	public void setOrder_memo(String order_memo)
	{
		this.order_memo = order_memo;
	}

	public String getAccount_moneyFormat()
	{
		return account_moneyFormat;
	}

	public void setAccount_moneyFormat(String account_moneyFormat)
	{
		this.account_moneyFormat = account_moneyFormat;
	}

	public String getAccount_money()
	{
		return account_money;
	}

	public void setAccount_money(String account_money)
	{
		this.account_money = account_money;
		this.account_moneyFormat = SDFormatUtil.formatMoneyChina(account_money);
	}

	public CartTotal_dataModel getTotal_data()
	{
		return total_data;
	}

	public void setTotal_data(CartTotal_dataModel total_data)
	{
		this.total_data = total_data;
	}

	public int getIs_score()
	{
		return is_score;
	}

	public void setIs_score(int is_score)
	{
		this.is_score = is_score;
	}

	public int getIs_coupon()
	{
		return is_coupon;
	}

	public void setIs_coupon(int is_coupon)
	{
		this.is_coupon = is_coupon;
	}

	public int getConsignee_count()
	{
		return consignee_count;
	}

	public void setConsignee_count(int consignee_count)
	{
		this.consignee_count = consignee_count;
	}

	public Consignee_infoModel getConsignee_info()
	{
		return consignee_info;
	}

	public void setConsignee_info(Consignee_infoModel consignee_info)
	{
		this.consignee_info = consignee_info;
	}

	public List<Delivery_listModel> getDelivery_list()
	{
		return delivery_list;
	}

	public void setDelivery_list(List<Delivery_listModel> delivery_list)
	{
		this.delivery_list = delivery_list;
	}

	public List<Payment_listModel> getPayment_list()
	{
		return payment_list;
	}

	public void setPayment_list(List<Payment_listModel> payment_list)
	{
		this.payment_list = payment_list;
	}

	public int getIs_delivery()
	{
		return is_delivery;
	}

	public void setIs_delivery(int is_delivery)
	{
		this.is_delivery = is_delivery;
	}

	public int getShow_payment()
	{
		return show_payment;
	}

	public void setShow_payment(int show_payment)
	{
		this.show_payment = show_payment;
	}

	public int getHas_account()
	{
		return has_account;
	}

	public void setHas_account(int has_account)
	{
		this.has_account = has_account;
	}

	public int getHas_ecv()
	{
		return has_ecv;
	}

	public void setHas_ecv(int has_ecv)
	{
		this.has_ecv = has_ecv;
	}

	public Cart_count_buy_totalModel getCalculateModel()
	{
		return calculateModel;
	}

	public void setCalculateModel(Cart_count_buy_totalModel model)
	{
		this.calculateModel = model;
		if (model != null)
		{
			updateDeliveryFee(model);
		}
	}

	/**
	 * 更新运费
	 * 
	 * @param model
	 */
	private void updateDeliveryFee(Cart_count_buy_totalModel model)
	{
		if (!SDCollectionUtil.isEmpty(listCartGroupsGoods)) // 商品数据不为空
		{
			if (model.getIs_pick() == 1)// 上门自提
			{
				for (CartGroupGoodsModel groupGoodsModel : listCartGroupsGoods)
				{
					groupGoodsModel.setDeliveryFeeInfo(null);
				}
			} else
			{
				Map<String, String> mapFee = model.getDelivery_fee_supplier();
				Delivery_infoModel delivery_infoModel = model.getDelivery_info();

				if (mapFee != null && !mapFee.isEmpty() && delivery_infoModel != null)
				{
					for (Entry<String, String> item : mapFee.entrySet())
					{
						String key = item.getKey();
						String value = item.getValue();
						double fee = SDTypeParseUtil.getDouble(value);

						CartGroupGoodsModel groupGoodsModel = cart_list_group.get(key);
						if (fee >= 0)
						{
							groupGoodsModel.setDeliveryFeeInfo("运费" + SDFormatUtil.formatMoneyChina(fee));
						} else
						{
							groupGoodsModel.setDeliveryFeeInfo("不支持" + delivery_infoModel.getName());
						}
					}
				} else
				{
					for (CartGroupGoodsModel groupGoodsModel : listCartGroupsGoods)
					{
						groupGoodsModel.setDeliveryFeeInfo(null);
					}
				}
			}
		}
	}

}
