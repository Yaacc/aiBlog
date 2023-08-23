package cn.ndky.mapper;

import cn.ndky.controller.dto.AdminPasswordDTO;
import cn.ndky.entity.Admin;
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
public interface AdminMapper extends BaseMapper<Admin> {
    @Update("update tb_admin set password = #{newPassword} where adminNumber = #{adminNumber} and password = #{password}")
    int updatePassword(AdminPasswordDTO adminPasswordDTO);
}