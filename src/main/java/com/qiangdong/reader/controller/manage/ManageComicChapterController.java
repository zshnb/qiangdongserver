package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.comic.ComicChapterCatalogItemDto;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.request.comic.GetRecycleComicChapterRequest;
import com.qiangdong.reader.request.comic.ListComicChapterCatalogRequest;
import com.qiangdong.reader.request.comic.ListRecycleComicChaptersRequest;
import com.qiangdong.reader.request.comic.UpdateComicChapterReviewStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.request.comic.AddOrUpdateComicChapterRequest;
import com.qiangdong.reader.request.comic.DeleteComicChapterRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/comic-chapter")
public class ManageComicChapterController {

	private final ComicChapterServiceImpl comicChapterService;

	public ManageComicChapterController(
		ComicChapterServiceImpl comicChapterService) {
		this.comicChapterService = comicChapterService;
	}

	@PostMapping("/add-update")
	public Response<ComicChapter> addOrUpdateComicChapter(
		@RequestBody AddOrUpdateComicChapterRequest request) {
		return Response.ok(comicChapterService.addOrUpdateComicChapter(request));
	}

	@DeleteMapping("/{comicId}/{chapterId}")
	public Response<String> deleteComicChapter(@RequestBody DeleteComicChapterRequest request) {
		return comicChapterService.deleteComicChapter(request);
	}

	@PostMapping("/list/catalog")
	public PageResponse<ComicChapterCatalogItemDto> listComicChapterCatalog(
			@RequestBody ListComicChapterCatalogRequest request) {
		return comicChapterService.listComicChapterCatalog(request);
	}

	@PostMapping("/update/review-status")
	public Response<ComicChapter> updateComicChapterReviewStatus(
			@RequestBody UpdateComicChapterReviewStatusRequest request) {
		return comicChapterService.updateComicChapterReviewStatus(request);
	}

	@PostMapping("/list-recycle")
	public PageResponse<ComicChapterDto> listRecycleChapters(@RequestBody ListRecycleComicChaptersRequest request) {
		return comicChapterService.listRecycleChapters(request);
	}

	@PostMapping("/recycle/{chapterId}")
	public Response<ComicChapterDto> getRecycleChapter(@RequestBody GetRecycleComicChapterRequest request) {
		return comicChapterService.getRecycleChapter(request);
	}
}
