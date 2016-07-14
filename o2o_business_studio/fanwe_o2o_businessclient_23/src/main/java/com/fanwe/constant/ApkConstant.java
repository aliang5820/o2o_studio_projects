package com.fanwe.constant;

public class ApkConstant {

    public static final boolean DEBUG = true;

    //public static final String SERVER_API_URL_MID = "www.taozyi.com";
    public static final String SERVER_API_URL_MID = "120.24.186.128";

    public static final String SERVER_API_URL_PRE = "http://";
    public static final String SERVER_API_URL_END = "/mapi/index.php";
    public static final String SERVER_API_URL = SERVER_API_URL_PRE + SERVER_API_URL_MID + SERVER_API_URL_END;
    public static final String KEY_AES = "FANWE5LMUQC436IM";

    public static String getServerApiUrl() {
        if (DEBUG) {
            return SERVER_API_URL_PRE + SERVER_API_URL_MID + SERVER_API_URL_END;
        } else {
            return SERVER_API_URL;
        }
    }

}
