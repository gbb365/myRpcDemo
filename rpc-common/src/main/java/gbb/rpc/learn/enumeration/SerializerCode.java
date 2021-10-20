package gbb.rpc.learn.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author goliang
 * @date 2021/10/19 20:52
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {
    JSON(1);
    private final int code;
}
