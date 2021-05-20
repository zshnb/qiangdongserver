package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.DeleteNovelRequest;
import com.qiangdong.reader.request.novel.RestoreDeletedNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceRestoreDeletedNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void restoreSuccessful() {
		DeleteNovelRequest deleteNovelRequest = new DeleteNovelRequest();
		deleteNovelRequest.setNovelId(1L);
		deleteNovelRequest.setUserId(authorUserId);
		novelService.deleteNovel(deleteNovelRequest);

		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserId(authorUserId);
		PageResponse<NovelDto> response = novelService.listDeletedNovel(baseRequest);
		assertThat(response.getList().size()).isEqualTo(1);
		assertThat(response.getList().get(0).getNovelId()).isEqualTo(1L);

		RestoreDeletedNovelRequest request = new RestoreDeletedNovelRequest();
		request.setNovelId(1L);
		request.setUserId(authorUserId);
		novelService.restoreDeletedNovel(request);

		List<Novel> novels = novelService.list();
		assertThat(novels.size()).isEqualTo(2);
	}

	@Test
	public void restoreFailedWhenNotExist() {
		RestoreDeletedNovelRequest request = new RestoreDeletedNovelRequest();
		request.setUserId(authorUserId);
		request.setNovelId(1L);
		assertException(InvalidArgumentException.class, () -> novelService.restoreDeletedNovel(request));
	}

	@Test
	public void restoreFailedWhenPermissionDeny() {
		RestoreDeletedNovelRequest request = new RestoreDeletedNovelRequest();
		request.setNovelId(1L);
		assertException(PermissionDenyException.class, () -> novelService.restoreDeletedNovel(request));
	}
}
