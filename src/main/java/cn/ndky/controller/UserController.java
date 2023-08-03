package cn.ndky.controller;


import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import cn.ndky.entity.User;
import cn.ndky.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yaacc
 * @since 2023-07-31
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    //登入功能
    @PostMapping("/login")
    public Result<?> login(HttpServletRequest request, @RequestBody User userParam) {
        String password=userParam.getPassword();
        LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
        lqw.eq(User::getUserNumber, userParam.getUserNumber());
        User user=userService.getOne(lqw);
        if(user==null){
            return Result.error(Constants.CODE_400,"用户不存在");
        }
        if(!user.getPassword().equals(password)){
            return Result.error(Constants.CODE_400,"用户名或密码错误");
        }
        request.getSession().setAttribute("userParam",user.getId());
        return Result.success(user);
    }

    //登出功能
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userParam");
        return Result.success("退出成功");
    }

    //分页查询
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "") Integer pageNum,
                              @RequestParam(defaultValue = "") Integer pageSize,
                              @RequestParam(defaultValue = "") String realName){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!(realName==null||"".equals(realName))){
            queryWrapper.like("realName",realName);
        }
        IPage<User> page1 = userService.page(page, queryWrapper);
        return Result.success(page1);
    }

    //修改员工信息
    @PutMapping
    public Result<?> update(@RequestBody User employee){
        userService.updateById(employee);
        return Result.success("修改信息成功");
    }

    //查找所有
    @GetMapping("/all")
    public Result<?> findAll() {
        return Result.success(userService.list());
    }

    //按id查询
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        log.info("查询的id：{}", id);
        return Result.success(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        log.info("将被删除的id：{}", id);
        userService.removeById(id);
        return Result.success();
    }
}