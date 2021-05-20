package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.WorksIncomeUserRankingDto;
import com.qiangdong.reader.entity.UserConsumption;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.user.ListWorksIncomeUserRankingRequest;
import com.qiangdong.reader.response.PageResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-22
 */
public interface IUserConsumptionService extends IService<UserConsumption> {

	PageResponse<WorksIncomeUserRankingDto> listWorksWorksIncomeUserRanking(
		ListWorksIncomeUserRankingRequest request);
}
