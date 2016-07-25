package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.fanwe.application.App;
import com.fanwe.application.SysConfig;
import com.fanwe.businessclient.R;
import com.fanwe.common.SDViewNavigatorManager;
import com.fanwe.common.SDViewNavigatorManager.SDViewNavigatorManagerListener;
import com.fanwe.customview.SDBottomTabItem;
import com.fanwe.fragment.Tab_0_Fragment;
import com.fanwe.fragment.Tab_1_Fragment;
import com.fanwe.fragment.Tab_2_Fragment;
import com.fanwe.fragment.Tab_3_Fragment;
import com.fanwe.fragment.Tab_4_Fragment;
import com.fanwe.service.AppUpgradeService;
import com.fanwe.utils.SDToast;

public class MainActivity extends BaseActivity {

    private SDBottomTabItem mTab0, mTab1, mTab2, mTab3, mTab4;

    private SDViewNavigatorManager mViewManager;

    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        register();
        initBottom();

        if (!SysConfig.getInstance().isCheckUpdate()) {
            startUpgradeService();
        }

    }

    private void startUpgradeService() {
        Intent updateIntent = new Intent(mActivity, AppUpgradeService.class);
        startService(updateIntent);
    }

    private void register() {
        mTab0 = (SDBottomTabItem) findViewById(R.id.act_main_tab_0);
        mTab1 = (SDBottomTabItem) findViewById(R.id.act_main_tab_1);
        mTab2 = (SDBottomTabItem) findViewById(R.id.act_main_tab_2);
        mTab3 = (SDBottomTabItem) findViewById(R.id.act_main_tab_3);
        mTab4 = (SDBottomTabItem) findViewById(R.id.act_main_tab_4);
        mViewManager = new SDViewNavigatorManager();
    }

    private void initBottom() {
        mTab0.setmBackgroundNormalId(R.drawable.m_tab_0_normal);
        mTab1.setmBackgroundNormalId(R.drawable.m_tab_1_normal);
        mTab2.setmBackgroundNormalId(R.drawable.m_tab_2_normal);
        mTab3.setmBackgroundNormalId(R.drawable.m_tab_3_normal);
        mTab4.setmBackgroundNormalId(R.drawable.m_tab_4_normal);

        mTab0.setmBackgroundPressId(R.drawable.m_tab_0_selected);
        mTab1.setmBackgroundPressId(R.drawable.m_tab_1_selected);
        mTab2.setmBackgroundPressId(R.drawable.m_tab_2_selected);
        mTab3.setmBackgroundPressId(R.drawable.m_tab_3_selected);
        mTab4.setmBackgroundPressId(R.drawable.m_tab_4_selected);

        SDBottomTabItem[] items = new SDBottomTabItem[]{mTab0, mTab1, mTab2, mTab3, mTab4};

        mViewManager.setItems(items);
        mViewManager.setmListener(new SDViewNavigatorManagerListener() {
            @Override
            public void onItemClick(View v, int index) {
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

            private void click0() {
                getSDFragmentManager().toggle(R.id.act_main_fl_fragment_content, null, Tab_0_Fragment.class);
            }

            private void click1() {
                getSDFragmentManager().toggle(R.id.act_main_fl_fragment_content, null, Tab_1_Fragment.class);
            }

            private void click2() {
                getSDFragmentManager().toggle(R.id.act_main_fl_fragment_content, null, Tab_2_Fragment.class);
            }

            private void click3() {
                getSDFragmentManager().toggle(R.id.act_main_fl_fragment_content, null, Tab_3_Fragment.class);
            }

            private void click4() {
                getSDFragmentManager().toggle(R.id.act_main_fl_fragment_content, null, Tab_4_Fragment.class);
            }

        });

        mViewManager.setSelectIndex(0, mTab0, true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            SDToast.showToast("再按一次退出!", Toast.LENGTH_SHORT);
        } else {
            App.getApp().exitApp(true);
        }
        mExitTime = System.currentTimeMillis();
    }

}
