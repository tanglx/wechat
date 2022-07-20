package com.tanglx.wechat.common.constant;

/**
 * @Describe 系统常量
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
public class Constants {

    /**
     * 默认的访问Token的HTTP请求头的名字，管理后台端
     */
    public static final String ACCESS_DEFAULT_TOKEN_HEADER_NAME = "Default-Access-Token";

    /**
     * 缓存用户key
     * 用法：key+token
     */
    public static final String SYS_USER_KEY = "SYS_USER_";

    /**
     * 用户过期时间(单位:秒，7天)
     */
    public static final int SYS_USER_KEY_EXP = 60 * 60 * 24 * 7;

    /**
     * 创建时间字段名
     */
    public static final String CREATE_TIME = "createTime";


    /**
     * 图片验证码过期时间(单位:秒)
     */
    public static final int IMG_VERIFY_CODE_EXP = 60 * 60;

    /**
     * 缓存图片验证码key
     * 用法：key+token
     */
    public static final String IMG_VERIFY_CODE = "IMG_VERIFY_CODE_";


    /**
     * 微信Access_token
     */
    public static final String WECHAT_ACCESS_TOKEN = "WECHAT_ACCESS_TOKEN";


}
