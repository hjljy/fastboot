package cn.hjljy.fastboot.autoconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 海加尔金鹰
 * @apiNote 读取项目自定义的配置信息
 * @since 2020/9/9 0:48
 **/
@Configuration
@ConfigurationProperties(prefix = "fastboot")
public class FastBootConfig {
    private Map<String, List<String>> request = new HashMap<>();

    public Map<String, List<String>> getRequest() {
        return request;
    }

    public void setRequest(Map<String, List<String>> request) {
        this.request = request;
    }
}
