package com.qiangdong.reader.schedule;

import com.qiangdong.reader.dao.UserLoginCountMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.UserLoginCount;
import com.qiangdong.reader.utils.DateUtil;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserLoginCountSchedule {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginCountSchedule.class);
	private final UserMapper userMapper;
	private final UserLoginCountMapper userLoginCountMapper;
	private final DateUtil dateUtil;

	public UserLoginCountSchedule(UserMapper userMapper,
	                              UserLoginCountMapper userLoginCountMapper,
	                              DateUtil dateUtil) {
		this.userMapper = userMapper;
		this.userLoginCountMapper = userLoginCountMapper;
		this.dateUtil = dateUtil;
	}

	@Scheduled(cron = "10 0 0 * * ?")
	public void calculateWorkDaySummary() {
		LOGGER.info("start calculate user login count");
		LocalDateTime yesterday = dateUtil.getYesterday();
		int count = userMapper.countLoginCountByTime(yesterday);
		UserLoginCount userLoginCount = new UserLoginCount();
		userLoginCount.setCount(count);
		userLoginCount.setCreateAt(yesterday);
		userLoginCountMapper.insert(userLoginCount);
		LOGGER.info("end calculate user login count");
	}
}
