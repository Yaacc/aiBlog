package cn.ndky.service;

import cn.ndky.entity.Video;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;


public interface IVideoService extends IService<Video> {
    void likeVideo(Integer UserId, Integer videoId);
    void unlikeVideo(Integer userId, Integer videoId);

    Page<Video> getVideos(Page<Video> page, String name);
}
