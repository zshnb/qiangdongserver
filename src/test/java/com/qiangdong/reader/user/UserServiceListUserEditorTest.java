package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceListUserEditorTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void listUserEditorSuccessful(){
		BaseRequest request = new BaseRequest();
		request.setUserId(adminUserId);
		List<UserDto> editor = userService.listUserEditor(request).getList();
		assertThat(editor.size()).isEqualTo(1);
		assertThat(editor.get(0).getNickname()).isEqualTo("无缺");
		assertThat(editor.get(0).getParentTypeName()).isEqualTo("分类1");
	}

	@Test
	public void listUserEditorFailedWhenUserNoPower(){
		BaseRequest request = new BaseRequest();
		request.setUserId(userId);
		assertException(PermissionDenyException.class, ()-> userService.listUserEditor(request));
	}
}
