package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.TypeDto;
import com.qiangdong.reader.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Repository
public interface TypeMapper extends BaseMapper<Type> {

    IPage<TypeDto> findTypeByBelong(Page<?> page,
                                    @Param("belong") TypeBelongEnum belong,
                                    @Param("parentId") Long parentId);
}
