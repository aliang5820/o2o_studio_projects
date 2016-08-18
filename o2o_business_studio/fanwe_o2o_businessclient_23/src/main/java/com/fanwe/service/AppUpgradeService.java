package com.fanwe.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.fanwe.businessclient.R;
import com.fanwe.common.HttpManagerX;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.listener.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.RequestModel;
import com.fanwe.model.Version_indexActModel;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.OtherUtils;

import java.io.File;

/**
 * 更新服务
 *
 * @author yhz
 */
public class AppUpgradeService extends Service {
    public static final String EXTRA_SERVICE_START_TYPE = "extra_service_start_type";
    private static final int DEFAULT_START_TYPE = 0;
    private int mStartType = DEFAULT_START_TYPE; // 0代表启动app时候程序自己检测，1代表用户手动检测版本
    public static final int mNotificationId = 100;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private PendingIntent mPendingIntent;

    private Version_indexActModel mModel;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getIntentData(intent);
        requestCheckVersion();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getIntentData(Intent intent) {
        if (intent != null) {
            mStartType = intent.getIntExtra(EXTRA_SERVICE_START_TYPE, DEFAULT_START_TYPE);
        }
    }

    private void requestCheckVersion() {
        PackageInfo info = SDPackageUtil.getCurrentPackageInfo();

        RequestModel model = new RequestModel();
        model.putCtl("biz_version");
        model.put("dev_type", "android");
        model.put("version", info.versionCode);
        SDRequestCallBack<Version_indexActModel> handler = new SDRequestCallBack<Version_indexActModel>() {

            @Override
            public void onStart() {
                if (mStartType == 1) {
                    SDDialogManager.showProgressDialog("正在检测新版本");
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (actModel.getStatus() == 1) {
                    mModel = actModel;
                    dealResult();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
                stopSelf();
            }
        };

        InterfaceServer.getInstance().requestInterface(model, handler);

    }

    protected void dealResult() {
        if (mModel == null) {
            return;
        }

        PackageInfo info = SDPackageUtil.getCurrentPackageInfo();
        int curVersion = info.versionCode;

        if (curVersion < mModel.getServerVersion()) // 需要升级
        {
            String downloadUrl = mModel.getFilename();
            if (!TextUtils.isEmpty(downloadUrl)) {
                // TODO 弹窗口提示升级
                showDialogUpgrade();
            } else {
                SDToast.showToast("未找到下载地址");
            }
        } else {
            if (mStartType == 1) // 用户手动检测版本
            {
                SDToast.showToast("当前已是最新版本!");
            }
        }

    }

    private void showDialogUpgrade() {
        if (mModel == null) {
            return;
        }

        SDDialogConfirm dialog = new SDDialogConfirm();
        if (mModel.getForced_upgrade() == 1) {
            dialog.setTextCancel(null).setCancelable(false);
        }
        dialog.setTextContent(mModel.getAndroid_upgrade()).setTextTitle("新版本");
        dialog.setmListener(new SDDialogCustomListener() {

            @Override
            public void onDismiss(SDDialogCustom dialog) {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                startDownload();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
            }
        }).show();
    }

    private void initNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification = new Notification();
        mNotification.icon = R.drawable.icon;
        mNotification.tickerText = mModel.getLocalFileName() + "正在下载中";
        mNotification.contentView = new RemoteViews(getApplication().getPackageName(), R.layout.service_download_view);

        Intent completingIntent = new Intent();
        completingIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        completingIntent.setClass(getApplication().getApplicationContext(), AppUpgradeService.class);
        mPendingIntent = PendingIntent.getActivity(AppUpgradeService.this, R.string.app_name, completingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.contentIntent = mPendingIntent;

        mNotification.contentView.setTextViewText(R.id.upgradeService_tv_appname, mModel.getLocalFileName());
        mNotification.contentView.setTextViewText(R.id.upgradeService_tv_status, "下载中");
        mNotification.contentView.setProgressBar(R.id.upgradeService_pb, 100, 0, false);
        mNotification.contentView.setTextViewText(R.id.upgradeService_tv, "0%");

        mNotificationManager.cancel(mNotificationId);
        mNotificationManager.notify(mNotificationId, mNotification);

    }

    private void startDownload() {
        final String target = OtherUtils.getDiskCacheDir(getApplicationContext(), "") + mModel.getLocalFileName();
        HttpManagerX.getHttpUtils().download(mModel.getFilename(), target, true, new RequestCallBack<File>() {

            @Override
            public void onStart() {
                initNotification();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                int progress = (int) ((current * 100) / (total));
                mNotification.contentView.setProgressBar(R.id.upgradeService_pb, 100, progress, false);
                mNotification.contentView.setTextViewText(R.id.upgradeService_tv, progress + "%");
                mNotificationManager.notify(mNotificationId, mNotification);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                dealDownloadSuccess(responseInfo.result.getAbsolutePath());
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                if (error != null) {
                    if (error.getExceptionCode() == 416) // 文件已经存在
                    {
                        dealDownloadSuccess(target);
                        return;
                    }
                }
                SDToast.showToast("下载失败");
            }

        });

    }

    public void dealDownloadSuccess(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            mNotification.contentView.setViewVisibility(R.id.upgradeService_pb, View.GONE);
            mNotification.defaults = Notification.DEFAULT_SOUND;
            mNotification.contentIntent = mPendingIntent;
            mNotification.contentView.setTextViewText(R.id.upgradeService_tv_status, "下载完成");
            mNotification.contentView.setTextViewText(R.id.upgradeService_tv, "100%");
            mNotificationManager.notify(mNotificationId, mNotification);
            mNotificationManager.cancel(mNotificationId);
            SDPackageUtil.installApkPackage(filePath);
            SDToast.showToast("下载完成");
        }
    }

}