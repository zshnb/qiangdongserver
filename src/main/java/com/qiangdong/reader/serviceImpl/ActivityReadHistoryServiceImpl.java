package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.ActivityReadHistoryMapper;
import com.qiangdong.reader.dto.read_activity_record.ActivityReadHistoryDto;
import com.qiangdong.reader.entity.ActivityReadHistory;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IActivityReadHistoryService;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-17
 */
@Service
public class ActivityReadHistoryServiceImpl extends ServiceImpl<ActivityReadHistoryMapper, ActivityReadHistory>
	implements IActivityReadHistoryService {

	private final ActivityReadHistoryMapper activityReadHistoryMapper;
	private final PageUtil pageUtil;

	public ActivityReadHistoryServiceImpl(ActivityReadHistoryMapper activityReadHistoryMapper,
	                                      PageUtil pageUtil) {
		this.activityReadHistoryMapper = activityReadHistoryMapper;
		this.pageUtil = pageUtil;
	}

	@Override
	public PageResponse<ActivityReadHistoryDto> listHistory(BaseRequest request) {
		IPage<ActivityReadHistoryDto> readActivityRecordDto =
				activityReadHistoryMapper.findActivityReadHistoryByUserId(pageUtil.of(request),request.getUserId());
		return PageResponse.of(readActivityRecordDto, request.getPageSize());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> cleanHistory(BaseRequest request) {
		remove(new QueryWrapper<ActivityReadHistory>()
			.eq("user_id", request.getUserId()));
		return Response.ok();
	}
}
