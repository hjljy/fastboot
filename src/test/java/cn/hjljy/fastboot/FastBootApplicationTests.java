package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.pojo.demo.po.DemoPo;
import cn.hjljy.fastboot.pojo.sys.po.SysUserPo;
import cn.hjljy.fastboot.service.demo.IDemoService;
import cn.hjljy.fastboot.service.sys.ISysUserService;
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

    @Autowired
    ISysUserService sysUserService;

    @Test
    public void getAllow() {
        SysUserPo userPo = sysUserService.selectByUserName("admin");
        System.out.println(userPo.getNickName());
    }
}
