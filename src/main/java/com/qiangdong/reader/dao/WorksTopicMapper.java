package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.entity.WorksTopic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
public interface WorksTopicMapper extends BaseMapper<WorksTopic> {
	IPage<WorksTopicDto> findTopicByTypeId(IPage<?> page, @Param("typeId") Long typeId);

	IPage<WorksTopicDto> findTopicByWorksType(IPage<?> page, @Param("worksType") Integer worksType);
}
