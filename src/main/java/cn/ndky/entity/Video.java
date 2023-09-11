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
@TableName("tb_video")
@ApiModel(value = "Comment对象", description = "")
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user")
    @ApiModelProperty("用户ID")
    private String user;
    @ApiModelProperty("视频标题")
    @TableField("video_title")
    private  String videoTitle;
    @ApiModelProperty("视频简介")
    private String videoDesc;
    @ApiModelProperty("视频封面")
    private String videoCover;
    @ApiModelProperty("视频路径")
    private String videoPath;
    @ApiModelProperty("点赞数")
    private Integer videoLike;
    @ApiModelProperty("视频状态（是否禁用）")
    private Boolean videoStatus;
    @ApiModelProperty("视频发布时间")
    private String videoCreated;
    @TableField(exist = false)
    private String userNumber;
}