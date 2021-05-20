package com.qiangdong.reader.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.dto.read_activity_record.ActivityReadHistoryDto;
import com.qiangdong.reader.entity.ActivityReadHistory;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-17
 */
public interface IActivityReadHistoryService extends IService<ActivityReadHistory> {
	PageResponse<ActivityReadHistoryDto> listHistory(BaseRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> cleanHistory(BaseRequest request);
}
