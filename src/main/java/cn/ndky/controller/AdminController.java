package cn.ndky.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import cn.ndky.entity.Admin;
import cn.ndky.entity.User;
import cn.ndky.service.IAdminService;
import cn.ndky.utils.RandomNameUtil;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;


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
    /*@PostMapping
    public Result<?> save(@RequestBody Admin admin){
        log.info("新增的员工信息：{}",admin.toString());
        admin.setPassword("123");
        adminService.save(admin);
        return Result.success("添加成功");
    }*/
    //修改员工信息
    @PostMapping("/update")
    public Result<?> update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return Result.success("修改信息成功");
    }
    @PostMapping("/password")
    public Result<?> password(@RequestBody AdminPasswordDTO adminPasswordDTO) {
//        adminPasswordDTO.setPassword(SecureUtil.md5(adminPasswordDTO.getPassword()));     // md5加密
//        adminPasswordDTO.setNewPassword(SecureUtil.md5(adminPasswordDTO.getNewPassword()));
        adminPasswordDTO.setPassword(adminPasswordDTO.getPassword());
        adminPasswordDTO.setNewPassword(adminPasswordDTO.getNewPassword());
        adminService.updatePassword(adminPasswordDTO);
        return Result.success("修改密码成功,请重新登录");
    }
    /**
    * 注册
    */
    // 新增或更新
    @PostMapping
    public Result<?> saveAndUpdate(@RequestBody Admin admin){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("adminNumber", admin.getAdminNumber());
        Admin one= adminService.getOne(queryWrapper);
        if(one!=null){
            return Result.error(Constants.CODE_400,"账号已存在");
        }
        else {
            if(admin.getId() == null && admin.getPassword() == null){
//            user.setPassword(SecureUtil.md5("123"));
                admin.setPassword("123");
            }
            if(admin.getUsername()==null){
                RandomNameUtil randomNameUtil = new RandomNameUtil();
                admin.setUsername(RandomNameUtil.getStringRandom(10));
            }
        }
        return Result.success(adminService.saveOrUpdate(admin));
    }
    //批量删除
    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(adminService.removeByIds(ids));
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
    // 按用户名查询
//    @GetMapping("/username/{username}")
//    public Result findByUsername(@PathVariable String username) {
//        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", username);
//        return Result.success(adminService.getOne(queryWrapper));
//    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        log.info("将被删除的id：{}", id);
        adminService.removeById(id);
        return Result.success();
    }

    //导入功能
    @PostMapping("/import")
    public Result<?> importExcel(MultipartFile multipartFile) throws Exception{
        InputStream inputStream = multipartFile.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //reader.addHeaderAlias("序号","id");
        reader.addHeaderAlias("管理员编号","adminNumber");
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("姓名","realName");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("身份证号","IDCard");
        List<Admin> list = reader.readAll(Admin.class);
        System.out.println(list);
        boolean saveBatch = adminService.saveBatch(list);
        if(!saveBatch){
            return Result.error(Constants.CODE_400,"导入失败,查看用户账号是否重复");
        }
        return Result.success("导入成功");
    }
    //导出功能
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response)throws Exception {
        List<Admin> list =adminService.list();
        ExcelWriter writer= ExcelUtil.getWriter(true);
        //只导出含有别名的属性
        writer.setOnlyAlias(true);
        //writer.addHeaderAlias("id","序号");
        writer.addHeaderAlias("adminNumber","管理员编号");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("realName","姓名");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("IDCard","身份证号");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("管理员信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream outputStream=response.getOutputStream();
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }
}