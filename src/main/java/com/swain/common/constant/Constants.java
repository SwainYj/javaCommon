package com.swain.common.constant;

/**
 * 常量定义
 */
public interface Constants {
    /**
     * 语言类型常量定义
     */
    interface LANGUAGES {
        //中文
        int LANGUAGE_ZH = 1;

        //英文
        int LANGUAGE_EN = 2;
    }

    /**
     * 分页查询常量定义
     */
    interface PAGE {
        //首页
        int PAGE_NO_ONE = 1;

        //页面大小
        int PAGE_SIZE = 15;
    }

    /**
     * 数字常量定义
     */
    interface NumConstant {
        Integer MINUE_ONE_NUM = -1;

        Integer ZERO_NUM = 0;

        Integer ONE_NUM = 1;

        Integer TWO_NUM = 2;

        Integer FOUR_NUM = 4;

        Integer FIFTEEN_NUM = 15;

        Integer TWENTY_FIVE_NUM = 25;

        Integer FIFTY_NUM = 50;

        Integer ONE_HUNDRED_NUM = 100;

        Integer ONE_THOUSAND_NUM = 1000;

        Integer THREE_THOUSAND_NUM = 3000;

        Integer ONE_HUNDRED_THOUSAND_NUM = 100000;

        Integer ONE_MILLION_NUM = 1000000;

        Integer MAX_ACTIVITY_USER = 99999999;
    }

    /**
     * 时间常量定义
     */
    interface TimeConstant {
        int MIN_2_SEC = 60;

        int HOUR_2_SEC = 3600;

        int DAY_2_SEC = 86400;

        int WEEK_2_DAY = 7;

        int MONTH_2_DAY = 30;

        int HALF_YEAR = 180;
    }

    /**
     * token校验类型
     */
    interface TokenType {
        Integer TOKEN_ALL = 0;//token
        Integer TOKEN_SYSTEM = 1;//系统token
        Integer TOKEN_USER = 2;//用户token
        Integer TOKEN_MAIN_ACCT = 3;//主账户token
        Integer TOKEN_CHILD_ACCT = 4;//子帐户token
    }
}
