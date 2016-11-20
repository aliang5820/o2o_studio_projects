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

import com.fanwe.constant.Constant;
import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.model.LocalUserModel;
import com.fanwe.o2o.newo2o.BuildConfig;
import com.fanwe.o2o.newo2o.R;
import com.fanwe.umeng.UmengSocialManager;
import com.fanwe.utils.QRCodeUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Edison on 2016/8/1.
 * 推广海报页
 */
public class MediaPosterActivity extends BaseActivity {
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
                    sharePoster();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmTitleType(Constant.TitleType.TITLE);
        setContentView(R.layout.act_media_poster);
        initTitle();
        initQRCode();
        share_btn.setOnClickListener(this);
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
        if (!file.exists()) {
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

    @Override
    protected void onResume() {
        super.onResume();
        share_btn.setVisibility(View.VISIBLE);
    }

    private void sharePoster() {
        String content = "我正在使用" + getString(R.string.app_name) + ",快来加入吧";
        String imageUrl = Environment.getExternalStorageDirectory() + File.separator + Constant.FILE_DIR + Constant.POSTER_FILE_NAME;
        String clickUrl = "http://www.shengdianyungou.com/";
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
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + Constant.FILE_DIR, Constant.POSTER_FILE_NAME);
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