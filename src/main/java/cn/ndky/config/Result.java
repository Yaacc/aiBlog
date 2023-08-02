package cn.ndky.config;

import cn.ndky.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class Result<T> {
    private String code;
    private String msg;
    private T data;
    public static Result success() {
        Result result = new Result<>();
        result.setCode(Constants.CODE_200);
        result.setMsg("成功");
        result.setData(null);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result<>();
        result.setCode(Constants.CODE_200);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(Constants.CODE_200);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}