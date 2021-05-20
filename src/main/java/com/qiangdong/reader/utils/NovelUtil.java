package com.qiangdong.reader.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.repository.NovelRepository;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.novel.ListRankingNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.search.NovelForSearch;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NovelUtil {
	private final RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate;
	private final NovelRepository novelRepository;

	public NovelUtil(RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate,
	                 NovelRepository novelRepository) {
		this.worksRankingDtoRedisTemplate = worksRankingDtoRedisTemplate;
		this.novelRepository = novelRepository;
	}

	/**
	 * 排行榜根据类型获取榜单数据
	 * */
	public List<WorksRankingDto> listRankingNovelByType(ListRankingNovelRequest request, String type) {
		ZSetOperations<String, WorksRankingDto> zSetOperations = worksRankingDtoRedisTemplate.opsForZSet();
		String key = String.format("%s-%s", type, request.getTypeId());
		Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, Double.MAX_VALUE);
		if (!CollectionUtils.isEmpty(worksRankingDtos)) {
			long start = (request.getPageNumber() - 1) * request.getPageSize();
			if (start >= worksRankingDtos.size()) {
				start = 0;
			}
			long end = start + request.getPageSize();

			List<WorksRankingDto> worksRankingDtoList = new ArrayList<>(worksRankingDtos);
			return CollectionUtil.sub(worksRankingDtoList, (int)start, (int)end);
		}
		return new ArrayList<>(0);
	}

	public List<RecommendNovelDto> listRecommendNovelByType(String key,
	                                                        SetOperations<String, RecommendNovelDto> setOperations) {
		Set<RecommendNovelDto> novelDtos = setOperations.members(key);
		if (!CollectionUtil.isEmpty(novelDtos)) {
			return new ArrayList<>(CollectionUtil.toCollection(novelDtos));
		}
		return new ArrayList<>();
	}

	public void indexNovel(Novel novel, User user) {
		NovelForSearch novelForSearch = new NovelForSearch();
		novelForSearch.setNovelId(novel.getId());
		novelForSearch.setName(novel.getName());
		novelForSearch.setCover(novel.getCover());
		novelForSearch.setDescription(novel.getDescription());
		novelForSearch.setAuthorName(user.getNickname());
		novelForSearch.setAvatar(user.getAvatar());
		novelForSearch.setAuthorId(user.getId());
		novelRepository.save(novelForSearch);
	}
}
