package cn.hjljy.fastauth;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author 海加尔金鹰 www.hjljy.cn
 * @version V1.0
 * @email hjljy@outlook.com
 * @description: mybaits-plus 代码生成器  直接运行main方法然后根据提示进行修改
 * @since 2020/1/15 23:14
 **/
public class CodeGenerator {


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/fast-auth/src/main/java");
        gc.setAuthor("海加尔金鹰（www.hjljy.cn）");
        gc.setOpen(false);
        //设置实体类后缀
        gc.setEntityName("%sPo");
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/springboot?serverTimezone=GMT&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        String scanner = scanner("请输入整体业务包名");
        String modelName = StringUtils.isBlank(scanner) ? "" : "."+scanner;
        //moduleName是整体分模块

        pc.setParent("cn.hjljy.fastauth");
        pc.setMapper("mapper" +modelName);
        pc.setService("service"+modelName);
        pc.setServiceImpl("service"+modelName+".impl");
        pc.setEntity("pojo"+modelName+".po");
        pc.setController("controller"+modelName);
        mpg.setPackageInfo(pc);


        String dtoPath = pc.getParent() + ".pojo.dto";
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 不输出默认的XML 默认生成的xml在mapper层里面
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        //配置自定义输出的文件   xml和dto
        //模板引擎是 velocity
        String xmlTemplatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(xmlTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/fast-auth/src/main/resources/mapper/" + scanner
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        String dtoTemplatePath = "/dto.java.vm";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(dtoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "fast-auth/src/main/java/cn/hjljy/fastauth/pojo/"+scanner+"/dto/" +
                        tableInfo.getEntityName() + "Dto" + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {

            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("dtoPackage", dtoPath);
                this.setMap(map);
            }
        };
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        //设置逻辑删除字段
        strategy.setLogicDeleteFieldName("status");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}


