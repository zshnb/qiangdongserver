package com.qiangdong.reader.service;

import com.aliyuncs.exceptions.ClientException;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.dto.user.UserAuthorDto;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.entity.WorkDaySummary;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.GetUserEditorRequest;
import com.qiangdong.reader.request.user.AddOrUpdateUserEditorRequest;
import com.qiangdong.reader.request.user.BecomeAuthorRequest;
import com.qiangdong.reader.request.user.CaptchaLoginRequest;
import com.qiangdong.reader.request.user.DeleteUserRequest;
import com.qiangdong.reader.request.user.GetAuthorCenterRequest;
import com.qiangdong.reader.request.user.GetPersonalCenterInfoRequest;
import com.qiangdong.reader.request.user.ManagerLoginRequest;
import com.qiangdong.reader.request.user.ManagerRegisterRequest;
import com.qiangdong.reader.request.user.VoteTicketRequest;
import com.qiangdong.reader.request.user.UpdateAuthorByEditorRequest;
import com.qiangdong.reader.request.user.UserLoginRequest;
import com.qiangdong.reader.request.user.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user.GetAuthorCenterResponse;
import com.qiangdong.reader.response.user.GetAuthorCompletionResponse;
import com.qiangdong.reader.response.user.GetAuthorIncomeResponse;
import com.qiangdong.reader.search.UserForSearch;
import com.qiangdong.reader.validate.user.ManagerLoginValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface IUserService extends IService<User> {
	Response<UserDto> login(UserLoginRequest request,
	                        HttpServletRequest httpServletRequest,
	                        HttpServletResponse httpServletResponse);

	Response<String> sendVerifyCode(SendMessageRegisterRequest request) throws ClientException;

	Response<UserDto> getPersonalCenterInfo(GetPersonalCenterInfoRequest request);

    PageResponse<UserForSearch> searchUser(SearchUserRequest request);

    Response<User> becomeAuthor(BecomeAuthorRequest request);

	GetAuthorCenterResponse getAuthorCenter(GetAuthorCenterRequest request);

	PageResponse<WorkDaySummary> listAuthorWorkDaySummary(BaseRequest request);

	Response<UserDto> getAuthorDetail(BaseRequest request);

    @RequireEditor
    @Transactional
    Response<String> enableAuthorAllowCharge(EnableAuthorAllowChargeRequest request);

    @Transactional(rollbackFor = RuntimeException.class)
	Response<Integer> voteRecommendTicket(VoteTicketRequest request, User user);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<Integer> voteWallTicket(VoteTicketRequest request, User user);

	Response<User> addOrUpdateUserEditor(AddOrUpdateUserEditorRequest request);

	PageResponse<UserDto> listUserEditor(BaseRequest request);

	Response<String> deleteUser(DeleteUserRequest request);

	Response<UserDto> getUserEditor(GetUserEditorRequest request);

	PageResponse<UserAuthorDto> listAuthor(BaseRequest request);

	Response<User> updateAuthorByEditor(UpdateAuthorByEditorRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> updateUserInfo(UpdateUserInfoRequest request);

	@Validation(ManagerLoginValidator.class)
	Response<UserDto> managerLogin(ManagerLoginRequest request,
	                               HttpServletRequest httpServletRequest,
	                               HttpServletResponse httpServletResponse);

	void generateCaptcha(CaptchaLoginRequest request, HttpServletResponse response);

	Response<String> managerRegister(ManagerRegisterRequest request);

	Response<String> logout(HttpServletRequest httpServletRequest);

	Response<User> getSecurityCenter(BaseRequest request);

	Response<String> sendSecurityPhoneVerifyCode(BaseRequest request, User user);

	Response<String> sendSecurityEmailVerifyCode(BaseRequest request, User user);

	Response<String> updatePassword(UpdateUserPasswordRequest request, User user);

	Response<String> updateEmail(UpdateUserEmailRequest request, User user);

	Response<String> deactivateAccount(BaseRequest request);

	Response<String> checkVerifyCode(CheckSecurityVerifyCodeRequest request, User user);

	PageResponse<User> listUser(ListUserRequest request);

	Response<String> updateSecrecyConfig(UpdateSecrecyConfigRequest request);

	Response<SecrecyConfig> getSecrecyConfig(BaseRequest request);

	Response<User> updateAuthorDetail(UpdateAuthorDetailRequest request, User user);

	Response<String> sendFastLoginVerifyCode(SendFastLoginVerifyCodeRequest request);

	Response<UserDto> fastLogin(UserFastLoginRequest request,
								HttpServletRequest httpServletRequest,
								HttpServletResponse httpServletResponse);

	GetAuthorCompletionResponse getAuthorCompletion(BaseRequest request, User user);

	Response<String> getPersonalCenterQrCode(BaseRequest request);

	Response<UserIdNameDto> checkUserExist(CheckUserRequest request);

	Response<UserDto> tokenLogin(BaseRequest request, User user);

	Response<User> updateChatStatus(UpdateUserChatStatusRequest request, User user);

	GetAuthorIncomeResponse getAuthorIncome(GetAuthorIncomeRequest request);

	Response<User> updateNewPassword(UpdateNewPasswordRequest request, User user);
}
