package com.qiangdong.reader.controller;

import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.WorkDaySummary;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.BecomeAuthorRequest;
import com.qiangdong.reader.request.user.GetAuthorCenterRequest;
import com.qiangdong.reader.request.user.GetPersonalCenterInfoRequest;
import com.qiangdong.reader.request.user.RewardComicRequest;
import com.qiangdong.reader.request.user.RewardNovelRequest;
import com.qiangdong.reader.request.user.SendMessageRegisterRequest;
import com.qiangdong.reader.request.user.UserLoginRequest;
import com.qiangdong.reader.request.user.UserRegisterRequest;
import com.qiangdong.reader.request.user.VoteTicketRequest;
import com.qiangdong.reader.request.user.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user.GetAuthorCenterResponse;
import com.qiangdong.reader.response.user.GetAuthorCompletionResponse;
import com.qiangdong.reader.response.user.GetAuthorIncomeResponse;
import com.qiangdong.reader.search.UserForSearch;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    public UserController(UserServiceImpl userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Response<UserDto> login(@RequestBody UserLoginRequest request,
                                   HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse){

        return userService.login(request, httpServletRequest, httpServletResponse);
    }

    @PostMapping("/register/send-verify-code")
    public Response<String> sendVerifyCode(@RequestBody SendMessageRegisterRequest request) {
        return userService.sendVerifyCode(request);
    }

    @PostMapping("/vote-recommend-ticket")
    public Response<Integer> voteRecommendTicket(@RequestBody VoteTicketRequest request) {
        return userService.voteRecommendTicket(request, new User());
    }

    @PostMapping("/vote-wall-ticket")
    public Response<Integer> voteWallTicket(@RequestBody VoteTicketRequest request) {
        return userService.voteWallTicket(request, new User());
    }

    @PostMapping("/personal-center")
    public Response<UserDto> getPersonalCenterInfo(@RequestBody GetPersonalCenterInfoRequest request) {
        return userService.getPersonalCenterInfo(request);
    }

    @PostMapping("/logout")
    public Response<String> logout(HttpServletRequest request,
                                   HttpServletResponse response) {
        String token = request.getHeader(JwtInterceptor.HEADER_AUTHENTICATION);
        String expireToken = jwtUtil.expireJwtToken(token);
        response.setHeader("Access-Control-Expose-Headers", JwtInterceptor.HEADER_AUTHENTICATION);
        response.setHeader(JwtInterceptor.HEADER_AUTHENTICATION, expireToken);
        return Response.ok();
    }

    @PostMapping("/become-author")
    public Response<User> becomeAuthor(@RequestBody BecomeAuthorRequest request) {
        return userService.becomeAuthor(request);
    }

    @PostMapping("/author-center")
    public GetAuthorCenterResponse getAuthorCenter(@RequestBody GetAuthorCenterRequest request) {
        return userService.getAuthorCenter(request);
    }

    @PostMapping("/create-calender")
    public PageResponse<WorkDaySummary> listAuthorWorkDaySummary(@RequestBody BaseRequest request) {
        return userService.listAuthorWorkDaySummary(request);
    }

    @PostMapping("/author-detail")
    public Response<UserDto> getAuthorDetail(@RequestBody BaseRequest request){
        return userService.getAuthorDetail(request);
    }

    @PostMapping("/security-center")
    public Response<User> getSecurityCenter(@RequestBody BaseRequest request) {
        return userService.getSecurityCenter(request);
    }

    @PostMapping("/security/mobile-verify-code")
    public Response<String> sendSecurityPhoneVerifyCode(@RequestBody BaseRequest request) {
        return userService.sendSecurityPhoneVerifyCode(request, new User());
    }

    @PostMapping("/security/email-verify-code")
    public Response<String> sendSecurityEmailVerifyCode(@RequestBody BaseRequest request) {
        return userService.sendSecurityEmailVerifyCode(request, new User());
    }

    @PostMapping("/security/check-verify-code")
    public Response<String> checkVerifyCode(@RequestBody CheckSecurityVerifyCodeRequest request){
        return userService.checkVerifyCode(request, new User());
    }

    @PostMapping("/update-password")
    public Response<String> updateUserPassword(@RequestBody UpdateUserPasswordRequest request) {
        return userService.updatePassword(request, new User());
    }

    @PostMapping("/update-email")
    public Response<String> updateUserEmail(@RequestBody UpdateUserEmailRequest request) {
        return userService.updateEmail(request, new User());
    }

    @PostMapping("/update-user-info")
    public Response<String> updateUserInfo(@RequestBody @Valid UpdateUserInfoRequest request){
        return userService.updateUserInfo(request);
    }

    @DeleteMapping("/deactivate")
    public Response<String> deactivateAccount(@RequestBody BaseRequest request){
        return userService.deactivateAccount(request);
    }

    @PostMapping("/update-secrecy-config")
    public Response<String> updateSecrecyConfig(@RequestBody UpdateSecrecyConfigRequest request) {
        return userService.updateSecrecyConfig(request);
    }

    @PostMapping("/secrecy-config")
    public Response<SecrecyConfig> getSecrecyConfig(@RequestBody BaseRequest request) {
        return userService.getSecrecyConfig(request);
    }

    @PostMapping("/update-author-detail")
    public Response<User> updateAuthorDetail(@RequestBody UpdateAuthorDetailRequest request){
        return userService.updateAuthorDetail(request, new User());
    }

    @PostMapping("/fast-login")
    public Response<UserDto> fastLogin(@RequestBody UserFastLoginRequest request,
                                       HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse){
        return userService.fastLogin(request, httpServletRequest, httpServletResponse);
    }

    @PostMapping("/fast-login/send-verify-code")
    public Response<String> sendFastLoginVerifyCode(@RequestBody SendFastLoginVerifyCodeRequest request) {
        return userService.sendFastLoginVerifyCode(request);
    }

    @PostMapping("/author-completion")
    public GetAuthorCompletionResponse getAuthorCompletion(@RequestBody BaseRequest request){
        return userService.getAuthorCompletion(request, new User());
    }

    @PostMapping("/search")
    public PageResponse<UserForSearch> searchUser(@RequestBody SearchUserRequest request) {
        return userService.searchUser(request);
    }

    @PostMapping("/personal-center-qr-code")
    public Response<String> getPersonalCenterQrCode(@RequestBody BaseRequest request) {
        return userService.getPersonalCenterQrCode(request);
    }

    @RequestMapping(value = "/check-exist", method = RequestMethod.OPTIONS)
    public Response<UserIdNameDto> checkUserExist(@RequestBody CheckUserRequest request) {
        return userService.checkUserExist(request);
    }

    @PostMapping("/token-login")
    public Response<UserDto> tokenLogin(@RequestBody BaseRequest request) {
        return userService.tokenLogin(request, new User());
    }

    @PostMapping("/update-chat-status")
    public Response<User> updateChatStatus(@RequestBody UpdateUserChatStatusRequest request) {
        return userService.updateChatStatus(request, new User());
    }

    @PostMapping("/author/income")
    public GetAuthorIncomeResponse getAuthorIncome(@RequestBody GetAuthorIncomeRequest request) {
        return userService.getAuthorIncome(request);
    }

    @PostMapping("/update-new-password")
    public Response<User> updatePassword(@RequestBody UpdateNewPasswordRequest request) {
        return userService.updateNewPassword(request, new User());
    }
}
