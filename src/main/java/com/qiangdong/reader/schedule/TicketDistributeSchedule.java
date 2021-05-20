package com.qiangdong.reader.schedule;

import com.qiangdong.reader.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TicketDistributeSchedule {
    private final Logger logger = LoggerFactory.getLogger(TicketDistributeSchedule.class);
    private final UserMapper userMapper;

    public TicketDistributeSchedule(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Scheduled(cron = "20 0 0 * * ?")
    public void distributeRecommendTicket() {
        logger.info("start distribute recommend ticket");
        userMapper.updateRecommendTicketForAllUser();
        logger.info("end distribute recommend ticket");
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void distributeWallTicket() {
        logger.info("start distribute wall ticket");
        userMapper.updateWallTicketForAllUser();
        logger.info("end distribute wall ticket");
    }
}
