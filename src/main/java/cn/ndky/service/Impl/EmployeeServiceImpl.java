package cn.ndky.service.Impl;

import cn.ndky.entity.Employee;
import cn.ndky.mapper.EmployeeMapper;
import cn.ndky.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}