package cn.ndky.controller;

import cn.hutool.core.collection.CollUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.User;
import cn.ndky.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/maleOrFemale")
    public Result<?> maleOrFemale(){
        QueryWrapper<User> wrapperMale = new QueryWrapper<>();
        QueryWrapper<User> wrapperFemale = new QueryWrapper<>();
        wrapperMale.eq("sex", "男");
        wrapperFemale.eq("sex","女");
        return Result.success(CollUtil.newArrayList(userService.count(wrapperMale),userService.count(wrapperFemale)));
    }

//    @GetMapping("/members")
//    public Result<?> members(){
//        List<User> list = userService.list();
//
//    }
}
