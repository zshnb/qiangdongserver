package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.novel.NovelChapterCatalogItemDto;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelChapterRequest;
import com.qiangdong.reader.request.novel.DeleteNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetRecycleNovelChapterRequest;
import com.qiangdong.reader.request.novel.ListNovelChapterCatalogRequest;
import com.qiangdong.reader.request.novel.ListRecycleNovelChaptersRequest;
import com.qiangdong.reader.request.novel.UpdateNovelChapterReviewStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/novel-chapter")
public class ManageNovelChapterController {
	private final NovelChapterServiceImpl novelChapterService;

	public ManageNovelChapterController(
		NovelChapterServiceImpl novelChapterService) {
		this.novelChapterService = novelChapterService;
	}

	@PostMapping("/add-update")
	public Response<NovelChapter> addOrUpdateNovelChapter(
		@RequestBody AddOrUpdateNovelChapterRequest request) {
		return Response.ok(novelChapterService.addOrUpdateNovelChapter(request));
	}

	@DeleteMapping("/{novelId}/{chapterId}")
	public Response<String> deleteNovelChapter(@RequestBody DeleteNovelChapterRequest request) {
		return novelChapterService.deleteNovelChapter(request);
	}

	@PostMapping("/list/catalog")
	public PageResponse<NovelChapterCatalogItemDto> listNovelChapterCatalog(
			@RequestBody ListNovelChapterCatalogRequest request){
		return novelChapterService.listNovelChapterCatalog(request);
	}

	@PostMapping("/update/review-status")
	public Response<NovelChapter> updateNovelChapterReviewStatus(
			@RequestBody UpdateNovelChapterReviewStatusRequest request){
		return novelChapterService.updateNovelChapterReviewStatus(request);
	}

	@PostMapping("/list-recycle")
	public PageResponse<NovelChapterDto> listRecycleChapters(@RequestBody ListRecycleNovelChaptersRequest request) {
		return novelChapterService.listRecycleChapters(request);
	}

	@PostMapping("/recycle/{chapterId}")
	public Response<NovelChapterDto> getRecycleChapter(@RequestBody GetRecycleNovelChapterRequest request) {
		return novelChapterService.getRecycleChapter(request);
	}
}
