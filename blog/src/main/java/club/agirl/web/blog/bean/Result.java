package club.agirl.web.blog.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    private boolean success;

    public static <T> Result<T> ok(T data) {
        return new Result<>(200, null, data, true);
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null, false);
    }

}
