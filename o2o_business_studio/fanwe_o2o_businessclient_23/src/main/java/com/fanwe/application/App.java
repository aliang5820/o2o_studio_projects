package com.fanwe.application;

import android.app.Application;
import android.content.Intent;

import com.fanwe.businessclient.R;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.dao.UserModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.SDLibrary;
import com.fanwe.library.config.SDLibraryConfig;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.LocalUserModel;
import com.fanwe.umeng.UmengPushManager;
import com.pingplusplus.android.PingppLog;
import com.sunday.eventbus.SDEventManager;

/**
 * App
 *
 * @author yhz
 * @create time 2014-7-28
 */
public class App extends Application {
    private static App mApp;
    private LocalUserModel mLocalUserModel;

    public Intent mPushIntent;

    public static App getApp() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mApp = this;
        ImageLoaderManager.getImageLoader();
        initSDLibrary();
        this.mLocalUserModel = UserModelDao.getModel();
        UmengPushManager.init(this);
        PingppLog.DEBUG = true;
    }

    public LocalUserModel getmLocalUser() {
        return mLocalUserModel;
    }

    public void setmLocalUser(LocalUserModel localUser) {
        if (localUser != null) {
            UserModelDao.saveModel(localUser);
            this.mLocalUserModel = localUser;
        } else {
            this.mLocalUserModel = null;
        }
    }

    private void initSDLibrary() {
        SDLibrary.getInstance().init(this);

        SDLibraryConfig config = new SDLibraryConfig();

        config.setmMainColor(getResources().getColor(R.color.main_color));
        config.setmMainColorPress(getResources().getColor(R.color.main_color_press));

        config.setmTitleColor(getResources().getColor(R.color.bg_title_bar));
        config.setmTitleColorPressed(getResources().getColor(R.color.bg_title_bar_pressed));
        config.setmTitleHeight(getResources().getDimensionPixelOffset(R.dimen.height_title_bar));

        config.setmStrokeColor(getResources().getColor(R.color.stroke));
        config.setmStrokeWidth(SDViewUtil.dp2px(1));

        config.setmCornerRadius(getResources().getDimensionPixelOffset(R.dimen.corner));
        config.setmGrayPressColor(getResources().getColor(R.color.gray_press));

        SDLibrary.getInstance().initConfig(config);
    }

    public void clearAppsLocalUserModel() {
        UserModelDao.deleteAllModel();
        this.mLocalUserModel = null;
    }

    public void exitApp(boolean isBackground) {
        releaseResource();
        SDEventManager.post(EnumEventTag.EXIT_APP.ordinal());
        if (isBackground) {

        } else {
            System.exit(0);
        }
    }

    private void releaseResource() {
    }

}
