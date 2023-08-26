package cn.ndky.controller;


import cn.hutool.core.date.DateUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.Article;
import cn.ndky.entity.Favorite;
import cn.ndky.entity.Like;
import cn.ndky.entity.User;
import cn.ndky.mapper.ArticleMapper;
import cn.ndky.mapper.FavoriteMapper;
import cn.ndky.mapper.LikeMapper;
import cn.ndky.service.IArticleService;
import cn.ndky.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yaacc
 * @since 2023-08-15
 */
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @Resource
    private LikeMapper likeMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private FavoriteMapper favoriteMapper;


    /**
    * 发布文章
    * */
    @PostMapping("/publish")
    public Result<?> publishArticle(@RequestBody Article article)
    {
        article.setUser(article.getUser());
        article.setCollect(0);
        article.setLikes(0);
        article.setTime(DateUtil.now());
        articleService.saveOrUpdate(article);
        return Result.success("发布成功");
    }
    /**
     * 点赞
     * */
    @PostMapping("/likes/{id}")
    public Result<?> likeArticle(@PathVariable Integer id) {
        // Get current user ID from authentication context
        User currentUser = TokenUtils.getCurrentUser();
        articleService.likeArticle(currentUser.getId(),id);
        Article article = articleMapper.selectById(id);
        if(article!=null){
            article.setLikes(article.getLikes()+1);
            articleMapper.updateById(article);
        }
        return Result.success("点赞成功");
    }
    @GetMapping("/isLike/{articleId}")
    public Result<?> isLike(@PathVariable Integer articleId){
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",currentUser.getId());
        queryWrapper.eq("articleId",articleId);
        Like like = likeMapper.selectOne(queryWrapper);
        if(like!=null){
           return Result.success("已点赞");
        }
       return null;
    }
    @DeleteMapping("/likes/{articleId}")
    public Result<?> unlikeArticle(@PathVariable Integer articleId) {
        // Get current user ID from authentication context
        User currentUser = TokenUtils.getCurrentUser();
        articleService.unlikeArticle(currentUser.getId(),articleId);
        Article article = articleMapper.selectById(articleId);
        if(article!=null){
            article.setLikes(article.getLikes()-1);
            articleMapper.updateById(article);
        }
        return Result.success("取消点赞");
    }
    @PostMapping
    public Result<?> save(@RequestBody Article article){
        if(article.getId()==null){
            article.setTime(DateUtil.now());
//            article.setUser();
        }
        articleService.saveOrUpdate(article);
        return Result.success();
    }
    /**
     * 获取文件总数
     */
    @GetMapping("/total")
    public Result<?> getNumberOfArticles(){
        return Result.success(articleService.count());
    }

    /**
     * 更新（文章启用）
     */
    @PostMapping("/update")
    public Result<?> saveAndUpdate(@RequestBody Article article){
        return Result.success(articleService.updateById(article));
    }
    /**
     * 按序号删除
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        Article article = articleMapper.selectById(id);
        article.setIsDelete(true);
        return Result.success(articleMapper.updateById(article));
    }
    /**
     * 批量删除
     */
    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        for (Article article : articles){
            article.setIsDelete(true);
            articleMapper.updateById(article);
        }
        return Result.success();
    }
    /**
     * 分页查询 http://localhost/article/page?pageNum=1&pageSize=2
     */
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String name) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> usersArticle = articleService.getUsersArticle(page,name);
        return Result.success(usersArticle);
    }
    @GetMapping("/admin/page")
    public Result<?> findAdminPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String name) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> adminsArticle = articleService.getAdminArticle(page,name);
        return Result.success(adminsArticle);
    }

    /**
     * 用户收藏
     * */
    @PostMapping("/favorite/{articleId}")
    public Result<?> favoriteArticle(@PathVariable Integer articleId) {
        // Get current user ID from authentication context
        User currentUser = TokenUtils.getCurrentUser();
        articleService.favoriteArticle(currentUser.getId(), articleId);
        Article article = articleMapper.selectById(articleId);
        if(article!=null){
            article.setCollect(article.getCollect()+1);
            articleMapper.updateById(article);
        }
        return Result.success("收藏成功");
    }
    /**
     * 用户是否收藏
     * */
    @GetMapping("/isFavorite/{articleId}")
    public Result<?> isFavorite(@PathVariable Integer articleId){
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",currentUser.getId());
        queryWrapper.eq("articleId",articleId);
        Favorite favorite = favoriteMapper.selectOne(queryWrapper);
        if(favorite!=null){
            return Result.success("已收藏");
        }
        return null;
    }
    /**
     * 用户取消收藏
     * */
    @DeleteMapping("/favorite/{articleId}")
    public Result<?> unFavoriteArticle(@PathVariable Integer articleId) {
        // Get current user ID from authentication context
        User currentUser = TokenUtils.getCurrentUser();
        articleService.unFavoriteArticle(currentUser.getId(),articleId);
        Article article = articleMapper.selectById(articleId);
        if(article!=null){
            article.setCollect(article.getCollect()-1);
            articleMapper.updateById(article);
        }
        return Result.success("取消收藏");
    }
}

