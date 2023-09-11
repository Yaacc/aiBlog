package cn.ndky.controller.dto;

import lombok.Data;

@Data
public class CommentDTo {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer articleId;
    private Integer parentId;
    private String userNumber;
    private String username;
}
