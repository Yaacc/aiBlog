package cn.ndky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
@TableName("tb_admin")
@ApiModel(value = "Admin对象", description = "")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("序号")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("管理员编号")
      @TableField("adminNumber")
      private String adminNumber;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("真实姓名")
      @TableField("realName")
      private String realName;

      @ApiModelProperty("性别")
      private String sex;

      @ApiModelProperty("年龄")
      private Integer age;

      @ApiModelProperty("身份证号")
      @TableField("IDCard")
      private String IDCard;


    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getAdminNumber() {
        return adminNumber;
    }

      public void setAdminNumber(String adminNumber) {
          this.adminNumber = adminNumber;
      }
    
    public String getUsername() {
        return username;
    }

      public void setUsername(String username) {
          this.username = username;
      }
    
    public String getPassword() {
        return password;
    }

      public void setPassword(String password) {
          this.password = password;
      }
    
    public String getRealName() {
        return realName;
    }

      public void setRealName(String realName) {
          this.realName = realName;
      }
    
    public String getSex() {
        return sex;
    }

      public void setSex(String sex) {
          this.sex = sex;
      }
    
    public Integer getAge() {
        return age;
    }

      public void setAge(Integer age) {
          this.age = age;
      }
    
    public String getIDCard() {
        return IDCard;
    }

      public void setIDCard(String IDCard) {
          this.IDCard = IDCard;
      }

    @Override
    public String toString() {
        return "Admin{" +
              "id=" + id +
                  ", adminNumber=" + adminNumber +
                  ", username=" + username +
                  ", password=" + password +
                  ", realName=" + realName +
                  ", sex=" + sex +
                  ", age=" + age +
                  ", IDCard=" + IDCard +
              "}";
    }
}
