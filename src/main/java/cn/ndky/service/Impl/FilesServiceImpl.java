package cn.ndky.service.Impl;

import cn.ndky.entity.Files;
import cn.ndky.mapper.FilesMapper;
import cn.ndky.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaacc
 * @since 2023-08-05
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {
}
