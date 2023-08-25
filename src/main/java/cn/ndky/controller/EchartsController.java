package cn.ndky.controller;

import cn.hutool.core.collection.CollUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.Role;
import cn.ndky.entity.User;
import cn.ndky.mapper.RoleMapper;
import cn.ndky.mapper.UserMapper;
import cn.ndky.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页控制
 *
 * @author yaacc
 * @since 2023-08-10
 */
@Slf4j
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 获取男女用户数
     */
    @GetMapping("/maleOrFemale")
    public Result<?> maleOrFemale(){
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "1"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<User> wrapperMale = new QueryWrapper<>();
        QueryWrapper<User> wrapperFemale = new QueryWrapper<>();
        wrapperMale.in("role_id",list)
                .eq("sex", "男");
        wrapperFemale.in("role_id",list)
                .eq("sex", "女");
        return Result.success(CollUtil.newArrayList(userService.count(wrapperMale),userService.count(wrapperFemale)));
    }

//    @GetMapping("/members")
//    public Result<?> members(){
//        List<User> list = userService.list();
//
//    }
}
