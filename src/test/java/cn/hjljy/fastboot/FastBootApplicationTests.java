package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FastBootApplicationTests {

    @Autowired
    FastBootConfig config;

    @Autowired
    ISysUserService sysUserService;

    @Test
    public void getAllow() {
        SysUser userPo = sysUserService.selectByUserName("admin");
        System.out.println(userPo.getNickName());
    }
}
