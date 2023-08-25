package cn.ndky.service.Impl;

import cn.ndky.common.Constants;
import cn.ndky.common.exception.ServiceException;
import cn.ndky.controller.dto.AdminPasswordDTO;
import cn.ndky.entity.Role;
import cn.ndky.entity.User;
import cn.ndky.mapper.RoleMapper;
import cn.ndky.mapper.UserMapper;
import cn.ndky.service.IUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Override
    public Page<User> getAdminUsers(Page<User> page,String username,String realName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", getAdminRoleId()); // Filter users with admin role
        if(!(username==null||"".equals(username))){
            queryWrapper.like("username",username);
        }
        if(!(realName==null||"".equals(realName))){
            queryWrapper.like("realName",realName);
        }
        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<User> getUsers(Page<User> page, String username, String realName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", getUserRoleId()); // Filter users with admin role
        if(!(username==null||"".equals(username))){
            queryWrapper.like("username",username);
        }
        if(!(realName==null||"".equals(realName))){
            queryWrapper.like("realName",realName);
        }
        return userMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void updatePassword(AdminPasswordDTO PasswordDTO) {
        int update = userMapper.updatePassword(PasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "旧密码错误，请重新输入");
        }
    }

    private List<Integer> getAdminRoleId() {

        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "0"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        return list;
    }
    private List<Integer> getUserRoleId() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "1"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        return list;
    }
}