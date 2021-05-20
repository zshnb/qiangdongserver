package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.UserActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.search.UserActivityForSearch;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户动态表 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
@Repository
public interface UserActivityMapper extends BaseMapper<UserActivity> {
	void save(UserActivity userActivity);

	UserActivity findById(Long id);

	List<UserActivityForSearch> findAllForSearch();

	void update(UserActivity userActivity);

	IPage<UserActivityDto> findActivityByManage(IPage<?> page, @Param("usernameOrContent") String usernameOrContent);

	UserActivityDto findCreateActivityById(Long activityId);

	IPage<UserActivityDto> findCreateActivityByUserId(Page<?> page, @Param("userId") Long userId);

	IPage<UserActivityDto> findCreateActivityByUserIdIn(Page<?> page, List<Long> userIds);

	List<UserActivityDto> findAgreeActivityByIdIn(List<Long> ids);

	List<UserActivityDto> findCreateActivityByIdIn(List<Long> ids);

	IPage<UserActivityDto> findCreateActivityByHeat(Page<?> page);
}
