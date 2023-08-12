package cn.ndky.controller;

import lombok.Data;

@Data
public class AdminPasswordDTO {
    private String adminNumber;
    private String password;
    private String newPassword;
}
