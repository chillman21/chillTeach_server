package club.chillman.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChillTeachException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;
    @Override
    public String toString() {
        return "ChillTeachException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }

}