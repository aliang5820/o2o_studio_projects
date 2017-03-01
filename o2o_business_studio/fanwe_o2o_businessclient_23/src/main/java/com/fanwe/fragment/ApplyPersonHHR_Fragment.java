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
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.ApplyHHRActivity;
import com.fanwe.ApplyPayActivity;
import com.fanwe.LoginActivity;
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
import java.util.Locale;
import java.util.Map;

/**
 * Created by Edison on 2016/9/7.
 * 个人合伙人
 */
public class ApplyPersonHHR_Fragment extends BaseFragment {

    private static final String TAG = ApplyHHR_Fragment.class.getName();

    @ViewInject(R.id.personIsAgree)
    private CheckBox personIsAgree; //是否同意合作协议

    @ViewInject(R.id.personName)
    private TextView personName; //姓名

    @ViewInject(R.id.personContactPhone)
    private TextView personContactPhone; //联系电话

    @ViewInject(R.id.personMobile)
    private TextView personMobile; //手机号

    @ViewInject(R.id.personBankAccountName)
    private TextView personBankAccountName; //开户行户名

    @ViewInject(R.id.personBankName)
    private TextView personBankName; //开户行名称

    @ViewInject(R.id.personBankAccount)
    private TextView personBankAccount; //开户行账号

    @ViewInject(R.id.companyPic1)
    private ImageView companyPic1;

    @ViewInject(R.id.companyPic2)
    private ImageView companyPic2;

    private Uri imageUri;
    private Uri imageCropUri;
    private ImageView currentImageView;//当前选择的ImageView
    private Map<Integer, ApplyPictureCtlActModel> picMap = new HashMap<>();
    private Map<Integer, String> picUrlMap = new HashMap<>();
    private int totalPic;

    private Dialog nDialog;
    private ApplyInfoModel applyInfoModel;

    public static ApplyPersonHHR_Fragment getInstance(ApplyInfoModel applyInfoModel) {
        ApplyPersonHHR_Fragment fragment = new ApplyPersonHHR_Fragment();
        if (applyInfoModel != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.ExtraConstant.EXTRA_MODEL, applyInfoModel);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.act_apply_person_hhr;
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

        findViewById(R.id.confirmBtn).setOnClickListener(this);
        companyPic1.setOnClickListener(this);
        companyPic2.setOnClickListener(this);
    }

    //显示已经存在的数据
    private void showData() {
        if (applyInfoModel != null) {
            //姓名
            personName.setText(applyInfoModel.getH_name());
            //手机号
            personMobile.setText(applyInfoModel.getMobile());
            //联系电话
            personContactPhone.setText(applyInfoModel.getH_tel());
            //开户行户名
            personBankAccountName.setText(applyInfoModel.getH_bank_user());
            //开户行名称
            personBankName.setText(applyInfoModel.getH_bank_name());
            //开户行账号
            personBankAccount.setText(applyInfoModel.getH_bank_info());
            //身份证照片
            ImageLoaderManager.getImageLoader().displayImage(ApkConstant.SERVER_IMG_URL + applyInfoModel.getH_license().substring(1), companyPic1);
            picUrlMap.put(companyPic1.getId(), applyInfoModel.getH_license());
            //个人照片
            ImageLoaderManager.getImageLoader().displayImage(ApkConstant.SERVER_IMG_URL + applyInfoModel.getH_other_license().substring(1), companyPic2);
            picUrlMap.put(companyPic2.getId(), applyInfoModel.getH_other_license());
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
                    /*imageCropUri = getTargetImageUri(false);
                    cropImg(imageUri, imageCropUri);*/
                    ImageLoaderManager.getImageLoader().displayImage(imageUri.toString(), currentImageView);
                    ApplyPictureCtlActModel picModel = new ApplyPictureCtlActModel();
                    picModel.setPath(imageUri.getPath());
                    picMap.put(currentImageView.getId(), picModel);
                    Log.e(TAG, "==================>" + imageUri.getPath());
                    break;
                case Constant.RESULT_CAMERA_ONLY:
                    //拍照
                    /*imageCropUri = getTargetImageUri(false);
                    cropImg(imageUri, imageCropUri);*/
                    ImageLoaderManager.getImageLoader().displayImage(imageUri.toString(), currentImageView);
                    ApplyPictureCtlActModel picModel1 = new ApplyPictureCtlActModel();
                    picModel1.setPath(imageUri.getPath());
                    picMap.put(currentImageView.getId(), picModel1);
                    Log.e(TAG, "==================>" + imageUri.getPath());
                    break;
                case Constant.RESULT_CROP_PATH_RESULT:
                    //获取裁剪结果
                    currentImageView.setImageURI(imageCropUri);
                    //存储起来，准备上传
                    ApplyPictureCtlActModel picModel2 = new ApplyPictureCtlActModel();
                    picModel2.setPath(imageCropUri.getPath());
                    picMap.put(currentImageView.getId(), picModel2);
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

                    //图片上传请求执行结束，检查是否有失败的请求
                    if (picMap.values().isEmpty()) {
                        //上传完毕，执行申请
                        requestPersonHHR();
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

    //申请个人合伙人Step.1
    private void requestRole() {
        if (TextUtils.isEmpty(personName.getText())) {
            SDToast.showToast("请填写姓名");
            personName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(personMobile.getText())) {
            SDToast.showToast("请填写手机号码");
            personMobile.requestFocus();
            return;
        } else if (TextUtils.isEmpty(personContactPhone.getText())) {
            SDToast.showToast("请填写联系电话");
            personContactPhone.requestFocus();
            return;
        } else if (TextUtils.isEmpty(personBankAccountName.getText())) {
            SDToast.showToast("请填写开户行户名");
            personBankAccountName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(personBankName.getText())) {
            SDToast.showToast("请填写开户行名称");
            personBankName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(personBankAccount.getText())) {
            SDToast.showToast("请填写开户行账号");
            personBankAccount.requestFocus();
            return;
        } else if (!picMap.containsKey(companyPic1.getId()) && !picUrlMap.containsKey(companyPic1.getId())) {
            SDToast.showToast("请选择身份证照片");
            return;
        } else if (!picMap.containsKey(companyPic2.getId()) && !picUrlMap.containsKey(companyPic2.getId())) {
            SDToast.showToast("请选择个人照片(免冠照)");
            return;
        } else if (!personIsAgree.isChecked()) {
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
            requestPersonHHR();
        }
    }

    ////申请个人合伙人Step.2
    private void requestPersonHHR() {
        RequestModel model = new RequestModel();
        model.putCtlAct("biz_member", "applyPartnerpersonShop");
        model.put("supplier_id", ((ApplyHHRActivity) getActivity()).submit_id);//商户的id
        model.put("area_id", ((ApplyHHRActivity) getActivity()).city.getId());//地区的id

        model.put("contact_name", personName.getText().toString());//姓名
        model.put("mobile", personMobile.getText().toString());//手机号
        model.put("h_tel", personContactPhone.getText().toString());//联系电话
        model.put("h_bank_user", personBankAccountName.getText().toString());//开户行户名
        model.put("h_bank_name", personBankName.getText().toString());//开户行名称
        model.put("h_bank_info", personBankAccount.getText().toString());//开户行账号
        //图片
        model.put("h_license", picUrlMap.get(companyPic1.getId()));//身份证照片
        model.put("h_other_license", picUrlMap.get(companyPic2.getId()));//个人照片

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
                            /*if(actModel.getIs_pay() == 0) {
                                Intent intent = new Intent(getContext(), ApplyPayActivity.class);
                                intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Apply.HHR_GR);
                                intent.putExtra(Constant.ExtraConstant.EXTRA_ID, actModel.getOrderId());
                                intent.putExtra(Constant.ExtraConstant.EXTRA_MORE, actModel.getOrder_sn());
                                intent.putExtra(Constant.ExtraConstant.EXTRA_OTHER_ID, ((ApplyHHRActivity) getActivity()).submit_id);
                                intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, actModel.getPrice());
                                startActivity(intent);
                            } else {
                                SDToast.showToast("已重新提交审核，请耐心等待");
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }*/
                            SDToast.showToast("已提交审核，请耐心等待");
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
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
}
