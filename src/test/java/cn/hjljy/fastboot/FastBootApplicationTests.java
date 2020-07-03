package cn.hjljy.fastboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
class FastBootApplicationTests {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;



}
