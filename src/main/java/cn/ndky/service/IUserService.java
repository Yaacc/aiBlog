package cn.ndky.service;

import cn.ndky.controller.dto.AdminPasswordDTO;
import cn.ndky.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
public interface IUserService extends IService<User> {

    Page<User> getAdminUsers(Page<User> page,String username,String realName);

    Page<User> getUsers(Page<User> page, String username, String realName);

    void updatePassword(AdminPasswordDTO adminPasswordDTO);
}
