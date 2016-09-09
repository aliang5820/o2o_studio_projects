package com.fanwe.model;

import com.fanwe.dao.LocalUserModelDao;
import com.fanwe.event.EnumEventTag;
import com.sunday.eventbus.SDEventManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.Data;

@Data
public class LocalUserModel implements Serializable {
    private int _id;
    private int user_id;
    private int supplier_id;//用于自媒体首页
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
            localModel.setSupplier_id(model.getSupplier_id());
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
