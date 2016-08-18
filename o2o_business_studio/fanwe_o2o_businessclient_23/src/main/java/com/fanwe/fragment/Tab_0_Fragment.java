package com.fanwe.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.MyCaptureActivity;
import com.fanwe.adapter.Biz_dealvAdapter;
import com.fanwe.businessclient.R;
import com.fanwe.customview.Frag_Tab0_VerifyDialog;
import com.fanwe.customview.SquareButton;
import com.fanwe.customview.SquareButton.OnGetTextOnClick;
import com.fanwe.event.EnumEventTag;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.listener.RefreshActListener;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.Biz_dealvCtlIndexActModel;
import com.fanwe.model.Biz_verifyActModel;
import com.fanwe.model.LocationModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.sunday.eventbus.SDBaseEvent;

import java.util.ArrayList;

/**
 * 验证
 */
public class Tab_0_Fragment extends BaseFragment implements OnClickListener {
    private final static int SCANNIN_GREQUEST_CODE = 1;

    private SquareButton mBtn0, mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8, mBtn9;
    private TextView mTvTitle, mTvGroupon, mTvCoupons, mTvActivity;
    private ImageButton mIb_delete;
    private LinearLayout mLl_code;
    private LinearLayout mLlMode;
    private Button mBtn_verify;
    private EditText mEt_num;
    private PopupWindow mPopupwindow;
    private Spinner mSpinner;

    private Biz_dealvAdapter mSpinnerAdapter;
    private ArrayList<LocationModel> arrayListModel = new ArrayList<LocationModel>();

    private String mCurrentCtl;
    private int mLocation_id;

    @Override
    protected int onCreateContentView() {
        return R.layout.m_frag_tab_0;
    }

    private void register(View view) {

        mEt_num = (EditText) view.findViewById(R.id.frag_tab0_et_num);
        mTvTitle = (TextView) view.findViewById(R.id.frag_tab0_tv_title);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);

        mIb_delete = (ImageButton) view.findViewById(R.id.frag_tab0_ib_delete);
        mIb_delete.setOnClickListener(this);

        mLl_code = (LinearLayout) view.findViewById(R.id.ll_code);
        mLl_code.setOnClickListener(this);

        mLlMode = (LinearLayout) view.findViewById(R.id.ll_mode);
        mLlMode.setOnClickListener(this);

        mBtn_verify = (Button) view.findViewById(R.id.frag_tab0_btn_verify);
        mBtn_verify.setOnClickListener(this);

        mBtn0 = (SquareButton) view.findViewById(R.id.fra_tab0_btn0);
        mBtn0.setOnGetTextOnClick(onGetTextOnClick);
        mBtn1 = (SquareButton) view.findViewById(R.id.fra_tab0_btn1);
        mBtn1.setOnGetTextOnClick(onGetTextOnClick);
        mBtn2 = (SquareButton) view.findViewById(R.id.fra_tab0_btn2);
        mBtn2.setOnGetTextOnClick(onGetTextOnClick);
        mBtn3 = (SquareButton) view.findViewById(R.id.fra_tab0_btn3);
        mBtn3.setOnGetTextOnClick(onGetTextOnClick);
        mBtn4 = (SquareButton) view.findViewById(R.id.fra_tab0_btn4);
        mBtn4.setOnGetTextOnClick(onGetTextOnClick);
        mBtn5 = (SquareButton) view.findViewById(R.id.fra_tab0_btn5);
        mBtn5.setOnGetTextOnClick(onGetTextOnClick);
        mBtn6 = (SquareButton) view.findViewById(R.id.fra_tab0_btn6);
        mBtn6.setOnGetTextOnClick(onGetTextOnClick);
        mBtn7 = (SquareButton) view.findViewById(R.id.fra_tab0_btn7);
        mBtn7.setOnGetTextOnClick(onGetTextOnClick);
        mBtn8 = (SquareButton) view.findViewById(R.id.fra_tab0_btn8);
        mBtn8.setOnGetTextOnClick(onGetTextOnClick);
        mBtn9 = (SquareButton) view.findViewById(R.id.fra_tab0_btn9);
        mBtn9.setOnGetTextOnClick(onGetTextOnClick);
    }

    @Override
    protected void init() {
        register(getView());
        setTitel();
        initSpinner();
        requestCtl("biz_dealv");
    }

    @SuppressLint("NewApi")
    private void initSpinner() {
        mSpinnerAdapter = new Biz_dealvAdapter(arrayListModel, getActivity());
        //mSpinner.setDropDownVerticalOffset(SDUIUtil.dp2px(getActivity(), 1));
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    private void setTitel() {
        mTvTitle.setText("团购券验证");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_code:
                clickCode();
                break;
            case R.id.ll_mode:
                clickMode(v);
                break;
            case R.id.frag_tab0_btn_verify:
                clickVerify();
                break;
            case R.id.frag_tab0_ib_delete:
                clickdelete();
                break;
            case R.id.tv_groupon:
                clickTvGroupon();
                break;
            case R.id.tv_coupons:
                clickTvCoupons();
                break;
            case R.id.tv_activity:
                clickTvActivity();
                break;
        }
    }

    private void clickCode() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MyCaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    private void clickMode(View v) {
        if (mPopupwindow == null) {
            initmPopupWindowView(v);
        } else {
            if (!mPopupwindow.isShowing()) {
                mPopupwindow.showAsDropDown(v, 0, 10);
            } else {
                closePopupView();
            }
        }

    }

    private void closePopupView() {
        if (mPopupwindow != null && mPopupwindow.isShowing()) {
            mPopupwindow.dismiss();
        }
    }

    public void initmPopupWindowView(View v) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.popupview_frag_tab0, null, false);
        mPopupwindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mPopupwindow.setOutsideTouchable(true);
        mPopupwindow.setFocusable(true);
        mPopupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupwindow.showAsDropDown(v, 0, 10);
        mTvGroupon = (TextView) view.findViewById(R.id.tv_groupon);
        mTvGroupon.setOnClickListener(this);
        mTvCoupons = (TextView) view.findViewById(R.id.tv_coupons);
        mTvCoupons.setOnClickListener(this);
        mTvActivity = (TextView) view.findViewById(R.id.tv_activity);
        mTvActivity.setOnClickListener(this);

    }

    private void clickVerify() {
        requestCheckCtlAct(mCurrentCtl);
    }

    private void requestCheckCtlAct(final String ctl) {

        final String coupon_pwd = mEt_num.getText().toString().replaceAll(" ", "");

        if (coupon_pwd.equals("")) {
            SDToast.showToast("请输入内容", Toast.LENGTH_SHORT);
            return;
        }

        RequestModel model = new RequestModel();
        if (ctl.equals("biz_dealv")) {
            model.putCtlAct(ctl, "check_coupon");
            model.put("coupon_pwd", coupon_pwd);
        } else if (ctl.equals("biz_youhuiv")) {
            model.putCtlAct(ctl, "check_youhui");
            model.put("youhui_sn", coupon_pwd);
        } else if (ctl.equals("biz_eventv")) {
            model.putCtlAct(ctl, "check_event");
            model.put("event_sn", coupon_pwd);
        } else {
            return;
        }
        model.put("location_id", mLocation_id);

        SDRequestCallBack<Biz_verifyActModel> handler = new SDRequestCallBack<Biz_verifyActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(Biz_verifyActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, getActivity())) {
                    if (!TextUtils.isEmpty(actModel.getInfo())) {
                        SDToast.showToast(actModel.getInfo());
                    }
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:

                            if (ctl.equals("biz_dealv")) {
                                Frag_Tab0_VerifyDialog dialog = new Frag_Tab0_VerifyDialog(getActivity(), R.style.Fra_Tab0_Dialog, actModel, coupon_pwd,
                                        dialog_Back_Frag_Tab0);
                                dialog.show();
                            } else {

                                SDDialogUtil.confirm("温馨提示", actModel.getInfo(), new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestUseBizCtlAct(ctl, coupon_pwd);
                                    }
                                }, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                            break;

                        default:
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中...");
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    private void clickdelete() {
        final Editable edit = mEt_num.getEditableText();
        if (edit.length() > 0) {
            if (edit.length() == 6 || edit.length() == 10) {
                edit.delete(edit.length() - 2, edit.length());
            } else {
                edit.delete(edit.length() - 1, edit.length());
            }
        }
    }

    private void clickTvGroupon() {
        String title = mTvGroupon.getText().toString();
        mTvTitle.setText(title);
        requestCtl("biz_dealv");
        closePopupView();
    }

    private void requestCtl(String ctl) {
        mCurrentCtl = ctl;
        RequestModel model = new RequestModel();
        model.putCtl(ctl);

        SDRequestCallBack<Biz_dealvCtlIndexActModel> handler = new SDRequestCallBack<Biz_dealvCtlIndexActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(Biz_dealvCtlIndexActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, getActivity())) {
                    if (!TextUtils.isEmpty(actModel.getInfo())) {
                        SDToast.showToast(actModel.getInfo());
                    }
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            if (actModel.getIs_auth() == 1) {
                                if (actModel.getLocation_list() != null && actModel.getLocation_list().size() > 0) {
                                    arrayListModel.clear();
                                    arrayListModel.addAll(actModel.getLocation_list());
                                    mSpinnerAdapter.updateData(arrayListModel);
                                }
                            } else {
                                arrayListModel.clear();
                                LocationModel locationModel = new LocationModel();
                                locationModel.setId(0);
                                locationModel.setName("请选择门店");
                                arrayListModel.add(locationModel);
                                mSpinnerAdapter.updateData(arrayListModel);
                            }
                            break;

                        default:
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中...");
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    private void clickTvCoupons() {
        String title = mTvCoupons.getText().toString();
        mTvTitle.setText(title);
        requestCtl("biz_youhuiv");
        closePopupView();
    }

    private void clickTvActivity() {
        String title = mTvActivity.getText().toString();
        mTvTitle.setText(title);
        requestCtl("biz_eventv");
        closePopupView();
    }

    private void requestUseBizCtlAct(String ctl, String coupon_pwd) {
        RequestModel model = new RequestModel();

        if (ctl.equals("biz_dealv")) {
            model.putCtlAct(ctl, "use_coupon");
            model.put("coupon_pwd", coupon_pwd);
        } else if (ctl.equals("biz_youhuiv")) {
            model.putCtlAct(ctl, "use_youhui");
            model.put("youhui_sn", coupon_pwd);
        } else if (ctl.equals("biz_eventv")) {
            model.putCtlAct(ctl, "use_event");
            model.put("event_sn", coupon_pwd);
        } else {
            return;
        }
        model.put("location_id", mLocation_id);

        SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>() {
            private Dialog nDialog = null;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(BaseCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, getActivity())) {
                    if (!TextUtils.isEmpty(actModel.getInfo())) {
                        SDToast.showToast(actModel.getInfo());
                    }
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            break;

                        default:
                            break;
                    }
                }

            }

            @Override
            public void onStart() {
                nDialog = SDDialogUtil.showLoading("加载中...");
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler);
    }

    private OnGetTextOnClick onGetTextOnClick = new OnGetTextOnClick() {

        @Override
        public void getText(String text) {
            int mEt_num_length = mEt_num.getText().toString().length();

            if (mEt_num_length == 4 || mEt_num_length == 9) {
                mEt_num.append(" " + text);
            } else {
                mEt_num.append(text);
            }
        }
    };

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        if (event.getTagInt() == EnumEventTag.SCAN_SUCCESS.ordinal()) {
            String code = (String) event.getData();
            if (code.contains("tuan")) {
                mCurrentCtl = "biz_dealv";
                code = code.replace("tuan", "");
                mTvTitle.setText("团购券验证");
                mEt_num.setText(code);
            } else if (code.contains("youhui")) {
                mTvTitle.setText("优惠券验证");
                mCurrentCtl = "biz_youhuiv";
                code = code.replace("youhui", "");
                mEt_num.setText(code);
            } else if (code.contains("event")) {
                mTvTitle.setText("活动验证");
                mCurrentCtl = "biz_eventv";
                code = code.replace("event", "");
                mEt_num.setText(code);
            } else {
                SDToast.showToast("暂不支持我们系统订单外的二维码");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closePopupView();
    }

    private RefreshActListener dialog_Back_Frag_Tab0 = new RefreshActListener() {

        @Override
        public void refreshActivity() {
            mEt_num.setText("");
        }

    };

    class SpinnerSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
            if (arrayListModel != null && arrayListModel.size() > 0) {
                mLocation_id = arrayListModel.get(arg2).getId();
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

}
