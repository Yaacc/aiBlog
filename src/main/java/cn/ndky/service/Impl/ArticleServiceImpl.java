package cn.ndky.service.Impl;

import cn.ndky.entity.Article;
import cn.ndky.entity.Like;
import cn.ndky.mapper.ArticleMapper;
import cn.ndky.mapper.LikeMapper;
import cn.ndky.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaacc
 * @since 2023-08-15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private LikeMapper likeMapper;
    @Override
    public void likeArticle(Integer userId, Integer articleId) {
        Like like = new Like();
        like.setUserId(userId);
        like.setArticleId(articleId);
        likeMapper.insert(like);
    }

    @Override
    public void unlikeArticle(Integer userId, Integer articleId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId)
                .eq("articleId", articleId);
        likeMapper.delete(queryWrapper);
    }
}
