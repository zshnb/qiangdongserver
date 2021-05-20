package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.DraftMapper;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.Draft;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.draft.AddOrUpdateDraftRequest;
import com.qiangdong.reader.request.draft.DeleteDraftRequest;
import com.qiangdong.reader.request.draft.ListWorksDraftRequest;
import com.qiangdong.reader.request.draft.PublishDraftRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IDraftService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.NovelChapterUtil;
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
 * @since 2020-07-04
 */
@Service
public class DraftServiceImpl extends ServiceImpl<DraftMapper, Draft> implements IDraftService {
	private final ComicMapper comicMapper;
	private final NovelMapper novelMapper;
	private final NovelChapterMapper novelChapterMapper;
	private final ComicChapterMapper comicChapterMapper;
	private final PageUtil pageUtil;
	private final NovelChapterUtil novelChapterUtil;

	public DraftServiceImpl(ComicMapper comicMapper, NovelMapper novelMapper,
	                        NovelChapterMapper novelChapterMapper,
	                        ComicChapterMapper comicChapterMapper, PageUtil pageUtil,
	                        NovelChapterUtil novelChapterUtil) {
		this.comicMapper = comicMapper;
		this.novelMapper = novelMapper;
		this.novelChapterMapper = novelChapterMapper;
		this.comicChapterMapper = comicChapterMapper;
		this.pageUtil = pageUtil;
		this.novelChapterUtil = novelChapterUtil;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Draft> addOrUpdateDraft(AddOrUpdateDraftRequest request) {
		Draft draft;
		switch (request.getType()) {
			case COMIC: {
				Comic comic = comicMapper.selectById(request.getWorksId());
				if (comic == null) {
					throw new InvalidArgumentException("漫画不存在");
				}
				if (request.getDraftId() == 0L) {
					draft = new Draft();
					BeanUtils.copyProperties(request, draft);
					save(draft);
				} else {
					draft = getById(request.getDraftId());
					if (draft == null) {
						throw new InvalidArgumentException("草稿不存在");
					}
					if (!draft.getType().equals(request.getType())) {
						throw new InvalidArgumentException("作品类型错误");
					}
					draft.setPictureUrl(request.getPictureUrl());
					updateById(draft);
				}
				break;
			}
			case NOVEL: {
				Novel novel = novelMapper.selectById(request.getWorksId());
				if (novel == null) {
					throw new InvalidArgumentException("小说不存在");
				}
				if (request.getDraftId() == 0L) {
					draft = new Draft();
					BeanUtils.copyProperties(request, draft);
					save(draft);
				} else {
					draft = getById(request.getDraftId());
					if (draft == null) {
						throw new InvalidArgumentException("草稿不存在");
					}
					if (!draft.getType().equals(request.getType())) {
						throw new InvalidArgumentException("作品类型错误");
					}
					draft.setTxtUrl(request.getTxtUrl());
					updateById(draft);
				}
				break;
			}
			default: {
				throw new InvalidArgumentException("无效的作品类型");
			}
		}
		return Response.ok(draft);
	}

	@Override
	public PageResponse<Draft> listWorksDraft(ListWorksDraftRequest request) {
		switch (request.getWorksType()) {
			case NOVEL: {
				Novel novel = novelMapper.selectById(request.getWorksId());
				if (novel == null) {
					throw new InvalidArgumentException("小说不存在");
				}
				break;
			}
			case COMIC: {
				Comic comic = comicMapper.selectById(request.getWorksId());
				if (comic == null) {
					throw new InvalidArgumentException("漫画不存在");
				}
				break;
			}
			default: {
				throw new InvalidArgumentException("无效的作品类型");
			}
		}
		IPage<Draft> drafts = page(pageUtil.of(request), new QueryWrapper<Draft>()
			.eq("works_id", request.getWorksId())
			.eq("type", request.getWorksType().code())
			.eq("user_id", request.getUserId()));
		return PageResponse.of(drafts, request.getPageSize());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> publishDraft(PublishDraftRequest request) {
		Draft draft = getById(request.getDraftId());
		AssertUtil.assertNotNull(draft, "草稿不存在");
		switch (draft.getType()) {
			case COMIC: {
				ComicChapter sameTitle = comicChapterMapper.selectOne(new QueryWrapper<ComicChapter>()
					.eq("title", draft.getTitle())
					.eq("comic_id", draft.getWorksId()));
				AssertUtil.assertNull(sameTitle, "章节标题已存在");
				ComicChapter comicChapter = new ComicChapter();
				BeanUtils.copyProperties(draft, comicChapter, "id");
				comicChapter.setReviewStatus(WorksChapterReviewStatusEnum.PENDING);
				comicChapter.setProgress(0.0);
				comicChapter.setType(request.getWorksChapterType());
				comicChapter.setComicId(draft.getWorksId());
				comicChapter.setPictureCount(0);
				comicChapter.setPrice(0.0);
				comicChapter.setAuthorWords(draft.getAuthorWords());

				Integer lastIndex = comicChapterMapper.findLastIndexByComicId(draft.getWorksId());
				if (lastIndex == null) {
					lastIndex = 0;
				}
				comicChapter.setIndex(lastIndex + 1);
				comicChapterMapper.insert(comicChapter);
				break;
			}
			case NOVEL: {
				NovelChapter sameTitle = novelChapterMapper.selectOne(new QueryWrapper<NovelChapter>()
					.eq("title", draft.getTitle())
					.eq("novel_id", draft.getWorksId()));
				AssertUtil.assertNull(sameTitle, "章节标题已存在");
				NovelChapter novelChapter = new NovelChapter();
				BeanUtils.copyProperties(draft, novelChapter, "id");
				novelChapter.setReviewStatus(WorksChapterReviewStatusEnum.PENDING);
				novelChapter.setProgress(0.0);
				novelChapter.setType(request.getWorksChapterType());
				novelChapter.setNovelId(draft.getWorksId());
				novelChapter.setPrice(novelChapterUtil.calculatePrice(novelChapter.getWordCount()));
				novelChapter.setWordCount(draft.getWordCount());
				novelChapter.setAuthorWords(draft.getAuthorWords());

				Integer lastIndex = novelChapterMapper.findLastIndexByNovelId(draft.getWorksId());
				if (lastIndex == null) {
					lastIndex = 0;
				}
				novelChapter.setIndex(lastIndex + 1);
				novelChapterMapper.insert(novelChapter);
				break;
			}
		}
		removeById(request.getDraftId());
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> deleteDraft(DeleteDraftRequest request) {
		Draft draft = getById(request.getDraftId());
		AssertUtil.assertNotNull(draft, "草稿不存在");
		removeById(request.getDraftId());
		return Response.ok();
	}
}
