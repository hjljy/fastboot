package cn.hjljy.fastboot.autoconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 海加尔金鹰
 * @apiNote 读取项目自定义的配置信息
 * @date  2021-06-23
 **/
@Configuration
@ConfigurationProperties(prefix = "fastboot")
public class FastBootConfig {
    /**
     * 描述: prefix = "fastboot" 配置表示读取配置文件当中fastboot开头的配置
     * request 属性对应配置文件当中的request  保持同名原则
     **/
    private Map<String, List<String>> request = new HashMap<>();

    public Map<String, List<String>> getRequest() {
        return request;
    }

    public void setRequest(Map<String, List<String>> request) {
        this.request = request;
    }

    public List<String> getRequestAllow(){
        return request.get("allow");
    }
}
