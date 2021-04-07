package cn.hjljy.fastboot.common.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yichaofan
 * @since  2020/6/4 17:47
 * @apiNote 项目操作日志切面
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 描述:
     * 这里可以使用注解 @Log 如果某个方法上面有这个注解就会记录操作日志
     * TODO 需要将包名换成项目包名
     **/
    @Pointcut("@annotation(cn.hjljy.fastboot.common.aspect.log.Log)")
    public void serviceLog() {
    }

    /**
     * 描述:
     *
     * @param point 切点
     * @return java.lang.Object
     **/
    @Around("serviceLog()")
    public Object serviceLogAround(ProceedingJoinPoint point) throws Throwable {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        // TODO  可以根据实际需求将操作日志信息记录到数据库当中，例如 mongodb
        log.info("\r\n操作类名：{}\r\n操作方法：{}\r\n操作参数：{}", className,methodName,handlerParameter(point));
        long start = System.currentTimeMillis();
        //需要执行切面 不捕捉异常信息直接抛出
        Object result = point.proceed();
        long runTime = System.currentTimeMillis() - start;
        log.info("\r\n操作用时：{}毫秒\r\n返回结果:{}",runTime,result.toString());
        return result;

    }

    /**
     * 获取切面点请求参数信息
     *
     * @param point 切点
     * @return String
     */
    private String handlerParameter(ProceedingJoinPoint point) {
        Map<String, Object> map = new HashMap<>(16);
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Log log = methodSignature.getMethod().getAnnotation(Log.class);
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = point.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            Object params = args[i];
            String name = parameterNames[i];
            //如果参数是request 不记录
            if(params instanceof ServletRequest){
                continue;
            }
            map.put(name,params);
        }
        if (log != null) {
            map.put("logDesc",log.description());
            map.put("logType",log.type());
        }
        return map.toString();
    }
}
