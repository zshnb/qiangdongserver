package com.qiangdong.reader.utils;

import cn.hutool.core.util.NumberUtil;
import org.springframework.stereotype.Component;

@Component
public class NovelChapterUtil {
	public double calculatePrice(int wordCount) {
		return NumberUtil.round(NumberUtil.mul(0.5, NumberUtil.div(wordCount, 1000)), 1).doubleValue();
	}
}
