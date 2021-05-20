package com.qiangdong.reader.serviceImpl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.common.ComicConstant;
import com.qiangdong.reader.dao.BookStandMapper;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dao.WorksReadHistoryMapper;
import com.qiangdong.reader.dao.WorksTagMapper;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.comic.ComicAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.comic.ComicByManageTypeDto;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.dto.statistics.ReadUserAgeWithCount;
import com.qiangdong.reader.dto.statistics.ReadUserSexWithCount;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.qiangdong.reader.entity.WorksTag;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
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
import com.qiangdong.reader.service.IComicsService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.ComicUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class ComicServiceImpl extends ServiceImpl<ComicMapper, Comic> implements IComicsService {

	private final UserMapper userMapper;
	private final TypeMapper typeMapper;
	private final ComicMapper comicMapper;
	private final ComicChapterMapper comicChapterMapper;
	private final BookStandMapper bookStandMapper;
	private final PageUtil pageUtil;
	private final ComicUtil comicUtil;
	private final RedisTemplate<String, RecommendComicDto> redisTemplate;
	private final RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate;
	private final WorksTagMapper worksTagMapper;
	private final WorksReadHistoryMapper worksReadHistoryMapper;
	private final ElasticsearchTemplate elasticsearchTemplate;

	public ComicServiceImpl(UserMapper userMapper,
	                        TypeMapper typeMapper,
	                        ComicMapper comicMapper,
	                        ComicChapterMapper comicChapterMapper,
	                        BookStandMapper bookStandMapper,
	                        PageUtil pageUtil,
	                        ComicUtil comicUtil,
	                        RedisTemplate<String, RecommendComicDto> redisTemplate,
	                        RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate,
	                        WorksTagMapper worksTagMapper,
	                        WorksReadHistoryMapper worksReadHistoryMapper,
	                        ElasticsearchTemplate elasticsearchTemplate) {
		this.userMapper = userMapper;
		this.typeMapper = typeMapper;
		this.comicMapper = comicMapper;
		this.comicChapterMapper = comicChapterMapper;
		this.bookStandMapper = bookStandMapper;
		this.pageUtil = pageUtil;
		this.comicUtil = comicUtil;
		this.redisTemplate = redisTemplate;
		this.worksRankingDtoRedisTemplate = worksRankingDtoRedisTemplate;
		this.worksTagMapper = worksTagMapper;
		this.worksReadHistoryMapper = worksReadHistoryMapper;
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Comic> addOrUpdateComic(AddOrUpdateComicRequest request) {
		QueryWrapper<Comic> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", request.getName());
		if (request.getComicId() == 0L) {
			Comic comic = comicMapper.selectOne(queryWrapper);
			if (comic != null) {
				throw new InvalidArgumentException("漫画已存在");
			}
		} else {
			Comic comic = comicMapper.selectById(request.getComicId());
			if (comic == null) {
				throw new InvalidArgumentException("漫画不存在");
			}
			if (!comic.getName().equals(request.getName())) {
				Comic mayExistComic = comicMapper.selectOne(queryWrapper);
				if (mayExistComic != null) {
					throw new InvalidArgumentException("漫画已存在");
				}
			}
			if (!comic.getCreateAt().equals(comic.getUpdateAt()) &&
				Duration.between(comic.getUpdateAt(), LocalDateTime.now()).toDays() <= 30) {
				throw new InvalidArgumentException("30天内只允许修改一次漫画信息");
			}
		}

		Comic comic;
		if (request.getComicId() == 0L) {
			comic = new Comic();
			BeanUtils.copyProperties(request, comic);
			save(comic);
			if (!request.getTags().isEmpty()) {
				List<WorksTag> tags = request.getTags().stream().map(tag -> {
					WorksTag entity = new WorksTag();
					BeanUtils.copyProperties(tag, entity);
					entity.setWorksId(comic.getId());
					entity.setWorksType(WorksTypeEnum.COMIC);
					entity.setUpdateAt(LocalDateTime.now());
					return entity;
				}).collect(Collectors.toList());
				worksTagMapper.saveAll(tags);
			}
		} else {
			comic = getById(request.getComicId());
			comic.setUpdateAt(LocalDateTime.now());
			BeanUtils.copyProperties(request, comic);
			updateById(comic);
		}

		User user = userMapper.selectById(request.getUserId());
		comicUtil.indexComic(comic, user);
		return Response.ok(comic);
	}

	/**
	 * 获取漫画详情
	 * */
	@Override
	public GetComicResponse getComic(GetComicRequest request, Comic comic) {
		ComicDto comicDto = new ComicDto();
		comicDto.setComicId(comic.getId());
		BeanUtils.copyProperties(comic, comicDto);

		User user = userMapper.selectById(comic.getAuthorId());
		comicDto.setAuthorName(user.getUsername());
		comicDto.setAvatar(user.getAvatar());

		Type type = typeMapper.selectById(comic.getTypeId());
		comicDto.setTypeName(type.getName());

		WorksReadHistory worksReadHistory = worksReadHistoryMapper.selectOne(new QueryWrapper<WorksReadHistory>()
			.eq("user_id", request.getUserId())
			.eq("works_id", comic.getId())
			.eq("works_type", WorksTypeEnum.COMIC));
		if (worksReadHistory != null) {
			comicDto.setLastReadChapterIndex(worksReadHistory.getLastReadChapterIndex());
			worksReadHistory.setUpdateAt(LocalDateTime.now());
			worksReadHistoryMapper.updateById(worksReadHistory);
		} else {
			worksReadHistory = new WorksReadHistory();
			worksReadHistory.setUserId(request.getUserId());
			worksReadHistory.setWorksId(comic.getId());
			worksReadHistory.setWorksType(WorksTypeEnum.COMIC);
			worksReadHistory.setLastReadChapterIndex(1);
			worksReadHistoryMapper.insert(worksReadHistory);
		}

		ComicChapterDto lastUpdateChapter = comicChapterMapper.findLastUpdateChapter(comic.getId());

		boolean isInBookStand = false;
		BookStand bookStand = bookStandMapper.selectOne(new QueryWrapper<BookStand>()
			.select("id")
			.eq("associate_id", request.getComicId())
			.eq("associate_type", WorksTypeEnum.COMIC)
			.eq("user_id", request.getUserId()));
		if (bookStand != null) {
			isInBookStand = true;
		}

		comic.setClickCount(comic.getClickCount() + 1);
		updateById(comic);

		GetComicResponse response = new GetComicResponse();
		response.setInBookStand(isInBookStand);
		response.setComic(comicDto);
		response.setLastUpdateChapter(lastUpdateChapter);
		return response;
	}

	/**
	 * 查看分类下漫画
	 * */
	@Override
	public PageResponse<ComicDto> listComicByType(ListComicByTypeRequest request) {
		IPage<ComicDto> comics =
			comicMapper.findByTypeId(pageUtil.of(request), request.getTypeId());
		return PageResponse.of(comics, request.getPageSize());
	}

	/**
	* 列出作者所有漫画，app和后台都有
	* */
	@Override
	public PageResponse<ComicDto> listAuthorComic(ListAuthorComicRequest request) {
		IPage<ComicDto> comics;
		if (request.getAuthorId() == 0L) {
			// 后台不过滤未审核的小说，且需要最近更新章节
			comics = comicMapper.findByUserId(pageUtil.of(request), request.getUserId(), false);
			comics.getRecords().forEach(it -> {
				it.setLastUpdateChapter(comicChapterMapper.findLastUpdateChapter(it.getComicId()));
			});
		} else {
			// app需要过滤未审核的小说
			comics = comicMapper.findByUserId(pageUtil.of(request), request.getAuthorId(), true);
		}

		return PageResponse.of(comics, request.getPageSize());
	}

	/**
	 * 作者删除漫画
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
    @RequireAuthor
	public Response<String> deleteComic(DeleteComicRequest request) {
		Comic comic = comicMapper.selectOne(new QueryWrapper<Comic>()
			.select("id")
			.eq("id", request.getComicId())
			.eq("author_id", request.getUserId()));
		AssertUtil.assertNotNull(comic, "漫画不存在");
		comicMapper.deleteById(request.getComicId());
		QueryWrapper<ComicChapter> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("comic_id", request.getComicId());
		comicChapterMapper.delete(queryWrapper);

		SetOperations<String, RecommendComicDto> setOperations = redisTemplate.opsForSet();
		CollectionUtil.newArrayList(RecommendTypeEnum.EDITOR_RECOMMEND, RecommendTypeEnum.WALL_RECOMMEND,
			RecommendTypeEnum.FREE, RecommendTypeEnum.NEWCOMER_RECOMMEND).forEach(t -> {
			String key = String.format("comic-%s", t.description());
			Optional<RecommendComicDto> optional = setOperations.members(key).stream()
				.filter(it -> it.getComicId().equals(request.getComicId()))
				.findFirst();
			optional.ifPresent(recommendComicDto -> setOperations.remove(key, recommendComicDto));
		});
		return Response.ok();
	}

	@Override
	public PageResponse<ComicForSearch> searchComic(SearchComicRequest request) {
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(request.getKeyword(), "name", "authorName");

		Pageable pageable = PageRequest.of((int)(request.getPageNumber() - 1), request.getPageSize().intValue());
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(queryBuilder)
			.withPageable(pageable)
			.build();
		Page<ComicForSearch> page = elasticsearchTemplate.queryForPage(searchQuery, ComicForSearch.class);
		return PageResponse.of(page.getContent(), request.getPageSize(), page.getTotalElements());
	}

	/**
	 * 作者获取漫画详情
	 * */
	@Override
	@RequireAuthor
	public GetComicResponse getComicWithAuthor(GetComicRequest request) {
		ComicDto comicDto = comicMapper.findByComicId(request.getComicId());
		AssertUtil.assertNotNull(comicDto, "漫画不存在");
		List<WorksTag> tags = worksTagMapper.selectList(new QueryWrapper<WorksTag>()
			.eq("works_id", request.getComicId())
			.eq("works_type", WorksTypeEnum.COMIC));
		ComicChapterDto lastUpdateChapter = comicChapterMapper.findLastUpdateChapter(comicDto.getComicId());
		GetComicResponse response = new GetComicResponse();
		response.setLastUpdateChapter(lastUpdateChapter);
		response.setComic(comicDto);
		response.setTags(tags);
		return response;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	@RequireEditor
	public Response<Comic> updateComicContractStatus(UpdateComicContractStatusRequest request) {
		Comic comic = getById(request.getComicId());
		if (comic == null){
			throw new InvalidArgumentException("漫画不存在");
		}
		BeanUtils.copyProperties(request,comic);
		updateById(comic);
		return Response.ok(comic);
	}

	/**
	 * 编辑列出所属分类下的漫画，要求漫画至少有一章
	 * */
	@Override
	@RequireEditor
	public PageResponse<ComicByManageTypeDto> listComicByManageType(ListComicByManageTypeRequest request, User user) {
		Type type = typeMapper.selectById(user.getTypeId());
		IPage<ComicByManageTypeDto> data = comicMapper.findComicByManageType(pageUtil.of(request),
				user.getTypeId(), request.getLevelName(), request.getSex().code());
		if (data.getRecords().size() > 0){
			List<Long> authorIds = data.getRecords().stream()
				.map(ComicByManageTypeDto::getAuthorId)
				.collect(Collectors.toList());

			Map<Long, List<User>> authors = userMapper.selectBatchIds(authorIds)
				.stream().collect(Collectors.groupingBy(User::getId));
			List<Long> comicIds = data.getRecords().stream()
				.map(ComicByManageTypeDto::getComicId)
				.collect(Collectors.toList());
			Map<Long, List<ComicChapter>> chapters = comicChapterMapper.selectList(new QueryWrapper<ComicChapter>()
				.in("comic_id", comicIds)
				.orderByDesc("`index`")
				.groupBy("comic_id")
				.having("count(*) > 0"))
				.stream()
				.collect(Collectors.groupingBy(ComicChapter::getComicId));
			for (ComicByManageTypeDto dto : data.getRecords()) {
				dto.setTypeName(type.getName());
				User author = authors.get(dto.getAuthorId()).get(0);
				BeanUtils.copyProperties(author, dto);
				ComicChapter chapter = chapters.get(dto.getComicId()).get(0);
				dto.setChapterId(chapter.getId());
				dto.setTitle(chapter.getTitle());
				dto.setIndex(chapter.getIndex());
			}
		}
		return PageResponse.of(data, request.getPageSize());
	}

	/**
	 * 首页4个分栏下的书籍
	 * */
	@Override
	public PageResponse<RecommendComicDto> listRecommendComic(ListRecommendComicRequest request) {
		SetOperations<String, RecommendComicDto> setOperations = redisTemplate.opsForSet();
		List<RecommendComicDto> comicDtos;
		if (request.getRecommendType().equals(RecommendTypeEnum.NONE)) {
			comicDtos = CollectionUtil.newArrayList(RecommendTypeEnum.EDITOR_RECOMMEND,
				RecommendTypeEnum.WALL_RECOMMEND, RecommendTypeEnum.NEWCOMER_RECOMMEND,
				RecommendTypeEnum.FREE, RecommendTypeEnum.ENDED).stream().map(type -> {
				String key = String.format("comic-%s", type.description());
				return comicUtil.listRecommendComicByType(key, setOperations);
			}).collect(Collectors.toList())
				.stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		} else {
			String key = String.format("comic-%s", request.getRecommendType().description());
			comicDtos = comicUtil.listRecommendComicByType(key, setOperations);
		}

		if (!CollectionUtils.isEmpty(comicDtos)) {
			long start = (request.getPageNumber() - 1) * request.getPageSize();
			if (start >= comicDtos.size()) {
				start = 0;
			}
			long end = start + request.getPageSize();
			return PageResponse.of(CollectionUtil.sub(comicDtos, (int)start, (int)end), request.getPageSize(),
				Integer.toUnsignedLong(comicDtos.size()));
		}
		return PageResponse.of();
	}

	/**
	 * 添加首页4个分栏下的书籍
	 * */
	@Override
	@RequireEditor
	public Response<String> addRecommendComic(AddRecommendComicRequest request) {
		if (request.getComicIds().isEmpty()) {
			return Response.ok();
		}
		String existSql = "select * from comic_chapter cc where cc.deleted = 0 and cc.comic_id = comic.id and cc.review_status = 2";
		int comicCount = comicMapper.selectCount(new QueryWrapper<Comic>()
			.in("comic.id", request.getComicIds())
		    .and(wrapper -> wrapper.exists(existSql)));
		if (comicCount != request.getComicIds().size()) {
			throw new InvalidArgumentException("存在不存在的漫画");
		}

		// 限时免费书籍新添加的结束时间要与现有的一致
		String key = String.format("comic-%s", request.getRecommendType().description());
		SetOperations<String, RecommendComicDto> setOperations = redisTemplate.opsForSet();
		if (request.getRecommendType().equals(RecommendTypeEnum.FREE)) {
			if (setOperations.size(key) != 0) {
				RecommendComicDto recommendComicDto = setOperations.members(key).iterator().next();
				if (recommendComicDto != null) {
					if (!recommendComicDto.getEndAt().isEqual(request.getEndAt())) {
						throw new InvalidArgumentException("结束时间与当前限时免费小说结束时间不一致");
					}
				}
			}
		}

		List<RecommendComicDto> recommendComicDtos = comicMapper.findRecommendNovelByIdIn(request.getComicIds());
		recommendComicDtos.forEach(it -> {
			it.setCreateAt(request.getCreateAt());
			it.setEndAt(request.getEndAt());
			it.setRecommendType(request.getRecommendType());
		});
		setOperations.add(key, ArrayUtil.toArray(recommendComicDtos, RecommendComicDto.class));
		long expire = Duration.between(Instant.now(),
			request.getEndAt().toInstant(ZoneOffset.ofHours(+8))).getSeconds();
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		return Response.ok();
	}

	/**
	 * 删除首页4个分栏下的书籍
	 * */
	@Override
	@RequireEditor
	public Response<String> deleteRecommendComic(DeleteRecommendComicRequest request) {
		RecommendComicDto recommendComicDto = comicMapper.findRecommendComicById(request.getComicId());
		AssertUtil.assertNotNull(recommendComicDto, "漫画不存在");
		recommendComicDto.setRecommendType(request.getRecommendType());
		recommendComicDto.setCreateAt(request.getCreateAt());
		recommendComicDto.setEndAt(request.getEndAt());
		String key = String.format("comic-%s", request.getRecommendType().description());
		SetOperations<String, RecommendComicDto> setOperations = redisTemplate.opsForSet();
		setOperations.remove(key, recommendComicDto);
		return Response.ok();
	}

	/**
	 * 畅销榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listSellRankingComic(BaseRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			comicUtil.listRankingComic(request, ComicConstant.KEY_TOP_SUBSCRIBE_COMIC);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 推荐榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listRecommendRankingComic(BaseRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			comicUtil.listRankingComic(request, ComicConstant.KEY_TOP_RECOMMEND_COMIC);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 打赏榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listRewardRankingComic(BaseRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			comicUtil.listRankingComic(request, ComicConstant.KEY_TOP_REWARD_COMIC);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 更新榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listUpdateRankingComic(BaseRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			comicUtil.listRankingComic(request, ComicConstant.KEY_TOP_UPDATE_COMIC);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 更新榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listCollectionRankingComic(BaseRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			comicUtil.listRankingComic(request, ComicConstant.KEY_TOP_COLLECTION_COMIC);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 获取漫画打赏榜名次
	 * */
	@Override
	public Response<Long> getComicRewardRanking(GetComicRewardRankingRequest request) {
		ComicDto comicDto = comicMapper.findByComicId(request.getComicId());
		AssertUtil.assertNotNull(comicDto, "漫画不存在");
		WorksRankingDto worksRankingDto = new WorksRankingDto();
		BeanUtils.copyProperties(comicDto, worksRankingDto);
		ZSetOperations<String, WorksRankingDto> zSetOperations = worksRankingDtoRedisTemplate.opsForZSet();
		Long ranking = zSetOperations.reverseRank(ComicConstant.KEY_TOP_REWARD_COMIC, worksRankingDto);
		if (ranking != null) {
			return Response.ok(ranking);
		}
		return Response.ok(-1L);
	}

	@Override
	public PageResponse<ComicAndFirstChapterWithReviewDto> listComicAndFirstChapterWithReview(
			ListComicAndFirstChapterWithReviewRequest request) {
		IPage<ComicAndFirstChapterWithReviewDto> comicDto =
				comicMapper.findComicAndFirstChapter(pageUtil.of(request));
		return PageResponse.of(comicDto, request.getPageSize());
	}

	/**
	 * 查看回收站里的漫画
	 * */
	@Override
	@RequireAuthor
	public PageResponse<ComicDto> listDeletedComic(BaseRequest request) {
		IPage<ComicDto> comicDtoIPage = comicMapper.findDeletedComics(pageUtil.of(request));
		return PageResponse.of(comicDtoIPage, request.getPageSize());
	}

	/**
	 * 回复回收站漫画
	 * */
	@Override
	@RequireAuthor
	public Response<String> restoreDeletedComic(RestoreDeletedComicRequest request) {
		Comic comic = comicMapper.findDeletedById(request.getComicId());
		AssertUtil.assertNotNull(comic, "回收站不存在该漫画");
		comicMapper.restoreComic(request.getComicId());
		return Response.ok();
	}

	/**
	 * 漫画阅读数据统计
	 * */
	@Override
	@RequireAuthor
	public Response<WorksReadStatisticsDto> getComicReadStatistics(GetComicRequest request, Comic comic) {
		Integer readUserCount =
			worksReadHistoryMapper.selectCount(new QueryWrapper<WorksReadHistory>()
				.eq("works_id", request.getComicId())
				.eq("works_type", WorksTypeEnum.COMIC));

		List<ReadUserSexWithCount> readUserSexWithCounts =
			worksReadHistoryMapper.findReadUserSexWithCount(request.getComicId(), WorksTypeEnum.COMIC);
		List<ReadUserAgeWithCount> readUserAgeWithCounts =
			worksReadHistoryMapper.findReadUserAgeWithCount(request.getComicId(), WorksTypeEnum.COMIC);

		WorksReadStatisticsDto worksReadStatisticsDto = new WorksReadStatisticsDto();
		worksReadStatisticsDto.setReadUserCount(readUserCount);
		worksReadStatisticsDto.setReadUserSexWithCounts(readUserSexWithCounts);
		worksReadStatisticsDto.setReadUserAgeWithCounts(readUserAgeWithCounts);
		return Response.ok(worksReadStatisticsDto);
	}
}
