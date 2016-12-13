package com.fanwe.umeng;

import android.app.Activity;
import android.text.TextUtils;

import com.fanwe.dao.InitActModelDao;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.model.InitActModel;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sensor.controller.UMShakeService;
import com.umeng.socialize.sensor.controller.impl.UMShakeServiceFactory;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import java.util.ArrayList;
import java.util.List;

public class UmengSocialManager {

    public static final String SHARE = "com.umeng.share";
    public static final String LOGIN = "com.umeng.login";

    public static void initHandler() {
        initHandler(SDActivityManager.getInstance().getLastActivity());
    }

    public static void initHandler(Activity activity) {
        if (activity == null) {
            return;
        }

        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
        // 添加邮件
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();

        InitActModel model = InitActModelDao.getModel();
        if (model == null) {
            return;
        }
        // /////////////////////////////////////////微信
        String wxAppKey = model.getWx_app_key();
        String wxAppSecret = model.getWx_app_secret();
        if (!TextUtils.isEmpty(wxAppKey) && !TextUtils.isEmpty(wxAppSecret)) {
            // 微信朋友圈
            UMWXHandler wxCircleHandler = new UMWXHandler(activity, wxAppKey, wxAppSecret);
            wxCircleHandler.setToCircle(true);
            wxCircleHandler.addToSocialSDK();
            wxCircleHandler.showCompressToast(false);
            // 微信
            UMWXHandler wxHandler = new UMWXHandler(activity, wxAppKey, wxAppSecret);
            wxHandler.addToSocialSDK();
            wxHandler.showCompressToast(false);
        }
    }

    /**
     * 初始化友盟分享可以显示的第三方平台
     */
    public static void initDisplay() {
        UMSocialService service = getUMShare();
        SocializeConfig config = service.getConfig();
        config.closeToast();

        List<SHARE_MEDIA> listPlat = new ArrayList<SHARE_MEDIA>();
        InitActModel model = InitActModelDao.getModel();
        if (model != null) {

            String wxAppKey = model.getWx_app_key();
            String wxAppSecret = model.getWx_app_secret();
            if (!TextUtils.isEmpty(wxAppKey) && !TextUtils.isEmpty(wxAppSecret)) {
                listPlat.add(SHARE_MEDIA.WEIXIN);
                //listPlat.add(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        }
        //listPlat.add(SHARE_MEDIA.SMS);
        //listPlat.add(SHARE_MEDIA.EMAIL);
        SHARE_MEDIA[] arrPlat = new SHARE_MEDIA[listPlat.size()];
        listPlat.toArray(arrPlat);
        config.setPlatforms(arrPlat);
    }

    public static UMSocialService getUMShare() {
        return UMServiceFactory.getUMSocialService(SHARE);
    }

    public static UMShakeService getUMShake() {
        return UMShakeServiceFactory.getShakeService(SHARE);
    }

    public static void doOauthVerify(Activity activity, SHARE_MEDIA shareMedia, UMAuthListener listener) {
        getUMShare().doOauthVerify(activity, shareMedia, listener);
    }

    public static void getPlatformInfo(Activity activity, SHARE_MEDIA shareMedia, UMDataListener listener) {
        getUMShare().getPlatformInfo(activity, shareMedia, listener);
    }

    public static void openShare(String title, String content, String imageUrl, String clickUrl, Activity activity, SnsPostListener listener) {
        UMImage umImage = null;
        if (!TextUtils.isEmpty(imageUrl)) {
            umImage = new UMImage(activity, imageUrl);
        }
        openShare(title, content, umImage, clickUrl, activity, listener);
    }

    public static void openShare(String title, String content, UMImage umImage, String clickUrl, Activity activity, SnsPostListener listener) {
        QZoneShareContent qzoneShare = new QZoneShareContent();
        QQShareContent qqShare = new QQShareContent();
        WeiXinShareContent wxShare = new WeiXinShareContent();
        CircleShareContent wxCircleShare = new CircleShareContent();
        SinaShareContent sinaShare = new SinaShareContent();

        // 设置分享内容
        if (TextUtils.isEmpty(content)) {
            getUMShare().setShareContent("");

            qzoneShare.setShareContent("");
            qqShare.setShareContent("");
            wxShare.setShareContent("");
            wxCircleShare.setShareContent("");
            sinaShare.setShareContent("");
        } else {
            getUMShare().setShareContent(content);

            qzoneShare.setShareContent(content);
            qqShare.setShareContent(content);
            wxShare.setShareContent(content);
            wxCircleShare.setShareContent(content);
            sinaShare.setShareContent(content);
        }

        // 设置分享图片
        // UmengSocialManager.getUMShare().setShareImage(umImage);

        qzoneShare.setShareImage(umImage);
        qqShare.setShareImage(umImage);
        wxShare.setShareImage(umImage);
        wxCircleShare.setShareImage(umImage);
        sinaShare.setShareImage(umImage);

        // 设置点击跳转链接
        if (!TextUtils.isEmpty(clickUrl)) {
            qzoneShare.setTargetUrl(clickUrl);
            qqShare.setTargetUrl(clickUrl);
            wxShare.setTargetUrl(clickUrl);
            wxCircleShare.setTargetUrl(clickUrl);
            sinaShare.setTargetUrl(clickUrl);
        } else {
            qzoneShare.setTargetUrl(null);
            qqShare.setTargetUrl(null);
            wxShare.setTargetUrl(null);
            wxCircleShare.setTargetUrl(null);
            sinaShare.setTargetUrl(null);
        }

        qzoneShare.setTitle(title);
        qqShare.setTitle(title);
        wxShare.setTitle(title);
        wxCircleShare.setTitle(content);
        sinaShare.setTitle(title);

        getUMShare().setShareMedia(qzoneShare);
        getUMShare().setShareMedia(qqShare);
        getUMShare().setShareMedia(wxShare);
        getUMShare().setShareMedia(wxCircleShare);
        getUMShare().setShareMedia(sinaShare);

        if (listener == null) {
            listener = getDefaultSnsListener();
        }

        getUMShare().openShare(activity, listener);
    }

    public static SnsPostListener getDefaultSnsListener() {
        return new SnsPostListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                if (eCode == 200) {

                } else {

                }
            }
        };
    }
}
