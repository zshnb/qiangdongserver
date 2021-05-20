package com.qiangdong.reader.block_user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.BlockUser;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.block_user.AddBlockUserRequest;
import com.qiangdong.reader.serviceImpl.BlockUserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BlockUserServiceAddBlockUserTest extends BaseTest {
	@Autowired
	private BlockUserServiceImpl blockUserService;

	@Test
	public void addBlockUserSuccessful() {
		AddBlockUserRequest request = new AddBlockUserRequest();
		request.setUserId(userId);
		request.setTargetUserId(adminUserId);
		BlockUser blockUser = blockUserService.addBlockUser(request).getData();
		assertThat(blockUser.getId()).isNotZero();
	}

	@Test
	public void addBlockUserFailedWhenTargetUserNotExist() {
		AddBlockUserRequest request = new AddBlockUserRequest();
		request.setUserId(userId);
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> blockUserService.addBlockUser(request));
	}

	@Test
	public void addBlockUserFailedWhenTargetUserSameWithUser() {
		AddBlockUserRequest request = new AddBlockUserRequest();
		request.setUserId(userId);
		request.setTargetUserId(userId);
		assertException(InvalidArgumentException.class, () -> blockUserService.addBlockUser(request));
	}
}
