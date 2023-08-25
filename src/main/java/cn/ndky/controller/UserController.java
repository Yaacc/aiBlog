package cn.ndky.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.ndky.common.Constants;
import cn.ndky.config.Result;
import cn.ndky.controller.dto.AdminPasswordDTO;
import cn.ndky.entity.Role;
import cn.ndky.entity.User;
import cn.ndky.mapper.RoleMapper;
import cn.ndky.mapper.UserMapper;
import cn.ndky.service.IUserService;
import cn.ndky.utils.RandomNameUtil;
import cn.ndky.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    private static final ObjectMapper JACKSON = new ObjectMapper();
    //登入功能
    @PostMapping("/login")
    public Result<?> login( @RequestBody AdminPasswordDTO userParam) {
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
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",user.getRoleId());
        Role role = roleMapper.selectOne(queryWrapper);
        BeanUtil.copyProperties(user,userParam , true);
        String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
        userParam.setToken(token);
        userParam.setId(user.getId());
        if(role.getRoleCode().equals(Constants.CODE_1)){
            return Result.success(userParam,Constants.CODE_1);
        }
        else {
            return Result.success(userParam,Constants.CODE_0);
        }
    }

    //登出功能
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userParam");
        return Result.success("退出成功");
    }
    /**
     * 修改密码
     */
    @PostMapping("/password")
    public Result<?> password(@RequestBody AdminPasswordDTO adminPasswordDTO) {
//        adminPasswordDTO.setPassword(SecureUtil.md5(adminPasswordDTO.getPassword()));     // md5加密
//        adminPasswordDTO.setNewPassword(SecureUtil.md5(adminPasswordDTO.getNewPassword()));
        adminPasswordDTO.setPassword(adminPasswordDTO.getPassword());
        adminPasswordDTO.setNewPassword(adminPasswordDTO.getNewPassword());
        userService.updatePassword(adminPasswordDTO);
        return Result.success("修改密码成功,请重新登录");
    }
    /**
     * 注册
     */
    @SneakyThrows
    @PostMapping("/register")
    public Result<?> register(@RequestBody Map<String,Object> map){
        Object aObject = map.get("user");
        Object bObject = map.get("role");
        User user = JACKSON.readValue(JACKSON.writeValueAsString(aObject), User.class);
        Role role = JACKSON.readValue(JACKSON.writeValueAsString(bObject),Role.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userNumber",user.getUserNumber());
        User one= userService.getOne(queryWrapper);
        if(one!=null){
            return Result.error(Constants.CODE_400,"账号已存在");
        }
        else {
            roleMapper.insert(role);
            Integer id = role.getId();
            if(user.getId() == null && user.getPassword() == null){
//            user.setPassword(SecureUtil.md5("123"));
                user.setPassword("123");
            }
            user.setRoleId(id);
            if(user.getUsername()==null){
                RandomNameUtil randomNameUtil = new RandomNameUtil();
                user.setUsername(RandomNameUtil.getStringRandom(10));
            }
            return Result.success(userService.saveOrUpdate(user));
        }

    }

    // 新增或更新
    @SneakyThrows
    @PostMapping
    public Result<?> saveAndUpdate(@RequestBody Map<String,Object> map){
        Object aObject = map.get("user");
        Object bObject = map.get("role");
        User user = JACKSON.readValue(JACKSON.writeValueAsString(aObject), User.class);
        Role role = JACKSON.readValue(JACKSON.writeValueAsString(bObject),Role.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userNumber",user.getUserNumber());
        User one= userService.getOne(queryWrapper);
        if(one!=null){
            return Result.success(userService.saveOrUpdate(user));
        }
        else {
            roleMapper.insert(role);
            Integer id = role.getId();
            if(user.getId() == null && user.getPassword() == null){
//            user.setPassword(SecureUtil.md5("123"));
                user.setPassword("123");
                user.setRoleId(id);
            }
            if(user.getUsername()==null){
                RandomNameUtil randomNameUtil = new RandomNameUtil();
                user.setUsername(RandomNameUtil.getStringRandom(10));
            }
            return Result.success(userService.saveOrUpdate(user));
        }

    }
    @PostMapping("/update")
    public Result<?> Update(@RequestBody User user){
        //QueryWrapper<User> queryWrapper = new QueryWrapper<>();
       //queryWrapper.eq("userNumber",user.getUserNumber());
        //User one= userService.getOne(queryWrapper);
        return Result.success(userService.saveOrUpdate(user));
    }
    //分页查询
    /*@GetMapping("/page")
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
    }*/
    /**
     * 管理员分页查询
     * */
    @GetMapping("/admin/page")
    public Result<?> getAdminUsers(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String realName){
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> adminUsers = userService.getAdminUsers(page,username,realName);
        return Result.success(adminUsers);
    }
    /**
     * 普通用户分页查询
     * */
    @GetMapping("/page")
    public Result<?> getUsers(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String realName){
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> adminUsers = userService.getUsers(page,username,realName);
        return Result.success(adminUsers);
    }

    //修改员工信息
    @PutMapping
    public Result<?> update(@RequestBody User user){
        userService.updateById(user);
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
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "1"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",list);
        List<User> userList = userMapper.selectList(queryWrapper);
        return Result.success(userList.size());
    }
    // 按id查询
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        log.info("查询的id：{}", id);
        return Result.success(userService.getById(id));
    }

    // 新增或更新
    /*@PostMapping
    public Result<?> saveAndUpdate(@RequestBody User user){
        if(user.getId() == null && user.getPassword() == null){
//            user.setPassword(SecureUtil.md5("123"));
            user.setPassword("123");
        }
        return Result.success(userService.saveOrUpdate(user));
    }*/

    // 按序号删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        log.info("将被删除的id：{}", id);
        User user = userMapper.selectById(id);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",user.getRoleId());
        roleMapper.delete(queryWrapper);
        userService.removeById(id);
        return Result.success();
    }
//    批量删除
    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids){
        List<User> users = userMapper.selectBatchIds(ids);
        ArrayList<Integer> list = new ArrayList<>();
        users.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",list);
        roleMapper.delete(queryWrapper);
        userService.removeByIds(ids);
        return Result.success();
    }
    /**
     * 普通用户导入
     * */
    @PostMapping("/import")
    public Result<?> importExcel(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        reader.addHeaderAlias("序号","id");
        reader.addHeaderAlias("编号","userNumber");
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("姓名","realName");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("电话号码","phone");
        reader.addHeaderAlias("身份证号","IDCard");
        List<User> list = reader.readAll(User.class);       // 表头必须是英文或匹配Bean
        System.out.println(list);
        list.forEach(item->{
            //list.add(item.getId());
            Role role = new Role();
            role.setRoleCode("1");
            role.setRoleName("Regular users");
            roleMapper.insert(role);
            Integer id = role.getId();
            item.setRoleId(id);
        });
        boolean saveBatch = userService.saveBatch(list);
        if(!saveBatch){
            return Result.error(Constants.CODE_400,"导入失败,查看用户账号是否重复");
        }
        return Result.success("导入成功");
    }
    /**
     * 普通用户导出
     * */
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response)throws Exception {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "1"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",list);
        List<User> userList = userMapper.selectList(queryWrapper);
        ExcelWriter writer= ExcelUtil.getWriter(true);
        //只导出含有别名的属性
        writer.setOnlyAlias(true);
        //writer.addHeaderAlias("id","序号");
        writer.addHeaderAlias("userNumber","编号");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("realName","姓名");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("phone","电话号码");
        writer.addHeaderAlias("IDCard","身份证号");
        writer.write(userList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream outputStream=response.getOutputStream();
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }

    /**
     * 普通用户导入
     * */
    @PostMapping("/admin/import")
    public Result<?> importAdminExcel(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        reader.addHeaderAlias("序号","id");
        reader.addHeaderAlias("编号","userNumber");
        reader.addHeaderAlias("用户名","username");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("姓名","realName");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("电话号码","phone");
        reader.addHeaderAlias("身份证号","IDCard");
        List<User> list = reader.readAll(User.class);       // 表头必须是英文或匹配Bean
        System.out.println(list);
        list.forEach(item->{
            //list.add(item.getId());
            Role role = new Role();
            role.setRoleCode("0");
            role.setRoleName("administrator");
            roleMapper.insert(role);
            Integer id = role.getId();
            item.setRoleId(id);
        });
        boolean saveBatch = userService.saveBatch(list);
        if(!saveBatch){
            return Result.error(Constants.CODE_400,"导入失败,查看用户账号是否重复");
        }
        return Result.success("导入成功");
    }
    /**
     * 普通用户导出
     * */
    @GetMapping("/admin/export")
    public void exportAdminExcel(HttpServletResponse response)throws Exception {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "0"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",list);
        List<User> userList = userMapper.selectList(queryWrapper);
        ExcelWriter writer= ExcelUtil.getWriter(true);
        //只导出含有别名的属性
        writer.setOnlyAlias(true);
        //writer.addHeaderAlias("id","序号");
        writer.addHeaderAlias("userNumber","编号");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("realName","姓名");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("phone","电话号码");
        writer.addHeaderAlias("IDCard","身份证号");
        writer.write(userList, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("管理员信息表","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream outputStream=response.getOutputStream();
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }

}