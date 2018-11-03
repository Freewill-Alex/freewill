package com.freewill.console;

import com.ibeetl.starter.BeetlSqlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.Banner.Mode.OFF;

/**
 * 将spring的启动和配置单独出来
 *
 * @author sanma
 * @SpringBootApplication 等价于 :
 * -     @Configuration 通过@ImportResource引入xml文件,@Value读取键值 ,@Bean读取配置的bean实例
 * -     @ComponentScan 同xml中的自动扫描组件
 * -     @EnableAutoConfiguration
 * @EnableAutoConfiguration 下包含了一系列自动配置类的清单 ,并按顺序执行 ,在Spring3时代就有@EnableWebMvc注解
 * [SpringBoot之@EnableAutoConfiguration原理及自定义扩展 ](http://blog.csdn.net/xiaoyu411502/article/details/52770723)
 * <p>
 * MongoAutoConfiguration.class为例 :
 * -    使用了@Configuration ,标识是一个配置
 * -    定义了必要的Mongo对象@Bean
 * -    使用了@EnableConfigurationProperties ,将application.properties配置文件中的属性映射到Java类中 ,便于使用
 * -    @ConditionOnClass表明加载条件 - Mongo.class位于类路径上
 * -    @ConditionalOnMissingBean说明Spring Boot仅仅在当前上下文中不存在Mongo对象时，才会实例化一个Bean
 */
@Slf4j
@SpringBootApplication(exclude = {BeetlSqlConfig.class})
public class FreewillConsoleBootstrap {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FreewillConsoleBootstrap.class);
        application.setBannerMode(OFF);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
        log.info("=====================SpringBoot Running Started===================");
    }
}
