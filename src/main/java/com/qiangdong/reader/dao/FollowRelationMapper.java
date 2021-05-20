package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 关注表 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
@Repository
public interface FollowRelationMapper extends BaseMapper<FollowRelation> {
	IPage<FollowRelationDto> findFollowUser(Page<?> page, @Param("userId") long userId);

	IPage<FollowRelationDto> findFansUser(Page<?> page, @Param("userId") long userId);

	List<FollowRelationDto> findFollowerByIdIn(List<Long> ids);
}
