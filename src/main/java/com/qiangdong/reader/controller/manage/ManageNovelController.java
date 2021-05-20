package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.novel.NovelAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.novel.NovelByManageTypeDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelRequest;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.request.novel.ConvertContentToOssRequest;
import com.qiangdong.reader.request.novel.DeleteNovelRequest;
import com.qiangdong.reader.request.novel.DeleteRecommendNovelRequest;
import com.qiangdong.reader.request.novel.GetNovelRequest;
import com.qiangdong.reader.request.novel.ListAuthorNovelRequest;
import com.qiangdong.reader.request.novel.ListNovelAndFirstChapterWithReviewRequest;
import com.qiangdong.reader.request.novel.ListNovelByManageTypeRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.request.novel.RestoreDeletedNovelRequest;
import com.qiangdong.reader.request.novel.UpdateNovelContractStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.novel.GetNovelResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/novel")
public class ManageNovelController {
	private final NovelServiceImpl novelService;

	public ManageNovelController(NovelServiceImpl novelService) {
		this.novelService = novelService;
	}

	@PostMapping("/add-update")
	public Response<Novel> addOrUpdateNovel(@RequestBody @Valid AddOrUpdateNovelRequest request) {
		return novelService.addOrUpdateNovel(request);
	}

	@PostMapping("/list")
	public PageResponse<NovelDto> listAuthorNovel(@RequestBody ListAuthorNovelRequest request) {
		return novelService.listAuthorNovel(request);
	}

	@DeleteMapping("/{novelId}")
	public Response<String> deleteNovel(@RequestBody DeleteNovelRequest request) {
		return novelService.deleteNovel(request);
	}

	@PostMapping("/list-review")
	public PageResponse<NovelAndFirstChapterWithReviewDto> listNovelAndFirstChapterWithReview(
			@RequestBody ListNovelAndFirstChapterWithReviewRequest request) {
		return novelService.listNovelAndFirstChapterWithReview(request);
	}


	@PostMapping("/list-type")
	public PageResponse<NovelByManageTypeDto> listNovelByType(@RequestBody ListNovelByManageTypeRequest request) {
		return novelService.listNovelByManageType(request, new User());
	}

	@PostMapping("/update-contract")
	public Response<Novel> update(@RequestBody @Valid UpdateNovelContractStatusRequest request) {
		return novelService.updateNovelContractStatus(request);
	}

	@PostMapping("/add-recommend")
	public Response<String> addRecommendNovel(@RequestBody @Valid AddRecommendNovelRequest request) {
		return novelService.addRecommendNovel(request);
	}

	@PostMapping("/list-recommend-novel")
	public PageResponse<RecommendNovelDto> listRecommendNovel(@RequestBody ListRecommendNovelRequest request) {
		return novelService.listRecommendNovel(request);
	}

	@DeleteMapping("/recommend/{novelId}")
	public Response<String> deleteRecommendNovel(@RequestBody @Valid DeleteRecommendNovelRequest request) {
		return novelService.deleteRecommendNovel(request);
	}

	@PostMapping("/{novelId}")
	public GetNovelResponse getNovel(@RequestBody GetNovelRequest request) {
		return novelService.getNovelWithAuthor(request);
	}

	@PostMapping("/list-deleted")
	public PageResponse<NovelDto> listDeletedComic(@RequestBody BaseRequest request) {
		return novelService.listDeletedNovel(request);
	}

	@PostMapping("/restore-deleted")
	public Response<String> restoreDeletedComic(@RequestBody RestoreDeletedNovelRequest request) {
		return novelService.restoreDeletedNovel(request);
	}

	@PostMapping("/read-statistics")
	public Response<WorksReadStatisticsDto> getReadStatistics(@RequestBody GetNovelRequest request) {
		return novelService.getNovelReadStatistics(request, new Novel());
	}

	@PostMapping("/upload")
	public Response<String> uploadChapterContent(@RequestBody ConvertContentToOssRequest request) throws IOException {
		return novelService.convertContentToOss(request, new Novel());
	}
}
