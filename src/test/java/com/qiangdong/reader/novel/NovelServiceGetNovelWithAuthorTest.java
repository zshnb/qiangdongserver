package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.response.novel.GetNovelResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceGetNovelWithAuthorTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void getNovelWithAuthorSuccessful() {
		GetNovelResponse response = novelService.getNovelWithAuthor(getNovelRequest);
		assertThat(response.getNovel().getNovelId()).isEqualTo(1L);
	}

	@Test
	public void getNovelWithAuthorFailedWhenNotExist() {
		getNovelRequest.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> novelService.getNovelWithAuthor(getNovelRequest));
	}

	@Test
	public void getNovelWithAuthorFailedWhenNoPermission() {
		getNovelRequest.setUserId(userId);
		assertException(PermissionDenyException.class, () -> novelService.getNovelWithAuthor(getNovelRequest));
	}
}
