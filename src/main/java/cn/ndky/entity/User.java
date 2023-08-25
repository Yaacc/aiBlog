package cn.ndky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("序号")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户编号")
      @TableField("userNumber")
      private String userNumber;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
//      @JsonIgnore
      private String password;

      @ApiModelProperty("真实姓名")
      @TableField("realName")
      private String realName;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("年龄")
      private Integer age;

      @ApiModelProperty("联系电话")
      private String phone;

      @ApiModelProperty("身份证号")
      @TableField("IDCard")
      private String IDCard;

      @ApiModelProperty("角色Id")
      private Integer roleId;
    @Override
    public String toString() {
        return "Employee{" +
              "id=" + id +
                  ", userNumber=" + userNumber +
                  ", username=" + username +
                  ", password=" + password +
                  ", realName=" + realName +
                  ", sex=" + sex +
                  ", age=" + age +
                  ", phone=" + phone +
                  ", IDCard=" + IDCard +
              "}";
    }
}