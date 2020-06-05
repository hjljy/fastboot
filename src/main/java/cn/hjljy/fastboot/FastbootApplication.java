package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.annotation.EnableCors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCors //开启跨域请求支持
public class FastbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastbootApplication.class, args);
    }
}
