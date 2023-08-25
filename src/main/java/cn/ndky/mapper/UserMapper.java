package cn.ndky.mapper;

import cn.ndky.controller.dto.AdminPasswordDTO;
import cn.ndky.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
public interface UserMapper extends BaseMapper<User> {
    @Update("update tb_user set password = #{newPassword} where userNumber = #{userNumber} and password = #{password}")
    int updatePassword(AdminPasswordDTO PasswordDTO);
}