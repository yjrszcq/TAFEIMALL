package cn.edu.xidian.tafei_mall;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        // 数据库连接配置
        String url = "jdbc:mysql://localhost:3306/TAFEI_MALL?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "19171107";

        // 代码生成配置
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("shenyaoguan") // 作者
                            .outputDir(System.getProperty("user.dir") + "/src/main/java") // 输出路径
                            .enableSwagger() // 开启Swagger注解
                            .fileOverride(); // 覆盖已生成文件
                })
                .packageConfig(builder -> {
                    builder.parent("cn.edu.xidian.tafei_mall") // 父包名
                            .moduleName("") // 模块名（为空则不生成模块目录）
                            .entity("entity") // 实体类包名
                            .mapper("mapper") // Mapper接口包名
                            .service("service") // Service接口包名
                            .serviceImpl("service.impl") // Service实现类包名
                            .controller("controller") // Controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")); // XML文件路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude( // 需要生成的表名
                                    "user",
                                    "address",
                                    "product",
                                    "cart",
                                    "cart_item",
                                    "order",
                                    "order_item",
                                    "session"
                            )
                            .addTablePrefix("t_", "sys_") // 过滤表前缀
                            .entityBuilder() // 实体类配置
                            .enableLombok() // 使用Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder() // Controller配置
                            .enableRestStyle() // 开启RestController
                            .serviceBuilder() // Service配置
                            .formatServiceFileName("%sService") // Service接口文件名格式
                            .mapperBuilder() // Mapper配置
                            .enableMapperAnnotation() // 启用Mapper注解
                            .enableBaseResultMap() // 启用BaseResultMap
                            .enableBaseColumnList(); // 启用BaseColumnList
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker模板引擎
                .execute();
    }
}