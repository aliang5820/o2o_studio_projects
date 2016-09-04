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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fanwe.application.App;
import com.fanwe.apply.City;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.ApplyOrderCtlActModel;
import com.fanwe.model.ApplyPictureCtlActModel;
import com.fanwe.model.ApplyServiceTypeCtlActModel;
import com.fanwe.model.ApplyServiceTypeModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

import java.io.File;
import java.util.List;

/**
 * Created by Edison on 2016/7/25.
 * 申请会员店
 */
public class ApplyHYDActivity extends TitleBaseActivity {
    @ViewInject(R.id.spinner1)
    private Spinner spinner1; //服务子类1

    @ViewInject(R.id.spinner2)
    private Spinner spinner2; //服务子类2

    @ViewInject(R.id.city)
    private TextView cityView;

    @ViewInject(R.id.storeName)
    private TextView storeName; //商户名称

    @ViewInject(R.id.address)
    private TextView address; //商户地址

    @ViewInject(R.id.contactPhone)
    private TextView contactPhone; //联系电话

    @ViewInject(R.id.isAgree)
    private CheckBox isAgree; //是否同意合作协议

    @ViewInject(R.id.shopPhone)
    private TextView shopPhone; //商户电话

    @ViewInject(R.id.shopTime)
    private TextView shopTime; //营业时间

    @ViewInject(R.id.mobile)
    private TextView mobile; //手机号

    @ViewInject(R.id.companyName)
    private TextView companyName; //企业名称

    @ViewInject(R.id.companyOwner)
    private TextView companyOwner; //法人姓名

    @ViewInject(R.id.bankAccountName)
    private TextView bankAccountName; //开户行户名

    @ViewInject(R.id.bankName)
    private TextView bankName; //开户行名称

    @ViewInject(R.id.bankAccount)
    private TextView bankAccount; //开户行账号

    @ViewInject(R.id.companyPic1)
    private ImageView companyPic1;
    @ViewInject(R.id.companyPic2)
    private ImageView companyPic2;
    @ViewInject(R.id.companyPic3)
    private ImageView companyPic3;
    @ViewInject(R.id.companyPic4)
    private ImageView companyPic4;

    private ApplyPictureCtlActModel pic_1;
    private ApplyPictureCtlActModel pic_2;
    private ApplyPictureCtlActModel pic_3;
    private ApplyPictureCtlActModel pic_4;

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.companyPic1:
                showPictureActivity(companyPic1);
                break;
            case R.id.companyPic2:
                showPictureActivity(companyPic2);
                break;
            case R.id.companyPic3:
                showPictureActivity(companyPic3);
                break;
            case R.id.companyPic4:
                showPictureActivity(companyPic4);
                break;
        }
    }

    private void initView() {
        mTitle.setText("申请会员店");
        findViewById(R.id.selected_city_layout).setVisibility(View.VISIBLE);

        companyPic1.setOnClickListener(this);
        companyPic2.setOnClickListener(this);
        companyPic3.setOnClickListener(this);
        companyPic4.setOnClickListener(this);
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
            //如果是子类，带上父类的id
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
        } else if (TextUtils.isEmpty(shopPhone.getText())) {
            SDToast.showToast("请填写商户电话");
            shopPhone.requestFocus();
            return;
        } else if (TextUtils.isEmpty(companyOwner.getText())) {
            SDToast.showToast("请填写法人姓名");
            companyOwner.requestFocus();
            return;
        } else if (TextUtils.isEmpty(mobile.getText())) {
            SDToast.showToast("请填写手机号");
            mobile.requestFocus();
            return;
        } else if (TextUtils.isEmpty(companyName.getText())) {
            SDToast.showToast("请填写企业名称");
            companyName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(shopTime.getText())) {
            SDToast.showToast("请填写营业时间");
            shopTime.requestFocus();
            return;
        } else if (TextUtils.isEmpty(contactPhone.getText())) {
            SDToast.showToast("请填写联系电话");
            contactPhone.requestFocus();
            return;
        } else if (TextUtils.isEmpty(bankAccountName.getText())) {
            SDToast.showToast("请填写开户行户名");
            bankAccountName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(bankName.getText())) {
            SDToast.showToast("请填写开户行名称");
            bankName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(bankAccount.getText())) {
            SDToast.showToast("请填写开户行账号");
            bankAccount.requestFocus();
            return;
        } else if (pic_1 == null) {
            SDToast.showToast("请选择营业执照");
            return;
        } else if (pic_2 == null) {
            SDToast.showToast("请选择其他资质");
            return;
        } else if (pic_3 == null) {
            SDToast.showToast("请选择商户logo");
            return;
        } else if (pic_4 == null) {
            SDToast.showToast("请选择门店照片");
            return;
        } else if (!isAgree.isChecked()) {
            SDToast.showToast("请阅读城市合作协议后，勾选同意");
            return;
        }
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "applyMemberShop");
        model.put("supplier_id", App.getApp().getmLocalUser().getSupplier_id());//商户的id
        model.put("area_id", city.getId());//地区的id
        model.put("supplier_name", storeName.getText().toString());//商户名称
        model.put("supplier_address", address.getText().toString());//商户地址
        model.put("deal_cate", ((ApplyServiceTypeModel) spinner1.getTag()).getId());//第一类
        model.put("deal_child_cate", ((ApplyServiceTypeModel) spinner2.getTag()).getId());//第二类
        model.put("tel", shopPhone.getText().toString());//商户电话
        model.put("contact_name", companyOwner.getText().toString());//法人姓名
        model.put("h_bank_name", bankAccountName.getText().toString());//开户行户名
        model.put("h_bank_user", bankName.getText().toString());//开户行名称
        model.put("h_bank_info", bankAccount.getText().toString());//开户行账号
        model.put("mobile", mobile.getText().toString());//手机号
        model.put("h_name", companyName.getText().toString());//企业名称
        model.put("open_time", shopTime.getText().toString());//营业时间
        model.put("h_tel", contactPhone.getText().toString());//联系电话
        /*图片*/
        model.put("h_license", companyPic1.getTag().toString());//营业执照
        model.put("h_other_license", contactPhone.getText().toString());//其他资质
        model.put("h_supplier_logo", contactPhone.getText().toString());//商户logo
        model.put("h_supplier_image", contactPhone.getText().toString());//门店图片

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
                            /*requestLoginInterface();*/
                            SDEventManager.post(EnumEventTag.EXIT_APP.ordinal());
                            startActivity(new Intent(mActivity, LoginActivity.class));
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

    //进入图片选择图片
    private void showPictureActivity(ImageView view) {
        Intent intent = new Intent(mActivity, ApplyPictureActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_ID, view.getId());
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
    }

    /*文件上传接口*/
    private void requestUploadPicInterface(String filePath) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "user_register_upload");
        model.putFile("file", new File(filePath));

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyPictureCtlActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(ApplyPictureCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 1:

                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("图片上传中...");
            }
        });
    }
}
