package cn.ndky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("userId")
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("文章ID")
    @TableField("articleId")
    private  Integer articleId;
    @ApiModelProperty("评论")
    private String content;
    @ApiModelProperty("评论时间")
    private String createTime;
    @ApiModelProperty("父ID")
    private Integer parentId;

    @TableField(exist = false)
    private Comment parentComment;

    @TableField(exist = false)
    private String avatar;
    @TableField(exist = false)
    private String userNumber;
}