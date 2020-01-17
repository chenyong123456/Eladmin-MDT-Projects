package cn.knowimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 * @author yong.Mr
 * @date 2020-01-15
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.knowimage.mapper"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }
}
