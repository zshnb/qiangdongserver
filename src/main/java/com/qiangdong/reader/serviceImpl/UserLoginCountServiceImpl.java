package com.qiangdong.reader.serviceImpl;

import com.qiangdong.reader.entity.UserLoginCount;
import com.qiangdong.reader.dao.UserLoginCountMapper;
import com.qiangdong.reader.service.IUserLoginCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 每日登录数 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-07
 */
@Service
public class UserLoginCountServiceImpl extends ServiceImpl<UserLoginCountMapper, UserLoginCount> implements IUserLoginCountService {

}
