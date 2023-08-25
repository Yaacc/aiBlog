package cn.ndky.controller.dto;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class AdminPasswordDTO {
    private Integer id;
    private String adminNumber;
    private String userNumber;
    private String username;
    private String password;
    private String newPassword;
    private String token;
}
