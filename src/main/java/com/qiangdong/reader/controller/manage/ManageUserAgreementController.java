package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_agreement.AddOrUpdateUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.DeleteUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.ListUserAgreementRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserAgreementService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user-agreement")
public class ManageUserAgreementController {

	@Autowired
	private IUserAgreementService userAgreementService;

	@PostMapping("/list-user-agreement")
	public PageResponse<UserAgreement> listUserAgreement(@RequestBody ListUserAgreementRequest request) {
		return userAgreementService.listUserAgreement(request);
	}

	@PostMapping("/detail")
	public Response<UserAgreement> getUserAgreement(@RequestBody GetUserAgreementRequest request) {
		return userAgreementService.getUserAgreement(request, new UserAgreement());
	}

	@PostMapping("/add-update")
	public Response<UserAgreement> addOrUpdateUserAgreement(@RequestBody @Valid AddOrUpdateUserAgreementRequest request) {
		return userAgreementService.addOrUpdateUserAgreement(request);
	}

	@DeleteMapping("/{userAgreementId}")
	public Response<String> deleteUserAgreement(@RequestBody DeleteUserAgreementRequest request) {
		return userAgreementService.deleteUserAgreement(request, new UserAgreement());
	}
}
