package com.freewill.console.config;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Description Beetl配置段
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-25 9:29
 */

@Component
@ConfigurationProperties(prefix = "beetl.config")
public class BeetlCustomTemplateConfig {
    /**
     * 模板根目录 ，比如 "templates"
     */
    private String templatesPath = "templates";

    private String suffix = "html";

    private boolean dev = true;


    @Bean(initMethod = "init", name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();

        try {
            Properties extProperties = new Properties();
            if (this.dev) {
                extProperties.put("RESOURCE.autoCheck", "true");
            }

            beetlGroupUtilConfiguration.setConfigProperties(extProperties);
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlCustomTemplateConfig.class.getClassLoader(), this.templatesPath);
            beetlGroupUtilConfiguration.setResourceLoader(cploder);

            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setViewNames("*." + this.suffix);
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }


}
