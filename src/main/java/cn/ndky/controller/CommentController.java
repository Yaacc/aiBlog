package cn.ndky.controller;

import cn.hutool.core.date.DateUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.Comment;
import cn.ndky.entity.User;
import cn.ndky.mapper.CommentMapper;
import cn.ndky.mapper.UserMapper;
import cn.ndky.service.ICommentService;
import cn.ndky.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    @PostMapping("/article")
    public Result<?> addCommentToArticle(@RequestBody Comment comment) {
        //comment.setCreateTime(LocalDateTime.now());
        User currentUser = TokenUtils.getCurrentUser();
        comment.setUserId(currentUser.getId());
        comment.setCreateTime(DateUtil.now());
        if(comment.getParentId()!=null){
            comment.setParentId(comment.getParentId());
        }
        return Result.success(commentMapper.insert(comment));
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        return Result.success(commentMapper.deleteById(id));
    }
    @GetMapping("/article/{articleId}")
    public Result<?> foreign(@PathVariable Integer articleId) {
        return Result.success(findByForeign(articleId));
    }
    private List<Comment> findByForeign(Integer articleId) {
        // 根据 foreignId 0 查询所有的留言数据
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery().eq(Comment::getArticleId, articleId).orderByDesc(Comment::getId);
        List<Comment> list = commentMapper.selectList(queryWrapper);
        // 循环所有留言数据
        for (Comment Comment : list) {
            User one = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId,Comment.getUserId()));
            Comment.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            /*if (StrUtil.isNotBlank(one.getAvatar())) {
                Comment.setAvatar(one.getAvatar());
            } else {
                // 默认一个头像
                Message.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            }*/
            Comment.setUserNumber(one.getUserNumber());
            Integer parentId = Comment.getParentId();
            // 判断当前的留言是否有父级，如果有，则返回父级留言的信息
            // 原理：遍历所有留言数据，如果id跟当前留言信息的parentId相等，则将其设置为父级评论信息，也就是Message::setParentMessage
            list.stream().filter(c -> c.getId().equals(parentId)).findFirst().ifPresent(Comment::setParentComment);
        }
        return list;
    }
}
