package cn.edu.xidian.tafei_mall.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class PayCreateVO {
    private List<String> orderIds;
}
