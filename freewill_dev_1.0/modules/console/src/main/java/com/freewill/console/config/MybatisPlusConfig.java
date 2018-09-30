package com.freewill.console.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @Description MybatisPlus扩展配置（如分页）
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-21 18:49
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.freewill.console.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}