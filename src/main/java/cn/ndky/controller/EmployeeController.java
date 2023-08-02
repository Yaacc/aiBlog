package cn.ndky.controller;


import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import cn.ndky.entity.Employee;
import cn.ndky.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
@RequestMapping("/employee")
public class EmployeeController{
    @Autowired
    private IEmployeeService employeeService;

    //登入功能
    @PostMapping("/login")
    public Result<?> login(HttpServletRequest request, @RequestBody Employee employeeParam) {
        String password=employeeParam.getPassword();
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Employee::getEmployeeNumber, employeeParam.getEmployeeNumber());
        Employee employee=employeeService.getOne(lqw);
        if(employee==null){
            return Result.error(Constants.CODE_400,"用户不存在");
        }
        if(!employee.getPassword().equals(password)){
            return Result.error(Constants.CODE_400,"用户名或密码错误");
        }
        request.getSession().setAttribute("adminParam",employee.getId());
        return Result.success(employee);
    }

    //登出功能
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeParam");
        return Result.success("退出成功");
    }

    //分页查询
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "") Integer pageNum,
                              @RequestParam(defaultValue = "") Integer pageSize,
                              @RequestParam(defaultValue = "") String realName){
        IPage<Employee> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if(!(realName==null||"".equals(realName))){
            queryWrapper.like("realName",realName);
        }
        IPage<Employee> page1 = employeeService.page(page, queryWrapper);
        return Result.success(page1);
    }

    //修改员工信息
    @PutMapping
    public Result<?> update(@RequestBody Employee employee){
        employeeService.updateById(employee);
        return Result.success("修改信息成功");
    }

    //查找所有
    @GetMapping("/all")
    public Result<?> findAll() {
        return Result.success(employeeService.list());
    }

    //按id查询
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        log.info("查询的id：{}", id);
        return Result.success(employeeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        log.info("将被删除的id：{}", id);
        employeeService.removeById(id);
        return Result.success();
    }
}