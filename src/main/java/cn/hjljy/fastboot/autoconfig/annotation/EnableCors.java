package cn.hjljy.fastboot.autoconfig.annotation;

import java.lang.annotation.*;

/**
 * @author yichaofan
 * @apiNote 开启支持跨域CORS请求  只需要在启动类添加即可
 * @since 2020/6/4 11:12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableCors {
}
