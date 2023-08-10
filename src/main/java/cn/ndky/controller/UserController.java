package cn.ndky.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import cn.ndky.entity.Admin;
import cn.ndky.entity.User;
import cn.ndky.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
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
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String username,
                              @RequestParam(defaultValue = "") String realName){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!(username==null||"".equals(username))){
            queryWrapper.like("username",username);
        }
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

    // 查找所有
    @GetMapping("/all")
    public Result<?> findAll() {
        return Result.success(userService.list());
    }

    // 获取用户总数
    @GetMapping("/total")
    public Result<?> getNumberOfUsers(){
        return Result.success(userService.count());
    }
    // 按id查询
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        log.info("查询的id：{}", id);
        return Result.success(userService.getById(id));
    }

    // 新增或更新
    @PostMapping
    public Result<?> saveAndUpdate(@RequestBody User user){
        if(user.getId() == null && user.getPassword() == null){
//            user.setPassword(SecureUtil.md5("123"));
            user.setPassword("123");
        }
        return Result.success(userService.saveOrUpdate(user));
    }

    // 按序号删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        log.info("将被删除的id：{}", id);
        userService.removeById(id);
        return Result.success();
    }
//    批量删除
    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }
    //导入功能
    @PostMapping("/import")
    public Result<?> importExcel(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        --------------------------------------
//        List<List<Object>> list = reader.read(1);
//        List<User> users = CollUtil.newArrayList();
//        for (List<Object> row : list){
//            User user = new User();
//            user.setUserNumber(row.get(0).toString());
//            user.setUsername(row.get(1).toString());
//            user.setPassword(row.get(2).toString());
//            user.setRealName(row.get(3).toString());
//            user.setSex(row.get(4).toString());
//            user.setAge(Integer.valueOf(row.get(5).toString()));
//            user.setPhone(row.get(6).toString());
//            user.setIDCard(row.get(7).toString());
//            users.add(user);
//        }
//        ---------------------------------------
//        reader.addHeaderAlias("序号","id");
        reader.addHeaderAlias("用户编号","userNumber");
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("姓名","realName");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("电话号码","phone");
        reader.addHeaderAlias("身份证号","IDCard");
        List<User> list = reader.readAll(User.class);       // 表头必须是英文或匹配Bean
        System.out.println(list);
        boolean saveBatch = userService.saveBatch(list);
        if(!saveBatch){
            return Result.error(Constants.CODE_400,"导入失败,查看用户账号是否重复");
        }
        return Result.success("导入成功");
    }
    //导出功能
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response)throws Exception {
        List<User> list =userService.list();
        ExcelWriter writer= ExcelUtil.getWriter(true);
        //只导出含有别名的属性
        writer.setOnlyAlias(true);
        //writer.addHeaderAlias("id","序号");
        writer.addHeaderAlias("userNumber","用户编号");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("realName","姓名");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("phone","电话号码");
        writer.addHeaderAlias("IDCard","身份证号");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream outputStream=response.getOutputStream();
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }

}