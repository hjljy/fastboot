package cn.hjljy.fastboot.common.aspect.log;

import cn.hjljy.fastboot.common.result.ResultInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

/**
 * @author yichaofan
 * @date 2020/6/4 17:47
 * @apiNote 项目操作日志切面
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //切点是Log这个注解
    @Pointcut("@annotation(cn.hjljy.fastboot.common.aspect.log.Log)")
    public void serviceLog() {
    }

    @Around("serviceLog()")
    public Object serviceLogAround(ProceedingJoinPoint point) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        try {
            long start = System.currentTimeMillis();
            Object result = point.proceed();
            long runTime = System.currentTimeMillis() - start;
            // TODO  通常会将操作日志信息记录到数据库当中，例如mysql mongodb
            logger.info("class:{},method:{},args:{},runTime:{}", className, methodName, handlerParameter(point), runTime);
            return result;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
            return ResultInfo.error("系统开小差了");
        }
    }

    /**
     * 获取切面点请求参数信息
     * @param point 切点
     * @return String
     */
    private String handlerParameter(ProceedingJoinPoint point) {
        StringBuilder stringBuilder = new StringBuilder();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Class[] parameterTypes = methodSignature.getParameterTypes();
        Log log = methodSignature.getMethod().getAnnotation(Log.class);
        Object[] args = point.getArgs();
        int i = 0;
        for (Object pojo : args) {
            stringBuilder.append("{parameterName:").append(parameterNames[i]);
            stringBuilder.append(" parameterType:").append(parameterTypes[i]);
            stringBuilder.append(" parameterValue:").append(pojo);
            stringBuilder.append(" },");
            i++;
        }
        stringBuilder.append(" logDesc:").append(log.description());
        stringBuilder.append(" logType:").append(log.type());
        return stringBuilder.toString();
    }
}
