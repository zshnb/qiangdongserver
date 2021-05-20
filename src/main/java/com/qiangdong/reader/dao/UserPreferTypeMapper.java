package com.qiangdong.reader.dao;

import com.qiangdong.reader.dto.UserPreferTypeDto;
import com.qiangdong.reader.entity.UserPreferType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-19
 */
@Repository
public interface UserPreferTypeMapper extends BaseMapper<UserPreferType> {
	List<UserPreferTypeDto> findUserPreferTypeByUserId(Long userId);
}
