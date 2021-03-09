package cn.hjljy.fastboot;

import cn.hjljy.fastboot.autoconfig.config.FastBootConfig;
import cn.hjljy.fastboot.pojo.sys.po.SysUser;
import cn.hjljy.fastboot.service.sys.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class FastBootApplicationTests {

    @Autowired
    FastBootConfig config;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void getAllow() throws UnknownHostException {
        String key = "lockTest";
        InetAddress ia = InetAddress.getLocalHost();
        String value = ia.toString();
        //加锁  锁的过期时间为20秒
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, value, 20, TimeUnit.SECONDS);
        if (!lock) {
            //未获取到锁，直接返回
            return;
        }
        try {
            System.out.println("业务逻辑执行在小于20秒范围内");
        } catch (Exception e) {
            System.out.println("业务错误信息");
        } finally {
            // 方式一 直接使用del
            redisTemplate.delete(key);
            // 方式二 使用lua脚本
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript redisScript = new DefaultRedisScript(script);
            List<String> keys = new ArrayList<>();
            keys.add(key);
            redisTemplate.execute(redisScript, keys, value);
        }

    }
}
