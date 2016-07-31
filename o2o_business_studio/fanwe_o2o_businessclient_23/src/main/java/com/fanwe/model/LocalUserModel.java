package com.fanwe.model;

import com.fanwe.library.utils.AESUtil;

public class LocalUserModel {
    private String user_id;
    private String account_name;
    private String account_password;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void encryptModel() {

        if (this.account_name != null) {
            this.account_name = AESUtil.encrypt(this.account_name);
        }
        if (this.account_password != null) {
            this.account_password = AESUtil.encrypt(this.account_password);
        }

    }

    public void decryptModel() {
        if (this.account_name != null) {
            this.account_name = AESUtil.decrypt(this.account_name);
        }
        if (this.account_password != null) {
            this.account_password = AESUtil.decrypt(this.account_password);
        }
    }

}
