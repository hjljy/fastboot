package cn.hjljy.fastauth.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author : yichaofan
 * @apiNote :数据库配置
 * @since : 2020/9/7 18:50
 */
@Configuration
@MapperScan(basePackages = "cn.hjljy.fastauth.mapper")
public class DatasourceConfiguration {

    @Bean
    @Primary
    public DataSource getDatasource(){
        return DruidDataSourceBuilder.create().build();
    }
}
