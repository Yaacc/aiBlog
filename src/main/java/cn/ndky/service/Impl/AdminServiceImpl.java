package cn.ndky.service.Impl;

import cn.ndky.common.Constants;
import cn.ndky.common.exception.ServiceException;
import cn.ndky.config.Result;
import cn.ndky.controller.AdminPasswordDTO;
import cn.ndky.entity.Admin;
import cn.ndky.mapper.AdminMapper;
import cn.ndky.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOError;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;
    @Override
    public void updatePassword(AdminPasswordDTO adminPasswordDTO)throws ServiceException{
        int update = adminMapper.updatePassword(adminPasswordDTO);
        if (update < 1) {
           throw new ServiceException(Constants.CODE_600, "旧密码错误，请重新输入");
        }
    }
}