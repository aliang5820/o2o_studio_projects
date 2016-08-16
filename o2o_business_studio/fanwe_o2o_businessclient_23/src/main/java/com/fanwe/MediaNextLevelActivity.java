package com.fanwe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.fragment.MediaNextLevelFragment;
import com.fanwe.model.LocalUserModel;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 2016/7/31.
 * 我的下级
 */
public class MediaNextLevelActivity extends TitleBaseActivity {

    @ViewInject(R.id.user_name)
    private TextView user_name;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    //private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<Fragment> mFragmentList = new ArrayList<>();//页卡视图集合

    @ViewInject(R.id.user_qr_code)
    private ImageView qrImageView;

    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message message) {
            Bitmap bitmap = BitmapFactory.decodeFile(message.obj.toString());
            qrImageView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_next_level);
        initView();
    }

    private void initView() {
        mTitle.setText("自媒体");
        initQRCode();
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        //添加页卡视图
        /*LayoutInflater mInflater = LayoutInflater.from(this);
        mViewList.add(mInflater.inflate(R.layout.act_apply_hyd, mTabLayout, false));//页卡视图1
        mViewList.add(mInflater.inflate(R.layout.act_apply_person_hhr, mTabLayout, false));//页卡视图2
        mViewList.add(mInflater.inflate(R.layout.act_apply_person_hhr, mTabLayout, false));//页卡视图2*/
        mFragmentList.add(MediaNextLevelFragment.getInstance(1));
        mFragmentList.add(MediaNextLevelFragment.getInstance(2));
        mFragmentList.add(MediaNextLevelFragment.getInstance(3));

        //添加页卡标题
        mTitleList.add("一级");
        mTitleList.add("二级");
        mTitleList.add("三级");

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        //MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。

        user_name.setText(App.getApp().getmLocalUser().getAccount_name());
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }
    }

    private void initQRCode() {
        //判断推广二维码是否存在
        //判断推广二维码是否存在
        final LocalUserModel localUserModel = App.getApp().getmLocalUser();
        final String dir = Environment.getExternalStorageDirectory() + File.separator + Constant.FILE_DIR;
        File file = new File(dir);
        if(!file.exists()) {
            file.mkdirs();
        }
        final String filePath = dir + localUserModel.getSupplier_id() + "_" + Constant.QR_CODE_FILE_NAME;
        File qrFile = new File(filePath);
        if (qrFile.exists()) {
            Message.obtain(handler, 0, filePath).sendToTarget();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    QRCodeUtil.createQRImage(localUserModel.getQr_code(), 200, 200, null, filePath);
                    //图片创建成功后，进行显示
                    Message.obtain(handler, 0, filePath).sendToTarget();
                }
            }).start();
        }
    }
}
