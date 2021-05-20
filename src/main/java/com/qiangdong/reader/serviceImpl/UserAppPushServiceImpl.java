package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.entity.UserAppPush;
import com.qiangdong.reader.dao.UserAppPushMapper;
import com.qiangdong.reader.service.IUserAppPushService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AppPushUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-09-30
 */
@Service
public class UserAppPushServiceImpl extends ServiceImpl<UserAppPushMapper, UserAppPush>
	implements IUserAppPushService {

	private final AppPushUtil appPushUtil;

	public UserAppPushServiceImpl(AppPushUtil appPushUtil) {
		this.appPushUtil = appPushUtil;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Boolean bindUser(String clientId, Long userId) {
		UserAppPush userAppPush = getOne(new QueryWrapper<UserAppPush>().eq("user_id", userId));
		if (userAppPush == null) {
			userAppPush.setClientId(clientId);
			userAppPush.setUserId(userId);
		} else if (userAppPush.getClientId().equals(clientId)) {
			return true;
		} else {
			userAppPush.setClientId(clientId);
		}
		return saveOrUpdate(userAppPush);
	}

}
