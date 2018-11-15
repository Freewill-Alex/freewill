package com.freewill.cms.generator.controller;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.freewill.cms.common.exception.BussinessException;
import com.freewill.cms.generator.wrapper.GeneratorWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 代码生成器的接口
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-28 14:07
 */
@RestController
@RequestMapping("/code")
@Slf4j
public class CodeGeneratorController {
    @RequestMapping("/generate")
    public String runGenerator() throws BussinessException {
        GeneratorWrapper gw = new GeneratorWrapper();
        gw.startGenerate(null);
        return "已完成代码生成，请查阅文件。";
    }

    /**
     *
     * @return
     */
    public static DataSourceConfig getDefDataSourceConf() {
        // 数据源配置传入
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://xxx:3306/ejauto_sdp?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("ejauto123");
        return dsc;
    }
}
