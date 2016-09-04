package com.fanwe;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.businessclient.R;
import com.fanwe.constant.Constant;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Edison on 2016/9/4.
 * 图片选择器
 */
public class ApplyPictureActivity extends TitleBaseActivity implements View.OnClickListener {
    private final String TAG = ApplyPictureActivity.class.getName();
    private ImageView mAccountHeadIcon;

    @ViewInject(R.id.pic_desc)
    private TextView pic_desc;

    //保存图片本地路径
    public static final String FILE_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator + Constant.FILE_DIR;
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
    private static final String IMG_PATH = FILE_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;

    private String IMAGE_FILE_NAME = "";
    private String TMP_IMAGE_FILE_NAME = "";

    //常量定义
    public static final int TAKE_A_PICTURE = 10;
    public static final int SELECT_A_PICTURE = 20;
    public static final int SET_PICTURE = 30;
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
    private String mAlbumPicturePath = null;
    private String mTargetPicturePath = null;

    File fileOne = null;
    File fileTwo = null;
    private int displayViewId;

    //版本比较：是否是4.4及以上版本
    final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_apply_picture);
        displayViewId = getIntent().getIntExtra(Constant.ExtraConstant.EXTRA_ID, -1);
        IMAGE_FILE_NAME = displayViewId + ".jpeg";
        TMP_IMAGE_FILE_NAME = displayViewId + "_temp.jpeg";
        switch (displayViewId) {
            case R.id.companyPic1:
                pic_desc.setText("营业执照");
                break;
            case R.id.companyPic2:
                pic_desc.setText("其他资质");
                break;
            case R.id.companyPic3:
                pic_desc.setText("商户Logo");
                break;
            case R.id.companyPic4:
                pic_desc.setText("门店图片");
                break;
        }

        mTitle.setText("选择图片");

        mAccountHeadIcon = (ImageView) findViewById(R.id.head_value);
        mAccountHeadIcon.setOnClickListener(this);
        File directory = new File(FILE_DIR);
        File imagePath = new File(IMG_PATH);
        if (!directory.exists()) {
            Log.i(TAG, "directory.mkdir()");
            directory.mkdir();
        }
        if (!imagePath.exists()) {
            Log.i(TAG, "imagepath.mkdir()");
            imagePath.mkdir();
        }

        fileOne = new File(IMG_PATH, IMAGE_FILE_NAME);
        fileTwo = new File(IMG_PATH, TMP_IMAGE_FILE_NAME);

        try {
            if (!fileOne.exists() && !fileTwo.exists()) {
                fileOne.createNewFile();
                fileTwo.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.head_value) {
            new AlertDialog.Builder(this).setTitle("选择图片")
                    .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mIsKitKat) {
                                selectImageUriAfterKikat();
                            } else {
                                cropImageUri();
                            }
                        }
                    }).setPositiveButton("相机", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMG_PATH, IMAGE_FILE_NAME)));
                    startActivityForResult(intent, TAKE_A_PICTURE);
                    Log.i(TAG, "TAKE_A_PICTURE");
                }
            }).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_A_PICTURE) {
            if (resultCode == RESULT_OK && null != data) {
                Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH, TMP_IMAGE_FILE_NAME)));
                mAccountHeadIcon.setImageBitmap(bitmap);

                Log.e(TAG, "4.4以下的");
                mTargetPicturePath = IMG_PATH + TMP_IMAGE_FILE_NAME;
                Log.e(TAG, "mTargetPicturePath:" + mTargetPicturePath);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mActivity, "取消头像设置", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
            if (resultCode == RESULT_OK && null != data) {
                //Log.i(TAG, "4.4以上的");
                mAlbumPicturePath = getPath(getApplicationContext(), data.getData());
                cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mActivity, "取消头像设置", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
            Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH, TMP_IMAGE_FILE_NAME)));
            mAccountHeadIcon.setImageBitmap(bitmap);

            Log.e(TAG, "4.4以上上的 RESULT_OK");
            mTargetPicturePath = IMG_PATH + TMP_IMAGE_FILE_NAME;
            Log.e(TAG, "mTargetPicturePath:" + mTargetPicturePath);
        } else if (requestCode == TAKE_A_PICTURE) {
            Log.e(TAG, "TAKE_A_PICTURE-resultCode:" + resultCode);
            if (resultCode == RESULT_OK) {
                cameraCropImageUri(Uri.fromFile(new File(IMG_PATH, IMAGE_FILE_NAME)));
            } else {
                Toast.makeText(mActivity, "取消头像设置", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SET_PICTURE) {
            //拍照的设置头像  不考虑版本
            if (resultCode == RESULT_OK && null != data) {
                Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMG_PATH, IMAGE_FILE_NAME)));
                mAccountHeadIcon.setImageBitmap(bitmap);

                Log.e(TAG, "拍照的设置头像  不考虑版本");
                mTargetPicturePath = IMG_PATH + IMAGE_FILE_NAME;
                Log.e(TAG, "mTargetPicturePath:" + mTargetPicturePath);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mActivity, "取消头像设置", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, "设置头像失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * <br>功能简述:裁剪图片方法实现---------------------- 相册
     * <br>功能详细描述:
     * <br>注意:
     */
    private void cropImageUri() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(IMG_PATH, TMP_IMAGE_FILE_NAME)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, SELECT_A_PICTURE);
    }


    /**
     * <br>功能简述:4.4以上裁剪图片方法实现---------------------- 相册
     * <br>功能详细描述:
     * <br>注意:
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
    }

    /**
     * <br>功能简述:裁剪图片方法实现----------------------相机
     * <br>功能详细描述:
     * <br>注意:
     */
    private void cameraCropImageUri(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        //		if (mIsKitKat) {
        //			intent.putExtra("return-data", true);
        //			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //		} else {
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //		}
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, SET_PICTURE);
    }

    /**
     * <br>功能简述: 4.4及以上改动版裁剪图片方法实现 --------------------相机
     * <br>功能详细描述:
     * <br>注意:
     */
    private void cropImageUriAfterKikat(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        //		intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMG_PATH, TMP_IMAGE_FILE_NAME)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * <br>功能简述:4.4及以上获取图片的方法
     * <br>功能详细描述:
     * <br>注意:
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    //回传图片
    public void onUpload(View view) {
        Intent intent = new Intent(mActivity, ApplyHYDActivity.class);
        intent.putExtra(Constant.ExtraConstant.EXTRA_MODEL, mTargetPicturePath);
        intent.putExtra(Constant.ExtraConstant.EXTRA_ID, displayViewId);
        setResult(RESULT_OK, intent);
        finish();
    }
}