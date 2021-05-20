package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.DeleteNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceListDeletedNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void listSuccessful() {
		List<Novel> novels = novelService.list();
		assertThat(novels.size()).isEqualTo(2);

		DeleteNovelRequest deleteNovelRequest = new DeleteNovelRequest();
		deleteNovelRequest.setNovelId(1L);
		deleteNovelRequest.setUserId(authorUserId);
		novelService.deleteNovel(deleteNovelRequest);

		BaseRequest request = new BaseRequest();
		request.setUserId(authorUserId);
		PageResponse<NovelDto> response = novelService.listDeletedNovel(request);
		assertThat(response.getList().size()).isEqualTo(1);
		assertThat(response.getList().get(0).getNovelId()).isEqualTo(1L);
	}

	@Test
	public void listFailedWhenPermissionDeny() {
		assertException(PermissionDenyException.class, () -> novelService.listDeletedNovel(new BaseRequest()));
	}
}
