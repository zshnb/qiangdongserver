package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.request.novel.GetLatestNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetNovelChapterRequest;
import com.qiangdong.reader.request.novel.ListNovelChaptersRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/novel-chapter")
public class NovelChapterController {
	private final NovelChapterServiceImpl novelChapterService;

	public NovelChapterController(
		NovelChapterServiceImpl novelChapterService) {
		this.novelChapterService = novelChapterService;
	}

	@PostMapping("/detail")
	public Response<NovelChapter> getNovelChapter(@RequestBody GetNovelChapterRequest request) {
		return Response.ok(novelChapterService.getNovelChapter(request));
	}

	@PostMapping("/chapters")
	public PageResponse<NovelChapter> listNovelChapters(@RequestBody ListNovelChaptersRequest request) {
		return novelChapterService.listNovelChapters(request);
	}

	@PostMapping("/latest/{novelId}")
	public Response<NovelChapter> getLatestComicChapter(@RequestBody GetLatestNovelChapterRequest request) {
		return novelChapterService.getLatestChapter(request, new Novel());
	}
}
