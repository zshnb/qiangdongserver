package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.user.UserAuthorDto;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.*;
import com.qiangdong.reader.request.user.GetUserEditorRequest;
import com.qiangdong.reader.request.user.AddOrUpdateUserEditorRequest;
import com.qiangdong.reader.request.user.CaptchaLoginRequest;
import com.qiangdong.reader.request.user.DeleteUserRequest;
import com.qiangdong.reader.request.user.ManagerLoginRequest;
import com.qiangdong.reader.request.user.ManagerRegisterRequest;
import com.qiangdong.reader.request.user.SendMessageRegisterRequest;
import com.qiangdong.reader.request.user.UpdateAuthorByEditorRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user")
public class ManageUserController {

	private final UserServiceImpl userService;

	public ManageUserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping("/add-update-editor")
	public Response<User> addOrUpdateUserEditor(@RequestBody AddOrUpdateUserEditorRequest request) {
		return userService.addOrUpdateUserEditor(request);
	}

	@PostMapping("/list-editor")
	public PageResponse<UserDto> listUserEditor(@RequestBody BaseRequest request) {
		return userService.listUserEditor(request);
	}

	@DeleteMapping("/{targetUserId}")
	public Response<String> deleteUser(@RequestBody DeleteUserRequest request) {
		return userService.deleteUser(request);
	}

	@PostMapping("/detail-editor")
	public Response<UserDto> getUserEditor(@RequestBody GetUserEditorRequest request) {
		return userService.getUserEditor(request);
	}

	@PostMapping("/list-author")
	public PageResponse<UserAuthorDto> listAuthor(@RequestBody BaseRequest request){
		return userService.listAuthor(request);
	}

	@PostMapping("/enable-author-charge")
	public Response<String> enableAuthorAllowCharge(@RequestBody EnableAuthorAllowChargeRequest request) {
		return userService.enableAuthorAllowCharge(request);
	}

	@PostMapping("/update-author")
	public Response<User> updateAuthorByEditor(@RequestBody UpdateAuthorByEditorRequest request){
		return userService.updateAuthorByEditor(request);
	}

	@PostMapping("/login")
	public Response<UserDto> login(@RequestBody ManagerLoginRequest request,
	                      HttpServletRequest httpServletRequest,
	                      HttpServletResponse httpServletResponse){
		return userService.managerLogin(request, httpServletRequest, httpServletResponse);
	}

	@PostMapping("/login/captcha")
	public void generateCaptcha(@RequestBody CaptchaLoginRequest request, HttpServletResponse response) {
		userService.generateCaptcha(request, response);
	}

	@PostMapping("/register")
	public Response<String> register(@RequestBody ManagerRegisterRequest request) {
		return userService.managerRegister(request);
	}

	@PostMapping("/register/verify-code")
	public Response<String> sendVerifyCode(@RequestBody SendMessageRegisterRequest request) {
		return userService.sendVerifyCode(request);
	}

	@GetMapping("/logout")
	public Response<String> logout(HttpServletRequest httpServletRequest) {
		return userService.logout(httpServletRequest);
	}

	@PostMapping("/list-user")
	public PageResponse<User> listUser(@RequestBody ListUserRequest request){
		return userService.listUser(request);
	}

}
