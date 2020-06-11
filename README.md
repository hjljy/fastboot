# fastboot
基于springboot 自己配置的一套可以直接用于开发的框架。整合了很多基础的配置，可以直接进行开发。

# 由来
通常开发新项目的时候需要重新搭建框架，然后就需要整合一大堆东西，例如：swagger,mybatis,全局切面，异常处理等等一系列的东西。
所以可不可以直接弄一个整合好的，到时直接使用就可以了呢？

## 已整合基础配置如下

logback-spring.xml: 项目日志配置

CustomException: 项目自定义异常

LogAspect: 项目操作日志切面

BindingResultAspect: 项目JSR规范参数验证处理切面

MainSiteErrorHandler: 404 error 页面映射处理

GlobalExceptionHandler: 全局异常处理器，用于处理全局异常的返回

TaskExecutor: 创建默认线程池，只需要@Autowird 注入就可以使用

CorsFilter :支持跨域CORS请求  需要在启动类上加注解 @EnableCors

Swagger2: dev环境默认开启swagger prod环境不开启  未采用原始swagger ui 采用国产UI https://gitee.com/xiaoym/knife4j 

druid: 配置阿里druid数据库连接池，默认开启各项统计，访问路径：127.0.0.1:{port}/druid  账号admin 密码123456