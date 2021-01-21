package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.common.enums.sys.SysUserTypeEnum;
import cn.hjljy.fastboot.common.exception.BusinessException;
import cn.hjljy.fastboot.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : yichaofan
 * @apiNote :用户信息工具类
 * @since : 2020/9/11 18:22
 */
@Slf4j
public class SecurityUtils {

    /**
     * 描述根据账号密码进行调用security进行认证授权 主动调
     * 用AuthenticationManager的authenticate方法实现
     * 授权成功后将用户信息存入SecurityContext当中
     * @param username 用户名
     * @param password 密码
     * @param authenticationManager 认证授权管理器,
     * @see  AuthenticationManager
     * @return UserInfo  用户信息
     */
    public static UserInfo login(String username, String password, AuthenticationManager authenticationManager) throws AuthenticationException {
        //使用security框架自带的验证token生成器  也可以自定义。
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(username,password );
        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return (UserInfo) authenticate.getPrincipal();
    }

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
        throw new BusinessException(ResultCode.USER_NOT_FOUND);
    }

    /**
     * 获取当前登录用户ID
     * @return 返回用户ID
     */
    public static String getUsername(){
        User userInfo = getUserInfo();
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

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        UserInfo userInfo = getUserInfo();
        return userInfo.getUserId();
    }

    /**
     * 获取用户类型
     */
    public static String getUserType() {
        UserInfo userInfo = getUserInfo();
        return userInfo.getUserType();
    }

    /**
     * 是否是超级管理员
     */
    public static boolean IsSuperAdmin(){
        return SysUserTypeEnum.SUPER_ADMIN.name().equals(getUserType());
    }

    /**
     * 是否是管理员
     */
    public static boolean IsAdmin(){
        return SysUserTypeEnum.ADMIN.name().equals(getUserType());
    }
}
