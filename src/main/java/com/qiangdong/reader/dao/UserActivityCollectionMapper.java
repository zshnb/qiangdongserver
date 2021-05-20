package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.UserActivityCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-23
 */
@Repository
public interface UserActivityCollectionMapper extends BaseMapper<UserActivityCollection> {

    IPage<UserActivityCollectionDto> listUserActivityCollection(Page<?> page, Long userId);
}
