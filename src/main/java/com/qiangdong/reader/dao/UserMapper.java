package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.statistics.RegisterStatisticsDto;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.dto.user.UserAuthorDto;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.search.UserForSearch;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    UserDto findAuthorInfoById(Long userId);

    IPage<UserDto> findUserEditor(Page<?> page);

    UserDto findUserEditorByUserId(Long userId);

    IPage<UserAuthorDto> findUserAuthor(Page<?> page);

    void updateSecrecyConfig(@Param("secrecyConfig") SecrecyConfig secrecyConfig, @Param("userId") Long userId);

    User findSecrecyConfigByUserId(Long userId);

    Integer countRegisterUserByTime(LocalDateTime time);

    List<RegisterStatisticsDto> findRegisterStatisticsByDay();

    List<RegisterStatisticsDto> findRegisterStatisticsByMonth();

    List<RegisterStatisticsDto> findRegisterStatisticsByYear();

    void updateRecommendTicketForAllUser();

    void updateWallTicketForAllUser();

    Integer countLoginCountByTime(LocalDateTime time);

    List<UserForSearch> findAllForSearch();

    int countTodayLoginUser();
}

