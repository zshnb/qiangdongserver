package com.qiangdong.reader.utils;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeUtil {

    private final Snowflake snowflake = new Snowflake(1, 1);

    public Long generateSnowflakeId() {
        return snowflake.nextId();
    }

}
