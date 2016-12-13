package com.fanwe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanwe.application.App;
import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.fanwe.dao.UserModelDao;
import com.fanwe.model.LocalUserModel;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Edison on 2016/8/1.
 * 推广海报页
 */
public class MediaPosterActivity extends TitleBaseActivity {
    private static final String TAG = MediaPosterActivity.class.getName();
    @ViewInject(R.id.img)
    private ImageView imageView;

    @ViewInject(R.id.rootLayout)
    private RelativeLayout rootLayout;

    @ViewInject(R.id.share_btn)
    private ImageView share_btn;

    private final int SAVE_QRCODE = 0;
    private final int SAVE_VIEW = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case SAVE_QRCODE:
                    Bitmap bitmap = BitmapFactory.decodeFile(message.obj.toString());
                    imageView.setImageBitmap(bitmap);
                    break;
                case SAVE_VIEW:
                    share_btn.setVisibility(View.VISIBLE);
                    sharePoster();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_poster);
        initTitle();
        initQRCode();
        share_btn.setOnClickListener(this);
    }

    private void initTitle() {
        mTitle.setText("推广海报");
    }

    private void initQRCode() {
        //判断推广二维码是否存在
        final LocalUserModel localUserModel = App.getApp().getmLocalUser();
        final String dir = Environment.getExternalStorageDirectory().getPath() + Constant.FILE_DIR;
        File file = new File(dir);
        if (!file.exists()) {
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
                    Message.obtain(handler, SAVE_QRCODE, filePath).sendToTarget();
                }
            }).start();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_btn:
                share_btn.setVisibility(View.INVISIBLE);
                saveBitmap(rootLayout);
                break;
        }
    }

    private void sharePoster() {
        //String content = "我正在使用" + getString(R.string.app_name) + ",快来加入吧";
        String imageUrl = Environment.getExternalStorageDirectory().getPath() + Constant.FILE_DIR + Constant.POSTER_FILE_NAME;
        //String clickUrl = "http://www.shengdianyungou.com/";
        UmengSocialManager.openShare("分享", null, imageUrl, null, this, null);
    }

    /**
     * 保存View为图片的方法
     */
    public void saveBitmap(final View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bm);
                view.draw(canvas);
                Log.e(TAG, "保存图片");
                File file = new File(Environment.getExternalStorageDirectory().getPath() + Constant.FILE_DIR, Constant.POSTER_FILE_NAME);
                if (file.exists()) {
                    file.delete();
                }
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                    Log.i(TAG, "已经保存");
                    //图片截屏保存后，进行分享
                    Message.obtain(handler, SAVE_VIEW, file.getPath()).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}