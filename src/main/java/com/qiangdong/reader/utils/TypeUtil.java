package com.qiangdong.reader.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeUtil {
	private final TypeMapper typeMapper;

	public TypeUtil(TypeMapper typeMapper) {
		this.typeMapper = typeMapper;
	}

	/**
	 * 获取小说根分类
	 * */
	public Type getNovelRootType(Type type) {
		QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", type.getParentId())
			.eq("belong", TypeBelongEnum.NOVEL);
		Type parent = typeMapper.selectOne(queryWrapper);
		while (parent.getParentId() != 0L) {
			queryWrapper.clear();
			queryWrapper.eq("id", parent.getParentId())
				.eq("belong", TypeBelongEnum.NOVEL);
			parent = typeMapper.selectOne(queryWrapper);
		}
		return parent;
	}
}
