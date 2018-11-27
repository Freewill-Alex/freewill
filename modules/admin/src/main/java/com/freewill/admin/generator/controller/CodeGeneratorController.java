package com.freewill.admin.generator.controller;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.freewill.admin.generator.wrapper.GeneratorWrapper;
import com.freewill.common.exception.BussinessException;
import com.freewill.common.web.annotation.ResponseResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.baomidou.mybatisplus.annotation.DbType.MYSQL;

/**
 * @Description 代码生成器的接口
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-28 14:07
 */
@Slf4j
@Data
@ConfigurationProperties("spring.datasource.druid")
@RestController
@ResponseResult
@RequestMapping("/generator")
public class CodeGeneratorController {

    private String url;
    private String driverClassName;
    private String username;
    private String password;

    @RequestMapping("/run")
    public String runGenerator() throws BussinessException {
        GeneratorWrapper gw = new GeneratorWrapper();
        gw.setPc(getCutsomPackageConfig());
        gw.startGenerate(getDefDataSourceConf());
        return "已完成代码生成，请查阅文件。";
    }

    /**
     * 获取当前数据源
     *
     * @return DataSourceConfig
     */
    private DataSourceConfig getDefDataSourceConf() {
        // 数据源配置传入

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(MYSQL);
        dsc.setUrl(url);
        dsc.setDriverName(driverClassName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        return dsc;
    }

    private PackageConfig getCutsomPackageConfig() {
        // 包配置传入
        PackageConfig pc = new PackageConfig();
        return pc.setParent("com.freewill.admin")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mappings")
                .setService("service")
                .setController("controller");
    }
}
