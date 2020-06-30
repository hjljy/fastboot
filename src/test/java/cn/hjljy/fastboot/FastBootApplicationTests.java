package cn.hjljy.fastboot;

import cn.hjljy.fastboot.demo.pojo.po.DemoPo;
import cn.hjljy.fastboot.demo.service.IDemoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EnableTransactionManagement
class FastBootApplicationTests {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    IDemoService demoService;

    @Test
    void testTransactionAndPage() {

        Page page =new Page(1,10);
        demoService.page(page);
        List records = page.getRecords();
        System.out.println(records.size());
    }

}
