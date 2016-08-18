package com.fanwe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.fanwe.application.App;
import com.fanwe.apply.City;
import com.fanwe.businessclient.R;
import com.fanwe.config.AppConfig;
import com.fanwe.constant.Constant;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.AccountInfoModel;
import com.fanwe.model.ApplyOrderCtlActModel;
import com.fanwe.model.ApplyServiceTypeCtlActModel;
import com.fanwe.model.ApplyServiceTypeModel;
import com.fanwe.model.BizUserCtlDoLoginActModel;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.RequestModel;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

/**
 * Created by Edison on 2016/7/25.
 */
public class ApplyHYDActivity extends TitleBaseActivity {
    @ViewInject(R.id.spinner1)
    private Spinner spinner1;

    @ViewInject(R.id.spinner2)
    private Spinner spinner2;

    @ViewInject(R.id.city)
    private TextView cityView;

    @ViewInject(R.id.storeName)
    private TextView storeName;

    @ViewInject(R.id.address)
    private TextView address;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.phone)
    private TextView phone;

    @ViewInject(R.id.isAgree)
    private CheckBox isAgree;

    private MyAdapter spinnerAdapter1;
    private MyAdapter spinnerAdapter2;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_hyd);
        initView();
        initData();
    }

    private void initView() {
        mTitle.setText("申请会员店");
        findViewById(R.id.selected_city_layout).setVisibility(View.VISIBLE);
    }

    private void initData() {
        city = (City) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
        cityView.setText(city.getName());
        //获取服务分类
        requestTypeList(true, -1);
    }

    public void onConfirm1(View view) {
        //申请
        requestRole();
    }

    private void initSpinner1(List<ApplyServiceTypeModel> list) {
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        spinnerAdapter1 = new MyAdapter(list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        //spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinner1.setAdapter(spinnerAdapter1);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ApplyServiceTypeModel typeModel = (ApplyServiceTypeModel) spinnerAdapter1.getItem(position);
                spinner1.setTag(typeModel);
                requestTypeList(false, typeModel.getId());
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void initSpinner2(List<ApplyServiceTypeModel> list) {
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        spinnerAdapter2 = new MyAdapter(list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        //spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinner2.setAdapter(spinnerAdapter2);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                spinner2.setTag(spinnerAdapter2.getItem(position));
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    class MyAdapter extends BaseAdapter {
        private List<ApplyServiceTypeModel> mList;

        public MyAdapter(List<ApplyServiceTypeModel> pList) {
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 下面是重要代码
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_apply_service_type, null);
            if (convertView != null) {
                TextView textView = (TextView) convertView;
                textView.setText(mList.get(position).getName());
            }
            return convertView;
        }
    }

    //根据type，获取服务分类
    private void requestTypeList(final boolean isParent, int typeId) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "get_deal_cate");
        if (!isParent) {
            model.put("deal_cate_id", typeId);
        }

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyServiceTypeCtlActModel>() {

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(ApplyServiceTypeCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            if (actModel.getTypeList() != null) {
                                if (isParent) {
                                    initSpinner1(actModel.getTypeList());
                                } else {
                                    initSpinner2(actModel.getTypeList());
                                }
                            }
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                showToast = false;
            }
        });
    }

    //申请会员店请求
    private void requestRole() {
        if (TextUtils.isEmpty(storeName.getText())) {
            SDToast.showToast("请填写商户名称");
            storeName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(address.getText())) {
            SDToast.showToast("请填写商户地址");
            address.requestFocus();
            return;
        } else if (spinner1.getTag() == null || spinner2.getTag() == null) {
            SDToast.showToast("请选择服务分类");
            address.requestFocus();
            return;
        } else if (TextUtils.isEmpty(phone.getText())) {
            SDToast.showToast("请填写联系人电话");
            phone.requestFocus();
            return;
        } else if (TextUtils.isEmpty(name.getText())) {
            SDToast.showToast("请填写联系人名称");
            name.requestFocus();
            return;
        } else if (!isAgree.isChecked()) {
            SDToast.showToast("请阅读城市合作协议后，勾选同意");
            return;
        }
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "applyMemberShop");
        model.put("supplier_id", App.getApp().getmLocalUser().getUser_id());//商户的id
        model.put("area_id", city.getId());//地区的id
        model.put("supplier_name", storeName.getText().toString());//商户名称
        model.put("supplier_add", address.getText().toString());//商户地址
        model.put("deal_cate", ((ApplyServiceTypeModel)spinner1.getTag()).getId());//第一类
        model.put("deal_child_cate", ((ApplyServiceTypeModel)spinner2.getTag()).getId());//第二类
        int sex = 0;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio1:
                sex = 0;
                break;
            case R.id.radio2:
                sex = 1;
                break;
            case R.id.radio3:
                sex = 2;
                break;
        }
        model.put("sex", sex);//性别
        model.put("tel", phone.getText().toString());//联系电话
        model.put("contact_name", name.getText().toString());//联系人名称

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyOrderCtlActModel>() {

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }

            @Override
            public void onSuccess(ApplyOrderCtlActModel actModel) {
                SDDialogManager.dismissProgressDialog();
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            /*Intent intent = new Intent(mActivity, ApplyPayActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.HYD);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_ID, actModel.getOrderId());
                            intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, actModel.getPrice());
                            startActivity(intent);
                            finish();*/
                            requestLoginInterface();
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }
        });
    }

    private void requestLoginInterface() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_user", "dologin");
        model.put("account_name", App.getApp().getmLocalUser().getAccount_name());
        model.put("account_password", App.getApp().getmLocalUser().getAccount_password());
        model.put("device_token", UmengPushManager.getPushAgent().getRegistrationId());

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<BizUserCtlDoLoginActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(BizUserCtlDoLoginActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            if (actModel.getAccount_info() != null) {
                                dealLoginSuccess(actModel.getAccount_info());
                            }
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("登录中...");
            }
        });
    }

    private void dealLoginSuccess(AccountInfoModel accountInfoModel) {
        LocalUserModel user = new LocalUserModel();
        user.setUser_id(accountInfoModel.getAccount_id());
        user.setSupplier_id(accountInfoModel.getSupplier_id());
        user.setAccount_name(accountInfoModel.getAccount_name());
        user.setAccount_password(accountInfoModel.getAccount_password());
        user.setAccount_type(accountInfoModel.getAccount_type());
        user.setQr_code(accountInfoModel.getQr_code());
        App.getApp().setmLocalUser(user);

        // 保存账号
        AppConfig.setUserName(accountInfoModel.getAccount_name());
        //登录成功进入主页之前，需要判断是否已经申请加盟
        SDEventManager.post(EnumEventTag.EXIT_APP.ordinal());
        startActivity(new Intent(mActivity, MainActivity.class));
    }
}
