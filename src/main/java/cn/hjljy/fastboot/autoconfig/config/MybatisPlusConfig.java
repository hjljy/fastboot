package cn.hjljy.fastboot.autoconfig.config;

import cn.hjljy.fastboot.autoconfig.security.SecurityUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author yichaofan
 * @apiNote mybatis-plus配置
 * @since 2020年06月29日 23:08:00
 * TODO 需要替换mapper所在位置
 */
@Configuration
@MapperScan("cn.hjljy.fastboot.mapper")
public class MybatisPlusConfig implements MetaObjectHandler {
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


    /**
     * 新增时自动填充字段
     *
     * @param metaObject meta信息
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Long userId = SecurityUtils.getUserId();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "status", Integer.class, 0);
        this.strictInsertFill(metaObject, "createUser", Long.class, userId);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, "updateUser", Long.class, userId);
    }

    /**
     * 更新时自动填充的字段
     *
     * @param metaObject meta信息
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateUser", Long.class, SecurityUtils.getUserId());
    }
}
