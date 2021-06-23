package cn.hjljy.fastboot.common.aspect.log;

import java.lang.annotation.*;

/**
 * @author hjljy
 * @since  2020/6/4 17:47
 * @apiNote 项目操作日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 描述
     */
    String description() default "";
    /**
     * 日志类型
     */
    int type() default 0;
}
