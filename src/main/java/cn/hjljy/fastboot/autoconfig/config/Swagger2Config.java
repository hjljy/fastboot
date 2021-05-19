package cn.hjljy.fastboot.autoconfig.config;


import cn.hutool.core.collection.CollectionUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yichaofan
 * @date 2020/6/4 17:36
 * @apiNote swagger配置
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        //schema
        List<GrantType> grantTypes=new ArrayList<>();
        //密码模式
        String passwordTokenUrl="/oauth/token";
        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant=new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
        grantTypes.add(resourceOwnerPasswordCredentialsGrant);

        OAuth oAuth=new OAuthBuilder().name("oauth2")
                .grantTypes(grantTypes).build();
        //context
        //scope方位
        List<AuthorizationScope> scopes=new ArrayList<>();
        scopes.add(new AuthorizationScope("web","read all resources"));
        SecurityReference securityReference=new SecurityReference("Authorization",scopes.toArray(new AuthorizationScope[]{}));
        SecurityContextBuilder securityContextBuilder =new SecurityContextBuilder();
        SecurityContext securityContext=   securityContextBuilder.securityReferences(CollectionUtil.newArrayList(securityReference)).build();
        //schemas
        List<SecurityScheme> securitySchemes=CollectionUtil.newArrayList(oAuth);
        //securityContexts
        List<SecurityContext> securityContexts=CollectionUtil.newArrayList(securityContext);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //TODO 设置controller层所在位置
                .apis(RequestHandlerSelectors.basePackage("cn.hjljy.fastboot.controller"))
                .paths(PathSelectors.any())
                .build().groupName("全部接口").securityContexts(securityContexts).securitySchemes(securitySchemes);
    }

    private Contact getContact() {
        return new Contact("hjljy", "https://www.hjljy.cn", "hjljy@outlook.com");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("全部接口API")
                .description("系统全部接口API文档")
                .contact(getContact())
                .version("1.0")
                .build();
    }
}
