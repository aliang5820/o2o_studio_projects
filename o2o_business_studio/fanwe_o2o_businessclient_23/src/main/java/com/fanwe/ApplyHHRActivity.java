package com.fanwe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
import com.fanwe.utils.ImageUriUtil;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDEventManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Edison on 2016/7/25.
 * 合伙人页面
 */
public class ApplyHHRActivity extends TitleBaseActivity {

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

    @ViewInject(R.id.personIsAgree)
    private CheckBox personIsAgree; //是否同意合作协议

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

    private Uri imageUri;
    private Uri imageCropUri;
    private ImageView currentImageView;//当前选择的ImageView
    private ApplyPictureCtlActModel pic_1;
    private ApplyPictureCtlActModel pic_2;
    private ApplyPictureCtlActModel pic_3;
    private ApplyPictureCtlActModel pic_4;
    private int totalPic = 4;

    private MyAdapter spinnerAdapter1;
    private MyAdapter spinnerAdapter2;
    private City city;
    private Dialog nDialog;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_hhr);
        initTitle();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        LayoutInflater mInflater = LayoutInflater.from(this);

        //添加页卡视图
        View view1 = mInflater.inflate(R.layout.act_apply_hyd, mTabLayout, false);
        View view2 = mInflater.inflate(R.layout.act_apply_person_hhr, mTabLayout, false);
        mViewList.add(view1);//页卡视图1
        mViewList.add(view2);//页卡视图2

        //添加页卡标题
        mTitleList.add("企业合伙人");
        mTitleList.add("个人合伙人");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        initData();
    }

    private void initTitle() {
        mTitle.setText("申请合伙人");
    }

    private void initData() {
        city = (City) getIntent().getSerializableExtra(Constant.ExtraConstant.EXTRA_MODEL);
        cityView.setText(city.getName());
        //获取服务分类
        requestTypeList(true, -1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.companyPic1:
                showSelectPicDialog(companyPic1);
                break;
            case R.id.companyPic2:
                showSelectPicDialog(companyPic2);
                break;
            case R.id.companyPic3:
                showSelectPicDialog(companyPic3);
                break;
            case R.id.companyPic4:
                showSelectPicDialog(companyPic4);
                break;
        }
    }

    private void showSelectPicDialog(final ImageView imageView) {
        new AlertDialog.Builder(this).setTitle("选择图片")
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectGallery();
                        currentImageView = imageView;
                    }
                })
                .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imageUri = getTargetImageUri(true);
                        takeCameraOnly(imageUri);
                        currentImageView = imageView;
                    }
                }).show();
    }

    public void onConfirm1(View view) {
        //申请企业合伙人
        requestRole1();
    }

    public void onConfirm2(View view) {
        //申请个人合伙人
        requestRole2();
    }

    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

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

    //选择相册
    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        startActivityForResult(intent, Constant.RESULT_GALLERY_ONLY);
    }

    //拍照
    private void takeCameraOnly(Uri targetUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, Constant.RESULT_CAMERA_ONLY);
    }

    //裁剪图片
    public void cropImg(Uri sourceUri, Uri targetUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, Constant.RESULT_CROP_PATH_RESULT);
    }

    /**
     * 获取图片Uri
     *
     * @param isTemp 是否是临时图片
     */
    public Uri getTargetImageUri(boolean isTemp) {
        //组装图片文件夹和裁剪后的目标文件
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            SDToast.showToast("SD存储卡不可用，请检查是否插入了SD存储卡");
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss", Locale.CHINA);
        String name;
        Date tempDate = Calendar.getInstance().getTime();
        if (isTemp) {
            name = "/" + format.format(tempDate) + "_temp.jpg";
        } else {
            name = "/" + format.format(tempDate) + "_crop.jpg";
        }
        //检查文件夹是否存在
        File imgFileDir = new File(Environment.getExternalStorageDirectory().getPath() + Constant.FILE_DIR);
        if (!imgFileDir.exists()) {
            // 创建文件夹
            if (!imgFileDir.mkdirs()) {
                Log.e(TAG, "创建文件夹失败：" + imgFileDir.getPath());
            }
        }
        File fileName = new File(imgFileDir.getPath() + name);
        Uri outputUri = Uri.fromFile(fileName);
        Log.d(TAG, "outputUri:" + outputUri);
        return outputUri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //图片来源
            switch (requestCode) {
                case Constant.RESULT_GALLERY_ONLY:
                    //选择照片
                    Uri uri = data.getData();
                    String realUri = ImageUriUtil.uri2filePath(uri, mActivity);
                    File file = new File(realUri);
                    imageUri = Uri.fromFile(file);
                    imageCropUri = getTargetImageUri(false);
                    cropImg(imageUri, imageCropUri);
                    break;
                case Constant.RESULT_CAMERA_ONLY:
                    //拍照
                    imageCropUri = getTargetImageUri(false);
                    cropImg(imageUri, imageCropUri);
                    break;
                case Constant.RESULT_CROP_PATH_RESULT:
                    //获取裁剪结果
                    currentImageView.setImageURI(imageCropUri);
                    currentImageView.setTag(imageCropUri.getPath());
                    Log.e(TAG, "==================>" + imageCropUri.getPath());
                    break;
            }
        }
    }

    /*文件上传接口*/
    private void requestUploadPicInterface(final ApplyPictureCtlActModel pictureCtlActModel) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "user_register_upload");
        model.putFile("file", new File(pictureCtlActModel.getPath()));

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyPictureCtlActModel>() {


            @Override
            public void onFinish() {
                totalPic--;
                if(totalPic == 0) {
                    if(nDialog != null) {
                        nDialog.dismiss();
                    }

                    //图片上传请求执行结束，检查是否有失败的请求
                    if(!TextUtils.isEmpty(pic_1.getUrl()) && !TextUtils.isEmpty(pic_2.getUrl()) && !TextUtils.isEmpty(pic_3.getUrl())
                            && !TextUtils.isEmpty(pic_4.getUrl())) {
                        //上传完毕，执行申请
                        Log.e(TAG, "pic_1 url:" + pic_1.getUrl());
                        Log.e(TAG, "pic_2 url:" + pic_2.getUrl());
                        Log.e(TAG, "pic_3 url:" + pic_3.getUrl());
                        Log.e(TAG, "pic_4 url:" + pic_4.getUrl());
                        requestHHR();
                    } else {
                        totalPic = 4;
                        SDToast.showToast("图片上传失败，请检查网络后重新提交");
                    }
                }
            }

            @Override
            public void onSuccess(ApplyPictureCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 1:
                            pictureCtlActModel.setUrl(actModel.getUrl());
                            break;
                    }
                }

            }

            @Override
            public void onStart() {

            }
        });
    }

    //申请企业合伙人Step.1
    private void requestRole1() {
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
        } else if (companyPic1.getTag() == null) {
            SDToast.showToast("请选择营业执照");
            return;
        } else if (companyPic2.getTag() == null) {
            SDToast.showToast("请选择其他资质");
            return;
        } else if (companyPic3.getTag() == null) {
            SDToast.showToast("请选择商户logo");
            return;
        } else if (companyPic4.getTag() == null) {
            SDToast.showToast("请选择门店照片");
            return;
        } else if (!isAgree.isChecked()) {
            SDToast.showToast("请阅读城市合作协议后，勾选同意");
            return;
        }

        //先上传图片
        pic_1 = new ApplyPictureCtlActModel();
        pic_1.setPath(companyPic1.getTag().toString());
        pic_2 = new ApplyPictureCtlActModel();
        pic_2.setPath(companyPic2.getTag().toString());
        pic_3 = new ApplyPictureCtlActModel();
        pic_3.setPath(companyPic3.getTag().toString());
        pic_4 = new ApplyPictureCtlActModel();
        pic_4.setPath(companyPic4.getTag().toString());

        //开始上传图片
        nDialog = SDDialogUtil.showLoading("图片上传中...");
        requestUploadPicInterface(pic_1);
        requestUploadPicInterface(pic_2);
        requestUploadPicInterface(pic_3);
        requestUploadPicInterface(pic_4);
    }

    ////申请企业合伙人Step.2
    private void requestHHR() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "applyPartnerSupplierShop");
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
        model.put("h_license", pic_1.getUrl());//营业执照
        model.put("h_other_license", pic_2.getUrl());//其他资质
        model.put("h_supplier_logo", pic_3.getUrl());//商户logo
        model.put("h_supplier_image", pic_4.getUrl());//门店图片

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
                SDDialogManager.showProgressDialog("数据提交中，请稍候...");
            }
        });
    }

    //申请个人合伙人
    private void requestRole2() {
        /*if (TextUtils.isEmpty(personName.getText())) {
            SDToast.showToast("请填写姓名");
            personName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(personPhone.getText())) {
            SDToast.showToast("请填写将绑定的手机号码");
            personPhone.requestFocus();
            return;
        } else if (!personIsAgree.isChecked()) {
            SDToast.showToast("请阅读城市合作协议后，勾选同意");
            return;
        }
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "applyPartnerpersonShop");
        model.put("supplier_id", App.getApp().getmLocalUser().getSupplier_id());//商户的id
        model.put("area_id", city.getId());//地区的id
        int sex = 0;
        switch (personRadioGroup.getCheckedRadioButtonId()) {
            case R.id.personRadio1:
                sex = 0;
                break;
            case R.id.personRadio2:
                sex = 1;
                break;
            case R.id.personRadio3:
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
                            *//*Intent intent = new Intent(mActivity, ApplyPayActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.HYD);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_ID, actModel.getOrderId());
                            intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, actModel.getPrice());
                            startActivity(intent);
                            finish();*//*
                            //requestLoginInterface();
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
        });*/
    }
}
