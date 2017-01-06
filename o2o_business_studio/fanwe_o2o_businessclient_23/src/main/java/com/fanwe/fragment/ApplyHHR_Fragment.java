package com.fanwe.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.fanwe.ApplyHHRActivity;
import com.fanwe.ApplyPayActivity;
import com.fanwe.LoginActivity;
import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.constant.ApkConstant;
import com.fanwe.constant.Constant;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.ApplyInfoModel;
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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Edison on 2016/9/7.
 * 企业合伙人
 */
public class ApplyHHR_Fragment extends BaseFragment {
    private static final String TAG = ApplyHHR_Fragment.class.getName();
    @ViewInject(R.id.isAgree)
    private CheckBox isAgree; //是否同意合作协议

    @ViewInject(R.id.spinner1)
    private Spinner spinner1; //服务子类1

    @ViewInject(R.id.spinner2)
    private Spinner spinner2; //服务子类2

    @ViewInject(R.id.storeName)
    private TextView storeName; //商户名称

    @ViewInject(R.id.address)
    private TextView address; //商户地址

    @ViewInject(R.id.contactPhone)
    private TextView contactPhone; //联系电话

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
    private Map<Integer, ApplyPictureCtlActModel> picMap = new HashMap<>();
    private Map<Integer, String> picUrlMap = new HashMap<>();
    private int totalPic;

    private MyAdapter spinnerAdapter1;
    private MyAdapter spinnerAdapter2;
    private Dialog nDialog;
    private ApplyInfoModel applyInfoModel;

    public static ApplyHHR_Fragment getInstance(ApplyInfoModel applyInfoModel) {
        ApplyHHR_Fragment fragment = new ApplyHHR_Fragment();
        if (applyInfoModel != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.ExtraConstant.EXTRA_MODEL, applyInfoModel);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.act_apply_hyd;
    }

    @Override
    protected void init() {
        super.init();
        //如果是编辑数据，显示已有的数据
        Serializable serializable = getArguments().getSerializable(Constant.ExtraConstant.EXTRA_MODEL);
        if (serializable != null) {
            applyInfoModel = (ApplyInfoModel) serializable;
            showData();
        }

        findViewById(R.id.selected_city_layout).setVisibility(View.GONE);
        findViewById(R.id.confirmBtn).setOnClickListener(this);
        companyPic1.setOnClickListener(this);
        companyPic2.setOnClickListener(this);
        companyPic3.setOnClickListener(this);
        companyPic4.setOnClickListener(this);
        //获取服务分类
        requestTypeList(true, -1);
    }

    //显示已经存在的数据
    private void showData() {
        if (applyInfoModel != null) {
            //商户名称
            storeName.setText(applyInfoModel.getName());
            //商户地址
            address.setText(applyInfoModel.getAddress());
            //商户电话
            shopPhone.setText(applyInfoModel.getTel());
            //法人代表
            companyOwner.setText(applyInfoModel.getH_faren());
            //手机号
            mobile.setText(applyInfoModel.getMobile());
            //企业名称
            companyName.setText(applyInfoModel.getH_name());
            //营业时间
            shopTime.setText(applyInfoModel.getOpen_time());
            //联系电话
            contactPhone.setText(applyInfoModel.getH_tel());
            //开户行户名
            bankAccountName.setText(applyInfoModel.getH_bank_user());
            //开户行名称
            bankName.setText(applyInfoModel.getH_bank_name());
            //开户行账号
            bankAccount.setText(applyInfoModel.getH_bank_info());
            //营业执照
            if(!TextUtils.isEmpty(applyInfoModel.getH_license())) {
                ImageLoaderManager.getImageLoader().displayImage(ApkConstant.SERVER_IMG_URL + applyInfoModel.getH_license().substring(1), companyPic1);
                picUrlMap.put(companyPic1.getId(), applyInfoModel.getH_license());
            }
            //其他资质
            if(!TextUtils.isEmpty(applyInfoModel.getH_other_license())) {
                ImageLoaderManager.getImageLoader().displayImage(ApkConstant.SERVER_IMG_URL + applyInfoModel.getH_other_license().substring(1), companyPic2);
                picUrlMap.put(companyPic2.getId(), applyInfoModel.getH_other_license());
            }
            //商户logo
            if(!TextUtils.isEmpty(applyInfoModel.getH_supplier_logo())) {
                ImageLoaderManager.getImageLoader().displayImage(ApkConstant.SERVER_IMG_URL + applyInfoModel.getH_supplier_logo().substring(1), companyPic3);
                picUrlMap.put(companyPic3.getId(), applyInfoModel.getH_supplier_logo());
            }
            //门店照片
            if(!TextUtils.isEmpty(applyInfoModel.getH_supplier_image())) {
                ImageLoaderManager.getImageLoader().displayImage(ApkConstant.SERVER_IMG_URL + applyInfoModel.getH_supplier_image().substring(1), companyPic4);
                picUrlMap.put(companyPic4.getId(), applyInfoModel.getH_supplier_image());
            }
        }
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
            case R.id.confirmBtn:
                //申请企业合伙人
                requestRole();
                break;
        }
    }

    private void showSelectPicDialog(final ImageView imageView) {
        new AlertDialog.Builder(getContext()).setTitle("选择图片")
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_apply_service_type, null);
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
        intent.putExtra("outputX", 1000);
        intent.putExtra("outputY", 1000);
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
        if (resultCode == Activity.RESULT_OK) {
            //图片来源
            switch (requestCode) {
                case Constant.RESULT_GALLERY_ONLY:
                    //选择照片
                    Uri uri = data.getData();
                    String realUri = ImageUriUtil.uri2filePath(uri, getContext());
                    File file = new File(realUri);
                    imageUri = Uri.fromFile(file);
                    if(currentImageView == companyPic3 || currentImageView == companyPic4) {
                        imageCropUri = getTargetImageUri(false);
                        cropImg(imageUri, imageCropUri);
                    } else {
                        ImageLoaderManager.getImageLoader().displayImage(imageUri.toString(), currentImageView);
                        ApplyPictureCtlActModel picModel = new ApplyPictureCtlActModel();
                        picModel.setPath(imageUri.getPath());
                        picMap.put(currentImageView.getId(), picModel);
                        Log.e(TAG, "==================>" + imageUri.getPath());
                    }
                    break;
                case Constant.RESULT_CAMERA_ONLY:
                    //拍照
                    if(currentImageView == companyPic3 || currentImageView == companyPic4) {
                        imageCropUri = getTargetImageUri(false);
                        cropImg(imageUri, imageCropUri);
                    } else {
                        ImageLoaderManager.getImageLoader().displayImage(imageUri.toString(), currentImageView);
                        ApplyPictureCtlActModel picModel = new ApplyPictureCtlActModel();
                        picModel.setPath(imageUri.getPath());
                        picMap.put(currentImageView.getId(), picModel);
                        Log.e(TAG, "==================>" + imageUri.getPath());
                    }
                    break;
                case Constant.RESULT_CROP_PATH_RESULT:
                    //获取裁剪结果
                    currentImageView.setImageURI(imageCropUri);
                    //存储起来，准备上传
                    ApplyPictureCtlActModel picModel = new ApplyPictureCtlActModel();
                    picModel.setPath(imageCropUri.getPath());
                    picMap.put(currentImageView.getId(), picModel);
                    Log.e(TAG, "==================>" + imageCropUri.getPath());
                    break;
            }
        }
    }

    /*文件上传接口*/
    private void requestUploadPicInterface(final int viewId, final ApplyPictureCtlActModel pictureCtlActModel) {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "user_register_upload");
        model.putFile("file", new File(pictureCtlActModel.getPath()));

        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<ApplyPictureCtlActModel>() {


            @Override
            public void onFinish() {
                totalPic--;
                if (totalPic == 0) {
                    if (nDialog != null) {
                        nDialog.dismiss();
                    }

                    if (picMap.values().isEmpty()) {
                        //上传完毕，执行申请
                        requestHHR();
                    } else {
                        SDToast.showToast("有图片上传失败，请检查网络后重新提交");
                    }
                }
            }

            @Override
            public void onSuccess(ApplyPictureCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, null)) {
                    switch (actModel.getStatus()) {
                        case 1:
                            picUrlMap.put(viewId, actModel.getUrl());
                            picMap.remove(viewId);
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
        } else if (!picMap.containsKey(companyPic1.getId()) && !picUrlMap.containsKey(companyPic1.getId())) {
            SDToast.showToast("请选择营业执照");
            return;
        } else if (!picMap.containsKey(companyPic4.getId()) && !picUrlMap.containsKey(companyPic4.getId())) {
            SDToast.showToast("请选择门店照片");
            return;
        } else if (!isAgree.isChecked()) {
            SDToast.showToast("请阅读城市合作协议后，勾选同意");
            return;
        }

        //开始上传图片
        totalPic = picMap.keySet().size();
        if(totalPic > 0) {
            nDialog = SDDialogUtil.showLoading("图片上传中...");
            Collection<Integer> collection = picMap.keySet();
            for (Integer viewId : collection) {
                Log.e(TAG, "准备上传的图片：" + viewId + ":" + picMap.get(viewId));
                requestUploadPicInterface(viewId, picMap.get(viewId));
            }
        } else {
            //没有修改图片，直接请求数据
            requestHHR();
        }
    }

    //申请企业合伙人Step.2
    private void requestHHR() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "applyPartnerSupplierShop");
        model.put("supplier_id", ((ApplyHHRActivity) getActivity()).submit_id);//商户的id
        model.put("area_id", ((ApplyHHRActivity) getActivity()).city.getId());//地区的id
        model.put("supplier_name", storeName.getText().toString());//商户名称
        model.put("supplier_address", address.getText().toString());//商户地址
        model.put("deal_cate", ((ApplyServiceTypeModel) spinner1.getTag()).getId());//第一类
        model.put("deal_child_cate", ((ApplyServiceTypeModel) spinner2.getTag()).getId());//第二类
        model.put("tel", shopPhone.getText().toString());//商户电话
        model.put("contact_name", companyOwner.getText().toString());//法人姓名
        model.put("h_bank_user", bankAccountName.getText().toString());//开户行户名
        model.put("h_bank_name", bankName.getText().toString());//开户行名称
        model.put("h_bank_info", bankAccount.getText().toString());//开户行账号
        model.put("mobile", mobile.getText().toString());//手机号
        model.put("h_name", companyName.getText().toString());//企业名称
        model.put("open_time", shopTime.getText().toString());//营业时间
        model.put("h_tel", contactPhone.getText().toString());//联系电话
        /*图片*/
        model.put("h_license", picUrlMap.get(companyPic1.getId()));//营业执照
        model.put("h_other_license", picUrlMap.get(companyPic2.getId()));//其他资质
        model.put("h_supplier_logo", picUrlMap.get(companyPic3.getId()));//商户logo
        model.put("h_supplier_image", picUrlMap.get(companyPic4.getId()));//门店图片

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
                            picMap.clear();
                            picUrlMap.clear();
                            SDEventManager.post(EnumEventTag.EXIT_APP.ordinal());
                            Intent intent = new Intent(getContext(), ApplyPayActivity.class);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.HYD);
                            intent.putExtra(Constant.ExtraConstant.EXTRA_ID, actModel.getOrderId());
                            intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, actModel.getPrice());
                            startActivity(intent);
                            //finish();
                            /*requestLoginInterface();*/
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
}
