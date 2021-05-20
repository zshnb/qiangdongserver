package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.entity.WorksTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-16
 */
@Repository
public interface WorksTagMapper extends BaseMapper<WorksTag> {

    void saveAll(List<WorksTag> tags);

}
