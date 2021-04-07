package cn.hjljy.fastboot.common.constant;

/**
 * @author yichaofan
 * @apiNote Oauth常量类
 * @since 2020/11/17 17:19
 */
public class Oauth2Constant {
    /**
     * 客户端
     */
    public static final String CLIENT_ID = "fastboot";
    /**
     * secret客户端安全码
     */
    public static final String CLIENT_SECRET = "hjljy2021";
    /**
     * 密码模式授权模式
     */
    public static final String GRANT_TYPE_PASSWORD = "password";
    /**
     * 授权码模式  授权码模式使用到了回调地址，是最为复杂的方式，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式。
     */
    public static final String AUTHORIZATION_CODE = "authorization_code";
    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN = "refresh_token";
    /**
     * 简化授权模式
     */
    public static final String IMPLICIT = "implicit";
    /**
     * 客户端模式
     */
    public static final String GRANT_TYPE = "client_credentials";
    /**
     * 授权范围  web端
     */
    public static final String SCOPE_WEB = "web";
    /**
     * 授权范围  ios端
     */
    public static final String SCOPE_IOS = "ios";
    /**
     * 授权范围  android端
     */
    public static final String SCOPE_ANDROID = "android";
    /**
     * 授权范围  项目名称
     */
    public static final String SCOPE_BOOT = "boot";
    /**
     * token 有效时间 一个月
     */
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
    /**
     * 刷新token有效时间 一个月
     */
    public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;


}
