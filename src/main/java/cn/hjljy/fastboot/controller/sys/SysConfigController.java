package cn.hjljy.fastboot.controller.sys;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通用配置接口 例如：字典枚举  版本信息等无需权限校验的接口
 * </p>
 *
 * @author 海加尔金鹰（www.hjljy.cn）
 * @since 2020-11-11
 */
@RestController
@RequestMapping("sys/config")
@Api(value = "通用配置接口", tags = "通用配置接口")
public class SysConfigController {

}
