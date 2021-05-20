package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.SubscribeMapper;
import com.qiangdong.reader.dto.comic.ComicChapterCatalogItemDto;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.Subscribe;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.AddOrUpdateComicChapterRequest;
import com.qiangdong.reader.request.comic.DeleteComicChapterRequest;
import com.qiangdong.reader.request.comic.GetComicChapterRequest;
import com.qiangdong.reader.request.comic.GetLatestComicChapterRequest;
import com.qiangdong.reader.request.comic.GetRecycleComicChapterRequest;
import com.qiangdong.reader.request.comic.ListComicChapterCatalogRequest;
import com.qiangdong.reader.request.comic.ListComicChaptersRequest;
import com.qiangdong.reader.request.comic.ListRecycleComicChaptersRequest;
import com.qiangdong.reader.request.comic.UpdateComicChapterReviewStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IComicsChapterService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class ComicChapterServiceImpl extends ServiceImpl<ComicChapterMapper, ComicChapter>
	implements IComicsChapterService {

	private final ComicChapterMapper comicChapterMapper;
	private final SubscribeMapper subscribeMapper;
	private final ComicMapper comicMapper;
	private final PageUtil pageUtil;

	public ComicChapterServiceImpl(ComicChapterMapper comicChapterMapper,
	                               SubscribeMapper subscribeMapper, ComicMapper comicMapper,
	                               PageUtil pageUtil) {
		this.comicChapterMapper = comicChapterMapper;
		this.subscribeMapper = subscribeMapper;
		this.comicMapper = comicMapper;
		this.pageUtil = pageUtil;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAuthor
	public ComicChapter addOrUpdateComicChapter(AddOrUpdateComicChapterRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		if (comic == null) {
			throw new InvalidArgumentException("漫画不存在");
		}
		QueryWrapper<ComicChapter> queryWrapper = new QueryWrapper<>();
		if (request.getChapterId() == 0L) {
			queryWrapper.eq("title", request.getTitle())
				.eq("comic_id", request.getComicId());
			ComicChapter existChapter = comicChapterMapper.selectOne(queryWrapper);
			if (existChapter != null) {
				throw new InvalidArgumentException("章节标题已存在");
			}
		} else {
			ComicChapter existChapter = comicChapterMapper.selectById(request.getChapterId());
			if (existChapter == null) {
				throw new InvalidArgumentException("章节不存在");
			}
			if (!request.getTitle().equals(existChapter.getTitle())) {
				queryWrapper.eq("title", request.getTitle())
					.eq("comic_id", request.getComicId());
				ComicChapter mayExistChapter = comicChapterMapper.selectOne(queryWrapper);
				if (mayExistChapter != null) {
					throw new InvalidArgumentException("章节标题已存在");
				}
			}
		}

		ComicChapter chapter;
		if (request.getChapterId() == 0L) {
			Integer index = comicChapterMapper.findLastIndexByComicId(request.getComicId());
			if (index == null) {
				index = 0;
			}
			chapter = new ComicChapter();
			chapter.setIndex(index + 1);
			chapter.setReviewStatus(WorksChapterReviewStatusEnum.PASS);
			chapter.setPrice(0.0); // todo 等漫画计算价格规则
			// TODO: 2020/9/1 等编辑审核功能
//			if (index != 0) {
//				chapter.setReviewStatus(WorksChapterReviewStatusEnum.PASS);
//			} else {
//				chapter.setReviewStatus(WorksChapterReviewStatusEnum.PENDING);
//			}
			BeanUtils.copyProperties(request, chapter);
			save(chapter);
		} else {
			chapter = getById(request.getChapterId());
			chapter.setTitle(request.getTitle());
			chapter.setPictureUrl(request.getPictureUrl());
			chapter.setPrice(0.0);
			chapter.setAuthorWords(request.getAuthorWords());
			chapter.setPictureCount(request.getPictureCount());
			chapter.setType(request.getType());
			updateById(chapter);
		}
		comicMapper.updateById(comic);
		return chapter;
	}

	@Override
	public ComicChapter getComicChapter(GetComicChapterRequest request) {
		ComicChapter comicChapter = getOne(new QueryWrapper<ComicChapter>()
			.eq("id", request.getChapterId())
			.eq("comic_id", request.getComicId()));
		AssertUtil.assertNotNull(comicChapter, "章节不存在");

		Comic comic = comicMapper.selectById(request.getComicId());
		if (!comic.getAuthorId().equals(request.getUserId()) && comicChapter.getType().equals(WorksChapterTypeEnum.VIP)) {
			Subscribe subscribe = subscribeMapper.selectOne(new QueryWrapper<Subscribe>()
				.eq("user_id", request.getUserId())
				.eq("associate_id", request.getChapterId()));
			AssertUtil.assertNotNull(subscribe, "未订阅章节");
		}
		return comicChapter;
	}

	/**
	 * 获取最新章节
	 * */
	@Override
	public Response<ComicChapter> getLatestChapter(GetLatestComicChapterRequest request, Comic comic) {
		ComicChapter comicChapter = comicChapterMapper.selectOne(new QueryWrapper<ComicChapter>()
		.eq("comic_id", comic.getId())
		.orderByDesc("`index`")
		.last("limit 1"));
		return Response.ok(comicChapter);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAuthor
	public Response<String> deleteComicChapter(DeleteComicChapterRequest request) {
		QueryWrapper<ComicChapter> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("comic_id", request.getComicId())
			.eq("id", request.getChapterId());
		ComicChapter chapter = comicChapterMapper.selectOne(queryWrapper);
		AssertUtil.assertNotNull(chapter, "章节不存在");

		remove(queryWrapper);
		return Response.ok();
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	@RequireEditor
	public Response<ComicChapter> updateComicChapterReviewStatus(UpdateComicChapterReviewStatusRequest request) {
		ComicChapter chapter = getById(request.getChapterId());
		AssertUtil.assertNotNull(chapter, "章节不存在");
		BeanUtils.copyProperties(request, chapter);
		updateById(chapter);
		return Response.ok(chapter);
	}

	@Override
	@RequireEditor
	public PageResponse<ComicChapterCatalogItemDto> listComicChapterCatalog(ListComicChapterCatalogRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		AssertUtil.assertNotNull(comic, "漫画不存在");
		IPage<ComicChapterCatalogItemDto> catalogItems =
				comicChapterMapper.findComicChapterCatalogItemByComicId(pageUtil.of(request), request.getComicId());
		return PageResponse.of(catalogItems, request.getPageSize());
	}

	@Override
	public PageResponse<ComicChapter> listComicChapters(ListComicChaptersRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		AssertUtil.assertNotNull(comic, "漫画不存在");
		QueryWrapper<ComicChapter> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id", "title", "`index`", "type", "picture_url", "update_at", "author_words")
			.eq("comic_id", request.getComicId());
		IPage<ComicChapter> comicChapters = page(pageUtil.of(request), queryWrapper);
		return PageResponse.of(comicChapters, request.getPageSize());
	}

	@Override
    @RequireAuthor
	public PageResponse<ComicChapterDto> listRecycleChapters(ListRecycleComicChaptersRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		AssertUtil.assertNotNull(comic, "漫画不存在");
		IPage<ComicChapterDto> chapters = comicChapterMapper.findDeleteChapters(
			pageUtil.of(request), request.getComicId());
		return PageResponse.of(chapters, request.getPageSize());
	}

	@Override
	@RequireAuthor
	public Response<ComicChapterDto> getRecycleChapter(GetRecycleComicChapterRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		AssertUtil.assertNotNull(comic, "漫画不存在");

		ComicChapterDto comicChapter = comicChapterMapper.findDeleteChapterById(request.getChapterId());
		AssertUtil.assertNotNull(comicChapter, "章节不存在");
		return Response.ok(comicChapter);
	}
}
