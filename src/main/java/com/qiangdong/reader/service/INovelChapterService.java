package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.novel.NovelChapterCatalogItemDto;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.novel.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface INovelChapterService extends IService<NovelChapter> {

	NovelChapter getNovelChapter(GetNovelChapterRequest request);

	NovelChapter addOrUpdateNovelChapter(AddOrUpdateNovelChapterRequest request);

    Response<NovelChapter> getLatestChapter(GetLatestNovelChapterRequest request, Novel novel);

    Response<String> deleteNovelChapter(DeleteNovelChapterRequest request);

	PageResponse<NovelChapterCatalogItemDto> listNovelChapterCatalog(ListNovelChapterCatalogRequest request);

	Response<NovelChapter> updateNovelChapterReviewStatus(UpdateNovelChapterReviewStatusRequest request);

	PageResponse<NovelChapter> listNovelChapters(ListNovelChaptersRequest request);

	PageResponse<NovelChapterDto> listRecycleChapters(ListRecycleNovelChaptersRequest request);

	Response<NovelChapterDto> getRecycleChapter(GetRecycleNovelChapterRequest request);
}
