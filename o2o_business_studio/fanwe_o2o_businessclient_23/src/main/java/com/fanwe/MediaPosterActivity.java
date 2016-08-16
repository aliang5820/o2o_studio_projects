package com.fanwe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.model.LocalUserModel;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Edison on 2016/8/1.
 * 推广海报页
 */
public class MediaPosterActivity extends TitleBaseActivity {

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
        setContentView(R.layout.act_media_poster);
        initTitle();
        initQRCode();
    }

    private void initTitle() {
        mTitle.setText("推广海报");
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