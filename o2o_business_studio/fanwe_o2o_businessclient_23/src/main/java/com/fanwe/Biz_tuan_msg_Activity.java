package com.fanwe;

import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.businessclient.R.color;
import com.fanwe.common.SDViewNavigatorManager;
import com.fanwe.common.SDViewNavigatorManager.SDViewNavigatorManagerListener;
import com.fanwe.customview.SDSimpleTabView;
import com.fanwe.fragment.Biz_tuan_msg_left_Fragment;
import com.fanwe.fragment.Biz_tuan_msg_right_Fragment;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.BizDealrCtlDealrDpListActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.fanwe.utils.SDTypeParseUtil;

/**
 * 
 * 评论详情
 */
public class Biz_tuan_msg_Activity extends TitleBaseActivity
{
	public static final String EXTRA_TYPE = "extra_type";
	public static final String EXTRA_ID = "extra_id";
	private static final int TEXT_SIZE_TAB = 16;

	private SDSimpleTabView mTabLeft, mTabRight;
	private SDViewNavigatorManager mNavigator = new SDViewNavigatorManager();

	private Biz_tuan_msg_left_Fragment mLeftFragment;
	private Biz_tuan_msg_right_Fragment mRightFragment;

	private TextView mTvStar5, mTvStar4, mTvStar3, mTvStar2, mTvStar1, mTvBuy_dp_avg, mTvBuy_dp_count;

	private ProgressBar mPbStar5, mPbStar4, mPbStar3, mPbStar2, mPbStar1;

	private RatingBar mRbStar;

	private String mDataID;

	private int mCurrentType;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		setContentView(R.layout.act_biz_tuan_msg);
		init();
	}

	private void init()
	{
		initIntentInfo();
		register();
		initTitle();
		initTabs();
		addFragments();
		requestCtlAct();
	}

	private void initIntentInfo()
	{
		mDataID = getIntent().getExtras().getString(Biz_tuan_msg_Activity.EXTRA_ID);
		mCurrentType = getIntent().getExtras().getInt(EXTRA_TYPE);
	}

	private void register()
	{
		mTabLeft = (SDSimpleTabView) findViewById(R.id.act_biztuanmsg_tabview_left);
		mTabRight = (SDSimpleTabView) findViewById(R.id.act_biztuanmsg_tabview_right);
		mTvStar5 = (TextView) findViewById(R.id.act_biztuanmsg_tv_start5);
		mTvStar4 = (TextView) findViewById(R.id.act_biztuanmsg_tv_start4);
		mTvStar3 = (TextView) findViewById(R.id.act_biztuanmsg_tv_start3);
		mTvStar2 = (TextView) findViewById(R.id.act_biztuanmsg_tv_start2);
		mTvStar1 = (TextView) findViewById(R.id.act_biztuanmsg_tv_start1);
		mPbStar5 = (ProgressBar) findViewById(R.id.act_biztuanmsg_pb_start5);
		mPbStar4 = (ProgressBar) findViewById(R.id.act_biztuanmsg_pb_start4);
		mPbStar3 = (ProgressBar) findViewById(R.id.act_biztuanmsg_pb_start3);
		mPbStar2 = (ProgressBar) findViewById(R.id.act_biztuanmsg_pb_start2);
		mPbStar1 = (ProgressBar) findViewById(R.id.act_biztuanmsg_pb_start1);
		mTvBuy_dp_avg = (TextView) findViewById(R.id.act_biztuanmsg_tv_buy_dp_avg);
		mTvBuy_dp_count = (TextView) findViewById(R.id.act_biztuanmsg_tv_buy_dp_count);
		mRbStar = (RatingBar) findViewById(R.id.act_biztuanmsg_rb_star);
	}

	private void initTitle()
	{
		SDViewBinder.setTextView(mTitle, "评论详情");
	}

	private void requestCtlAct()
	{
		RequestModel model = new RequestModel();
		if (mCurrentType == 0)
		{
			model.putCtlAct("biz_dealr", "dealr_dp_list");
		} else if (mCurrentType == 1)
		{
			model.putCtlAct("biz_youhuir", "youhuir_dp_list");
		} else if (mCurrentType == 2)
		{
			model.putCtlAct("biz_eventr", "eventr_dp_list");
		} else if (mCurrentType == 3)
		{
			model.putCtlAct("biz_storer", "storer_dp_list");
		}
		model.put("data_id", mDataID);
		model.put("page", 1);
		SDRequestCallBack<BizDealrCtlDealrDpListActModel> handler = new SDRequestCallBack<BizDealrCtlDealrDpListActModel>()
		{
			private Dialog nDialog;

			@Override
			public void onStart()
			{
				nDialog = SDDialogUtil.showLoading("加载中...");
			}

			@Override
			public void onFinish()
			{
				if (nDialog != null)
				{
					nDialog.dismiss();
				}
			}

			@Override
			public void onSuccess(BizDealrCtlDealrDpListActModel actModel)
			{
				if (!SDInterfaceUtil.dealactModel(actModel, Biz_tuan_msg_Activity.this))
				{
					switch (actModel.getStatus())
					{
					case 0:
						SDToast.showToast(actModel.getInfo());
						break;
					case 1:
						initViewInfo(actModel);
						break;
					}
				}
			}

		};

		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	private void initViewInfo(BizDealrCtlDealrDpListActModel mCtlActModel)
	{
		int star_5 = Integer.valueOf(mCtlActModel.getStar_5());
		int star_4 = Integer.valueOf(mCtlActModel.getStar_4());
		int star_3 = Integer.valueOf(mCtlActModel.getStar_3());
		int star_2 = Integer.valueOf(mCtlActModel.getStar_2());
		int star_1 = Integer.valueOf(mCtlActModel.getStar_1());
		int numCount = star_5 + star_4 + star_3 + star_2 + star_1;

		mTvBuy_dp_avg.setText(mCtlActModel.getBuy_dp_avg());
		mTvBuy_dp_count.setText(mCtlActModel.getBuy_dp_sum());
		mRbStar.setRating(SDTypeParseUtil.getFloatFromString(mCtlActModel.getBuy_dp_avg(), 0));
		mTvStar5.setText(mCtlActModel.getStar_5());
		mTvStar4.setText(mCtlActModel.getStar_4());
		mTvStar3.setText(mCtlActModel.getStar_3());
		mTvStar2.setText(mCtlActModel.getStar_2());
		mTvStar1.setText(mCtlActModel.getStar_1());

		if (numCount > 0)
		{
			mPbStar5.setMax(numCount);
			mPbStar5.setProgress(star_5);
			mPbStar4.setMax(numCount);
			mPbStar4.setProgress(star_4);
			mPbStar3.setMax(numCount);
			mPbStar3.setProgress(star_3);
			mPbStar2.setMax(numCount);
			mPbStar2.setProgress(star_2);
			mPbStar1.setMax(numCount);
			mPbStar1.setProgress(star_1);
		} else
		{
			mPbStar5.setProgress(0);
			mPbStar4.setProgress(0);
			mPbStar3.setProgress(0);
			mPbStar2.setProgress(0);
			mPbStar1.setProgress(0);
		}
	}

	private void addFragments()
	{
		mLeftFragment = new Biz_tuan_msg_left_Fragment();
		mRightFragment = new Biz_tuan_msg_right_Fragment();

		getSDFragmentManager().replace(R.id.act_biztuanmsgr_frame_left, mLeftFragment);
		getSDFragmentManager().replace(R.id.act_biztuanmsg_frame_right, mRightFragment);

		mNavigator.setSelectIndex(0, mTabLeft, true);
	}

	private void initTabs()
	{
		mTabLeft.setmBackgroundImageNormal(R.drawable.act_biz_tuan_msg_tab_normal);
		mTabLeft.setmBackgroundImageSelect(R.drawable.act_biz_tuan_msg_tab_selected);
		mTabLeft.setmTextColorNormal(getResources().getColor(color.gray0));
		mTabLeft.setmTextColorSelect(getResources().getColor(color.white));
		mTabLeft.setTabName("全部");
		mTabLeft.mTxtTabName.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_TAB);

		mTabRight.setmBackgroundImageNormal(R.drawable.act_biz_tuan_msg_tab_normal);
		mTabRight.setmBackgroundImageSelect(R.drawable.act_biz_tuan_msg_tab_selected);
		mTabRight.setmTextColorNormal(getResources().getColor(color.gray0));
		mTabRight.setmTextColorSelect(getResources().getColor(color.white));
		mTabRight.setTabName("差评");
		mTabRight.mTxtTabName.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_TAB);

		SDSimpleTabView[] items = new SDSimpleTabView[] { mTabLeft, mTabRight };

		mNavigator.setItems(items);
		mNavigator.setmListener(new SDViewNavigatorManagerListener()
		{

			@Override
			public void onItemClick(View v, int index)
			{
				switch (index)
				{
				case 0:
					getSDFragmentManager().show(mLeftFragment);
					getSDFragmentManager().hide(mRightFragment);
					break;
				case 1:
					getSDFragmentManager().show(mRightFragment);
					getSDFragmentManager().hide(mLeftFragment);
					break;
				default:
					break;
				}
			}
		});
	}

}
