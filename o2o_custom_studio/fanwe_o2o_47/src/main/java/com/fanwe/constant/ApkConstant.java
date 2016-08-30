package com.fanwe.constant;

import com.fanwe.o2o.newo2o.BuildConfig;

public class ApkConstant {

    public static final boolean DEBUG = true;

    public static String SERVER_API_URL_MID = BuildConfig.API_HOST;//gradle配置环境
    //public static String SERVER_API_URL_MID = "120.24.186.128";//测试环境
    //public static String SERVER_API_URL_MID = "101.200.88.185";//正式环境

    public static final String SERVER_API_URL_PRE = "http://";
    public static final String SERVER_API_URL_END = "/mapi/index.php";
    public static final String URL_PART_WAP = "/wap/index.php";
    public static final String KEY_AES = "FANWE5LMUQC436IM";

    private static final String SERVER_API_URL = SERVER_API_URL_PRE + SERVER_API_URL_MID + SERVER_API_URL_END;

    public static String getWapUrl() {
        return SERVER_API_URL_PRE + SERVER_API_URL_MID + URL_PART_WAP;
    }

    public static String getServerApiUrl() {
        if (DEBUG) {
            return SERVER_API_URL_PRE + SERVER_API_URL_MID + SERVER_API_URL_END;
        } else {
            return SERVER_API_URL;
        }
    }

}
