package gbb.rpc.learn.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author goliang
 * @date 2021/9/25 19:40
 */
@Getter
@AllArgsConstructor

public enum ResponseCode {

    SUCCESS(200,"调用方法成功"),
    FAIL(500,"调用方法失败"),
    NOT_FOUND_METHOD(501,"未找到指定方法"),
    NOT_FOUND_CLASS(502,"未找到指定类");
    private final int code;
    private final String message;
}
