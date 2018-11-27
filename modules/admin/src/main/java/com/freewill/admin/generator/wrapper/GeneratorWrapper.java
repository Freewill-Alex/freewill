package com.freewill.admin.generator.wrapper;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.freewill.common.exception.BussinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 代码生成器自定义封装
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-28 16:33
 */
@Slf4j
@Data
public class GeneratorWrapper {
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private GlobalConfig gc;
    private PackageConfig pc;
    private StrategyConfig strategy;
    private InjectionConfig cfg;
    private TemplateConfig tc;

    private GlobalConfig getDefClobalConf() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(PROJECT_PATH + "/代码生成器");
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(false);
        // XML columList
        gc.setBaseColumnList(true);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        //设置开发者（默认获取主机名）
        gc.setAuthor(System.getProperty("user.name"));
        gc.setOpen(false);

        return gc;
    }


    private PackageConfig getDefPackageConfig() {
        // 包配置传入
        PackageConfig pc = new PackageConfig();
        return pc.setParent("com.freewill.file")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mappings")
                .setService("service")
                .setController("controller");
    }

    private StrategyConfig getDefStrategyConf() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 下划线转驼峰命名 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 字段名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表 表名 new String[]
        strategy.setInclude();
        //lombok集成
        strategy.setEntityLombokModel(false);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.entityTableFieldAnnotationEnable(true);
        //是否生成controller控制器
        strategy.setRestControllerStyle(true);
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix();

        // 自定义实体父类
        strategy.setSuperEntityClass("");
        // 自定义实体,公共字段
        strategy.setSuperEntityColumns("", "");
        // 自定义 mapper 父类
        strategy.setSuperMapperClass("");
        // 自定义 service 父类
        strategy.setSuperServiceClass("");
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass("");
        // 自定义 controller 父类
        strategy.setSuperControllerClass("");
        return strategy;
    }

    public void startGenerate(DataSourceConfig dsc) throws BussinessException {
        if (null == dsc) {
            throw new BussinessException("数据源配置未生效");
        }
        gc = (null == gc) ? getDefClobalConf() : gc;
        pc = (null == pc) ? getDefPackageConfig() : pc;
        strategy = (null == strategy) ? getDefStrategyConf() : strategy;
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setPackageInfo(pc);
        mpg.setStrategy(strategy);
        if (null != cfg) {
            mpg.setCfg(cfg);
        }
        if (null != tc) {
            mpg.setTemplate(tc);
        }
        log.info("==========Generate success===========");
        // 关闭默认 xml 生成，调整生成 至 根目录 new TemplateConfig().setXml(null)
        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        // .setController("...");
        // .setEntity("...");
        // .setMapper("...");
        // .setXml("...");
        // .setService("...");
        // .setServiceImpl("..."); );
        mpg.execute();
    }
}
