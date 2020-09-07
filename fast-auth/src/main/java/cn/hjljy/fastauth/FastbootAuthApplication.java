package cn.hjljy.fastauth;

import cn.hjljy.fastboot.autoconfig.annotation.EnableCors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yichaofan
 */
@SpringBootApplication
@EnableCors
public class FastbootAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastbootAuthApplication.class, args);
    }
}
