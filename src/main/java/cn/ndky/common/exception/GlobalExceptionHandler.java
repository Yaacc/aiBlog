package cn.ndky.common.exception;

import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
@Slf4j
@RestControllerAdvice
//全局异常处理器
public class GlobalExceptionHandler {
    //捕获异常，并返回结果
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<?> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.error(exception.getMessage());
        //如果包含Duplicate entry，则说明有条目重复
        if (exception.getMessage().contains("Duplicate entry")) {
            //对字符串切片
            String[] split = exception.getMessage().split(" ");
            //字符串格式是固定的，所以这个位置必然是username
            String adminNumber = split[2];
            //拼串作为错误信息返回
            return Result.error(Constants.CODE_400,"用户名" + adminNumber + "已存在");
        }
        //如果是别的错误那我也没招儿了
        return Result.error("-1","未知错误");
    }
}