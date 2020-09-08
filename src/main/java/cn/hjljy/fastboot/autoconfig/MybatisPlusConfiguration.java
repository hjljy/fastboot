package cn.hjljy.fastboot.autoconfig;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import javassist.expr.NewArray;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yichaofan
 * @apiNote mybatis-plus配置
 * @since 2020年06月29日 23:08:00
 * TODO 需要替换mapper所在位置
 */
@Configuration
@MapperScan("cn.hjljy.fastboot.mapper")
@EnableTransactionManagement
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
