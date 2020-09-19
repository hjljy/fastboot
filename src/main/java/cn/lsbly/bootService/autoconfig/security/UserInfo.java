package cn.lsbly.bootService.autoconfig.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author 海加尔金鹰
 * @apiNote 用户信息类 主要是提供给验证框架使用，并将用户基本信息保存到这个类
 * @since 2020/9/11
 **/
public class UserInfo extends User {

    private static final long serialVersionUID = 1L;

    /**
     * 描述: 可以添加自定义的用户属性
     * 用户邮箱
     **/
    private String email;
    /**
     * 描述: 用户ID
     **/
    private String userId;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public UserInfo(String username, String password, String userId,Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId=userId;
    }

    public UserInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
