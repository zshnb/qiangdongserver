package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.dto.novel.NovelAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.novel.NovelByManageTypeDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Novel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.novel.GetNovelResponse;
import com.qiangdong.reader.search.NovelForSearch;
import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface INovelService extends IService<Novel> {

	GetNovelResponse getNovel(GetNovelRequest request, Novel novel);

	PageResponse<NovelDto> listNovelByType(ListNovelByTypeRequest request);

    PageResponse<RecommendNovelDto> listRecommendNovel(ListRecommendNovelRequest request);

	Response<Novel> addOrUpdateNovel(@RequestBody AddOrUpdateNovelRequest request);

	PageResponse<NovelDto> listAuthorNovel(ListAuthorNovelRequest request);

    @Transactional(rollbackFor = RuntimeException.class)
    @RequireAuthor
    Response<String> deleteNovel(DeleteNovelRequest request);

    PageResponse<NovelForSearch> searchNovel(SearchNovelRequest request);

	PageResponse<NovelAndFirstChapterWithReviewDto> listNovelAndFirstChapterWithReview(ListNovelAndFirstChapterWithReviewRequest request);

	GetNovelResponse getNovelWithAuthor(GetNovelRequest request);

	@RequireEditor
	PageResponse<NovelByManageTypeDto> listNovelByManageType(ListNovelByManageTypeRequest request,
	                                                         User user);

	Response<Novel> updateNovelContractStatus(UpdateNovelContractStatusRequest request);

	Response<String> addRecommendNovel(AddRecommendNovelRequest request);

	Response<String> deleteRecommendNovel(DeleteRecommendNovelRequest request);

	PageResponse<WorksRankingDto> listSellRankingNovel(ListRankingNovelRequest request);

	PageResponse<WorksRankingDto> listRecommendRankingNovel(ListRankingNovelRequest request);

	PageResponse<WorksRankingDto> listRewardRankingNovel(ListRankingNovelRequest request);

	PageResponse<WorksRankingDto> listUpdateRankingNovel(ListRankingNovelRequest request);

    PageResponse<WorksRankingDto> listCollectionRankingNovel(ListRankingNovelRequest request);

    Response<Long> getNovelRewardRanking(GetNovelRewardRankingRequest request);

	PageResponse<NovelDto> listDeletedNovel(BaseRequest request);

	Response<String> restoreDeletedNovel(RestoreDeletedNovelRequest request);

	Response<WorksReadStatisticsDto> getNovelReadStatistics(GetNovelRequest request, Novel novel);

    Response<String> convertContentToOss(ConvertContentToOssRequest request, Novel novel) throws IOException;
}
