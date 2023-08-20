package cn.ndky.controller;


import cn.hutool.core.date.DateUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.Admin;
import cn.ndky.entity.Article;
import cn.ndky.entity.Files;
import cn.ndky.mapper.ArticleMapper;
import cn.ndky.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    private ArticleMapper articleMapper;


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
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!(name == null || "".equals(name))) {
            queryWrapper.like("name", name);
        }
        return Result.success(articleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

