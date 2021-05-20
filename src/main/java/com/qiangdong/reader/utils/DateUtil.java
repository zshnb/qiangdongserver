package com.qiangdong.reader.utils;

import cn.hutool.core.date.DateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	public LocalDateTime getYesterday() {
		DateTime dateTime = cn.hutool.core.date.DateUtil.yesterday();
		return LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault());
	}
}
