package cn.ndky.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.ndky.entity.Files;
import cn.ndky.mapper.FilesMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 文件控制器
 * </p>
 *
 * @author yaacc
 * @since 2023-08-05
 */
@RestController
@RequestMapping("/files")
public class FilesController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper filesMapper;

    /**
     * 文件上传
     * POST http://localhost/files/upload
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();   // 文件原名称
        String type = FileUtil.extName(originalFilename);       // 文件类型
        long size = file.getSize();                             // 文件大小

        String uuid = IdUtil.fastSimpleUUID();          // 定义文件唯一标识码
        String fileUUID = uuid + StrUtil.DOT + type;    // 带上文件后缀
        File uploadFile = new File(fileUploadPath + fileUUID);

        // 判断存储目录是否存在，若不存在则创建一个新的文件目录
        File uploadParentFile = uploadFile.getParentFile();
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }

        String url;
        // 上传文件
        file.transferTo(uploadFile);
        // 获取文件md5
        String md5 = SecureUtil.md5(uploadFile);
        // 查询数据库中文件md5是否存在
        Files dbFiles = getFileMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
            // 删除后来重复文件
            uploadFile.delete();
        } else {
            url = "http://localhost/files/" + fileUUID;
        }

        // 存储到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);    // B转换为KB
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        filesMapper.insert(saveFile);
        return url;
    }

    /**
     * 文件下载
     * GET http://localhost/files/{fileUUID}
     *
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件md5查询
     *
     * @param md5
     * @return
     */
    private Files getFileMd5(String md5) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = filesMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }
}

