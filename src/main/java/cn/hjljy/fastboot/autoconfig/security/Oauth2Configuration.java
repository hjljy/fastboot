package cn.hjljy.fastboot.autoconfig.security;

import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author 海加尔金鹰
 * @apiNote TODO
 * @since 2020/9/20 22:32
 **/
@EnableAuthorizationServer
public class Oauth2Configuration extends AuthorizationServerConfigurerAdapter {
}
