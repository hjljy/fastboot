package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.annotation.EnableCors;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author hjljy
 */
@SpringBootApplication
@EnableCors
public class FastbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastbootApplication.class, args);
    }
}
