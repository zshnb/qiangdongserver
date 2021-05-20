package com.qiangdong.reader.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.repository.ComicRepository;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.search.ComicForSearch;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ComicUtil {
	private final RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate;
	private final ComicRepository comicRepository;

	public ComicUtil(RedisTemplate<String, WorksRankingDto> worksRankingDtoRedisTemplate,
	                 ComicRepository comicRepository) {
		this.worksRankingDtoRedisTemplate = worksRankingDtoRedisTemplate;
		this.comicRepository = comicRepository;
	}

	/**
	 * 排行榜根据类型获取榜单数据
	 * */
	public List<WorksRankingDto> listRankingComic(BaseRequest request, String type) {
		ZSetOperations<String, WorksRankingDto> zSetOperations = worksRankingDtoRedisTemplate.opsForZSet();
		Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(type, 0, Double.MAX_VALUE);
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

	public List<RecommendComicDto> listRecommendComicByType(String key,
	                                                        SetOperations<String, RecommendComicDto> setOperations) {
		Set<RecommendComicDto> comicDtos = setOperations.members(key);
		if (!CollectionUtil.isEmpty(comicDtos)) {
			return new ArrayList<>(CollectionUtil.toCollection(comicDtos));
		}
		return new ArrayList<>();
	}

	public void indexComic(Comic comic, User user) {
		ComicForSearch comicForSearch = new ComicForSearch();
		comicForSearch.setComicId(comic.getId());
		comicForSearch.setName(comic.getName());
		comicForSearch.setCover(comic.getCover());
		comicForSearch.setDescription(comic.getDescription());
		comicForSearch.setAuthorName(user.getNickname());
		comicForSearch.setAvatar(user.getAvatar());
		comicForSearch.setAuthorId(user.getId());
		comicRepository.save(comicForSearch);
	}
}
