package com.fanwe.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.businessclient.R;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.listener.RefreshActListener;
import com.fanwe.model.BaseCtlActModel;
import com.fanwe.model.Biz_verifyActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.utils.SDDialogUtil;
import com.fanwe.utils.SDInterfaceUtil;
import com.fanwe.utils.SDToast;
import com.fanwe.utils.SDUIUtil;

/**
 * @author yhz
 * @create time 2014-8-1
 */
public class Frag_Tab0_VerifyDialog extends Dialog implements View.OnClickListener {

    private RefreshActListener mDialog_Back_Frag_Tab0 = null;

    private Button mCancel = null, mConfirm = null;

    private Activity mActivity = null;

    private TextView mContent = null;

    private ImageView mDelete = null, mAdd = null, mDismiss = null;

    private EditText mNumber = null;

    private int mMinNum = 1, mMaxNum = 0, mDefaultNum = 1;

    private String mCoupon_pwd = null;

    private Biz_verifyActModel mActModel = null;

    public Frag_Tab0_VerifyDialog(Context context, int theme, Biz_verifyActModel actModel, String coupon_pwd, RefreshActListener dialog_Back_Frag_Tab0) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.mActivity = (Activity) context;
        this.mCoupon_pwd = coupon_pwd;
        this.mActModel = actModel;
        this.mDialog_Back_Frag_Tab0 = dialog_Back_Frag_Tab0;
    }

    private void register() {
        mCancel = (Button) findViewById(R.id.fragtab0_verifydialog_btn_cancel);
        mCancel.setOnClickListener(this);
        mConfirm = (Button) findViewById(R.id.fragtab0_verifydialog_btn_confirm);
        mConfirm.setOnClickListener(this);
        mDismiss = (ImageView) findViewById(R.id.fragtab0_verifydialog_tv_dismiss);
        mDismiss.setOnClickListener(this);
        mDelete = (ImageView) findViewById(R.id.fragtab0_verifydialog_tv_delete);
        mDelete.setOnClickListener(this);
        mAdd = (ImageView) findViewById(R.id.fragtab0_verifydialog_tv_add);
        mAdd.setOnClickListener(this);
        mContent = (TextView) findViewById(R.id.fragtab0_verifydialog_tv_content);
        mNumber = (EditText) findViewById(R.id.fragtab0_verifydialog_et_number);
    }

    private void init() {
        mMaxNum = mActModel.getData().getCount();
        mNumber.setText(String.valueOf(mDefaultNum));
        mContent.setText(mActModel.getInfo());
    }

    private void setDialogSize() {
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = mActivity.getWindowManager();
        // Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (SDUIUtil.getScreenWidth(mActivity) * 0.75); //
        p.width = (int) (SDUIUtil.getScreenWidth(mActivity) * 0.8); //
        dialogWindow.setAttributes(p);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_tab0_verify_dialog);
        register();
        init();
        setDialogSize();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.fragtab0_verifydialog_btn_cancel:
                dismiss();
                break;
            case R.id.fragtab0_verifydialog_btn_confirm:
                clickConfirm();
                break;
            case R.id.fragtab0_verifydialog_tv_dismiss:
                dismiss();
                break;
            case R.id.fragtab0_verifydialog_tv_delete:
                clickDelete();
                break;
            case R.id.fragtab0_verifydialog_tv_add:
                clickAdd();
                break;
        }
    }

    private void clickConfirm() {
        // TODO Auto-generated method stub
        requestBiz_use_coupon();
    }

    private void clickAdd() {
        // TODO Auto-generated method stub
        int num = Integer.valueOf(mNumber.getText().toString());
        if (num < mMaxNum) {
            mNumber.setText(String.valueOf(num + 1));
        } else {
            return;
        }
    }

    private void clickDelete() {
        // TODO Auto-generated method stub
        int num = Integer.valueOf(mNumber.getText().toString());
        if (num > mDefaultNum) {
            mNumber.setText(String.valueOf(num - 1));
        } else {
            return;
        }
    }

    private void requestBiz_use_coupon() {

        RequestModel model = new RequestModel();
        model.putCtlAct("biz_dealv", "use_coupon");
        model.put("location_id", mActModel.getData().getLocation_id());
        model.put("coupon_pwd", mCoupon_pwd);
        model.put("coupon_use_count", Integer.valueOf(mNumber.getText().toString()));

        SDRequestCallBack<BaseCtlActModel> handler = new SDRequestCallBack<BaseCtlActModel>() {
            private Dialog nDialog;

            @Override
            public void onFinish() {
                if (nDialog != null) {
                    nDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(BaseCtlActModel actModel) {
                if (!SDInterfaceUtil.dealactModel(actModel, mActivity)) {
                    switch (actModel.getStatus()) {
                        case 0:
                            break;
                        case 1:
                            if (!TextUtils.isEmpty(actModel.getInfo())) {
                                SDToast.showToast(actModel.getInfo());
                            }

                            dismiss();
                            mDialog_Back_Frag_Tab0.refreshActivity();

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

}
