package cn.ndky.service.Impl;

import cn.ndky.entity.*;
import cn.ndky.mapper.*;
import cn.ndky.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private FavoriteMapper favoriteMapper;
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
    @Override
    public void favoriteArticle(Integer userId, Integer articleId) {
        Favorite favorite = new Favorite();
        favorite.setArticleId(articleId);
        favorite.setUserId(userId);
        favoriteMapper.insert(favorite);
    }

    @Override
    public void unFavoriteArticle(Integer userId, Integer articleId) {
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId)
                .eq("articleId", articleId);
        favoriteMapper.delete(queryWrapper);
    }

    @Override
    public Page<Article> getUsersArticle(Page<Article> page, String name) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        List<String> userRoleId = getUserRoleId();
        queryWrapper.in("user", getUserRoleId()) // Filter users with admin role
        .eq("is_delete", false)
        .orderByDesc("id");
        if(!(name==null||"".equals(name))){
            queryWrapper.like("name",name);
        }
        return articleMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Page<Article> getAdminArticle(Page<Article> page, String name) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        List<String> userRoleId = getUserRoleId();
        queryWrapper.in("user", getAdminRoleId()) // Filter users with admin role
                .eq("is_delete", false)
                .orderByDesc("id");
        if(!(name==null||"".equals(name))){
            queryWrapper.like("name",name);
        }
        return articleMapper.selectPage(page,queryWrapper);
    }



    private List<String> getUserRoleId() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "1"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", list);
        List<User> users = userMapper.selectList(queryWrapper);
        ArrayList<String> arrayList = new ArrayList<>();
        users.forEach(item->{
            arrayList.add(item.getUserNumber());
        });
        return arrayList;
    }
    private List<String> getAdminRoleId() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("role_code", "0"));
        ArrayList<Integer> list = new ArrayList<>();
        roles.forEach(item->{
            list.add(item.getId());
        });
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", list);
        List<User> users = userMapper.selectList(queryWrapper);
        ArrayList<String> arrayList = new ArrayList<>();
        users.forEach(item->{
            arrayList.add(item.getUserNumber());
        });
        return arrayList;
    }
}
