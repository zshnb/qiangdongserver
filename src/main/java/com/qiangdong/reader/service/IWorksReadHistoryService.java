package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.works_history.AddOrUpdateWorksHistoryRequest;
import com.qiangdong.reader.request.works_history.ListWorksReadHistoryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-20
 */
public interface IWorksReadHistoryService extends IService<WorksReadHistory> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<WorksReadHistory> addOrUpdateWorksReadHistory(AddOrUpdateWorksHistoryRequest request);

	PageResponse<WorksReadHistoryDto> listWorksReadHistory(ListWorksReadHistoryRequest request);
}
