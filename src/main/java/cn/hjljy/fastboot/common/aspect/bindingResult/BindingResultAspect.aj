package cn.hjljy.fastboot.common.aspect.bindingResult;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author yichaofan
 * @date 2020/6/5 18:04
 * @apiNote 全局JSR参数校验结果处理器
 */
@Aspect
@Component
public class BindingResultAspect {

    @Around("execution(* cn.hjljy..*.controller.*.*(..)) && args(..,bindingResult)")
    public Object validateParam(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
        Object obj ;
        if (bindingResult.hasErrors()) {
            ResultInfo error = ResultInfo.error(ResultCode.PARAMERS_EXCEPTION);
            error.setMsg(bindingResult.getAllErrors().toString());
            return error;
        } else {
            // 没有错误方法继续执行
            obj = joinPoint.proceed();
        }
        return obj;
    }

}
