package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dto.SubscribeDto;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.Subscribe;
import com.qiangdong.reader.dao.SubscribeMapper;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.subscribe.GetWorksStatisticsRequest;
import com.qiangdong.reader.request.subscribe.ListWorksChapterSubscribeRequest;
import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.subscribe.WorksStatisticsResponse;
import com.qiangdong.reader.service.ISubscriptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import java.util.List;

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
public class SubscribeServiceImpl extends ServiceImpl<SubscribeMapper, Subscribe> implements ISubscriptService {
	private final NovelChapterMapper novelChapterMapper;
	private final ComicChapterMapper comicChapterMapper;
	private final NovelMapper novelMapper;
	private final ComicMapper comicMapper;
	private final SubscribeMapper subscribeMapper;
	private final NovelChapterServiceImpl novelChapterService;
	private final ComicChapterServiceImpl comicChapterService;
	private final PageUtil pageUtil;
	private final CommentServiceImpl commentService;

	public SubscribeServiceImpl(NovelChapterServiceImpl novelChapterService,
	                            NovelChapterMapper novelChapterMapper,
	                            ComicChapterMapper comicChapterMapper,
	                            NovelMapper novelMapper,
	                            ComicMapper comicMapper,
	                            SubscribeMapper subscribeMapper,
	                            ComicChapterServiceImpl comicChapterService,
	                            PageUtil pageUtil,
	                            CommentServiceImpl commentService) {
		this.novelChapterService = novelChapterService;
		this.novelChapterMapper = novelChapterMapper;
		this.comicChapterMapper = comicChapterMapper;
		this.novelMapper = novelMapper;
		this.comicMapper = comicMapper;
		this.subscribeMapper = subscribeMapper;
		this.comicChapterService = comicChapterService;
		this.pageUtil = pageUtil;
		this.commentService = commentService;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> subscribeChapter(SubscribeChapterRequest request, User user) {
		Subscribe subscribe = getOne(new QueryWrapper<Subscribe>()
			.eq("user_id", request.getUserId())
			.eq("works_id", request.getWorksId())
			.eq("associate_id", request.getChapterId())
			.eq("associate_type", request.getWorksType()));
		AssertUtil.assertNull(subscribe, "已订阅");
		switch (request.getWorksType()) {
			case COMIC: {
				ComicChapter comicChapter = comicChapterMapper.selectOne(new QueryWrapper<ComicChapter>()
					.eq("id", request.getChapterId())
					.eq("comic_id", request.getWorksId()));
				AssertUtil.assertNotNull(comicChapter, "漫画章节不存在");
				if (user.getCoin() < comicChapter.getPrice()) {
					throw new InvalidArgumentException("墙币不足");
				}
				subscribe = new Subscribe();
				subscribe.setAssociateId(comicChapter.getId());
				subscribe.setCoin(comicChapter.getPrice());
				break;
			}
			case NOVEL: {
				NovelChapter novelChapter = novelChapterMapper.selectOne(new QueryWrapper<NovelChapter>()
					.eq("id", request.getChapterId())
					.eq("novel_id", request.getWorksId()));
				AssertUtil.assertNotNull(novelChapter, "小说章节不存在");
				if (user.getCoin() < novelChapter.getPrice()) {
					throw new InvalidArgumentException("墙币不足");
				}
				subscribe = new Subscribe();
				subscribe.setAssociateId(novelChapter.getId());
				subscribe.setCoin(novelChapter.getPrice());
				break;
			}
			default: throw new InvalidArgumentException("无效的作品类型");
		}
		subscribe.setWorksId(request.getWorksId());
		subscribe.setAssociateType(request.getWorksType());
		subscribe.setUserId(request.getUserId());
		subscribe.setAuto(false);
		save(subscribe);
		return Response.ok();
	}

	@RequireAuthor
	@Override
	public WorksStatisticsResponse getWorksStatistics(GetWorksStatisticsRequest request) {
		WorksStatisticsResponse response = new WorksStatisticsResponse();
		switch (request.getWorksType()) {
			case COMIC: {
				Comic comic = comicMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(comic, "漫画不存在");
				BeanUtils.copyProperties(comic, response);
				QueryWrapper<Subscribe> queryWrapper = new QueryWrapper<Subscribe>()
					.eq("works_id", request.getWorksId())
					.eq("associate_type", request.getWorksType().code());
				List<Subscribe> subscribeList = subscribeMapper.selectList(queryWrapper);
				QueryWrapper<ComicChapter> comicChapterQuery = new QueryWrapper<ComicChapter>()
					.eq("comic_id", comic.getId());
				Integer commentCount = commentService.count(new QueryWrapper<Comment>()
					.eq("associate_id", request.getWorksId())
					.eq("type", CommentTypeEnum.COMIC));
				int chapterCount = comicChapterService.count(comicChapterQuery);
				int averageChapterSubscribe = chapterCount > 0 ? subscribeList.size() / chapterCount : 0;
				response.setAverageChapterSubscribeCount(averageChapterSubscribe);
				response.setSubscribeCount(subscribeList.size());
				response.setCommentCount(commentCount);
				break;
			}
			case NOVEL: {
				Novel novel = novelMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(novel, "小说不存在");
				BeanUtils.copyProperties(novel, response);
				QueryWrapper<Subscribe> queryWrapper = new QueryWrapper<Subscribe>()
						.eq("works_id", request.getWorksId())
						.eq("associate_type", request.getWorksType().code());
				List<Subscribe> subscribeList = subscribeMapper.selectList(queryWrapper);
				QueryWrapper<NovelChapter> novelChapterQuery = new QueryWrapper<NovelChapter>()
						.eq("novel_id", novel.getId());
				Integer commentCount = commentService.count(new QueryWrapper<Comment>()
					.eq("associate_id", request.getWorksId())
					.eq("type", CommentTypeEnum.NOVEL));
				int chapterCount = novelChapterService.count(novelChapterQuery);
				int averageChapterSubscribe = chapterCount > 0 ? subscribeList.size() / chapterCount : 0;
				response.setAverageChapterSubscribeCount(averageChapterSubscribe);
				response.setSubscribeCount(subscribeList.size());
				response.setCommentCount(commentCount);
				break;
			}
			default:
				throw new InvalidArgumentException("无效的作品类型");
		}
		Integer yesterdaySubscribeCount =
				subscribeMapper.findYesterdaySubscribeCount(request.getWorksId(), request.getWorksType().code());
		Integer maxChapterSubscribeCount =
				subscribeMapper.countMaxChapterSubscribe(request.getWorksId(), request.getWorksType().code());
		response.setYesterdaySubscribeCount(yesterdaySubscribeCount);
		response.setMaxChapterSubscribeCount(maxChapterSubscribeCount);
		return response;
	}

	@RequireAuthor
	@Override
	public PageResponse<SubscribeDto> listChapterSubscribe(ListWorksChapterSubscribeRequest request) {
		IPage<SubscribeDto> subscribeDtos;
		switch (request.getWorksType()) {
			case COMIC: {
				subscribeDtos = subscribeMapper
						.findByComicAndPostTime(pageUtil.of(request), request.getWorksId(), request.getPostTime().toString());
				break;
			}
			case NOVEL: {
				subscribeDtos = subscribeMapper
						.findByNovelAndPostTime(pageUtil.of(request), request.getWorksId(), request.getPostTime().toString());
				break;
			}
			default:
				throw new InvalidArgumentException("无效的作品类型");
		}
		return PageResponse.of(subscribeDtos, request.getPageSize());
	}
}
