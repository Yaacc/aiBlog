package cn.ndky.service;

import cn.ndky.controller.dto.AdminPasswordDTO;
import cn.ndky.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
public interface IAdminService extends IService<Admin> {
    void updatePassword(AdminPasswordDTO userPasswordDTO);
}