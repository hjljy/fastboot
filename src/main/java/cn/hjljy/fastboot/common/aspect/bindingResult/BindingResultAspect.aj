package cn.hjljy.fastboot.common.aspect.bindingResult;

import cn.hjljy.fastboot.common.result.ResultCode;
import cn.hjljy.fastboot.common.result.ResultInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yichaofan
 * @since 2020/6/5 18:04
 * @apiNote  已废弃，直接使用全局异常进行捕捉返回比较方便  详见下面的说明
 * @see cn.hjljy.fastboot.common.exception.GlobalExceptionHandler#errorHandler(MethodArgumentNotValidException) (HttpServletRequest, MethodArgumentNotValidException)
 */
@Deprecated
public class BindingResultAspect {

    /**
     * 环绕切面  切点是controller层所有带有BindingResult 参数的方法。
     * @param joinPoint 切点
     * @param bindingResult  JSR参数校验结果类
     * @return Object
     * @throws Throwable
     * TODO 设置controller层所在位置
     */
    @Around("execution(* cn.hjljy..*.controller..*.*(..)) && args(..,bindingResult)")
    public Object validateParam(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
        Object obj;
        if (bindingResult.hasErrors()) {
            ResultInfo<Boolean> error = ResultInfo.error(ResultCode.PARAMETERS_EXCEPTION);
            error.setMsg(bindingResult.getAllErrors().toString());
            return error;
        } else {
            // 校验通过  方法继续执行
            obj = joinPoint.proceed();
        }
        return obj;
    }

}
