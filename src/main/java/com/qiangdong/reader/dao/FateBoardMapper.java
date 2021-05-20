package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.entity.FateBoard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.user.UserSexEnum;
import java.util.Set;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-29
 */
@Repository
public interface FateBoardMapper extends BaseMapper<FateBoard> {
	IPage<FateBoardDto> findByPage(Page<?> page, Long userId, Set<Long> set);

	FateBoardDto findByUserId(Long userId);
}
