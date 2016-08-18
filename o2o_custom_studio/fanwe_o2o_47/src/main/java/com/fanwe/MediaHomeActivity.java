package com.fanwe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.app.App;
import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.http.InterfaceServer;
import com.fanwe.http.SDRequestCallBack;
import com.fanwe.library.dialog.SDDialogManager;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.MediaHomeCtlActModel;
import com.fanwe.model.RequestModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Edison on 2016/7/29.
 * 自媒体首页
 */
public class MediaHomeActivity extends BaseActivity {

    @ViewInject(R.id.user_name)
    private TextView user_name;

    @ViewInject(R.id.label1)
    private TextView label1;

    @ViewInject(R.id.label2)
    private TextView label2;

    @ViewInject(R.id.label3)
    private TextView label3;

    @ViewInject(R.id.label4)
    private TextView label4;

    @ViewInject(R.id.label5)
    private TextView label5;

    @ViewInject(R.id.label6)
    private TextView label6;

    @ViewInject(R.id.label7)
    private TextView label7;

    @ViewInject(R.id.label8)
    private TextView label8;

    @ViewInject(R.id.label9)
    private TextView label9;

    @ViewInject(R.id.label10)
    private TextView label10;

    @ViewInject(R.id.user_qr_code)
    private ImageView qrImageView;

    @ViewInject(R.id.extra_name)
    private TextView extra_name;

    @ViewInject(R.id.user_level)
    private TextView user_level;

    private LocalUserModel localUserModel;

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
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_media_home);
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("自媒体");
    }

    private void initData() {
        localUserModel = LocalUserModelDao.queryModel();
        user_name.setText(localUserModel.getUser_name());
        user_level.setText("消费股东");
        requestUserMediaInfo();
        initQRCode();
    }

    public void onNextLevel(View view) {
        startActivity(new Intent(mActivity, MediaNextLevelActivity.class));
    }

    public void onPoster(View view) {
        startActivity(new Intent(mActivity, MediaPosterActivity.class));
    }

    public void onRewardOrder(View view) {
        Intent intent = new Intent(mActivity, MediaRewardActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Reward.ORDER);
        startActivity(intent);
    }

    public void onRewardHHR(View view) {
        Intent intent = new Intent(mActivity, MediaRewardActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Reward.HHR);
        startActivity(intent);
    }

    public void onRewardHYD(View view) {
        Intent intent = new Intent(mActivity, MediaRewardActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_TYPE, Constant.Reward.HYD);
        startActivity(intent);
    }

    private void initQRCode() {
        //判断推广二维码是否存在
        final LocalUserModel localUserModel = LocalUserModelDao.queryModel();
        final String dir = Environment.getExternalStorageDirectory() + File.separator + Constant.FILE_DIR;
        File file = new File(dir);
        if(!file.exists()) {
            file.mkdirs();
        }
        final String filePath = dir + localUserModel.getUser_id() + "_" + Constant.QR_CODE_FILE_NAME;
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

    /**
     * 用户自媒体信息
     */
    private void requestUserMediaInfo() {
        RequestModel model = new RequestModel();
        model.putCtl("media");
        model.putAct("personalDetails");
        model.put("user_id", localUserModel.getUser_id());
        InterfaceServer.getInstance().requestInterface(model, new SDRequestCallBack<MediaHomeCtlActModel>() {

            @Override
            public void onStart() {
                SDDialogManager.showProgressDialog("请稍候...");
            }

            @Override
            public void onSuccess(MediaHomeCtlActModel actModel) {
                if (actModel.getStatus() == 1) {
                    SDDialogManager.dismissProgressDialog();
                    label1.setText(getString(R.string.number, actModel.getClassPersonNum()));//总下线人数
                    label2.setText(getString(R.string.money, actModel.getTotalRewardMoney()));//总奖励金额
                    label3.setText(getString(R.string.number, actModel.getA_Class_Person()));//一级
                    label4.setText(getString(R.string.number, actModel.getB_Class_Person()));//二级
                    label5.setText(getString(R.string.number, actModel.getC_Class_Person()));//三级
                    label6.setText(getString(R.string.money, actModel.getNowMonthSaleMoney()));//本月订单奖励
                    label7.setText(getString(R.string.money, actModel.getNowMonthPartnerMemMoney()));//本月会员店招募奖励
                    label8.setText(getString(R.string.money, actModel.getNowMonthMemMoney()));//本月合伙人奖励
                    label9.setText(getString(R.string.money, actModel.getWithdrawalsMoney()));//已提现佣金
                    label10.setText(getString(R.string.money, actModel.getDepositMoney()));//未提现佣金
                    if(TextUtils.isEmpty(actModel.getExtension_person())) {
                        extra_name.setText("");
                    } else {
                        extra_name.setText(getString(R.string.extension_person, actModel.getExtension_person()));//推荐人
                    }
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }

            @Override
            public void onFinish() {
                SDDialogManager.dismissProgressDialog();
            }
        });
    }
}
