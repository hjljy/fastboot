package cn.lsbly.bootService.autoconfig.annotation;

import java.lang.annotation.*;

/**
 * @author yichaofan
 * @date 2020/6/4 11:12
 * @apiNote 开启支持跨域CORS请求  只需要在启动类添加即可
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableCors {
}
