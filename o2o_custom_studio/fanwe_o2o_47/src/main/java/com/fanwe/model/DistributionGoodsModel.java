package com.fanwe.model;

import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.SDFormatUtil;

public class DistributionGoodsModel
{
	private int id;
	private String name;
	private String sub_name;
	private String icon;
	private String icon_157;
	private String icon_85;
	private String current_price; // 当前价格
	private String origin_price;
	private int sale_count; // 销量
	private String sale_total; // 总销售额
	private String sale_balance; // 分销商品获得的总佣金
	private String fx_salary; // 分销佣金比率或者金额
	private int fx_salary_type; // 分销佣金的类型 0金额 1比率
	private String fx_salary_money; // 分销商品可以获得的佣金
	private String url;
	private String share_url;
	private int ud_is_effect; // 是否上架 1上架，0下架
	private int ud_type; // 分销商品类型 0用户领取，1为系统分配
	private int end_status; // 0过期，1进行中，2预告

	// add
	private String sale_balanceFormat;
	private String sale_totalFormat;
	private String current_priceFormat;
	private String origin_priceFormat;
	private String fx_salary_moneyFormat;
	private String end_statusFormat;

	private String cancelText;
	private int cancelTextColor;
	private int cancelBackground;

	private String shareText;
	private int shareTextColor;
	private int shareBackground;

	public String getIcon_157()
	{
		return icon_157;
	}

	public void setIcon_157(String icon_157)
	{
		this.icon_157 = icon_157;
	}

	public String getIcon_85()
	{
		return icon_85;
	}

	public void setIcon_85(String icon_85)
	{
		this.icon_85 = icon_85;
	}

	public String getOrigin_price()
	{
		return origin_price;
	}

	public void setOrigin_price(String origin_price)
	{
		this.origin_price = origin_price;
		this.origin_priceFormat = SDFormatUtil.formatMoneyChina(origin_price);
	}

	public String getOrigin_priceFormat()
	{
		return origin_priceFormat;
	}

	public void setOrigin_priceFormat(String origin_priceFormat)
	{
		this.origin_priceFormat = origin_priceFormat;
	}

	public String getSale_totalFormat()
	{
		return sale_totalFormat;
	}

	public void setSale_totalFormat(String sale_totalFormat)
	{
		this.sale_totalFormat = sale_totalFormat;
	}

	public int getSale_count()
	{
		return sale_count;
	}

	public void setSale_count(int sale_count)
	{
		this.sale_count = sale_count;
	}

	public String getSale_total()
	{
		return sale_total;
	}

	public void setSale_total(String sale_total)
	{
		this.sale_total = sale_total;
		this.sale_totalFormat = SDFormatUtil.formatMoneyChina(sale_total);
	}

	public String getSale_balance()
	{
		return sale_balance;
	}

	public void setSale_balance(String sale_balance)
	{
		this.sale_balance = sale_balance;
		this.sale_balanceFormat = SDFormatUtil.formatMoneyChina(sale_balance);
	}

	public String getSale_balanceFormat()
	{
		return sale_balanceFormat;
	}

	public void setSale_balanceFormat(String sale_balanceFormat)
	{
		this.sale_balanceFormat = sale_balanceFormat;
	}

	public void toggleIsEffect()
	{
		if (ud_is_effect == 1)
		{
			setUd_is_effect(0);
		} else if (ud_is_effect == 0)
		{
			setUd_is_effect(1);
		}
	}

	public String getCancelText()
	{
		return cancelText;
	}

	public void setCancelText(String cancelText)
	{
		this.cancelText = cancelText;
	}

	public int getCancelTextColor()
	{
		return cancelTextColor;
	}

	public void setCancelTextColor(int cancelTextColor)
	{
		this.cancelTextColor = cancelTextColor;
	}

	public int getCancelBackground()
	{
		return cancelBackground;
	}

	public void setCancelBackground(int cancelBackground)
	{
		this.cancelBackground = cancelBackground;
	}

	public String getShareText()
	{
		return shareText;
	}

	public void setShareText(String shareText)
	{
		this.shareText = shareText;
	}

	public int getShareTextColor()
	{
		return shareTextColor;
	}

	public void setShareTextColor(int shareTextColor)
	{
		this.shareTextColor = shareTextColor;
	}

	public int getShareBackground()
	{
		return shareBackground;
	}

	public void setShareBackground(int shareBackground)
	{
		this.shareBackground = shareBackground;
	}

	public String getEnd_statusFormat()
	{
		return end_statusFormat;
	}

	public void setEnd_statusFormat(String end_statusFormat)
	{
		this.end_statusFormat = end_statusFormat;
	}

	public String getFx_salary_moneyFormat()
	{
		return fx_salary_moneyFormat;
	}

	public void setFx_salary_moneyFormat(String fx_salary_moneyFormat)
	{
		this.fx_salary_moneyFormat = fx_salary_moneyFormat;
	}

	public String getCurrent_priceFormat()
	{
		return current_priceFormat;
	}

	public void setCurrent_priceFormat(String current_priceFormat)
	{
		this.current_priceFormat = current_priceFormat;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getCurrent_price()
	{
		return current_price;
	}

	public void setCurrent_price(String current_price)
	{
		this.current_price = current_price;
		this.current_priceFormat = SDFormatUtil.formatMoneyChina(current_price);
	}

	public String getFx_salary()
	{
		return fx_salary;
	}

	public void setFx_salary(String fx_salary)
	{
		this.fx_salary = fx_salary;
	}

	public int getFx_salary_type()
	{
		return fx_salary_type;
	}

	public void setFx_salary_type(int fx_salary_type)
	{
		this.fx_salary_type = fx_salary_type;
	}

	public String getFx_salary_money()
	{
		return fx_salary_money;
	}

	public void setFx_salary_money(String fx_salary_money)
	{
		this.fx_salary_money = fx_salary_money;
		this.fx_salary_moneyFormat = SDFormatUtil.formatMoneyChina(fx_salary_money);
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getShare_url()
	{
		return share_url;
	}

	public void setShare_url(String share_url)
	{
		this.share_url = share_url;
	}

	public int getUd_is_effect()
	{
		return ud_is_effect;
	}

	public void setUd_is_effect(int ud_is_effect)
	{
		this.ud_is_effect = ud_is_effect;
		updateButton();
	}

	private void updateButton()
	{
		switch (ud_is_effect)
		{
		case 0:
			this.shareText = "删除";
			this.shareTextColor = R.color.gray;
			this.shareBackground = R.drawable.layer_gray_stroke_corner_item_single;

			this.cancelText = "重新上架";
			this.cancelTextColor = R.color.white;
			this.cancelBackground = R.drawable.layer_re_shelves;
			break;
		case 1:
			this.shareText = "分享";
			this.shareTextColor = R.color.white;
			this.shareBackground = R.drawable.layer_main_color_corner_normal;

			this.cancelText = "取消分销";
			this.cancelTextColor = R.color.gray;
			this.cancelBackground = R.drawable.layer_gray_stroke_corner_item_single;
			break;

		default:
			break;
		}

		if (ud_type == 1)
		{
			this.cancelText = "系统分配";
			this.cancelTextColor = R.color.gray;
			this.cancelBackground = 0;
		}
	}

	public int getUd_type()
	{
		return ud_type;
	}

	public void setUd_type(int ud_type)
	{
		this.ud_type = ud_type;
		updateButton();
	}

	public int getEnd_status()
	{
		return end_status;
	}

	public void setEnd_status(int end_status)
	{
		this.end_status = end_status;
		switch (end_status)
		{
		case 0: // 已过期
			this.end_statusFormat = "已过期";
			break;
		case 1:
			this.end_statusFormat = "";
			break;
		case 2: // 预告中
			this.end_statusFormat = "预告中";
			break;

		default:
			break;
		}
	}

}
