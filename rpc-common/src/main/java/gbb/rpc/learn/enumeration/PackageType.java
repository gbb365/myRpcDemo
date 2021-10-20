package gbb.rpc.learn.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author goliang
 * @date 2021/10/19 20:48
 */
@Getter
@AllArgsConstructor
public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);
    private final int code;
}
