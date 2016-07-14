package com.fanwe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.fanwe.config.AppConfig;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.dao.Region_confModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.adapter.SDSimpleTextAdapter;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.model.Consignee_infoModel;
import com.fanwe.model.Delivery_region_indexActModel;
import com.fanwe.model.Region_confModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

/**
 * 添加配送地址
 * 
 * @author js02
 * 
 */
public class AddDeliveryAddressActivity extends BaseActivity
{
	public static final String EXTRA_DELIVERY_MODEL = "extra_delivery_model";

	@ViewInject(R.id.act_add_delivery_address_et_consignee)
	private ClearEditText mEtConsignee;

	@ViewInject(R.id.act_add_delivery_address_et_phone)
	private ClearEditText mEtPhone;

	@ViewInject(R.id.act_add_delivery_address_tv_delivery)
	private TextView mTvDelivery;

	@ViewInject(R.id.act_add_delivery_address_et_address_detail)
	private ClearEditText mEtAddressDetail;

	@ViewInject(R.id.act_add_delivery_address_et_postcode)
	private ClearEditText mEtPostcode;

	@ViewInject(R.id.act_add_delivery_address_tv_submit)
	private TextView mTvSubmit;

	private Consignee_infoModel mModel;

	private List<Region_confModel> mListRegionCountry = new ArrayList<Region_confModel>(); // 国家
	private List<Region_confModel> mListRegionProvince = new ArrayList<Region_confModel>(); // 省
	private List<Region_confModel> mListRegionCity = new ArrayList<Region_confModel>(); // 市
	private List<Region_confModel> mListRegionEarn = new ArrayList<Region_confModel>(); // 区域

	private TextView mTvCountry;
	private TextView mTvProvince;
	private TextView mTvCity;
	private TextView mTvEarn;

	private SDSimpleTextAdapter<Region_confModel> mAdapterCountry;
	private SDSimpleTextAdapter<Region_confModel> mAdapterProvince;
	private SDSimpleTextAdapter<Region_confModel> mAdapterCity;
	private SDSimpleTextAdapter<Region_confModel> mAdapterEarn;

	private int mSelectIndexCountry, mSelectIndexProvince, mSelectIndexCity, mSelectIndexArea;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setmTitleType(TitleType.TITLE);
		setContentView(R.layout.act_add_delivery_address);
		init();
	}

	private void init()
	{
		getIntentData();
		initTitle();
		registeClick();
		initDefaultData();
		bindData();
	}

	private void initDefaultData()
	{
		mAdapterCountry = new SDSimpleTextAdapter<Region_confModel>(mListRegionCountry, AddDeliveryAddressActivity.this);
		mAdapterProvince = new SDSimpleTextAdapter<Region_confModel>(mListRegionProvince, AddDeliveryAddressActivity.this);
		mAdapterCity = new SDSimpleTextAdapter<Region_confModel>(mListRegionCity, AddDeliveryAddressActivity.this);
		mAdapterEarn = new SDSimpleTextAdapter<Region_confModel>(mListRegionEarn, AddDeliveryAddressActivity.this);
	}

	private void bindData()
	{
		if (mModel != null)
		{
			SDViewBinder.setTextView(mEtConsignee, mModel.getConsignee());
			SDViewBinder.setTextView(mEtPhone, mModel.getMobile());
			SDViewBinder.setTextView(mTvDelivery, mModel.getAddressRegion());
			SDViewBinder.setTextView(mEtAddressDetail, mModel.getAddress());
			SDViewBinder.setTextView(mEtPostcode, mModel.getZip());
		}
	}

	private void getIntentData()
	{
		mModel = (Consignee_infoModel) getIntent().getSerializableExtra(EXTRA_DELIVERY_MODEL);
	}

	private void initTitle()
	{
		String title = null;
		String textRight = null;
		if (mModel != null)
		{
			title = "编辑配送地址";
			textRight = "删除";
		} else
		{
			title = "创建配送地址";
		}

		mTitle.setMiddleTextTop(title);

		mTitle.initRightItem(1);
		mTitle.getItemRight(0).setTextBot(textRight);
	}

	@Override
	public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
	{
		clickDeleteAddress();
	}

	/**
	 * 删除地址
	 */
	protected void clickDeleteAddress()
	{
		if (mModel != null)
		{
			if (!AppRuntimeWorker.isLogin())
			{
				return;
			}

			// TODO 开始删除配送地址
			RequestModel model = new RequestModel();
			model.putCtl("uc_address");
			model.putAct("del");
			model.putUser();
			model.put("id", mModel.getId());
			SDRequestCallBack<JSONObject> handler = new SDRequestCallBack<JSONObject>()
			{

				@Override
				public void onStart()
				{
					SDDialogManager.showProgressDialog("请稍候...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					int status = actModel.getIntValue("del_status");
					if (status == 1)
					{
						SDEventManager.post(EnumEventTag.USER_DELIVERY_CHANGE.ordinal());
						finish();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{

				}

				@Override
				public void onFinish()
				{
					SDDialogManager.dismissProgressDialog();
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		}

	}

	private void registeClick()
	{
		mTvDelivery.setOnClickListener(this);
		mTvSubmit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.act_add_delivery_address_tv_delivery:
			clickDelivery();
			break;

		case R.id.act_add_delivery_address_tv_submit:
			clickSave();
			break;

		default:
			break;
		}
	}

	/**
	 * 保存地址
	 */
	private void clickSave()
	{
		if (validateParam())
		{
			if (!AppRuntimeWorker.isLogin())
			{
				return;
			}

			RequestModel model = new RequestModel();
			model.putCtl("uc_address");
			model.putAct("save");
			model.putUser();

			model.put("id", mModel.getId());

			model.put("region_lv1", mModel.getRegion_lv1());// 国家
			model.put("region_lv2", mModel.getRegion_lv2());// 省
			model.put("region_lv3", mModel.getRegion_lv3());// 城市
			model.put("region_lv4", mModel.getRegion_lv4());// 地区/县

			model.put("consignee", mModel.getConsignee());// 联系人姓名
			model.put("address", mModel.getAddress());// 详细地址
			model.put("mobile", mModel.getMobile());// 手机号码
			model.put("zip", mModel.getZip());// 邮编
			SDRequestCallBack<JSONObject> handler = new SDRequestCallBack<JSONObject>()
			{

				@Override
				public void onStart()
				{
					SDDialogManager.showProgressDialog("请稍候...");
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					String info = actModel.getString("infos");
					SDToast.showToast(info);

					int status = actModel.getIntValue("add_status");
					if (status == 1)
					{
						SDEventManager.post(EnumEventTag.USER_DELIVERY_CHANGE.ordinal());
						finish();
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{

				}

				@Override
				public void onFinish()
				{
					SDDialogManager.dismissProgressDialog();
				}
			};
			InterfaceServer.getInstance().requestInterface(model, handler);
		}
	}

	/**
	 * 验证输入参数
	 * 
	 * @return
	 */
	private boolean validateParam()
	{
		if (mModel == null) // 是添加模式
		{
			mModel = new Consignee_infoModel();
		}

		String consignee = mEtConsignee.getText().toString().trim();
		if (TextUtils.isEmpty(consignee)) // 名字为空
		{
			SDToast.showToast("姓名不能为空");
			return false;
		}
		mModel.setConsignee(consignee);

		String phone = mEtPhone.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) // 手机号为空
		{
			SDToast.showToast("手机号不能为空");
			return false;
		} else
		{
			if (phone.length() < 11)
			{
				SDToast.showToast("手机号不能少于11位");
				return false;
			}
		}
		mModel.setMobile(phone);

		if (mModel.getRegion_lv1() <= 0 || mModel.getRegion_lv2() <= 0 || mModel.getRegion_lv3() <= 0 || mModel.getRegion_lv4() <= 0)
		{
			SDToast.showToast("请选择省市地区");
			return false;
		}

		String addressDetail = mEtAddressDetail.getText().toString().trim();
		if (TextUtils.isEmpty(addressDetail)) // 详细地址为空
		{
			SDToast.showToast("详细地址不能为空");
			return false;
		}
		mModel.setAddress(addressDetail);

		String postCode = mEtPostcode.getText().toString().trim();
		if (TextUtils.isEmpty(postCode)) // 邮政编码为空
		{
			SDToast.showToast("邮政编码不能为空");
			return false;
		}
		mModel.setZip(postCode);

		return true;
	}

	/**
	 * 加载省市区地址列表
	 */
	private void clickDelivery()
	{
		dealCheckResult();
	}

	private OnClickListener mOnClickListenerRegions = new OnClickListener()
	{
		@Override
		public void onClick(final View view)
		{
			final SDDialogMenu dialogMenu = new SDDialogMenu();
			switch (view.getId())
			{
			case R.id.dialog_select_region_tv_country:
				dialogMenu.setAdapter(mAdapterCountry);
				break;
			case R.id.dialog_select_region_tv_province:
				dialogMenu.setAdapter(mAdapterProvince);
				break;
			case R.id.dialog_select_region_tv_city:
				dialogMenu.setAdapter(mAdapterCity);
				break;
			case R.id.dialog_select_region_tv_earn:
				dialogMenu.setAdapter(mAdapterEarn);
				break;

			default:
				break;
			}
			dialogMenu.setmListener(new SDDialogMenuListener()
			{
				@Override
				public void onItemClick(View v, int index, SDDialogMenu dialog)
				{
					switch (view.getId())
					{
					case R.id.dialog_select_region_tv_country:
						setTextViewCountryIndex(index, true);
						break;
					case R.id.dialog_select_region_tv_province:
						setTextViewProvinceIndex(index, true);
						break;
					case R.id.dialog_select_region_tv_city:
						setTextViewCityIndex(index, true);
						break;
					case R.id.dialog_select_region_tv_earn:
						setTextViewEarnIndex(index);
						break;

					default:
						break;
					}
				}

				@Override
				public void onDismiss(SDDialogMenu dialog)
				{
				}

				@Override
				public void onCancelClick(View v, SDDialogMenu dialog)
				{
				}
			}).showBottom();
		}
	};

	private void setTextViewCountryIndex(int index, boolean loadData)
	{
		Region_confModel model = (Region_confModel) mAdapterCountry.getItem(index);
		if (mTvCountry != null && model != null)
		{
			if (loadData)
			{
				loadRegionDataBySpnSelectChange(model, 3);
			}
			mTvCountry.setText(model.getName());
			mTvCountry.setTag(model);
		}
	}

	private void setTextViewProvinceIndex(int index, boolean loadData)
	{
		Region_confModel model = (Region_confModel) mAdapterProvince.getItem(index);
		if (mTvProvince != null && model != null)
		{
			if (loadData)
			{
				loadRegionDataBySpnSelectChange(model, 2);
			}
			mTvProvince.setText(model.getName());
			mTvProvince.setTag(model);
		}
	}

	private void setTextViewCityIndex(int index, boolean loadData)
	{
		Region_confModel model = (Region_confModel) mAdapterCity.getItem(index);
		if (mTvCity != null && model != null)
		{
			if (loadData)
			{
				loadRegionDataBySpnSelectChange(model, 1);
			}
			mTvCity.setText(model.getName());
			mTvCity.setTag(model);
		}
	}

	private void setTextViewEarnIndex(int index)
	{
		Region_confModel model = (Region_confModel) mAdapterEarn.getItem(index);
		if (mTvEarn != null && model != null)
		{
			mTvEarn.setText(model.getName());
			mTvEarn.setTag(model);
		}
	}

	/**
	 * 弹出选择区域窗口
	 */
	private void showSelectRegionDialog()
	{
		loadRegionsDataFromDb();
		findSelectIndexs();

		View view = getLayoutInflater().inflate(R.layout.dialog_select_region_new, null);

		mTvCountry = (TextView) view.findViewById(R.id.dialog_select_region_tv_country);
		mTvProvince = (TextView) view.findViewById(R.id.dialog_select_region_tv_province);
		mTvCity = (TextView) view.findViewById(R.id.dialog_select_region_tv_city);
		mTvEarn = (TextView) view.findViewById(R.id.dialog_select_region_tv_earn);

		mTvCountry.setOnClickListener(mOnClickListenerRegions);
		mTvProvince.setOnClickListener(mOnClickListenerRegions);
		mTvCity.setOnClickListener(mOnClickListenerRegions);
		mTvEarn.setOnClickListener(mOnClickListenerRegions);

		setTextViewCountryIndex(mSelectIndexCountry, true);
		setTextViewProvinceIndex(mSelectIndexProvince, true);
		setTextViewCityIndex(mSelectIndexCity, true);
		setTextViewEarnIndex(mSelectIndexArea);

		new SDDialogCustom().setTextTitle("选择区域").setCustomView(view).setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				clickDialogConfirm();
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		}).show();
	}

	private void clickDialogConfirm()
	{
		if (mModel == null) // 是添加模式
		{
			mModel = new Consignee_infoModel();
		}

		Region_confModel modelCountry = (Region_confModel) mTvCountry.getTag();
		Region_confModel modelProvince = (Region_confModel) mTvProvince.getTag();
		Region_confModel modelCity = (Region_confModel) mTvCity.getTag();
		Region_confModel modelEarn = (Region_confModel) mTvEarn.getTag();
		if (modelEarn != null && modelCity != null && modelCountry != null && modelProvince != null)
		{
			String nameCountry = modelCountry.getName();
			String nameProvince = modelProvince.getName();
			String nameCity = modelCity.getName();
			String nameArea = modelEarn.getName();
			int idCountry = modelCountry.getId();
			int idProvince = modelProvince.getId();
			int idCity = modelCity.getId();
			int idArea = modelEarn.getId();

			if (!TextUtils.isEmpty(nameCountry) && !TextUtils.isEmpty(nameProvince) && !TextUtils.isEmpty(nameCity) && !TextUtils.isEmpty(nameArea))
			{
				mTvDelivery.setText(nameCountry + " " + nameProvince + " " + nameCity + " " + nameArea);
			}

			if (idCountry > 0 && idProvince > 0 && idCity > 0 && idArea > 0)
			{
				mModel.setRegion_lv1(idCountry);
				mModel.setRegion_lv2(idProvince);
				mModel.setRegion_lv3(idCity);
				mModel.setRegion_lv4(idArea);

				mModel.setRegion_lv1_name(nameCountry);
				mModel.setRegion_lv2_name(nameProvince);
				mModel.setRegion_lv3_name(nameCity);
				mModel.setRegion_lv4_name(nameArea);
			}
		}
	}

	protected void dealCheckResult()
	{
		int serverVersion = AppRuntimeWorker.getServerRegionVersion();
		int localVersion = AppConfig.getRegionConfVersion();

		if (serverVersion < 0) // 服务器不存在区域数据
		{
			if (localVersion <= 0) // 本地也没有数据
			{
				SDToast.showToast("未获取到服务器地区区域数据");
				return;
			} else
			{
				showSelectRegionDialog();
			}
		} else
		{
			if (localVersion != serverVersion) // 需要更新本地数据
			{
				requestRegionListData(serverVersion);
			} else
			{
				showSelectRegionDialog();
			}
		}
	}

	/**
	 * 请求服务器地区列表数据
	 */
	private void requestRegionListData(final int serverVersion)
	{
		RequestModel model = new RequestModel();
		model.putCtl("delivery_region");

		SDRequestCallBack<Delivery_region_indexActModel> handler = new SDRequestCallBack<Delivery_region_indexActModel>()
		{

			@Override
			public void onStart()
			{
				SDDialogManager.showProgressDialog("请稍候，正在请求地区列表数据...");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				if (actModel.getStatus() == 1)
				{
					// 保存数据到数据库成功
					if (Region_confModelDao.getInstance().deleteOldAndSaveNew(actModel.getRegion_list()))
					{
						AppConfig.setRegionConfVersion(serverVersion);
						showSelectRegionDialog();
					}
				}
			}

			@Override
			public void onFinish()
			{
				SDDialogManager.dismissProgressDialog();
			}
		};
		InterfaceServer.getInstance().requestInterface(model, handler);
	}

	/**
	 * 从数据库获取地区列表数据
	 */
	private void loadRegionsDataFromDb()
	{
		if (!SDCollectionUtil.isEmpty(mListRegionEarn) && !SDCollectionUtil.isEmpty(mListRegionCity) && !SDCollectionUtil.isEmpty(mListRegionCountry)
				&& !SDCollectionUtil.isEmpty(mListRegionProvince))
		{

		} else
		{
			// 如果当前有的集合中没有数据才从数据库中获取
			List<Region_confModel> listCountry = Region_confModelDao.getInstance().getCountryList();
			if (!SDCollectionUtil.isEmpty(listCountry))
			{
				mListRegionCountry.clear();
				mListRegionCountry.addAll(listCountry);
				loadRegionDataBySpnSelectChange(mListRegionCountry.get(0), 3);
			}
		}
	}

	/**
	 * 找到选中项
	 */
	public void findSelectIndexs()
	{
		if (mModel != null)
		{
			int idCountry = mModel.getRegion_lv1();
			int idProvince = mModel.getRegion_lv2();
			int idCity = mModel.getRegion_lv3();
			int idArea = mModel.getRegion_lv4();

			if (idCountry > 0 && idProvince > 0 && idCity > 0 && idArea > 0)
			{
				List<Region_confModel> listModelsProvince = Region_confModelDao.getInstance().getListByPid(idCountry);
				if (listModelsProvince != null)
				{
					mListRegionProvince.clear();
					mListRegionProvince.addAll(listModelsProvince);
				}

				List<Region_confModel> listModelsCity = Region_confModelDao.getInstance().getListByPid(idProvince);
				if (listModelsCity != null)
				{
					mListRegionCity.clear();
					mListRegionCity.addAll(listModelsCity);
				}

				List<Region_confModel> listModelsArea = Region_confModelDao.getInstance().getListByPid(idCity);
				if (listModelsArea != null)
				{
					mListRegionEarn.clear();
					mListRegionEarn.addAll(listModelsArea);
				}

				Region_confModel model = null;
				if (!SDCollectionUtil.isEmpty(mListRegionCountry))
				{
					for (int i = 0; i < mListRegionCountry.size(); i++)
					{
						model = mListRegionCountry.get(i);
						if (model != null && idCountry == model.getId())
						{
							mSelectIndexCountry = i;
							break;
						}
					}
				}
				if (!SDCollectionUtil.isEmpty(mListRegionProvince))
				{
					for (int i = 0; i < mListRegionProvince.size(); i++)
					{
						model = mListRegionProvince.get(i);
						if (model != null && idProvince == model.getId())
						{
							mSelectIndexProvince = i;
							break;
						}
					}
				}
				if (!SDCollectionUtil.isEmpty(mListRegionCity))
				{
					for (int i = 0; i < mListRegionCity.size(); i++)
					{
						model = mListRegionCity.get(i);
						if (model != null && idCity == model.getId())
						{
							mSelectIndexCity = i;
							break;
						}
					}
				}
				if (!SDCollectionUtil.isEmpty(mListRegionEarn))
				{
					for (int i = 0; i < mListRegionEarn.size(); i++)
					{
						model = mListRegionEarn.get(i);
						if (model != null && idArea == model.getId())
						{
							mSelectIndexArea = i;
							break;
						}
					}
				}
			}
		}
	}

	private void loadRegionDataBySpnSelectChange(Region_confModel model, int level)
	{
		if (model != null && level >= 1 && level <= 3)
		{
			List<Region_confModel> listModel = Region_confModelDao.getInstance().getListByParentModel(model);
			if (SDCollectionUtil.isEmpty(listModel))
			{
				return;
			}
			switch (level)
			{
			case 1: // 区域
				mListRegionEarn.clear();
				mListRegionEarn.addAll(listModel);
				mAdapterEarn.notifyDataSetChanged();
				setTextViewEarnIndex(0);
				break;
			case 2: // 市
				mListRegionCity.clear();
				mListRegionCity.addAll(listModel);
				mAdapterCity.notifyDataSetChanged();
				setTextViewCityIndex(0, false);
				loadRegionDataBySpnSelectChange(mListRegionCity.get(0), --level);
				break;
			case 3: // 省
				mListRegionProvince.clear();
				mListRegionProvince.addAll(listModel);
				mAdapterProvince.notifyDataSetChanged();
				setTextViewProvinceIndex(0, false);
				loadRegionDataBySpnSelectChange(mListRegionProvince.get(0), --level);
				break;
			default:
				break;
			}
		}
	}
}