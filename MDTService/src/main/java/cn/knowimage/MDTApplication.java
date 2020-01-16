package cn.knowimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * MDT的启动类
 * @author yong.Mr
 * #date 2019-1-6
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.knowimage.mdt.patienthistory.mapper","cn.knowimage.mdt.mapper"})  // 可以进行多包的扫描, 每个包的扫描用','号分隔
public class MDTApplication {

    public static void main(String[] args) {
        SpringApplication.run(MDTApplication.class, args);
    }

}
