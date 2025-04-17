package cn.edu.xidian.tafei_mall;


import cn.edu.xidian.tafei_mall.mapper.FavoriteMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/*@SpringBootApplication
@MapperScan("cn.edu.xidian.tafei_mall.mapper")
public class TaFeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaFeiApplication.class, args);
    }
}*/


/*测试用*/
@SpringBootApplication
@MapperScan(
        value = "cn.edu.xidian.tafei_mall.mapper",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = FavoriteMapper.class
        )
)
public class TaFeiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaFeiApplication.class, args);
    }
}