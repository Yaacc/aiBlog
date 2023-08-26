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
@TableName("tb_favorite")
@ApiModel(value = "Favorite对象", description = "")
public class Favorite implements Serializable {
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

}