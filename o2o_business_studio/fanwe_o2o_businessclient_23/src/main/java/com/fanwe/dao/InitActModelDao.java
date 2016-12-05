package com.fanwe.dao;

import com.fanwe.model.InitActModel;

/**
 * Created by Edison on 2016/12/5.
 */

public class InitActModelDao {
    public static boolean saveModel(InitActModel model) {
        return JsonDbModelDao.getInstance().insertOrUpdateJsonDbModel(model, true);

    }

    public static InitActModel getModel() {
        return JsonDbModelDao.getInstance().queryJsonDbModel(InitActModel.class, true);
    }

    public static void deleteAllModel() {
        JsonDbModelDao.getInstance().deleteJsonDbModel(InitActModel.class);
    }
}
