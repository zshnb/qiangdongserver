package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.request.novel.GetNovelRequest;
import com.qiangdong.reader.request.novel.GetNovelRewardRankingRequest;
import com.qiangdong.reader.request.novel.ListAuthorNovelRequest;
import com.qiangdong.reader.request.novel.ListNovelByTypeRequest;
import com.qiangdong.reader.request.novel.ListRankingNovelRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.request.novel.SearchNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.novel.GetNovelResponse;
import com.qiangdong.reader.search.NovelForSearch;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
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
@RequestMapping("/novel")
public class NovelController {
	private final NovelServiceImpl novelService;

	public NovelController(NovelServiceImpl novelService) {
		this.novelService = novelService;
	}

	@PostMapping("/detail/{novelId}")
	public Response<GetNovelResponse> getNovel(@RequestBody GetNovelRequest request) {
		return Response.ok(novelService.getNovel(request, new Novel()));
	}

	@PostMapping("/list-type")
	public PageResponse<NovelDto> listNovelByType(@RequestBody ListNovelByTypeRequest request) {
		return novelService.listNovelByType(request);
	}

	@PostMapping("/list-recommend-novel")
	public PageResponse<RecommendNovelDto> listNovelByEditorRecommend(@RequestBody ListRecommendNovelRequest request) {
		return novelService.listRecommendNovel(request);
	}

	@PostMapping("/search")
	public PageResponse<NovelForSearch> searchNovel(@RequestBody SearchNovelRequest request) {
		return novelService.searchNovel(request);
	}

	@PostMapping("/list-author")
	public PageResponse<NovelDto> listAuthorNovel(@RequestBody ListAuthorNovelRequest request) {
		return novelService.listAuthorNovel(request);
	}

	@PostMapping("/list-sell-ranking")
	public PageResponse<WorksRankingDto> listSellRanking(@RequestBody ListRankingNovelRequest request) {
		return novelService.listSellRankingNovel(request);
	}

	@PostMapping("/list-recommend-ranking")
	public PageResponse<WorksRankingDto> listRecommendRanking(@RequestBody ListRankingNovelRequest request) {
		return novelService.listRecommendRankingNovel(request);
	}

	@PostMapping("/list-reward-ranking")
	public PageResponse<WorksRankingDto> listRewardRanking(@RequestBody ListRankingNovelRequest request) {
		return novelService.listRewardRankingNovel(request);
	}

	@PostMapping("/list-update-ranking")
	public PageResponse<WorksRankingDto> listUpdateRanking(@RequestBody ListRankingNovelRequest request) {
		return novelService.listUpdateRankingNovel(request);
	}

	@PostMapping("/list-collection-ranking")
	public PageResponse<WorksRankingDto> listCollectionRanking(@RequestBody ListRankingNovelRequest request) {
		return novelService.listCollectionRankingNovel(request);
	}

	@PostMapping("/reward-ranking")
	public Response<Long> getNovelRewardRanking(@RequestBody GetNovelRewardRankingRequest request) {
		return novelService.getNovelRewardRanking(request);
	}
}
