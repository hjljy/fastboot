package cn.hjljy.fastboot.autoconfig.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author 海加尔金鹰
 * @apiNote 用户验证类
 * @since 2020/9/10 22:41
 **/
@Data
public class UserInfo extends User {

    private static final long serialVersionUID = 1L;

    private String email;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public UserInfo(String username, String password, String email,Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email=email;
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
}
