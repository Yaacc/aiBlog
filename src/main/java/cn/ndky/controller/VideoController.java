package cn.ndky.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.ndky.config.AuthAccess;
import cn.ndky.config.Result;
import cn.ndky.entity.*;
import cn.ndky.mapper.LikeMapper;
import cn.ndky.mapper.VideoMapper;
import cn.ndky.service.IVideoService;
import cn.ndky.utils.FrameGrabberKit;
import cn.ndky.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/video")
public class VideoController {

    @Resource
    private VideoMapper videoMapper;
    @Resource
    private IVideoService videoService;
    @Resource
    private LikeMapper likeMapper;
    @Value("${files.upload.path}")
    private String fileUploadPath;

    /**
     * 发布视频
     * */
    @AuthAccess
    @PostMapping("/upload")
    public Result<?> uploadVideo(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();   // 文件原名称
        String type = FileUtil.extName(originalFilename);       // 文件类型
        long size = file.getSize();                             // 文件大小
        String uuid = IdUtil.fastSimpleUUID();          // 定义文件唯一标识码
        String fileUUID = uuid + StrUtil.DOT + type;    // 带上文件后缀
        File uploadFile = new File(fileUploadPath+ "video/" +fileUUID);
        // 判断存储目录是否存在，若不存在则创建一个新的文件目录
        File uploadParentFile = uploadFile.getParentFile();
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        file.transferTo(uploadFile);
        String videoUrl="http://localhost/files/video/" + fileUUID;
        //视频封面图处理
        String newImgName = uuid+".jpg";
        String frameFile=fileUploadPath+ "video/" +newImgName;
        String imgUrlSave = "http://localhost/files/video/" + fileUUID;//图片最终位置路径
        //视频截取封面图
        String imgUrl= FrameGrabberKit.getVideoImg(videoUrl, frameFile, imgUrlSave);
        Video video = new Video();
        video.setVideoPath(videoUrl);
        video.setVideoCover(imgUrl);
        return Result.success(video);
    }
    @PostMapping("/public")
    public Result<?> publicVideo(@RequestBody Video video){
        User currentUser = TokenUtils.getCurrentUser();
        video.setUser(currentUser.getUserNumber());
        video.setVideoLike(0);
        video.setVideoCreated(DateUtil.now());
        videoMapper.insert(video);
        return Result.success("发布成功");
    }
    /**
     * 点赞
     * */
    @PostMapping("/likes/{id}")
    public Result<?> likeVideo(@PathVariable Integer id) {
        // Get current user ID from authentication context
        User currentUser = TokenUtils.getCurrentUser();
        videoService.likeVideo(currentUser.getId(),id);
        Video video = videoMapper.selectById(id);
        if(video!=null){
            video.setVideoLike(video.getVideoLike()+1);
            videoMapper.updateById(video);
        }
        return Result.success("点赞成功");
    }
    /**
     * 是否点赞
     * */
    @GetMapping("/isLike/{videoId}")
    public Result<?> isLike(@PathVariable Integer videoId){
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",currentUser.getId());
        queryWrapper.eq("videoId",videoId);
        Like like = likeMapper.selectOne(queryWrapper);
        if(like!=null){
            return Result.success("已点赞");
        }
        return null;
    }
    /**
     * 取消点赞
     * */
    @DeleteMapping("/likes/{videoId}")
    public Result<?> unlikeVideo(@PathVariable Integer videoId) {
        // Get current user ID from authentication context
        User currentUser = TokenUtils.getCurrentUser();
        videoService.unlikeVideo(currentUser.getId(),videoId);
        Video video = videoMapper.selectById(videoId);

        if(video!=null){
            video.setVideoLike(video.getVideoLike()-1);
            videoService.updateById(video);
        }
        return Result.success("取消点赞");
    }
    /**
     * 更新（文章启用）
     */
    @PostMapping("/update")
    public Result<?> saveAndUpdate(@RequestBody Video video){
        return Result.success(videoService.updateById(video));
    }
    /**
     * 按序号删除
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        Video video = videoMapper.selectById(id);
        return Result.success(videoService.removeById(video));
    }
    /**
     * 批量删除
     */
    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Video> videos = videoMapper.selectList(queryWrapper);
        return Result.success(videoService.removeBatchByIds(videos));
    }
    /**
     * 分页查询 http://localhost/article/page?pageNum=1&pageSize=2
     */
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String name) {
        Page<Video> page = new Page<>(pageNum, pageSize);
        Page<Video> usersVideo = videoService.getVideos(page,name);
        return Result.success(usersVideo);
    }
}
