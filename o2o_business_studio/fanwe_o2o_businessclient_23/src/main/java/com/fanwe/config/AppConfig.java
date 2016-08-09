package com.fanwe.config;

import com.fanwe.businessclient.R;
import com.fanwe.library.config.SDConfig;

public class AppConfig {

    //
    public static String getSessionId() {
        return SDConfig.getInstance().getString(R.string.config_session_id, "");
    }

    public static void setSessionId(String value) {
        SDConfig.getInstance().setString(R.string.config_session_id, value);
    }

    //
    public static String getUserName() {
        return SDConfig.getInstance().getString(R.string.config_user_name, "");
    }

    public static void setUserName(String userName) {
        SDConfig.getInstance().setString(R.string.config_user_name, userName);
    }

    //
    public static String getImageCode() {
        return SDConfig.getInstance().getString(R.string.config_image_code, null);
    }

    public static void setImageCode(String value) {
        SDConfig.getInstance().setString(R.string.config_image_code, value);
    }

}
