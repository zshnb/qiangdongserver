package com.qiangdong.reader.fate_board;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.fate_board.AddOrUpdateFateBoardRequest;
import com.qiangdong.reader.serviceImpl.FateBoardServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FateBoardServiceAddOrUpdateFateBoardTest extends BaseTest {
	@Autowired
	private FateBoardServiceImpl fateBoardService;

	@Test
	public void addFateBoardSuccessful() {
		AddOrUpdateFateBoardRequest request = new AddOrUpdateFateBoardRequest();
		request.setContent("content");
		request.setGoodsId(1L);
		request.setUserId(3L);
		FateBoardDto fateBoard = fateBoardService.addOrUpdateFateBoard(request).getData();
		assertThat(fateBoard.getFateBoardId()).isNotZero();
	}

	@Test
	public void addFateBoardFailedWhenExist() {
		AddOrUpdateFateBoardRequest request = new AddOrUpdateFateBoardRequest();
		request.setUserId(1L);
		request.setGoodsId(1L);
		assertException(InvalidArgumentException.class, () -> fateBoardService.addOrUpdateFateBoard(request));
	}

	@Test
	public void addFateBoardFailedWhenGoodsNotExist() {
		AddOrUpdateFateBoardRequest request = new AddOrUpdateFateBoardRequest();
		request.setUserId(2L);
		assertException(InvalidArgumentException.class, () -> fateBoardService.addOrUpdateFateBoard(request));
	}

	@Test
	public void updateFateBoardSuccessful() {
		AddOrUpdateFateBoardRequest request = new AddOrUpdateFateBoardRequest();
		request.setFateBoardId(1L);
		request.setContent("hah");
		request.setGoodsId(2L);
		request.setUserId(1L);
		FateBoardDto fateBoard = fateBoardService.addOrUpdateFateBoard(request).getData();
		assertThat(fateBoard.getContent()).isEqualTo("hah");
	}

	@Test
	public void updateFateBoardFailedWhenNotExist() {
		AddOrUpdateFateBoardRequest request = new AddOrUpdateFateBoardRequest();
		request.setFateBoardId(-1L);
		request.setGoodsId(1L);
		assertException(InvalidArgumentException.class, () -> fateBoardService.addOrUpdateFateBoard(request));
	}

	@Test
	public void updateFateBoardFailedWhenHasSensitiveWord() {
		AddOrUpdateFateBoardRequest request = new AddOrUpdateFateBoardRequest();
		request.setGoodsId(1L);
		request.setUserId(1L);
		request.setContent("fuck");
		assertException(InvalidArgumentException.class, () -> fateBoardService.addOrUpdateFateBoard(request));
	}
}
