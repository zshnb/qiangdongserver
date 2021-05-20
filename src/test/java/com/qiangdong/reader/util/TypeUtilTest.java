package com.qiangdong.reader.util;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.utils.TypeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TypeUtilTest extends BaseTest {
	@Autowired
	private TypeUtil typeUtil;

	@Autowired
	private TypeMapper typeMapper;

	@Test
	public void getNovelRootTypeSuccessful() {
		Type type = typeMapper.selectById(5L);
		Type rootType = typeUtil.getNovelRootType(type);
		assertThat(rootType.getId()).isEqualTo(2L);
		assertThat(rootType.getName()).isEqualTo("分类2");
	}
}
