package cn.ndky.service;

import cn.ndky.entity.Article;
import cn.ndky.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Page<Article> getUsersArticle(Page<Article> page, String name);

    Page<Article> getAdminArticle(Page<Article> page, String name);

    void favoriteArticle(Integer id, Integer articleId);

    void unFavoriteArticle(Integer id, Integer articleId);
}
