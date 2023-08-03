package cn.ndky.controller;


import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import cn.ndky.entity.Admin;
import cn.ndky.service.IAdminService;
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
@RequestMapping("/admin")
public class AdminController{
    @Autowired
    private IAdminService adminService;

    //登入功能
    @PostMapping("/login")
    public Result<?> login(HttpServletRequest request, @RequestBody Admin adminParam) {
        String password=adminParam.getPassword();
        LambdaQueryWrapper<Admin> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Admin::getAdminNumber, adminParam.getAdminNumber());
        Admin admin=adminService.getOne(lqw);
        if(admin==null){
            return Result.error(Constants.CODE_400,"用户不存在");
        }
        if(!admin.getPassword().equals(password)){
            return Result.error(Constants.CODE_400,"用户名或密码错误");
        }
        request.getSession().setAttribute("adminParam",admin.getId());
        return Result.success(admin);
    }

    //登出功能
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("adminParam");
        return Result.success("退出成功");
    }

    //分页查询
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String username,
                              @RequestParam(defaultValue = "") String realName){
        IPage<Admin> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if(!(username==null||"".equals(username))){
            queryWrapper.like("username",username);
        }
        if(!(realName==null||"".equals(realName))){
            queryWrapper.like("realName",realName);
        }
        IPage<Admin> page1 = adminService.page(page, queryWrapper);
        return Result.success(page1);
    }

    //添加管理员
    @PostMapping
    public Result<?> save(@RequestBody Admin admin){
        log.info("新增的员工信息：{}",admin.toString());
        admin.setPassword("123");
        adminService.save(admin);
        return Result.success("添加成功");
    }
    //修改员工信息
    @PutMapping
    public Result<?> update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return Result.success("修改信息成功");
    }

    //查找所有
    @GetMapping("/all")
    public Result<?> findAll() {
        return Result.success(adminService.list());
    }

    //按id查询
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        log.info("查询的id：{}", id);
        return Result.success(adminService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        log.info("将被删除的id：{}", id);
        adminService.removeById(id);
        return Result.success();
    }
}