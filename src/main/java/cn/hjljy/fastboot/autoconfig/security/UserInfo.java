package cn.hjljy.fastboot.autoconfig.security;

import cn.hjljy.fastboot.pojo.sys.po.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @author 海加尔金鹰
 * @apiNote 用户信息类 主要是提供给验证框架使用，并将用户基本信息保存到这个类
 * @since 2020/9/11
 **/
@ApiModel("用户信息")
public class UserInfo extends User {

    private static final long serialVersionUID = 1L;

    /**
     * 描述: 可以添加自定义的用户属性
     * 用户邮箱
     **/
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    /**
     * 描述: 用户ID
     **/
    private Long userId;

    /**
     * 描述: 用户机构ID
     **/
    private Long orgId;
    /**
     * 描述: 用户昵称
     **/
    private String NickName;
    /**
     * 描述: 用户scope
     **/
    private String scope;
    /**
     * 描述：用户角色信息
     */
    private List<SysRole> roleDtos;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserInfo(String username, String password, Long userId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<SysRole> getRoleDtos() {
        return roleDtos;
    }

    public void setRoleDtos(List<SysRole> roleDtos) {
        this.roleDtos = roleDtos;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
