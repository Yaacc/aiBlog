package cn.ndky.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.ndky.config.Result;
import cn.ndky.entity.Article;
import cn.ndky.entity.Files;
import cn.ndky.mapper.ArticleMapper;
import cn.ndky.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @Resource
    private ArticleMapper articleMapper;


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
