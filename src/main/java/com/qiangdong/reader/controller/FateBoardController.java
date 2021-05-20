package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.fate_board.AddOrUpdateFateBoardRequest;
import com.qiangdong.reader.request.fate_board.MatchFateBoardRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.FateBoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/fate-board")
public class FateBoardController {
	@Autowired
	private FateBoardServiceImpl fateBoardService;

	@PostMapping("/add-update")
	public Response<FateBoardDto> addOrUpdateFateBoard(@RequestBody AddOrUpdateFateBoardRequest request) {
		return fateBoardService.addOrUpdateFateBoard(request);
	}

	@PostMapping("/list")
	public PageResponse<FateBoardDto> listFateBoard(@RequestBody BaseRequest request) {
		return fateBoardService.listFateBoard(request);
	}

	@PostMapping("/own")
	public Response<FateBoardDto> getFateBoard(@RequestBody BaseRequest request) {
		return fateBoardService.getFateBoard(request);
	}

	@PostMapping("/match")
	public Response<UserIdNameDto> matchFateBoard(@RequestBody MatchFateBoardRequest request) {
		return fateBoardService.matchFateBoard(request);
	}

	@PostMapping("/cancel-match")
	public Response<String> cancelMatch(@RequestBody BaseRequest request) {
		return fateBoardService.cancelMatch(request);
	}

	@PostMapping("/online-person")
	public Response<Integer> getOnlinePersonCount(@RequestBody BaseRequest request) {
		return fateBoardService.getOnlinePersonCount(request);
	}
}
