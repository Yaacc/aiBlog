package cn.ndky.service.Impl;

import cn.ndky.entity.Article;
import cn.ndky.entity.Like;
import cn.ndky.entity.Video;
import cn.ndky.mapper.LikeMapper;
import cn.ndky.mapper.VideoMapper;
import cn.ndky.service.IVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private VideoMapper videoMapper;
    @Override
    public void likeVideo(Integer userId, Integer videoId) {
        Like like = new Like();
        like.setUserId(userId);
        like.setVideoId(videoId);
        likeMapper.insert(like);
    }

    @Override
    public void unlikeVideo(Integer userId, Integer videoId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId)
                .eq("videoId", videoId);
        likeMapper.delete(queryWrapper);
    }

    @Override
    public Page<Video> getVideos(Page<Video> page, String name) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(!(name==null||"".equals(name))){
            queryWrapper.like("video_title",name);
        }
        return videoMapper.selectPage(page,queryWrapper);
    }
}
