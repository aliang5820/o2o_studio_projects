package com.fanwe.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.adapter.ExchangeRedEnvelopeAdapter;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.BaseActModel;
import com.fanwe.model.PageModel;
import com.fanwe.model.RedEnvelopeModel;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Uc_ecv_exchangeModel;
import com.fanwe.o2o.newo2o.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

/**
 * 兑换红包
 * 
 * @author Administrator
 * 
 */
public class ExchangeRedEnvelopeFragment extends BaseFragment
{

	@ViewInject(R.id.et_red_envelope_code)
	private EditText mEt_code;

	@ViewInject(R.id.tv_score)
	private TextView mTv_score;

	@ViewInject(R.id.btn_get_red_envelope)
	private TextView mBtn_get_red_envelope;

	@ViewInject(R.id.ptrlv_content)
	private PullToRefreshListView mPtrlv_content;

	private ExchangeRedEnvelopeAdapter mAdapter;
	private List<RedEnvelopeModel> mListModel = new ArrayList<RedEnvelopeModel>();

	private PageModel mPage = new PageModel();

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return setContentView(R.layout.frag_exchange_red_envelope);
	}

	@Override
	protected void init()
	{
		initView();
		bindDefaultData();
		initPullToRefreshListView();
	}

	private void initView()
	{
		mEt_code.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				String content = s.toString();
				if (isEmpty(content))
				{
					SDViewUtil.invisible(mBtn_get_red_envelope);
				} else
				{
					SDViewUtil.show(mBtn_get_red_envelope);
				}
			}
		});

		mBtn_get_red_envelope.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				requestGetRedEnvelope();
			}
		});
	}

	protected void requestGetRedEnvelope()
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_ecv");
		model.putAct("do_snexchange");
		model.put("sn", mEt_code.getText().toString());
		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BaseActModel>()
		{
			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					SDEventManager.post(EnumEventTag.GET_RED_ENVELOPE_SUCCESS.ordinal());
					mEt_code.setText("");
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		});
	}

	private void bindDefaultData()
	{
		mAdapter = new ExchangeRedEnvelopeAdapter(mListModel, getActivity());
		mPtrlv_content.setAdapter(mAdapter);
	}

	private void initPullToRefreshListView()
	{
		mPtrlv_content.setMode(Mode.BOTH);
		mPtrlv_content.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				mPage.resetPage();
				requestData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mPage.increment())
				{
					requestData(true);
				} else
				{
					SDToast.showToast("没有更多数据了");
					mPtrlv_content.onRefreshComplete();
				}
			}
		});
		mPtrlv_content.setRefreshing();
	}

	protected void requestData(final boolean isLoadMore)
	{
		RequestModel model = new RequestModel();
		model.putCtl("uc_ecv");
		model.putAct("exchange");
		model.putPage(mPage);

		InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<Uc_ecv_exchangeModel>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() > 0)
				{
					SDViewBinder.setTextView(mTv_score, String.valueOf(actModel.getScore()));
					SDViewUtil.updateAdapterByList(mListModel, actModel.getData(), mAdapter, isLoadMore);
				}
			}

			@Override
			public void onFinish()
			{
				mPtrlv_content.onRefreshComplete();
				super.onFinish();
			}
		});
	}

	@Override
	public void onEventMainThread(SDBaseEvent event)
	{
		super.onEventMainThread(event);
		switch (EnumEventTag.valueOf(event.getTagInt()))
		{
		case EXCHANGE_RED_ENVELOPE_SUCCESS:
			requestData(false);
			break;

		default:
			break;
		}
	}
}
