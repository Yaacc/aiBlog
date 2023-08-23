package cn.ndky.service;

import cn.ndky.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaacc
 * @since 2023-08-15
 */
public interface IArticleService extends IService<Article> {
    void likeArticle(Integer userId, Integer articleId);

    void unlikeArticle(Integer userId, Integer articleId);
}
