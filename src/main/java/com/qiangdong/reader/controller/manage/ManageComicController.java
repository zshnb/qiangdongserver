package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.comic.ComicAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.comic.ComicByManageTypeDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comic.AddOrUpdateComicRequest;
import com.qiangdong.reader.request.comic.AddRecommendComicRequest;
import com.qiangdong.reader.request.comic.DeleteComicRequest;
import com.qiangdong.reader.request.comic.DeleteRecommendComicRequest;
import com.qiangdong.reader.request.comic.GetComicRequest;
import com.qiangdong.reader.request.comic.ListAuthorComicRequest;
import com.qiangdong.reader.request.comic.ListComicAndFirstChapterWithReviewRequest;
import com.qiangdong.reader.request.comic.ListComicByManageTypeRequest;
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.request.comic.RestoreDeletedComicRequest;
import com.qiangdong.reader.request.comic.UpdateComicContractStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.comic.GetComicResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/comic")
public class ManageComicController {

	@Autowired
	private ComicServiceImpl comicService;

	@PostMapping("/add-update")
	public Response<Comic> addOrUpdateComic(@RequestBody @Valid AddOrUpdateComicRequest request) {
		return comicService.addOrUpdateComic(request);
	}

	@PostMapping("/list")
	public PageResponse<ComicDto> listAuthorComic(@RequestBody ListAuthorComicRequest request) {
		return comicService.listAuthorComic(request);
	}

	@PostMapping("/{comicId}")
	public GetComicResponse getComic(@RequestBody GetComicRequest request) {
		return comicService.getComicWithAuthor(request);
	}

	@DeleteMapping("/{comicId}")
	public Response<String> deleteComic(@RequestBody DeleteComicRequest request) {
		return comicService.deleteComic(request);
	}

    @PostMapping("/update-contract")
    public Response<Comic> updateComicContractStatus(@RequestBody @Valid UpdateComicContractStatusRequest request) {
        return comicService.updateComicContractStatus(request);
    }

    @PostMapping("/list-type")
    public PageResponse<ComicByManageTypeDto> listComicByManageType(@RequestBody ListComicByManageTypeRequest request) {
        return comicService.listComicByManageType(request, new User());
    }

    @PostMapping("/list-review")
    public PageResponse<ComicAndFirstChapterWithReviewDto> listComicAndFirstChapterWithReview(
            @RequestBody ListComicAndFirstChapterWithReviewRequest request) {
        return comicService.listComicAndFirstChapterWithReview(request);
    }

	@PostMapping("/add-recommend")
	public Response<String> addRecommendComic(@RequestBody @Valid AddRecommendComicRequest request) {
		return comicService.addRecommendComic(request);
	}

	@PostMapping("/list-recommend-comic")
	public PageResponse<RecommendComicDto> listRecommendComic(@RequestBody ListRecommendComicRequest request) {
		return comicService.listRecommendComic(request);
	}

	@DeleteMapping("/recommend/{comicId}")
	public Response<String> deleteRecommendComic(@RequestBody @Valid DeleteRecommendComicRequest request) {
		return comicService.deleteRecommendComic(request);
	}

	@PostMapping("/list-deleted")
	public PageResponse<ComicDto> listDeletedComic(@RequestBody BaseRequest request) {
		return comicService.listDeletedComic(request);
	}

	@PostMapping("/restore-deleted")
	public Response<String> restoreDeletedComic(@RequestBody RestoreDeletedComicRequest request) {
		return comicService.restoreDeletedComic(request);
	}

	@PostMapping("/read-statistics")
	public Response<WorksReadStatisticsDto> getReadStatistics(@RequestBody GetComicRequest request) {
		return comicService.getComicReadStatistics(request, new Comic());
	}
}
