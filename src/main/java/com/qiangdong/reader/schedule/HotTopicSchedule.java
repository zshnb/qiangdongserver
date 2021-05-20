package com.qiangdong.reader.schedule;

import com.qiangdong.reader.common.TopicConstant;
import com.qiangdong.reader.entity.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HotTopicSchedule {
	private final Logger logger = LoggerFactory.getLogger(HotTopicSchedule.class);

	private final RedisTemplate<String, Topic> redisTemplate;

	public HotTopicSchedule(RedisTemplate<String, Topic> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void decreaseTopicHeatScore() {
		logger.info("start decrease topic's core");
		ZSetOperations<String, Topic> zSetOperations = redisTemplate.opsForZSet();
		zSetOperations.rangeByScore(TopicConstant.KEY_HOT_TOPIC, 0, Integer.MAX_VALUE).forEach(it -> {
			zSetOperations.incrementScore(TopicConstant.KEY_HOT_TOPIC, it, -2.0);
		});
		logger.info("end decrease topic's core");
	}
}
