package com.fanwe;

import android.os.Bundle;

import com.fanwe.constant.Constant.TitleType;
import com.fanwe.fragment.ExchangeRedEnvelopeFragment;
import com.fanwe.fragment.MyRedEnvelopeFragment;
import com.fanwe.fragment.MyRedEnvelopeInvalidFragment;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.newo2o.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 会员中心-我的红包
 * 
 * @author Administrator
 * 
 */
public class MyRedEnvelopeActivity extends BaseActivity
{

	@ViewInject(R.id.tab_red_envelope)
	private SDTabCornerText mTab_red_envelope;

	@ViewInject(R.id.tab_invalid)
	private SDTabCornerText mTab_invalid;

	@ViewInject(R.id.tab_exchange)
	private SDTabCornerText mTab_exchange;

	private SDSelectViewManager<SDTabCornerText> mNavigatorManager = new SDSelectViewManager<SDTabCornerText>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_my_red_envelope);
		init();
	}

	private void init()
	{
		initTitle();
		initTabs();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("我的红包");

	}

	private void initTabs()
	{

		mTab_red_envelope.setTextTitle("我的红包");
		mTab_red_envelope.setTextSizeTitle(14);
		mTab_red_envelope.setPosition(TabPosition.FIRST);

		mTab_invalid.setTextTitle("已失效");
		mTab_invalid.setTextSizeTitle(14);
		mTab_invalid.setPosition(TabPosition.MIDDLE);

		mTab_exchange.setTextTitle("兑换红包");
		mTab_exchange.setTextSizeTitle(14);
		mTab_exchange.setPosition(TabPosition.LAST);

		mNavigatorManager.setItems(new SDTabCornerText[] { mTab_red_envelope, mTab_invalid, mTab_exchange });
		mNavigatorManager.setListener(new SDSelectManagerListener<SDTabCornerText>()
		{

			@Override
			public void onNormal(int index, SDTabCornerText item)
			{

			}

			@Override
			public void onSelected(int index, SDTabCornerText item)
			{
				switch (index)
				{
				case 0:
					clickRedEnvelope();
					break;
				case 1:
					clickInvalid();
					break;
				case 2:
					clickExchange();
					break;
				default:
					break;
				}
			}
		});
		mNavigatorManager.performClick(0);
	}

	/**
	 * 我的红包
	 */
	protected void clickRedEnvelope()
	{
		getSDFragmentManager().toggle(R.id.act_my_red_envelope_fl_content, null, MyRedEnvelopeFragment.class);
	}

	/**
	 * 已失效
	 */
	protected void clickInvalid()
	{
		getSDFragmentManager().toggle(R.id.act_my_red_envelope_fl_content, null, MyRedEnvelopeInvalidFragment.class);
	}

	/**
	 * 兑换红包
	 */
	protected void clickExchange()
	{
		getSDFragmentManager().toggle(R.id.act_my_red_envelope_fl_content, null, ExchangeRedEnvelopeFragment.class);
	}

}
