package com.qiangdong.reader.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.dao.*;
import com.qiangdong.reader.dao.WorksTagMapper;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.dto.statistics.ReadUserAgeWithCount;
import com.qiangdong.reader.dto.statistics.ReadUserSexWithCount;
import com.qiangdong.reader.dto.statistics.WorksReadStatisticsDto;
import com.qiangdong.reader.dto.novel.NovelAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.novel.NovelByManageTypeDto;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.qiangdong.reader.entity.WorksTag;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.repository.NovelRepository;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelRequest;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.request.novel.ConvertContentToOssRequest;
import com.qiangdong.reader.request.novel.DeleteNovelRequest;
import com.qiangdong.reader.request.novel.DeleteRecommendNovelRequest;
import com.qiangdong.reader.request.novel.GetNovelRequest;
import com.qiangdong.reader.request.novel.GetNovelRewardRankingRequest;
import com.qiangdong.reader.request.novel.ListAuthorNovelRequest;
import com.qiangdong.reader.request.novel.ListNovelAndFirstChapterWithReviewRequest;
import com.qiangdong.reader.request.novel.ListNovelByManageTypeRequest;
import com.qiangdong.reader.request.novel.ListNovelByTypeRequest;
import com.qiangdong.reader.request.novel.ListRankingNovelRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.request.novel.RestoreDeletedNovelRequest;
import com.qiangdong.reader.request.novel.SearchNovelRequest;
import com.qiangdong.reader.request.novel.UpdateNovelContractStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.novel.GetNovelResponse;
import com.qiangdong.reader.search.NovelForSearch;
import com.qiangdong.reader.service.INovelService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.NovelUtil;
import com.qiangdong.reader.utils.OssUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.utils.TypeUtil;
import com.qiangdong.reader.utils.UuidUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
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
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements INovelService {
	private final NovelMapper novelMapper;
	private final UserMapper userMapper;
	private final TypeMapper typeMapper;
	private final NovelChapterMapper novelChapterMapper;
	private final BookStandMapper bookStandMapper;
	private final PageUtil pageUtil;
	private final RedisTemplate<String, RecommendNovelDto> redisTemplate;
	private final RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate;
	private final WorksTagMapper worksTagMapper;
	private final WorksReadHistoryMapper worksReadHistoryMapper;
	private final NovelUtil novelUtil;
	private final OssUtil ossUtil;
	private final TypeUtil typeUtil;
	private final ElasticsearchTemplate elasticsearchTemplate;

	public NovelServiceImpl(NovelMapper novelMapper, UserMapper userMapper,
	                        TypeMapper typeMapper, NovelChapterMapper novelChapterMapper,
	                        BookStandMapper bookStandMapper, PageUtil pageUtil,
	                        RedisTemplate<String, RecommendNovelDto> redisTemplate,
	                        RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate,
	                        WorksTagMapper worksTagMapper,
	                        WorksReadHistoryMapper worksReadHistoryMapper,
	                        NovelUtil novelUtil, OssUtil ossUtil,
	                        TypeUtil typeUtil,
	                        ElasticsearchTemplate elasticsearchTemplate) {
		this.novelMapper = novelMapper;
		this.userMapper = userMapper;
		this.typeMapper = typeMapper;
		this.novelChapterMapper = novelChapterMapper;
		this.bookStandMapper = bookStandMapper;
		this.pageUtil = pageUtil;
		this.redisTemplate = redisTemplate;
		this.worksRankingDtoRedisTemplate = worksRankingDtoRedisTemplate;
		this.worksTagMapper = worksTagMapper;
		this.worksReadHistoryMapper = worksReadHistoryMapper;
		this.novelUtil = novelUtil;
		this.ossUtil = ossUtil;
		this.typeUtil = typeUtil;
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	/**
	 * 获取小说详情
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public GetNovelResponse getNovel(GetNovelRequest request, Novel novel) {
		NovelDto novelDto = new NovelDto();
		novelDto.setNovelId(novel.getId());
		BeanUtils.copyProperties(novel, novelDto);

		User user = userMapper.selectById(novel.getAuthorId());
		novelDto.setAuthorName(user.getNickname());
		novelDto.setAvatar(user.getAvatar());

		Type type = typeMapper.selectById(novel.getTypeId());
		novelDto.setTypeName(type.getName());

		WorksReadHistory worksReadHistory = worksReadHistoryMapper.selectOne(new QueryWrapper<WorksReadHistory>()
			.eq("user_id", request.getUserId())
			.eq("works_id", novel.getId())
			.eq("works_type", WorksTypeEnum.NOVEL));
		if (worksReadHistory != null) {
			novelDto.setLastReadChapterIndex(worksReadHistory.getLastReadChapterIndex());
			worksReadHistory.setUpdateAt(LocalDateTime.now());
			worksReadHistoryMapper.updateById(worksReadHistory);
		} else {
			worksReadHistory = new WorksReadHistory();
			worksReadHistory.setUserId(request.getUserId());
			worksReadHistory.setWorksId(novel.getId());
			worksReadHistory.setWorksType(WorksTypeEnum.NOVEL);
			worksReadHistory.setLastReadChapterIndex(1);
			worksReadHistoryMapper.insert(worksReadHistory);
		}

		NovelChapterDto lastUpdateChapter = novelChapterMapper.findLastUpdateChapter(novel.getId());

		boolean isInBookStand = false;
		BookStand bookStand = bookStandMapper.selectOne(new QueryWrapper<BookStand>()
			.select("id")
			.eq("associate_id", request.getNovelId())
			.eq("associate_type", WorksTypeEnum.NOVEL)
			.eq("user_id", request.getUserId()));
		if (bookStand != null) {
			isInBookStand = true;
		}

		novel.setClickCount(novel.getClickCount() + 1);
		updateById(novel);

		GetNovelResponse response = new GetNovelResponse();
		response.setNovel(novelDto);
		response.setLastUpdateChapter(lastUpdateChapter);
		response.setInBookStand(isInBookStand);
		return response;
	}

	/**
	 * 查看分类下小说
	 * */
	@Override
	public PageResponse<NovelDto> listNovelByType(ListNovelByTypeRequest request) {
		if (request.getTypeId() == 1L || request.getTypeId() == 2L) {
			IPage<NovelDto> novels =
				novelMapper.findByParentTypeId(pageUtil.of(request), request.getTypeId(),
					request.getFilter().getMinWordCount(), request.getFilter().getMaxWordCount(),
					request.getFilter().getUpdateStatus());
			return PageResponse.of(novels, request.getPageSize());
		} else {
			IPage<NovelDto> novels =
				novelMapper.findByTypeId(pageUtil.of(request), request.getTypeId(),
					request.getFilter().getMinWordCount(), request.getFilter().getMaxWordCount(),
					request.getFilter().getUpdateStatus());
			return PageResponse.of(novels, request.getPageSize());
		}
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAuthor
	public Response<Novel> addOrUpdateNovel(AddOrUpdateNovelRequest request) {
		QueryWrapper<Novel> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", request.getName());
		if (request.getNovelId() == 0L) {
			Novel novel = novelMapper.selectOne(queryWrapper);
			if (novel != null) {
				throw new InvalidArgumentException("小说已存在");
			}
		} else {
			Novel novel = novelMapper.selectById(request.getNovelId());
			if (novel == null) {
				throw new InvalidArgumentException("小说不存在");
			}
			if (!novel.getName().equals(request.getName())) {
				Novel mayExistNovel = novelMapper.selectOne(queryWrapper);
				if (mayExistNovel != null) {
					throw new InvalidArgumentException("小说已存在");
				}
			}
			if (!novel.getCreateAt().isEqual(novel.getUpdateAt()) &&
				Duration.between(novel.getUpdateAt(), LocalDateTime.now()).toDays() <= 30) {
				throw new InvalidArgumentException("30天内只允许修改一次小说信息");
			}
		}

		Novel novel;
		if (request.getNovelId() == 0L) {
			novel = new Novel();
			BeanUtils.copyProperties(request, novel);
			save(novel);
			if (!request.getTags().isEmpty()) {
				List<WorksTag> tags = request.getTags().stream().map(tag -> {
					WorksTag entity = new WorksTag();
					BeanUtils.copyProperties(tag, entity);
					entity.setWorksId(novel.getId());
					entity.setWorksType(WorksTypeEnum.NOVEL);
					entity.setUpdateAt(LocalDateTime.now());
					return entity;
				}).collect(Collectors.toList());
				worksTagMapper.saveAll(tags);
			}
		} else {
			novel = getById(request.getNovelId());
			novel.setUpdateAt(LocalDateTime.now());
			BeanUtils.copyProperties(request, novel);
			updateById(novel);
		}
		User user = userMapper.selectById(request.getUserId());
		novelUtil.indexNovel(novel, user);
		return Response.ok(novel);
	}

	/**
	 * 列出作者所有小说，app和后台都有
	 * */
	@Override
	public PageResponse<NovelDto> listAuthorNovel(ListAuthorNovelRequest request) {
		IPage<NovelDto> novels;
		if (request.getAuthorId() == 0L) {
			// 后台不过滤未审核的小说，且需要最近更新章节
			novels = novelMapper.findByUserId(pageUtil.of(request), request.getUserId(), false);
			novels.getRecords().forEach(it -> {
				it.setLastUpdateChapter(novelChapterMapper.findLastUpdateChapter(it.getNovelId()));
			});
		} else {
			// app需要过滤未审核的小说
			novels = novelMapper.findByUserId(pageUtil.of(request), request.getAuthorId(), true);
		}
		return PageResponse.of(novels, request.getPageSize());
	}

	/**
	 * 作者删除小说
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAuthor
	public Response<String> deleteNovel(DeleteNovelRequest request) {
		Novel novel = novelMapper.selectOne(new QueryWrapper<Novel>()
			.select("id")
			.eq("id", request.getNovelId())
			.eq("author_id", request.getUserId()));
		AssertUtil.assertNotNull(novel, "小说不存在");
		novelMapper.deleteById(request.getNovelId());
		QueryWrapper<NovelChapter> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("novel_id", request.getNovelId());
		novelChapterMapper.delete(queryWrapper);

		SetOperations<String, RecommendNovelDto> setOperations = redisTemplate.opsForSet();
		CollectionUtil.newArrayList(RecommendTypeEnum.EDITOR_RECOMMEND, RecommendTypeEnum.WALL_RECOMMEND,
			RecommendTypeEnum.FREE, RecommendTypeEnum.NEWCOMER_RECOMMEND).forEach(t -> {
				String key = String.format("novel-%s", t.description());
				Optional<RecommendNovelDto> optional = setOperations.members(key).stream()
					.filter(it -> it.getNovelId().equals(request.getNovelId()))
					.findFirst();
			optional.ifPresent(recommendNovelDto -> setOperations.remove(key, recommendNovelDto));
		});
		return Response.ok();
	}

	@Override
	public PageResponse<NovelForSearch> searchNovel(SearchNovelRequest request) {
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(request.getKeyword(), "name", "authorName");

		Pageable pageable = PageRequest.of((int)(request.getPageNumber() - 1), request.getPageSize().intValue());
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(queryBuilder)
			.withPageable(pageable)
			.build();
		Page<NovelForSearch> page = elasticsearchTemplate.queryForPage(searchQuery, NovelForSearch.class);
		return PageResponse.of(page.getContent(), request.getPageSize(), page.getTotalElements());
	}

	@Override
	public PageResponse<NovelAndFirstChapterWithReviewDto> listNovelAndFirstChapterWithReview(
			ListNovelAndFirstChapterWithReviewRequest request) {
		IPage<NovelAndFirstChapterWithReviewDto> novelDto =
				novelMapper.findNovelAndFirstChapter(pageUtil.of(request));
		return PageResponse.of(novelDto, request.getPageSize());
	}

	@Override
	@RequireAuthor
	public GetNovelResponse getNovelWithAuthor(GetNovelRequest request) {
		NovelDto novelDto = novelMapper.findByNovelId(request.getNovelId());
		AssertUtil.assertNotNull(novelDto, "小说不存在");
		List<WorksTag> tags = worksTagMapper.selectList(new QueryWrapper<WorksTag>()
			.eq("works_id", request.getNovelId())
			.eq("works_type", WorksTypeEnum.NOVEL));
		NovelChapterDto lastUpdateChapter = novelChapterMapper.findLastUpdateChapter(novelDto.getNovelId());
		GetNovelResponse response = new GetNovelResponse();
		response.setLastUpdateChapter(lastUpdateChapter);
		response.setNovel(novelDto);
		response.setTags(tags);
		return response;
	}

	/**
	 * 编辑列出所属分类下的小说，要求小说至少有一章
	 * */
	@Override
	@RequireEditor
	public PageResponse<NovelByManageTypeDto> listNovelByManageType(ListNovelByManageTypeRequest request,
	                                                                User user) {
	    Type type = typeMapper.selectById(user.getTypeId());
		IPage<NovelByManageTypeDto> data = novelMapper.findNovelByManageTypeId(pageUtil.of(request),
				user.getTypeId(), request.getLevelName(), request.getSex().code());
		if (!CollectionUtils.isEmpty(data.getRecords())) {
			List<Long> authorIds = data.getRecords().stream()
				.map(NovelByManageTypeDto::getAuthorId).collect(Collectors.toList());
			Map<Long, List<User>> authors = userMapper.selectBatchIds(authorIds)
				.stream().collect(Collectors.groupingBy(User::getId));
			List<Long> novelIds = data.getRecords().stream()
				.map(NovelByManageTypeDto::getNovelId).collect(Collectors.toList());
			Map<Long, List<NovelChapter>> chapters = novelChapterMapper.selectList(new QueryWrapper<NovelChapter>()
				.in("novel_id", novelIds)
				.orderByDesc("`index`")
				.groupBy("novel_id")
				.having("count(*) > 0"))
				.stream()
				.collect(Collectors.groupingBy(NovelChapter::getNovelId));
			for (NovelByManageTypeDto dto : data.getRecords()) {
				dto.setTypeName(type.getName());
				User author = authors.get(dto.getAuthorId()).get(0);
				BeanUtils.copyProperties(author, dto);
				NovelChapter chapter = chapters.get(dto.getNovelId()).get(0);
				dto.setChapterId(chapter.getId());
				dto.setTitle(chapter.getTitle());
				dto.setIndex(chapter.getIndex());
			}
		}
		return PageResponse.of(data, request.getPageSize());
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	@RequireEditor
	public Response<Novel> updateNovelContractStatus(UpdateNovelContractStatusRequest request) {
		Novel novel = getById(request.getNovelId());
		if (novel == null) {
			throw new InvalidArgumentException("小说不存在");
		}
		novel.setContractStatus(request.getContractStatus());
		updateById(novel);
		return Response.ok(novel);
	}

	/**
	 * 添加首页4个分栏下的书籍
	 * */
	@Override
	@RequireEditor
	public Response<String> addRecommendNovel(AddRecommendNovelRequest request) {
		if (request.getNovelIds().isEmpty()) {
			return Response.ok();
		}

		String existSql = "select * from novel_chapter nc where nc.deleted = 0 and nc.novel_id = novel.id and nc.review_status = 2";
		int novelCount = novelMapper.selectCount(new QueryWrapper<Novel>()
			.in("novel.id", request.getNovelIds())
		    .and(wrapper -> wrapper.exists(existSql)));
		if (novelCount != request.getNovelIds().size()) {
			throw new InvalidArgumentException("存在不存在的小说");
		}

		// 限时免费书籍新添加的结束时间要与现有的一致
		String key = String.format("novel-%s", request.getRecommendType().description());
		SetOperations<String, RecommendNovelDto> setOperations = redisTemplate.opsForSet();
		if (request.getRecommendType().equals(RecommendTypeEnum.FREE)) {
			if (setOperations.size(key) != 0) {
				RecommendNovelDto recommendNovelDto = setOperations.members(key).iterator().next();
				if (recommendNovelDto != null) {
					if (!recommendNovelDto.getEndAt().isEqual(request.getEndAt())) {
						throw new InvalidArgumentException("结束时间与当前限时免费小说结束时间不一致");
					}
				}
			}
		}

		List<RecommendNovelDto> recommendNovelDtos = novelMapper.findRecommendNovelByIdIn(request.getNovelIds());
		recommendNovelDtos.forEach(it -> {
			it.setCreateAt(request.getCreateAt());
			it.setEndAt(request.getEndAt());
			it.setRecommendType(request.getRecommendType());
		});
		setOperations.add(key, ArrayUtil.toArray(recommendNovelDtos, RecommendNovelDto.class));
		long expire = Duration.between(Instant.now(),
			request.getEndAt().toInstant(ZoneOffset.ofHours(+8))).getSeconds();
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		return Response.ok();
	}

	/**
	 * 首页4个分栏下列小说
	 * */
	@Override
	public PageResponse<RecommendNovelDto> listRecommendNovel(ListRecommendNovelRequest request) {
		SetOperations<String, RecommendNovelDto> setOperations = redisTemplate.opsForSet();
		List<RecommendNovelDto> novelDtos;
	    if (request.getRecommendType().equals(RecommendTypeEnum.NONE)) {
			novelDtos = CollectionUtil.newArrayList(RecommendTypeEnum.EDITOR_RECOMMEND,
				RecommendTypeEnum.WALL_RECOMMEND, RecommendTypeEnum.NEWCOMER_RECOMMEND,
				RecommendTypeEnum.FREE, RecommendTypeEnum.ENDED).stream().map(type -> {
				String key = String.format("novel-%s", type.description());
				return novelUtil.listRecommendNovelByType(key, setOperations);
			}).collect(Collectors.toList())
				.stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	    } else {
		    String key = String.format("novel-%s", request.getRecommendType().description());
		    novelDtos = novelUtil.listRecommendNovelByType(key, setOperations);
	    }

	    if (!CollectionUtils.isEmpty(novelDtos)) {
		    long start = (request.getPageNumber() - 1) * request.getPageSize();
		    if (start >= novelDtos.size()) {
			    start = 0;
		    }
		    long end = start + request.getPageSize();
		    return PageResponse.of(CollectionUtil.sub(novelDtos, (int)start, (int)end), request.getPageSize(),
			    Integer.toUnsignedLong(novelDtos.size()));
	    }
	    return PageResponse.of();
	}

	/**
	 * 删除编辑推荐某个类别下的小说
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireEditor
	public Response<String> deleteRecommendNovel(DeleteRecommendNovelRequest request) {
		RecommendNovelDto recommendNovelDto = novelMapper.findRecommendNovelById(request.getNovelId());
		AssertUtil.assertNotNull(recommendNovelDto, "小说不存在");
		recommendNovelDto.setRecommendType(request.getRecommendType());
		recommendNovelDto.setCreateAt(request.getCreateAt());
		recommendNovelDto.setEndAt(request.getEndAt());
		String key = String.format("novel-%s", request.getRecommendType().description());
		SetOperations<String, RecommendNovelDto> setOperations = redisTemplate.opsForSet();
		setOperations.remove(key, recommendNovelDto);
		return Response.ok();
	}

	/**
	 * 畅销榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listSellRankingNovel(ListRankingNovelRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			novelUtil.listRankingNovelByType(request, NovelConstant.KEY_TOP_SUBSCRIBE_NOVEL);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 推荐榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listRecommendRankingNovel(ListRankingNovelRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			novelUtil.listRankingNovelByType(request, NovelConstant.KEY_TOP_RECOMMEND_NOVEL);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 打赏榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listRewardRankingNovel(ListRankingNovelRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			novelUtil.listRankingNovelByType(request, NovelConstant.KEY_TOP_REWARD_NOVEL);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 更新榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listUpdateRankingNovel(ListRankingNovelRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			novelUtil.listRankingNovelByType(request, NovelConstant.KEY_TOP_UPDATE_NOVEL);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 更新榜
	 * */
	@Override
	public PageResponse<WorksRankingDto> listCollectionRankingNovel(ListRankingNovelRequest request) {
		List<WorksRankingDto> worksRankingDtos =
			novelUtil.listRankingNovelByType(request, NovelConstant.KEY_TOP_COLLECTION_NOVEL);
		if (CollectionUtils.isEmpty(worksRankingDtos)) {
			return PageResponse.of();
		}
		return PageResponse.of(worksRankingDtos);
	}

	/**
	 * 获取小说打赏榜名次
	 * */
	@Override
	public Response<Long> getNovelRewardRanking(GetNovelRewardRankingRequest request) {
		NovelDto novelDto = novelMapper.findByNovelId(request.getNovelId());
		AssertUtil.assertNotNull(novelDto, "小说不存在");
		WorksRankingDto worksRankingDto = new WorksRankingDto();
		BeanUtils.copyProperties(novelDto, worksRankingDto);
		Type rootType = typeUtil.getNovelRootType(typeMapper.selectById(novelDto.getTypeId()));
		String key = String.format("%s-%s", NovelConstant.KEY_TOP_REWARD_NOVEL, rootType.getId());
		ZSetOperations<String, WorksRankingDto> zSetOperations = worksRankingDtoRedisTemplate.opsForZSet();
		Long ranking = zSetOperations.reverseRank(key, worksRankingDto);
		if (ranking != null) {
			return Response.ok(ranking);
		}
		return Response.ok(-1L);
	}

	/**
	 * 查看回收站里的小说
	 * */
	@Override
	@RequireAuthor
	public PageResponse<NovelDto> listDeletedNovel(BaseRequest request) {
		IPage<NovelDto> novelDtoIPage = novelMapper.findDeletedNovels(pageUtil.of(request));
		return PageResponse.of(novelDtoIPage, request.getPageSize());
	}

	/**
	 * 恢复回收站小说
	 * */
	@Override
	@RequireAuthor
	public Response<String> restoreDeletedNovel(RestoreDeletedNovelRequest request) {
		Novel novel = novelMapper.findDeletedById(request.getNovelId());
		AssertUtil.assertNotNull(novel, "回收站不存在该小说");
		novelMapper.restoreNovel(request.getNovelId());
		return Response.ok();
	}

	/**
	 * 小说阅读数据统计
	 * */
	@Override
	@RequireAuthor
	public Response<WorksReadStatisticsDto> getNovelReadStatistics(GetNovelRequest request, Novel novel) {
		Integer readUserCount =
			worksReadHistoryMapper.selectCount(new QueryWrapper<WorksReadHistory>()
				.eq("works_id", request.getNovelId())
				.eq("works_type", WorksTypeEnum.NOVEL));

		List<ReadUserSexWithCount> readUserSexWithCounts =
			worksReadHistoryMapper.findReadUserSexWithCount(request.getNovelId(), WorksTypeEnum.NOVEL);
		List<ReadUserAgeWithCount> readUserAgeWithCounts =
			worksReadHistoryMapper.findReadUserAgeWithCount(request.getNovelId(), WorksTypeEnum.NOVEL);

		WorksReadStatisticsDto worksReadStatisticsDto = new WorksReadStatisticsDto();
		worksReadStatisticsDto.setReadUserCount(readUserCount);
		worksReadStatisticsDto.setReadUserSexWithCounts(readUserSexWithCounts);
		worksReadStatisticsDto.setReadUserAgeWithCounts(readUserAgeWithCounts);
		return Response.ok(worksReadStatisticsDto);
	}

	/**
	 * 把前端参数里的文字上传到oss
	 * */
	@Override
	public Response<String> convertContentToOss(ConvertContentToOssRequest request, Novel novel) throws IOException {
		String url = ossUtil.uploadNovelChapterTxt(request.getContent(), request.getNovelId());
		return Response.ok(url);
	}
}
