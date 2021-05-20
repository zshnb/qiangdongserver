package com.qiangdong.reader.utils;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class NumberUtil {
	/**
	 * 安全获取拆箱的整数，防止NPE
	 * */
	public int secureInteger(Integer value) {
		return value == null ? 0 : value;
	}

	public long secureLong(Long value) {
		return value == null ? 0L : value;
	}

	public BigDecimal secureBigDecimal(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}
}
