package com.fanwe.model;

import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.event.EnumEventTag;
import com.sunday.eventbus.SDEventManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LocalUserModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int _id;

    private int user_id;
    private String user_email;
    private String user_name;
    private String user_pwd;
    private String user_money;
    private String user_money_format;
    private String user_score;
    private String user_mobile;
    private String qr_code;
    private int is_tmp;

    public static void dealLoginSuccess(User_infoModel model, boolean postEvent) {
        if (model != null) {
            LocalUserModel localModel = new LocalUserModel();

            localModel.setUser_id(model.getId());
            localModel.setUser_name(model.getUser_name());
            localModel.setUser_pwd(model.getUser_pwd());
            localModel.setUser_email(model.getEmail());
            localModel.setUser_mobile(model.getMobile());
            localModel.setIs_tmp(model.getIs_tmp());
            localModel.setQr_code(model.getQr_code());

            dealLoginSuccess(localModel, postEvent);
        }
    }

    public static void dealLoginSuccess(LocalUserModel model, boolean postEvent) {
        LocalUserModelDao.insertModel(model);
        if (postEvent) {
            SDEventManager.post(EnumEventTag.LOGIN_SUCCESS.ordinal());
        }
    }

    public int getIs_tmp() {
        return is_tmp;
    }

    public void setIs_tmp(int is_tmp) {
        this.is_tmp = is_tmp;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getUser_money_format() {
        return user_money_format;
    }

    public void setUser_money_format(String user_money_format) {
        this.user_money_format = user_money_format;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public LocalUserModel deepClone() {
        try {
            // 将对象写到流里
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            // 从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (LocalUserModel) (oi.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
