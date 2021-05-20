package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.WorkDaySummary;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelChapterRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.schedule.AuthorWorkDaySummarySchedule;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.DateUtil;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceListWorkDaySummaryTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthorWorkDaySummarySchedule authorWorkDaySummarySchedule;

	@Autowired
	private NovelChapterMapper novelChapterMapper;

	@Autowired
	private DateUtil dateUtil;

	@Test
	public void listSuccessful() {
		NovelChapter novelChapter = new NovelChapter();
		novelChapter.setIndex(1);
		BeanUtils.copyProperties(new AddOrUpdateNovelChapterRequest(), novelChapter);
		novelChapter.setUpdateAt(dateUtil.getYesterday());
		novelChapter.setNovelId(1L);
		novelChapter.setPrice(0.0);
		novelChapter.setReviewStatus(WorksChapterReviewStatusEnum.PASS);
		novelChapterMapper.insert(novelChapter);

		authorWorkDaySummarySchedule.calculateWorkDaySummary();
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		PageResponse<WorkDaySummary> response = userService.listAuthorWorkDaySummary(request);
		assertThat(response.getList().size()).isEqualTo(1);
	}
}
