package com.fanwe.constant;

public class Constant {
    /*18500142331 123456*/

    public static final String FILE_DIR = "o2o/biz/";
    public static final String QR_CODE_FILE_NAME = "biz_qr_code.jpg";

    public final static int CONSULT_DOC_PICTURE = 1000;
    public final static int CONSULT_DOC_CAMERA = 1001;

    public enum TitleType {
        TITLE_NONE, TITLE
    }

    public static final class UserLoginState {
        public static final int UN_LOGIN = 0;
        public static final int LOGIN = 1;
        public static final int TEMP_LOGIN = 2;
    }

    public static final class ExtraConstant {
        public static final String EXTRA_ID = "extra_id";
        public static final String EXTRA_MODEL = "extra_model";
        public static final String EXTRA_TYPE = "extra_type";
    }

    public static final class RequestCodeActicity {
        public static final int REQUESTCODENORMAL = 1;
    }

    public static final class Apply {
        public static final int HHR = 0;//合伙人
        public static final int HYD = 1;//会员店
    }

    public static final class Reward {
        public static final int ORDER = 1;//订单奖励
        public static final int HHR = 2;//合伙人招募奖励
        public static final int HYD = 3;//会员店招募奖励
    }

    public static final class Pay {
        /**
         * 微信支付渠道
         */
        public static final String CHANNEL_WECHAT = "wx";
        /**
         * 支付支付渠道
         */
        public static final String CHANNEL_ALIPAY = "alipay";
    }
}
