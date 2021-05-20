package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.dto.comic.ComicChapterCatalogItemDto;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.comic.GetLatestComicChapterRequest;
import com.qiangdong.reader.request.comic.GetRecycleComicChapterRequest;
import com.qiangdong.reader.request.comic.ListComicChapterCatalogRequest;
import com.qiangdong.reader.request.comic.ListComicChaptersRequest;
import com.qiangdong.reader.request.comic.ListRecycleComicChaptersRequest;
import com.qiangdong.reader.request.comic.UpdateComicChapterReviewStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.request.comic.AddOrUpdateComicChapterRequest;
import com.qiangdong.reader.request.comic.DeleteComicChapterRequest;
import com.qiangdong.reader.request.comic.GetComicChapterRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface IComicsChapterService extends IService<ComicChapter> {

	ComicChapter getComicChapter(GetComicChapterRequest request);

    Response<ComicChapter> getLatestChapter(GetLatestComicChapterRequest request, Comic comic);

    ComicChapter addOrUpdateComicChapter(AddOrUpdateComicChapterRequest request);

	Response<String> deleteComicChapter(DeleteComicChapterRequest request);

	Response<ComicChapter> updateComicChapterReviewStatus(UpdateComicChapterReviewStatusRequest request);

	PageResponse<ComicChapterCatalogItemDto> listComicChapterCatalog(ListComicChapterCatalogRequest request);

	PageResponse<ComicChapter> listComicChapters(ListComicChaptersRequest request);

	PageResponse<ComicChapterDto> listRecycleChapters(ListRecycleComicChaptersRequest request);

	Response<ComicChapterDto> getRecycleChapter(GetRecycleComicChapterRequest request);
}
