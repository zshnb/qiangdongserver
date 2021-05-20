package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.entity.FateBoard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.fate_board.AddOrUpdateFateBoardRequest;
import com.qiangdong.reader.request.fate_board.MatchFateBoardRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-29
 */
public interface IFateBoardService extends IService<FateBoard> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<FateBoardDto> addOrUpdateFateBoard(AddOrUpdateFateBoardRequest request);

	PageResponse<FateBoardDto> listFateBoard(BaseRequest request);

    Response<UserIdNameDto> matchFateBoard(MatchFateBoardRequest request);

	Response<String> cancelMatch(BaseRequest request);

	Response<FateBoardDto> getFateBoard(BaseRequest request);

	Response<Integer> getOnlinePersonCount(BaseRequest request);
}
