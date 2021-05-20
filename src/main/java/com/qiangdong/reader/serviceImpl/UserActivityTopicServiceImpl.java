package com.qiangdong.reader.serviceImpl;

import com.qiangdong.reader.entity.UserActivityTopic;
import com.qiangdong.reader.dao.UserActivityTopicMapper;
import com.qiangdong.reader.service.IUserActivityTopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 话题 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-16
 */
@Service
public class UserActivityTopicServiceImpl extends ServiceImpl<UserActivityTopicMapper, UserActivityTopic> implements IUserActivityTopicService {

}
