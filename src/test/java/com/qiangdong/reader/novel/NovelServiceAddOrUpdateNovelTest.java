package com.qiangdong.reader.novel;

import static org.mockito.ArgumentMatchers.any;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelRequest;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import com.qiangdong.reader.utils.NovelUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class NovelServiceAddOrUpdateNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@SpyBean
	private NovelUtil novelUtil;

	@Before
	public void beforeMock() {
		Mockito.doNothing().when(novelUtil).indexNovel(any(), any());
	}

	@Test
	public void addNovelSuccessful() {
		Novel novel = novelService.addOrUpdateNovel(addNovelRequest).getData();
		Assert.assertNotEquals(0L, novel.getId().longValue());
		Assert.assertEquals("test novel", novel.getName());
		Assert.assertEquals(1L, novel.getAuthorId().longValue());
		Assert.assertEquals(1L, novel.getTypeId().longValue());
	}

	@Test
	public void addNovelFailedWhenDuplicateName() {
		novelService.addOrUpdateNovel(addNovelRequest);
		assertException(InvalidArgumentException.class, () -> novelService.addOrUpdateNovel(addNovelRequest));
	}

	@Test
	public void updateNovelSuccessful() {
		AddOrUpdateNovelRequest request = new AddOrUpdateNovelRequest();
		request.setNovelId(1L);
		request.setUserId(authorUserId);
		request.setName("new novel");
		Novel updateNovel = novelService.addOrUpdateNovel(request).getData();
		assertThat(updateNovel.getName()).isEqualTo("new novel");
	}

	@Test
	public void updateNovelFailedWhenUpdateTwice() {
		AddOrUpdateNovelRequest request = new AddOrUpdateNovelRequest();
		request.setNovelId(1L);
		request.setUserId(authorUserId);
		request.setName("new novel");
		Novel updateNovel = novelService.addOrUpdateNovel(request).getData();
		assertThat(updateNovel.getName()).isEqualTo("new novel");

		assertException(InvalidArgumentException.class, () -> novelService.addOrUpdateNovel(request));
	}

	@Test
	public void updateFailedWhenNovelIdIsInvalid() {
		AddOrUpdateNovelRequest request = new AddOrUpdateNovelRequest();
		request.setUserId(authorUserId);
		request.setNovelId(-1L);
		request.setName("new novel");
		assertException(InvalidArgumentException.class, () -> novelService.addOrUpdateNovel(request));
	}

	@Test
	public void updateNovelFailedWhenDuplicateName() {
		Novel addNovel = novelService.addOrUpdateNovel(addNovelRequest).getData();

		AddOrUpdateNovelRequest request = new AddOrUpdateNovelRequest();
		request.setUserId(authorUserId);
		request.setNovelId(addNovel.getId());
		request.setName("novel1");
		assertException(InvalidArgumentException.class, () -> novelService.addOrUpdateNovel(request));
	}
}
