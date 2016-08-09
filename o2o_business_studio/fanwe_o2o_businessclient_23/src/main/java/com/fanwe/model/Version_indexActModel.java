package com.fanwe.model;

import com.fanwe.businessclient.R;
import com.fanwe.library.utils.SDResourcesUtil;

public class Version_indexActModel extends BaseCtlActModel {
    private int serverVersion; // 服务端版本号
    private String filename; // 下载链接
    private String android_upgrade;
    private int forced_upgrade;

    // add
    private String localFileName;

    public String getLocalFileName() {
        return localFileName;
    }

    public void setLocalFileName(String localFileName) {
        this.localFileName = localFileName;
    }

    public int getForced_upgrade() {
        return forced_upgrade;
    }

    public void setForced_upgrade(int forced_upgrade) {
        this.forced_upgrade = forced_upgrade;
    }

    public int getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(int serverVersion) {
        this.serverVersion = serverVersion;
        this.localFileName = SDResourcesUtil.getString(R.string.app_name) + "_" + serverVersion + ".apk";
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAndroid_upgrade() {
        return android_upgrade;
    }

    public void setAndroid_upgrade(String android_upgrade) {
        this.android_upgrade = android_upgrade;
    }

}
