package cn.ndky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("tb_employee")
@ApiModel(value = "Employee对象", description = "")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("序号")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("员工编号")
      @TableField("employeeNumber")
      private String employeeNumber;

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


    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }

      public void setEmployeeNumber(String employeeNumber) {
          this.employeeNumber = employeeNumber;
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
    
    public String getPhone() {
        return phone;
    }

      public void setPhone(String phone) {
          this.phone = phone;
      }
    
    public String getIDCard() {
        return IDCard;
    }

      public void setIDCard(String IDCard) {
          this.IDCard = IDCard;
      }


    @Override
    public String toString() {
        return "Employee{" +
              "id=" + id +
                  ", employeeNumber=" + employeeNumber +
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