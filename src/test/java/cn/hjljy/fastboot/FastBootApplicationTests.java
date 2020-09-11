package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.pojo.demo.po.DemoPo;
import cn.hjljy.fastboot.service.demo.IDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@SpringBootTest
class FastBootApplicationTests {

    @Autowired
    FastBootConfig config;

    @Test
    public void getAllow() {
        Map<String, List<String>> request = config.getRequest();
        List<String> allow = request.get("allow");
        System.out.println(allow.toString());
    }
}
