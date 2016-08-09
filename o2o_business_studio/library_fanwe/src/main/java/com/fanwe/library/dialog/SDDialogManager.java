package com.fanwe.library.dialog;

import android.app.Dialog;

import com.fanwe.library.dialog.SDDialogProgress.SDDialogProgressListener;
import com.fanwe.library.utils.SDHandlerUtil;
import com.fanwe.library.utils.SDViewUtil;

public class SDDialogManager {

    private static SDDialogProgress mProgress = null;

    public static SDDialogProgress showProgressDialog(String msg) {
        return showProgressDialog(msg, null);
    }

    public static SDDialogProgress showProgressDialog(SDDialogProgressListener listener) {
        return showProgressDialog(null, listener);
    }

    public static SDDialogProgress showProgressDialog(final String msg, final SDDialogProgressListener listener) {
        dismissProgressDialog();
        mProgress = new SDDialogProgress();
        mProgress.setTextMsg(msg);
        mProgress.setmListener(listener);
        showDialog(mProgress);
        return mProgress;
    }

    public static void dismissProgressDialog() {
        dismissDialog(mProgress);
    }

    /**
     * 可以在任意线程调用显示dialog
     *
     * @param dialog
     */
    public static void showDialog(final Dialog dialog) {
        if (dialog != null) {
            if (SDViewUtil.isUIThread()) {
                showDialogMainThread(dialog);
            } else {
                SDHandlerUtil.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showDialogMainThread(dialog);
                    }
                });
            }
        }
    }

    /**
     * 可以在任意线程调用隐藏dialog
     *
     * @param dialog
     */
    public static void dismissDialog(final Dialog dialog) {
        if (dialog != null) {
            if (SDViewUtil.isUIThread()) {
                dismissDialogMainThread(dialog);
            } else {
                SDHandlerUtil.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        dismissDialogMainThread(dialog);
                    }
                });
            }
        }
    }

    private static void showDialogMainThread(Dialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
    }

    private static void dismissDialogMainThread(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
