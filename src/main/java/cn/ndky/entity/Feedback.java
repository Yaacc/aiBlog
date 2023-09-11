package cn.ndky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_feedback")
@ApiModel(value = "Feedback对象", description = "")
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("内容")
    @TableField("content")
    private String content;
    @ApiModelProperty("反馈时间")
    @TableField("createDate")
    private String createDate;
    @ApiModelProperty("回复ID")
    private Integer adminId;
    @ApiModelProperty("回复")
    private String answer;
    @TableField("answerDate")
    @ApiModelProperty("回复时间")
    private String answerDate;
    @TableField(exist = false)
    private String userNumber;
}