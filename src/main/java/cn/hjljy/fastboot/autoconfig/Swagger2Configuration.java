package cn.hjljy.fastboot.autoconfig;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
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
public class Swagger2Configuration {

    @Bean
    public Docket createRestApi() {

        List<Parameter> pars = new ArrayList<>();

        //构建默认参数
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Content-Type").description("Content-Type")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true)
                .defaultValue("application/json").build();
        pars.add(ticketPar.build());
        //构建默认请求验证参数
        ticketPar = new ParameterBuilder();
        ticketPar.name("Authorization").description("Authorization")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true)
                .defaultValue("111111111111111111111111111111111111111111111111").build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //TODO 设置controller层所在位置
                .apis(RequestHandlerSelectors.basePackage("cn.hjljy.fastboot.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private Contact getContact(){
        return new Contact("hjljy","https://www.hjljy.cn","hjljy@outlook.com");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("全部接口API")
                .description("系统全部接口API文档")
                .version("1.0")
                .build();
    }

    private ApiInfo sysApiInfo() {
        return new ApiInfoBuilder()
                .title("系统管理模块api")
                .description("sys下所有api接口")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket sysApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(sysApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/v1/sys/**"))
                .build()
                .groupName("系统管理")
                .pathMapping("/");
    }

    private ApiInfo memberApiInfo() {
        return new ApiInfoBuilder()
                .title("会员管理模块api")
                .description("member下所有api接口")
                .title("swagger RESTful APIs")
                .description("swagger RESTful APIs")
                .termsOfServiceUrl("http://www.hjljy.cn/")
                .contact(getContact())
                .version("1.0")
                .build();
    }

    @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(memberApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/v1/member/**"))
                .build()
                .groupName("会员管理")
                .pathMapping("/");
    }
}
