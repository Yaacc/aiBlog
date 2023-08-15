package cn.ndky.service.Impl;

import cn.ndky.entity.Article;
import cn.ndky.mapper.ArticleMapper;
import cn.ndky.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
