package cn.hjljy.fastboot;

import cn.hjljy.fastboot.pojo.demo.po.DemoPo;
import cn.hjljy.fastboot.service.demo.IDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
class FastBootApplicationTests {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;


    @Autowired
    IDemoService demoService;

    @Test
    public void setDemo() {
        DemoPo demoPo = new DemoPo();
        demoPo.setName("测试数据");
        demoPo.setAge(76);
        demoService.save(demoPo);
    }
}
