package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FastBootApplicationTests {

    @Autowired
    FastBootConfig config;

    @Test
    public void getAllow() {
        List<String> requestAllow = config.getRequestAllow();
    }
}
