package com.dimple.file;

import com.dimple.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 文件服务
 *
 * @author Dimple
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DimpleFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(DimpleFileApplication.class, args);
    }
}