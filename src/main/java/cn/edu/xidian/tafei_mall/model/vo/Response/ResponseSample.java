package cn.edu.xidian.tafei_mall.model.vo.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSample<T> {
    @Getter
    private int code;
    @Getter
    private String msg;
    @Getter
    private T data;

    public ResponseSample(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
