package cn.edu.xidian.tafei_mall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.xidian.tafei_mall.mapper")
public class TaFeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaFeiApplication.class, args);
    }
}
