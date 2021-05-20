package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comic.GetComicRequest;
import com.qiangdong.reader.request.comic.GetComicRewardRankingRequest;
import com.qiangdong.reader.request.comic.ListAuthorComicRequest;
import com.qiangdong.reader.request.comic.ListComicByTypeRequest;
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.request.comic.RestoreDeletedComicRequest;
import com.qiangdong.reader.request.comic.SearchComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.comic.GetComicResponse;
import com.qiangdong.reader.search.ComicForSearch;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
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
@RequestMapping("/comic")
public class ComicController {
	private final ComicServiceImpl comicsService;

	public ComicController(ComicServiceImpl comicsService) {
		this.comicsService = comicsService;
	}

	@PostMapping("/detail/{comicId}")
	public Response<GetComicResponse> getComic(@RequestBody GetComicRequest request) {
		return Response.ok(comicsService.getComic(request, new Comic()));
	}

	@PostMapping("/list-type")
	public PageResponse<ComicDto> listComicByType(@RequestBody ListComicByTypeRequest request) {
		return comicsService.listComicByType(request);
	}

	@PostMapping("/list-recommend-comic")
	public PageResponse<RecommendComicDto> listComicByEditorRecommend(@RequestBody ListRecommendComicRequest request) {
		return comicsService.listRecommendComic(request);
	}

	@PostMapping("/list-sell-ranking")
	public PageResponse<WorksRankingDto> listSellRanking(@RequestBody BaseRequest request) {
		return comicsService.listSellRankingComic(request);
	}

	@PostMapping("/list-recommend-ranking")
	public PageResponse<WorksRankingDto> listRecommendRanking(@RequestBody BaseRequest request) {
		return comicsService.listRecommendRankingComic(request);
	}

	@PostMapping("/list-reward-ranking")
	public PageResponse<WorksRankingDto> listRewardRanking(@RequestBody BaseRequest request) {
		return comicsService.listRewardRankingComic(request);
	}

	@PostMapping("/list-update-ranking")
	public PageResponse<WorksRankingDto> listUpdateRanking(@RequestBody BaseRequest request) {
		return comicsService.listUpdateRankingComic(request);
	}

	@PostMapping("/list-collection-ranking")
	public PageResponse<WorksRankingDto> listCollectionRanking(@RequestBody BaseRequest request) {
		return comicsService.listCollectionRankingComic(request);
	}

	@PostMapping("/reward-ranking")
	public Response<Long> getNovelRewardRanking(@RequestBody GetComicRewardRankingRequest request) {
		return comicsService.getComicRewardRanking(request);
	}

	@PostMapping("/search")
	public PageResponse<ComicForSearch> searchComic(@RequestBody SearchComicRequest request) {
		return comicsService.searchComic(request);
	}

	@PostMapping("/list-author")
	public PageResponse<ComicDto> listAuthorComic(@RequestBody ListAuthorComicRequest request) {
		return comicsService.listAuthorComic(request);
	}

	@PostMapping("/list-deleted")
	public PageResponse<ComicDto> listDeletedComic(@RequestBody BaseRequest request) {
		return comicsService.listDeletedComic(request);
	}

	@PostMapping("/restore-deleted")
	public Response<String> restoreDeletedComic(@RequestBody RestoreDeletedComicRequest request) {
		return comicsService.restoreDeletedComic(request);
	}
}
