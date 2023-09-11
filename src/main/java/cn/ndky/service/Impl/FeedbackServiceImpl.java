package cn.ndky.service.Impl;

import cn.ndky.entity.Feedback;
import cn.ndky.mapper.FeedbackMapper;
import cn.ndky.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {
}
