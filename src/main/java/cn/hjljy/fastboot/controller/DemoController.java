package cn.hjljy.fastboot.controller;

import cn.hjljy.fastboot.common.response.AjaxResult;
import cn.hjljy.fastboot.pojo.vo.DemoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yichaofan
 * @date 2020/6/4 17:47
 * @apiNote //TODO
 */
@Api(tags = "测试管理")
@RequestMapping("/ASAG")
@RestController
public class DemoController {

    @ApiOperation(value = "测试数据")
    @GetMapping(value = "/1234")
    public AjaxResult getName(){
        return null;
    }

    @ApiOperation(value = "aaa", notes = "测试数据")
    @PostMapping(value = "/123")
    public AjaxResult getName2(@RequestBody DemoVo vo){
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.setMsg(vo.getName());
        return ajaxResult;
    }
}
