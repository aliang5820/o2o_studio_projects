package com.fanwe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.constant.Constant.EnumLoginState;
import com.fanwe.constant.Constant.TitleType;
import com.fanwe.event.EnumEventTag;
import com.fanwe.fragment.LoginFragment;
import com.fanwe.fragment.LoginPhoneFragment;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.view.SDTabCorner.TabPosition;
import com.fanwe.library.view.SDTabCornerText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.work.AppRuntimeWorker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sunday.eventbus.SDBaseEvent;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    public static final String EXTRA_SELECT_TAG_INDEX = "extra_select_tag_index";

    @ViewInject(R.id.ll_tabs)
    private LinearLayout mLl_tabs;

    @ViewInject(R.id.tv_find_password)
    private TextView mTv_find_password;

    @ViewInject(R.id.act_login_new_tab_login_normal)
    private SDTabCornerText mTabLoginNormal;

    @ViewInject(R.id.act_login_new_tab_login_phone)
    private SDTabCornerText mTabLoginPhone;

    private SDSelectViewManager<SDTabCornerText> mSelectManager = new SDSelectViewManager<SDTabCornerText>();

    private int mSelectTabIndex = 0;
    private List<Integer> mListSelectIndex = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(TitleType.TITLE);
        setContentView(R.layout.act_login);
        init();
    }

    private void init() {
        getIntentData();
        initTitle();
        changeViewState();
        registerClick();
    }

    private void registerClick() {
        mTv_find_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ModifyPasswordActivity.class);
                intent.putExtra(ModifyPasswordActivity.EXTRA_TITLE, "忘记密码");
                startActivity(intent);
            }
        });
    }

    private void getIntentData() {
        mListSelectIndex.add(0);
        mListSelectIndex.add(1);

        mSelectTabIndex = getIntent().getIntExtra(EXTRA_SELECT_TAG_INDEX, 0);
        if (!mListSelectIndex.contains(mSelectTabIndex)) {
            mSelectTabIndex = 0;
        }
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("登录");

        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("注册");
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
        startRegisterActivity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        init();
        super.onNewIntent(intent);
    }

    private void changeViewState() {
        EnumLoginState state = AppRuntimeWorker.getLoginState();
        switch (state) {
            case LOGIN_EMPTY_PHONE:
                changeViewLoginEmptyPhone();
                break;
            case UN_LOGIN:
                changeViewUnLogin();
                break;
            case LOGIN_NEED_BIND_PHONE:
                changeViewUnLogin();
                showBindPhoneDialog();
                break;
            case LOGIN_NEED_VALIDATE:
                changeViewUnLogin();
                break;

            default:
                break;
        }
    }

    private void showBindPhoneDialog() {
        Intent intent = new Intent(getApplicationContext(), BindMobileActivity.class);
        startActivity(intent);
        finish();
    }

    private void changeViewLoginEmptyPhone() {
        mLl_tabs.setVisibility(View.GONE);
        clickLoginNormal();
    }

    private void changeViewUnLogin() {
        mTabLoginNormal.setTextTitle("登录");
        mTabLoginNormal.setTextSizeTitle(18);
        mTabLoginNormal.setPosition(TabPosition.FIRST);

        mTabLoginPhone.setTextTitle("手机登录");
        mTabLoginPhone.setTextSizeTitle(18);
        mTabLoginPhone.setPosition(TabPosition.LAST);

        mSelectManager.setListener(new SDSelectManagerListener<SDTabCornerText>() {

            @Override
            public void onNormal(int index, SDTabCornerText item) {
            }

            @Override
            public void onSelected(int index, SDTabCornerText item) {
                switch (index) {
                    case 0: // 正常登录
                        clickLoginNormal();
                        break;
                    case 1: // 快捷登录
                        clickLoginPhone();
                        break;
                    default:
                        break;
                }
            }
        });

        mSelectManager.setItems(new SDTabCornerText[]{mTabLoginNormal, mTabLoginPhone});
        mSelectManager.performClick(mSelectTabIndex);
    }

    /**
     * 正常登录的选项卡被选中
     */
    protected void clickLoginNormal() {
        getSDFragmentManager().toggle(R.id.act_login_fl_content, null, LoginFragment.class);
    }

    /**
     * 手机号快捷登录的选项卡被选中
     */
    protected void clickLoginPhone() {
        getSDFragmentManager().toggle(R.id.act_login_fl_content, null, LoginPhoneFragment.class);
    }

    protected void startRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case LOGIN_SUCCESS:
                finish();
                break;

            default:
                break;
        }
    }

}