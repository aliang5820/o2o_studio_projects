package com.fanwe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.library.utils.SDToast;
import com.fanwe.model.LocalUserModel;
import com.fanwe.model.Youhui_infoModel;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Edison on 2016/8/1.
 * 推广海报页
 */
public class MediaPosterActivity extends BaseActivity {

    @ViewInject(R.id.img)
    private ImageView imageView;

    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message message) {
            Bitmap bitmap = BitmapFactory.decodeFile(message.obj.toString());
            imageView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_media_poster);
        initTitle();
        initQRCode();
        imageView.setOnClickListener(this);
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("推广海报");
    }

    private void initQRCode() {
        final String qr_code = getIntent().getStringExtra(Constant.ExtraConstant.EXTRA_MODEL);
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
                    QRCodeUtil.createQRImage(qr_code, 200, 200, null, filePath);
                    //图片创建成功后，进行显示
                    Message.obtain(handler, 0, filePath).sendToTarget();
                }
            }).start();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img:
                sharePoster();
                break;
        }
    }

    private void sharePoster() {
        String content = "测试分享";
        String imageUrl = "http://tb.himg.baidu.com/sys/portrait/item/6f70e7bc96e58fb73331303230260f";
        String clickUrl = "http://www.baidu.com";
        UmengSocialManager.openShare("分享", content, imageUrl, clickUrl, this, null);
    }

    /*private void clickShare() {
        if (mActModel == null) {
            SDToast.showToast("未找到可分享内容");
            return;
        }

        Youhui_infoModel infoModel = mActModel.getYouhui_info();
        if (infoModel == null) {
            SDToast.showToast("未找到可分享内容");
            return;
        }

        String content = infoModel.getName() + infoModel.getShare_url();
        String imageUrl = infoModel.getIcon();
        String clickUrl = infoModel.getShare_url();

        UmengSocialManager.openShare("分享", content, imageUrl, clickUrl, this, null);
    }*/
}