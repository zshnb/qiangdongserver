package com.qiangdong.reader.block_user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.BlockUser;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.block_user.DeleteBlockUserRequest;
import com.qiangdong.reader.serviceImpl.BlockUserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BlockUserServiceDeleteBlockUserTest extends BaseTest {
	@Autowired
	private BlockUserServiceImpl blockUserService;

	@Test
	public void deleteBlockUserSuccessful() {
		DeleteBlockUserRequest request = new DeleteBlockUserRequest();
		request.setBlockUserId(1L);
		blockUserService.deleteBlockUser(request, new BlockUser());
		BlockUser blockUser = blockUserService.getById(1L);
		assertThat(blockUser).isNull();
	}

	@Test
	public void deleteBlockUserFailedWhenBlockUserNotExist() {
		DeleteBlockUserRequest request = new DeleteBlockUserRequest();
		request.setBlockUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			blockUserService.deleteBlockUser(request, new BlockUser());
		});
	}
}
