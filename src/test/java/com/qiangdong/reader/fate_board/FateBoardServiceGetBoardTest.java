package com.qiangdong.reader.fate_board;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.enums.fate_board.FateBoardStatusEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.FateBoardServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FateBoardServiceGetBoardTest extends BaseTest {
	@Autowired
	private FateBoardServiceImpl fateBoardService;

	@Test
	public void getFateBoardSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		FateBoardDto fateBoard = fateBoardService.getFateBoard(request).getData();
		assertThat(fateBoard.getUsername()).isEqualTo("user1");
		assertThat(fateBoard.getStatus()).isEqualTo(FateBoardStatusEnum.UP);
		assertThat(fateBoard.getMatchSex()).isEqualTo(UserSexEnum.MAN);
		assertThat(fateBoard.getGoods().getUpAppearance()).isEqualTo("aa");
		assertThat(fateBoard.getGoods().getName()).isEqualTo("board1");
	}

	@Test
	public void getFateBoardFailedWhenNotExist() {
		BaseRequest request = new BaseRequest();
		request.setUserId(-1L);
		assertException(InvalidArgumentException.class, () -> fateBoardService.getFateBoard(request));
	}
}
