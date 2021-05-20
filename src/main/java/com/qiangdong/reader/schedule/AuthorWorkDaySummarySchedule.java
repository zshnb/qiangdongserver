package com.qiangdong.reader.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dao.WorkDaySummaryMapper;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.WorkDaySummary;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.utils.DateUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuthorWorkDaySummarySchedule {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorWorkDaySummarySchedule.class);
	private final UserMapper userMapper;
	private final NovelChapterMapper novelChapterMapper;
	private final WorkDaySummaryMapper workDaySummaryMapper;
	private final DateUtil dateUtil;

	public AuthorWorkDaySummarySchedule(UserMapper userMapper,
	                                    NovelChapterMapper novelChapterMapper,
	                                    WorkDaySummaryMapper workDaySummaryMapper,
	                                    DateUtil dateUtil) {
		this.userMapper = userMapper;
		this.novelChapterMapper = novelChapterMapper;
		this.workDaySummaryMapper = workDaySummaryMapper;
		this.dateUtil = dateUtil;
	}

	/**
	 * 每天凌晨计算作者前一天创作数据
	 * */
	@Scheduled(cron = "30 0 0 * * ?")
	public void calculateWorkDaySummary() {
		LOGGER.info("start calculate work day summary");
		List<Long> userIds = userMapper.selectList(new QueryWrapper<User>()
			.eq("role", UserRoleEnum.AUTHOR.code())).stream()
			.map(User::getId).collect(Collectors.toList());

		if (!userIds.isEmpty()) {
			LocalDateTime yesterday = dateUtil.getYesterday();
			List<WorkDaySummary> workDaySummaries = novelChapterMapper.findYesterdayWriteWordCount(userIds, yesterday);
			if (!workDaySummaries.isEmpty()) {
				workDaySummaries.forEach(it -> it.setCreateAt(yesterday));
				workDaySummaryMapper.saveAll(workDaySummaries);
			}
		}
		LOGGER.info("end calculate work day summary");
	}
}
