
# fastboot
基于springboot 自己配置的一套可以直接用于开发的框架。整合了很多基础的配置，可以直接进行开发。

# 由来

通常开发新项目的时候需要重新搭建框架，然后就需要整合一大堆东西，例如：swagger,mybatis,全局切面，异常处理等等一系列的东西。
所以可不可以直接弄一个整合好的，到时直接使用就可以了呢？
# 项目说明
如果是自己进行简单的开发，没有团队合作的因素，代码想怎么写就怎么写，分不分层也无所谓，不过保持一个良好的开发习惯，还是比较好的！！！

    fastboot
        --autoconfig    配置信息 常规配置信息 全局异常，切面，过滤器，权限等等

        --common        公共模块 包含枚举，常量，工具类等等

        --controller    接口层

        --mapper        数据库层

        --pojo          实体类

        --service       服务层

        --CodeGenerator 代码生成器

        --FastbootApplication

## 使用说明
1 下载代码

    git clone https://github.com/hjljy/fastboot.git/

2 修改application.yml当中的数据库连接

3 查看项目当中的TODO 按提示进行修改（可选）

4 启动项目即可
    
## master分支：基础版本 无任何业务数据

### 已整合基础配置

logback-spring.xml: 项目日志配置 分级别，分天记录日志，最多保留15天

BusinessException: 项目自定义异常

GlobalExceptionHandler: 全局异常处理器，用于处理全局异常的返回

LogAspect: 项目操作日志切面

TaskExecutor: 创建默认线程池，只需要@Autowird 注入就可以使用

CorsFilter :支持跨域CORS请求  需要在启动类上加注解 @EnableCors

TransactionConfiguration：全局事务切面处理器

ResponseBodyAdvice： 全局请求参数日志打印

JacksonCustomizerConfig：全局返回时间戳并且将参数里面的时间戳转换成LocalDateTime

SnowFlakeUtil: 雪花算法分布式全局随机ID生成器

### 已整合开发工具或框架
Springboot:2.5.0

Lombok：getter,setter注解生成器

Swagger3: api接口管理工具，dev环境默认开启swagger prod环境不开启  未采用原始swagger ui 采用国产UI https://gitee.com/xiaoym/knife4j 

Druid 1.1.22: 阿里druid数据库连接池，默认开启各项统计，访问路径：127.0.0.1:{port}/druid  账号admin 密码123456

Mybatis-plus 3.4.0: 基于mybatis的数据库ORM框架，CodeGenerator: 基于mybatis-plus的代码生成器 一键生成controller，service,dao，xml，entity类

HuTool: 糊涂工具包，一个包含众多工具类的jar包

Security 2.1.3.RELEASE: 一个功能强大且高度可定制的身份验证和访问控制权限框架。

Redisson: redis操作工具包