package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.UserAppPush;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-09-30
 */
public interface IUserAppPushService extends IService<UserAppPush> {
	Boolean bindUser(String clientId, Long userId);

}
