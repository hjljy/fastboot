package cn.hjljy.fastboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class FastbootApplicationTests {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Test
    void contextLoads() throws InterruptedException {
        //用于获取到本java进程，进而获取总线程数
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtimeBean.getName();
        System.out.println("JVM Name = " + jvmName);
        long pid = Long.valueOf(jvmName.split("@")[0]);
        System.out.println("JVM PID  = " + pid);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        int n = 100;
        for (int i = 0; i < n; i++) {
            int finalI = i;
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(finalI +"当前线程总数为：" + bean.getThreadCount());
            });
        }
        Thread.sleep(1000*61);


        System.out.println("线程总数为 = " + bean.getThreadCount());
    }

}
