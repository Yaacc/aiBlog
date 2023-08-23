package cn.ndky.controller.dto;

import lombok.Data;

@Data
public class AdminPasswordDTO {
    private String adminNumber;
    private String username;
    private String password;
    private String newPassword;
    private String token;
}
