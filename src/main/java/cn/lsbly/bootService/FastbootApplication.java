package cn.lsbly.bootService;

import cn.lsbly.bootService.autoconfig.annotation.EnableCors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCors
public class FastbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastbootApplication.class, args);
    }
}
