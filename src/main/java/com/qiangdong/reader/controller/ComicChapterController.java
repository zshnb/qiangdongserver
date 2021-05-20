package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.request.comic.GetComicChapterRequest;
import com.qiangdong.reader.request.comic.GetLatestComicChapterRequest;
import com.qiangdong.reader.request.comic.ListComicChaptersRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
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
@RequestMapping("/comic-chapter")
public class ComicChapterController {
	private final ComicChapterServiceImpl comicChapterService;

	public ComicChapterController(
		ComicChapterServiceImpl comicChapterService) {
		this.comicChapterService = comicChapterService;
	}

	@PostMapping("/detail")
	public Response<ComicChapter> getComicChapter(@RequestBody GetComicChapterRequest request) {
		return Response.ok(comicChapterService.getComicChapter(request));
	}

	@PostMapping("/chapters")
	public PageResponse<ComicChapter> listComicChapters(@RequestBody ListComicChaptersRequest request) {
		return comicChapterService.listComicChapters(request);
	}

	@PostMapping("/latest/{comicId}")
	public Response<ComicChapter> getLatestComicChapter(@RequestBody GetLatestComicChapterRequest request) {
		return comicChapterService.getLatestChapter(request, new Comic());
	}
}
