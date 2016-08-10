package com.fanwe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.model.LocalUserModel;
import com.fanwe.o2o.newo2o.R;
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
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("推广海报");
    }

    private void initQRCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LocalUserModel localUserModel = LocalUserModelDao.queryModel();

                String filePath = Environment.getExternalStorageDirectory() + File.separator + "qr_code.jpg";
                QRCodeUtil.createQRImage(localUserModel.getQr_code(), 200, 200, null, filePath);
                //图片创建成功后，进行显示
                Message.obtain(handler, 0, filePath).sendToTarget();
            }
        }).start();
    }
}