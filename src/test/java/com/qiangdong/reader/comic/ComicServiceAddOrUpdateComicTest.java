package com.qiangdong.reader.comic;

import static org.mockito.ArgumentMatchers.any;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksTagDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.WorksTag;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.AddOrUpdateComicRequest;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import com.qiangdong.reader.serviceImpl.WorksTagServiceImpl;
import com.qiangdong.reader.utils.ComicUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class ComicServiceAddOrUpdateComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Autowired
	private WorksTagServiceImpl worksTagService;

	@SpyBean
	private ComicUtil comicUtil;

	@Before
	public void beforeMock() {
        Mockito.doNothing().when(comicUtil).indexComic(any(), any());
	}

	@Test
	public void addComicSuccessful() {
		Comic comic = comicService.addOrUpdateComic(addComicRequest).getData();
		Assert.assertNotEquals(0L, comic.getId().longValue());
		Assert.assertEquals("test comic", comic.getName());
		Assert.assertEquals(1L, comic.getAuthorId().longValue());
		Assert.assertEquals(1L, comic.getTypeId().longValue());
		QueryWrapper<WorksTag> queryWrapper = new QueryWrapper<WorksTag>()
				.eq("works_id", comic.getId())
				.eq("works_type", WorksTypeEnum.COMIC);
		WorksTag tag = worksTagService.getOne(queryWrapper);
		assertThat(tag.getTagName()).isEqualTo("坚毅");
		assertThat(tag.getGroupName()).isEqualTo("风格");
	}

	@Test
	public void addComicFailedWhenDuplicateName() {
		comicService.addOrUpdateComic(addComicRequest);
		assertException(InvalidArgumentException.class, () -> {
			comicService.addOrUpdateComic(addComicRequest);
		});
	}

	@Test
	public void updateComicSuccessful() {
		addComicRequest.setComicId(1L);
		addComicRequest.setName("new comic");
		Comic updateComic = comicService.addOrUpdateComic(addComicRequest).getData();
		assertThat(updateComic.getName()).isEqualTo("new comic");
	}

	@Test
	public void updateComicFailedWhenUpdateTwice() {
		addComicRequest.setComicId(1L);
		addComicRequest.setName("new comic");
		comicService.addOrUpdateComic(addComicRequest);
		assertException(InvalidArgumentException.class, () -> {
			comicService.addOrUpdateComic(addComicRequest);
		});
	}

	@Test
	public void updateFailedWhenComicIdIsInvalid() {
		AddOrUpdateComicRequest request = new AddOrUpdateComicRequest();
		request.setComicId(-1L);
		request.setName("new comic");
		assertException(InvalidArgumentException.class, () -> {
			comicService.addOrUpdateComic(request);
		});
	}

	@Test
	public void updateComicFailedWhenDuplicateName() {
		Comic addComic = comicService.addOrUpdateComic(addComicRequest).getData();

		AddOrUpdateComicRequest request = new AddOrUpdateComicRequest();
		request.setComicId(addComic.getId());
		request.setName("comic1");
		assertException(InvalidArgumentException.class, () -> {
			comicService.addOrUpdateComic(request);
		});
	}
}
