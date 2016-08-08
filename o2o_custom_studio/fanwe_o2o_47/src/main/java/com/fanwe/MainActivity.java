package com.fanwe;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.app.App;
import com.fanwe.constant.ApkConstant;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.HomeFragment;
import com.fanwe.fragment.MoreFragment;
import com.fanwe.fragment.MyFragment;
import com.fanwe.fragment.ShopCartFragment;
import com.fanwe.fragment.StoreListContainerFragment;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDActivityUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDTabMenu;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.service.AppUpgradeService;
import com.fanwe.umeng.UmengPushManager;
import com.fanwe.utils.DebugHelper;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

public class MainActivity extends BaseActivity {

    /**
     * 启动主页的时候要选中的底部菜单位置(int)
     */
    public static final String EXTRA_SELECT_INDEX = "extra_select_index";

    @ViewInject(R.id.tab0)
    private SDTabMenu mTab0;

    @ViewInject(R.id.tab1)
    private SDTabMenu mTab1;

    @ViewInject(R.id.tab2)
    private SDTabMenu mTab2;

    @ViewInject(R.id.tab3)
    private SDTabMenu mTab3;

    @ViewInject(R.id.tab4)
    private SDTabMenu mTab4;

    private SDSelectViewManager<SDTabMenu> mViewManager = new SDSelectViewManager<SDTabMenu>();

    private MyFragment mFragMyAccount;

    private long mExitTime;
    private int mSelectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        init();
    }

    private void init() {
        getIntentData();
        startUpgradeService();
        initBottom();
        startPushIntent();
        DebugHelper.getInstance().registerShake(this);

        if (ApkConstant.DEBUG) {
            LogUtil.i("regId:" + UmengPushManager.getPushAgent().getRegistrationId());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        init();
        super.onNewIntent(intent);
    }

    private void getIntentData() {
        int index = getIntent().getIntExtra(EXTRA_SELECT_INDEX, -1);
        if (index >= 0) {
            mSelectedIndex = index;
        }
    }

    private void startPushIntent() {
        SDActivityUtil.startActivity(this, App.getApplication().mPushIntent);
        App.getApplication().mPushIntent = null;
    }

    private void startUpgradeService() {
        startService(new Intent(MainActivity.this, AppUpgradeService.class));
    }

    private void initBottom() {
        mTab0.setBackgroundTextTitleNumber(R.drawable.bg_number);
        mTab1.setBackgroundTextTitleNumber(R.drawable.bg_number);
        mTab2.setBackgroundTextTitleNumber(R.drawable.bg_number);
        mTab3.setBackgroundTextTitleNumber(R.drawable.bg_number);
        mTab4.setBackgroundTextTitleNumber(R.drawable.bg_number);

        mTab0.setTextTitle(SDResourcesUtil.getString(R.string.home));
        mTab1.setTextTitle(SDResourcesUtil.getString(R.string.supplier));
        mTab2.setTextTitle(SDResourcesUtil.getString(R.string.shopcart));
        mTab3.setTextTitle(SDResourcesUtil.getString(R.string.mine));
        mTab4.setTextTitle(SDResourcesUtil.getString(R.string.more));

        mTab0.getViewConfig(mTab0.mIvTitle).setImageNormalResId(R.drawable.tab_0_normal).setImageSelectedResId(R.drawable.tab_0_press);
        mTab1.getViewConfig(mTab1.mIvTitle).setImageNormalResId(R.drawable.tab_1_normal).setImageSelectedResId(R.drawable.tab_1_press);
        mTab2.getViewConfig(mTab2.mIvTitle).setImageNormalResId(R.drawable.tab_2_normal).setImageSelectedResId(R.drawable.tab_2_press);
        mTab3.getViewConfig(mTab3.mIvTitle).setImageNormalResId(R.drawable.tab_3_normal).setImageSelectedResId(R.drawable.tab_3_press);
        mTab4.getViewConfig(mTab4.mIvTitle).setImageNormalResId(R.drawable.tab_4_normal).setImageSelectedResId(R.drawable.tab_4_press);

        mTab0.getViewConfig(mTab0.mTvTitle).setTextColorNormalResId(R.color.text_home_menu_normal)
                .setTextColorSelectedResId(R.color.text_home_menu_selected);
        mTab1.getViewConfig(mTab1.mTvTitle).setTextColorNormalResId(R.color.text_home_menu_normal)
                .setTextColorSelectedResId(R.color.text_home_menu_selected);
        mTab2.getViewConfig(mTab2.mTvTitle).setTextColorNormalResId(R.color.text_home_menu_normal)
                .setTextColorSelectedResId(R.color.text_home_menu_selected);
        mTab3.getViewConfig(mTab3.mTvTitle).setTextColorNormalResId(R.color.text_home_menu_normal)
                .setTextColorSelectedResId(R.color.text_home_menu_selected);
        mTab4.getViewConfig(mTab4.mTvTitle).setTextColorNormalResId(R.color.text_home_menu_normal)
                .setTextColorSelectedResId(R.color.text_home_menu_selected);

        mViewManager.setListener(new SDSelectManagerListener<SDTabMenu>() {
            @Override
            public void onNormal(int index, SDTabMenu item) {
            }

            @Override
            public void onSelected(int index, SDTabMenu item) {
                mSelectedIndex = index;
                switch (index) {
                    case 0:
                        click0();
                        break;
                    case 1:
                        click1();
                        break;
                    case 2:
                        click2();
                        break;
                    case 3:
                        click3();
                        break;
                    case 4:
                        click4();
                        break;

                    default:
                        break;
                }
            }
        });
        SDTabMenu[] items = new SDTabMenu[]{mTab0, mTab1, mTab2, mTab3, mTab4};
        mViewManager.setItems(items);
        mViewManager.performClick(mSelectedIndex);
    }

    /**
     * 首页
     */
    protected void click0() {
        getSDFragmentManager().toggle(R.id.act_main_fl_content, null, HomeFragment.class);
    }

    /**
     * 商家
     */
    protected void click1() {
        getSDFragmentManager().toggle(R.id.act_main_fl_content, null, StoreListContainerFragment.class);
    }

    /**
     * 分享
     */
    protected void click2() {
        getSDFragmentManager().toggle(R.id.act_main_fl_content, null, ShopCartFragment.class);
    }

    /**
     * 我的账户
     */
    protected void click3() {
        if (!AppRuntimeWorker.isLogin(this)) // 未登录
        {
            mViewManager.selectLastIndex();
        } else {
            mFragMyAccount = (MyFragment) getSDFragmentManager().toggle(R.id.act_main_fl_content, null, MyFragment.class);
        }
    }

    /**
     * 更多
     */
    protected void click4() {
        getSDFragmentManager().toggle(R.id.act_main_fl_content, null, MoreFragment.class);
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case LOGIN_SUCCESS:
                mViewManager.performClick(0);
                break;
            case LOGOUT:
                mTab3.setTextTitleNumber(null);
                removeMyAccountFragment();
                mViewManager.performClick(0);
                break;
            case TEMP_LOGIN:
                removeMyAccountFragment();
                mViewManager.performClick(0);
                break;
            case UN_LOGIN:
                removeMyAccountFragment();
                mViewManager.performClick(0);
                break;
            default:
                break;
        }
    }

    private void removeMyAccountFragment() {
        getSDFragmentManager().remove(mFragMyAccount);
        mFragMyAccount = null;
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            SDToast.showToast("再按一次退出!");
        } else {
            App.getApplication().exitApp(true);
        }
        mExitTime = System.currentTimeMillis();
    }

}
