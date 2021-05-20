package com.qiangdong.reader.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.comic.ComicAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.comic.ComicByManageTypeDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comic.AddOrUpdateComicRequest;
import com.qiangdong.reader.request.comic.AddRecommendComicRequest;
import com.qiangdong.reader.request.comic.DeleteComicRequest;
import com.qiangdong.reader.request.comic.DeleteRecommendComicRequest;
import com.qiangdong.reader.request.comic.GetComicRequest;
import com.qiangdong.reader.request.comic.GetComicRewardRankingRequest;
import com.qiangdong.reader.request.comic.ListAuthorComicRequest;
import com.qiangdong.reader.request.comic.ListComicAndFirstChapterWithReviewRequest;
import com.qiangdong.reader.request.comic.ListComicByManageTypeRequest;
import com.qiangdong.reader.request.comic.ListComicByTypeRequest;
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.request.comic.RestoreDeletedComicRequest;
import com.qiangdong.reader.request.comic.SearchComicRequest;
import com.qiangdong.reader.request.comic.UpdateComicContractStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.comic.GetComicResponse;
import com.qiangdong.reader.search.ComicForSearch;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface IComicsService extends IService<Comic> {

	GetComicResponse getComic(GetComicRequest request, Comic comic);

	PageResponse<ComicDto> listComicByType(ListComicByTypeRequest request);

	@RequireEditor
	PageResponse<ComicByManageTypeDto> listComicByManageType(ListComicByManageTypeRequest request, User user);

	Response<Comic> addOrUpdateComic(AddOrUpdateComicRequest request);

	PageResponse<ComicDto> listAuthorComic(ListAuthorComicRequest request);

	Response<String> deleteComic(DeleteComicRequest request);

	PageResponse<ComicForSearch> searchComic(SearchComicRequest request);

	GetComicResponse getComicWithAuthor(GetComicRequest request);

	Response<Comic> updateComicContractStatus(UpdateComicContractStatusRequest request);

	PageResponse<RecommendComicDto> listRecommendComic(ListRecommendComicRequest request);

	Response<String> addRecommendComic(AddRecommendComicRequest request);

	Response<String> deleteRecommendComic(DeleteRecommendComicRequest request);

	PageResponse<WorksRankingDto> listSellRankingComic(BaseRequest request);

	PageResponse<WorksRankingDto> listRecommendRankingComic(BaseRequest request);

	PageResponse<WorksRankingDto> listRewardRankingComic(BaseRequest request);

	PageResponse<WorksRankingDto> listUpdateRankingComic(BaseRequest request);

    PageResponse<WorksRankingDto> listCollectionRankingComic(BaseRequest request);

    Response<Long> getComicRewardRanking(GetComicRewardRankingRequest request);

	PageResponse<ComicAndFirstChapterWithReviewDto> listComicAndFirstChapterWithReview(
			ListComicAndFirstChapterWithReviewRequest request);

	PageResponse<ComicDto> listDeletedComic(BaseRequest request);

	Response<String> restoreDeletedComic(RestoreDeletedComicRequest request);

	@RequireAuthor
	Response<WorksReadStatisticsDto> getComicReadStatistics(GetComicRequest request, Comic comic);
}
