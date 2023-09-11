package cn.ndky.controller;

import cn.hutool.core.date.DateUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.*;
import cn.ndky.mapper.FeedbackMapper;
import cn.ndky.mapper.UserMapper;
import cn.ndky.service.IFeedbackService;
import cn.ndky.service.IUserService;
import cn.ndky.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private IFeedbackService feedbackService;

    @Resource
    private UserMapper userMapper;

    /**
     * 用户发表反馈内容
     */
    @PostMapping("/public")
    public Result<?> addCommentToArticle(@RequestBody Feedback feedback) {
        //comment.setCreateTime(LocalDateTime.now());
        User currentUser = TokenUtils.getCurrentUser();
        feedback.setUserId(currentUser.getId());
        feedback.setCreateDate(DateUtil.now());
        return Result.success(feedbackMapper.insert(feedback));
    }
    /**
     * 删除发表反馈内容
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        return Result.success(feedbackMapper.deleteById(id));
    }
    /**
     * 批量删除发表反馈内容
     */
    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(feedbackMapper.deleteBatchIds(ids));
    }

    @PostMapping("/update")
    public Result<?> saveAndUpdate(@RequestBody Feedback feedback){
        User currentUser = TokenUtils.getCurrentUser();
        feedback.setAdminId(currentUser.getId());
        feedback.setAnswerDate(DateUtil.now());
        return Result.success(feedbackMapper.updateById(feedback));
    }
    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String name){
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!(name==null||"".equals(name))) {
            queryWrapper.like("content",name);
        }
        Page<Feedback> feedbackPage = feedbackMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        for (int i=0;i<feedbackPage.getRecords().size();i++){
            User user = userMapper.selectById(feedbackPage.getRecords().get(i).getUserId());
            feedbackPage.getRecords().get(i).setUserNumber(user.getUserNumber());
            if(feedbackPage.getRecords().get(i).getAdminId()!=null){
                User user1 = userMapper.selectById(feedbackPage.getRecords().get(i).getUserId());
                feedbackPage.getRecords().get(i).setUserNumber(user.getUserNumber());
            }
        }
        return Result.success(feedbackPage);
    }
}
