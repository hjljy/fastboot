package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.common.constant.SystemConstant;
import cn.hjljy.fastboot.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * @author : hjljy
 * @apiNote :用户信息工具类
 * @since : 2020/9/11 18:22
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录的所有认证信息
     * @return 返回认证信息
     */
    public static Authentication getAuthentication(){
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    /**
     * 获取当前登录用户信息
     * @return 返回用户信息
     */
    public static UserInfo getUserInfo(){
        Authentication authentication = getAuthentication();
        if(authentication!=null){
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserInfo){
                return (UserInfo)principal;
            }
        }
        return null;
    }

    /**
     * 获取当前登录用户信息
     * @return 返回用户信息
     */
    public static Long getUserId(){
        UserInfo userInfo = getUserInfo();
       if(null==userInfo){
           return SystemConstant.DEFAULT_USER_ID;
       }
        return userInfo.getUserId();
    }

    /**
     * 获取当前登录用户账号
     * @return 返回用户账号
     */
    public static String getUsername(){
        User userInfo = getUserInfo();
        Assert.notNull(userInfo,"当前未登录");
        return userInfo.getUsername();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
