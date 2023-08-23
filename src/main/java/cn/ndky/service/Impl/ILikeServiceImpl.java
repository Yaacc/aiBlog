package cn.ndky.service.Impl;

import cn.ndky.entity.Like;
import cn.ndky.mapper.LikeMapper;
import cn.ndky.service.ILikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ILikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {
}
