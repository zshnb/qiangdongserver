package com.qiangdong.reader.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
* 主要用于生成文件名
* */
@Component
public class UuidUtil {
    public String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
