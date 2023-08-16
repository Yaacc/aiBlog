package cn.ndky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-08-15
 */
@TableName("tb_article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("文章编号")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("发布人")
      private String user;

      @ApiModelProperty("标题")
      private String name;

      @ApiModelProperty("内容")
      private String content;

      @ApiModelProperty("发布时间")
      private String time;

      @ApiModelProperty("点赞")
      private Integer likes;

      @ApiModelProperty("收藏")
      private Integer collect;

      @ApiModelProperty("1删除")
      private Boolean isDelete;

      @ApiModelProperty("1启用")
      private Boolean enable;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getUser() {
        return user;
    }

      public void setUser(String user) {
          this.user = user;
      }
    
    public String getName() {
        return name;
    }

      public void setName(String name) {
          this.name = name;
      }
    
    public String getContent() {
        return content;
    }

      public void setContent(String content) {
          this.content = content;
      }
    
    public String getTime() {
        return time;
    }

      public void setTime(String time) {
          this.time = time;
      }
    
    public Integer getLikes() {
        return likes;
    }

      public void setLikes(Integer likes) {
          this.likes = likes;
      }
    
    public Integer getCollect() {
        return collect;
    }

      public void setCollect(Integer collect) {
          this.collect = collect;
      }
    
    public Boolean getIsDelete() {
        return isDelete;
    }

      public void setIsDelete(Boolean isDelete) {
          this.isDelete = isDelete;
      }
    
    public Boolean getEnable() {
        return enable;
    }

      public void setEnable(Boolean enable) {
          this.enable = enable;
      }

    @Override
    public String toString() {
        return "Article{" +
              "id=" + id +
                  ", user=" + user +
                  ", name=" + name +
                  ", content=" + content +
                  ", time=" + time +
                  ", likes=" + likes +
                  ", collect=" + collect +
                  ", isDelete=" + isDelete +
                  ", enable=" + enable +
              "}";
    }
}
