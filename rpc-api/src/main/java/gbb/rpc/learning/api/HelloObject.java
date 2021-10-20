package gbb.rpc.learning.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author goliang
 * @date 2021/9/25 19:23
 */
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
    public HelloObject(){}
    public HelloObject(int id, String message){
        this.id = id;
        this.message = message;
    }
}
