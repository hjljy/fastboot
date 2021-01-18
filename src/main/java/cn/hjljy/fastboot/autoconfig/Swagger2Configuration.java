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
                .title("swagger RESTful APIs")
                .description("swagger RESTful APIs")
                .termsOfServiceUrl("http://www.hjljy.cn/")
                .contact(getContact())
                .version("1.0")
                .build();
    }


}
