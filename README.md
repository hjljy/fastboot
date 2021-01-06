
# fastboot
基于springboot 自己配置的一套可以直接用于开发的框架。整合了很多基础的配置，可以直接进行开发。

# 由来
通常开发新项目的时候需要重新搭建框架，然后就需要整合一大堆东西，例如：swagger,mybatis,全局切面，异常处理等等一系列的东西。
所以可不可以直接弄一个整合好的，到时直接使用就可以了呢？

# 项目分支说明
master：单一数据源

member_dev: 会员系统开发分支   

## 已整合基础配置

logback-spring.xml: 项目日志配置 分级别，分天记录日志

BusinessException: 项目自定义异常

MainSiteErrorHandler: 404 500 error 页面映射处理

GlobalExceptionHandler: 全局异常处理器，用于处理全局异常的返回

LogAspect: 项目操作日志切面

BindingResultAspect: 项目JSR规范参数验证处理切面

TaskExecutor: 创建默认线程池，只需要@Autowird 注入就可以使用

CorsFilter :支持跨域CORS请求  需要在启动类上加注解 @EnableCors

TransactionConfiguration：全局事务处理器

ResponseBodyAdvice： 全局请求参数日志打印

JacksonCustomizerConfig：全局返回时间戳并且将参数里面的时间戳转换成LocalDateTime,全局将Long类型数据返回为string类型避免前端大整数精度丢失

JacksonUtil: jackson工具包 使用jackson实现字符串和实体类的转换，可自主扩展

SnowFlakeUtil: 雪花算法分布式全局随机ID生成器


## 已整合开发工具

Lombok：getter,setter注解生成器

Swagger2: api接口管理工具，dev环境默认开启swagger prod环境不开启  未采用原始swagger ui 采用国产UI https://gitee.com/xiaoym/knife4j 

Druid: 阿里druid数据库连接池，默认开启各项统计，访问路径：127.0.0.1:{port}/druid  账号admin 密码123456

Mybatis-plus: 基于mybatis的数据库ORM框架，CodeGenerator: 基于mybatis-plus的代码生成器 一键生成controller，service,dao，xml，entity类

HuTool: 糊涂工具包，一个包含众多工具类的jar包

Security: 一个功能强大且高度可定制的身份验证和访问控制权限框架。