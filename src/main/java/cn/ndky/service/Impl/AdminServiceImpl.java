package cn.ndky.service.Impl;

import cn.ndky.entity.Admin;
import cn.ndky.mapper.AdminMapper;
import cn.ndky.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}