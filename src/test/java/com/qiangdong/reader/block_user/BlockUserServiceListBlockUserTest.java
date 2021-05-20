package com.qiangdong.reader.block_user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.BlockUserDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.BlockUserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BlockUserServiceListBlockUserTest extends BaseTest {
	@Autowired
	private BlockUserServiceImpl blockUserService;

	@Test
	public void listBlockUserSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(authorUserId);
		PageResponse<BlockUserDto> response = blockUserService.listBlockUser(request);
		assertThat(response.getList().size()).isEqualTo(1);

		BlockUserDto blockUserDto = response.getList().get(0);
		assertThat(blockUserDto.getUserId()).isEqualTo(2L);
		assertThat(blockUserDto.getId()).isEqualTo(1L);
	}
}
