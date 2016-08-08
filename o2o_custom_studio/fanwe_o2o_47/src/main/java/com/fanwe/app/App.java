package com.fanwe.app;

import android.app.Application;
import android.content.Intent;

import com.fanwe.BaseActivity;
import com.fanwe.MainActivity;
import com.fanwe.baidumap.BaiduMapManager;
import com.fanwe.common.ImageLoaderManager;
import com.fanwe.config.AppConfig;
import com.fanwe.constant.ApkConstant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.dao.SettingModelDao;
import com.fanwe.event.EnumEventTag;
import com.fanwe.library.SDLibrary;
import com.fanwe.library.command.SDCommandManager;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.config.SDLibraryConfig;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.model.RuntimeConfigModel;
import com.fanwe.model.SettingModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.utils.CrashHandler;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;
import com.ta.util.netstate.TANetChangeObserver;
import com.ta.util.netstate.TANetWorkUtil.netType;
import com.ta.util.netstate.TANetworkStateReceiver;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class App extends Application implements SDEventObserver, TANetChangeObserver {

    private static App mApp = null;

    public List<Class<? extends BaseActivity>> mListClassNotFinishWhenLoginState0 = new ArrayList<Class<? extends BaseActivity>>();
    public RuntimeConfigModel mRuntimeConfig = new RuntimeConfigModel();
    public Intent mPushIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mApp = this;
        x.Ext.init(this);
        ImageLoaderManager.initImageLoader();
        initSDLibrary();
        initAppCrashHandler();
        initBaiduMap();
        SDEventManager.register(this);
        TANetworkStateReceiver.registerNetworkStateReceiver(this);
        TANetworkStateReceiver.registerObserver(this);
        initSettingModel();
        initUmengPush();
        addClassesNotFinishWhenLoginState0();
        SDCommandManager.getInstance().initialize();
        LogUtil.isDebug = ApkConstant.DEBUG;
    }

    private void initUmengPush() {
        UmengPushManager.init(this);
    }

    private void initSDLibrary() {
        SDLibrary.getInstance().init(getApplication());

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

        SDLibrary.getInstance().setConfig(config);
    }

    private void addClassesNotFinishWhenLoginState0() {
        mListClassNotFinishWhenLoginState0.add(MainActivity.class);
    }

    private void initAppCrashHandler() {
        if (!ApkConstant.DEBUG) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
    }

    private void initSettingModel() {
        // 插入成功或者数据库已经存在记录
        SettingModelDao.insertOrCreate(new SettingModel());
        mRuntimeConfig.updateIsCanLoadImage();
        mRuntimeConfig.updateIsCanPushMessage();
    }

    private void initBaiduMap() {
        BaiduMapManager.getInstance().init(this);
    }

    public static App getApplication() {
        return mApp;
    }

    public void exitApp(boolean isBackground) {
        AppConfig.setRefId("");
        SDActivityManager.getInstance().finishAllActivity();
        SDEventManager.post(EnumEventTag.EXIT_APP.ordinal());
        if (isBackground) {

        } else {
            System.exit(0);
        }
    }

    public void clearAppsLocalUserModel() {
        LocalUserModelDao.deleteAllModel();
        AppConfig.setSessionId("");
    }

    public static String getStringById(int resId) {
        return getApplication().getString(resId);
    }

    @Override
    public void onConnect(netType type) {
        mRuntimeConfig.updateIsCanLoadImage();
    }

    @Override
    public void onDisConnect() {

    }

    @Override
    public void onTerminate() {
        SDEventManager.unregister(this);
        TANetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        TANetworkStateReceiver.removeRegisterObserver(this);
        super.onTerminate();
    }

    @Override
    public void onEvent(SDBaseEvent event) {

    }

    @Override
    public void onEventBackgroundThread(SDBaseEvent event) {

    }

    @Override
    public void onEventAsync(SDBaseEvent event) {

    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {

    }

}
