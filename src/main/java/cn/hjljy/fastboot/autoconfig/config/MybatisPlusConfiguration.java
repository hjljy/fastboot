package cn.hjljy.fastboot.autoconfig.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hjljy
 * @apiNote mybatis-plus配置
 * @since 2020年06月29日 23:08:00
 * TODO 需要替换mapper所在位置
 */
@Configuration
@MapperScan("cn.hjljy.fastboot.mapper")
public class MybatisPlusConfiguration {
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        //设置分页数据库类型
        page.setDbType(DbType.MYSQL);
        page.setDialect(new MySqlDialect());
        //优化count sql
        page.setCountSqlParser(new JsqlParserCountOptimize(true));
        //设置每页最大值
        page.setLimit(999L);
        return page;
    }
}
