package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementByTypeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
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
 * @since 2020-07-09
 */
@RestController
@RequestMapping("/user-agreement")
public class UserAgreementController {

	private UserAgreementServiceImpl userAgreementService;

	public UserAgreementController(
		UserAgreementServiceImpl userAgreementService) {
		this.userAgreementService = userAgreementService;
	}

	@PostMapping("/detail")
	public Response<UserAgreement> getLatestUserAgreement(@RequestBody BaseRequest request){
		return userAgreementService.getLatestUserAgreement(request);
	}

	@PostMapping("/by-type")
	public Response<UserAgreement> getUserAgreementByType(@RequestBody GetUserAgreementByTypeRequest request) {
		return userAgreementService.getUserAgreementByType(request);
	}
}
