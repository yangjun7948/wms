package com.xiaoyai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @author yangjun
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan("com.xiaoyai.**")
public class WareHouseApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(WareHouseApplication.class, args);
    }
}
