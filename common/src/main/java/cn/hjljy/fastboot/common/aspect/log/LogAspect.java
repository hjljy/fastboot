package cn.hjljy.fastboot.common.aspect.log;

import cn.hjljy.fastboot.common.result.ResultInfo;
import cn.hutool.json.JSONObject;
import com.alibaba.druid.support.json.JSONUtils;
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

    /**
     * 描述:
     * 这里可以使用注解 @Log 如果某个方法上面有这个注解就会记录操作日志
     * <p>
     * 作者: yichaofan
     * 日期: 16:56 2020/8/20
     * TODO 需要将包名换成项目包名
     **/
    @Pointcut("@annotation(cn.hjljy.fastboot.common.aspect.log.Log)")
    public void serviceLog1() {
    }

    /**
     * 描述:
     * 这里就将记录controller层所有方法的请求日志
     * <p>
     * 作者: yichaofan
     * 日期: 16:56 2020/8/20
     * TODO 需要将包名换成项目包名
     *
     * @return void
     **/
    @Pointcut("execution(* cn.hjljy.fastboot.controller..*.*(..))")
    public void serviceLog() {
    }

    /**
     * 描述:
     * TODO 需要选择对应的切面日志记录方式
     * <p>
     * 作者: yichaofan
     * 日期: 16:59 2020/8/20
     *
     * @param point
     * @return java.lang.Object
     **/
    @Around("serviceLog()")
    public Object serviceLogAround(ProceedingJoinPoint point) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        try {
            long start = System.currentTimeMillis();
            Object result = point.proceed();
            long runTime = System.currentTimeMillis() - start;
            System.out.println(result.toString());
            // TODO  通常会将操作日志信息记录到数据库当中，例如mysql mongodb
            logger.info("\r\n请求对应类:{}\r\n请求对应方法:{}\r\n请求操作参数:{}\r\n执行耗时:{}", className, methodName, handlerParameter(point), runTime);
            return result;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
            return ResultInfo.error("系统开小差了");
        }
    }

    /**
     * 获取切面点请求参数信息
     *
     * @param point 切点
     * @return String
     */
    private String handlerParameter(ProceedingJoinPoint point) {
        StringBuilder stringBuilder = new StringBuilder();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Log log = methodSignature.getMethod().getAnnotation(Log.class);
        Object[] args = point.getArgs();
        for (Object pojo : args) {
            JSONObject object =new JSONObject();
            stringBuilder.append("\n{parameterValue:").append( JSONUtils.toJSONString(pojo)).append(" }");
        }
        if (log != null) {
            stringBuilder.append(" logDesc:").append(log.description());
            stringBuilder.append(" logType:").append(log.type());
        }
        return stringBuilder.toString();
    }
}
