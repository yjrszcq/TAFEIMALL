package cn.edu.xidian.tafei_mall.model.vo.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSample<T> {
    private final int code;
    private final String msg;
    private final T data;

    public ResponseSample(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
