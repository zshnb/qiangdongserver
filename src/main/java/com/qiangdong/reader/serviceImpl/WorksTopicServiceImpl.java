package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.WorksTopic;
import com.qiangdong.reader.dao.WorksTopicMapper;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.works_topic.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.works_topic.GetWorksTopicResponse;
import com.qiangdong.reader.response.works_topic.ListWorksTopicWorksResponse;
import com.qiangdong.reader.service.IWorksTopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
@Service
public class WorksTopicServiceImpl extends ServiceImpl<WorksTopicMapper, WorksTopic> implements
	IWorksTopicService {

	private final TypeMapper typeMapper;
	private final WorksTopicMapper topicMapper;
	private final ComicMapper comicMapper;
	private final NovelMapper novelMapper;
	private final PageUtil pageUtil;

	public WorksTopicServiceImpl(TypeMapper typeMapper, WorksTopicMapper topicMapper,
	                             ComicMapper comicMapper, NovelMapper novelMapper,
	                             PageUtil pageUtil) {
		this.typeMapper = typeMapper;
		this.topicMapper = topicMapper;
		this.comicMapper = comicMapper;
		this.novelMapper = novelMapper;
		this.pageUtil = pageUtil;
	}

	@RequireEditor
	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<WorksTopic> addOrUpdateTopic(AddOrUpdateWorksTopicRequest request, Type type) {
		WorksTopic topic;
		if (request.getTopicId() == 0L) {
			topic = new WorksTopic();
			BeanUtils.copyProperties(request, topic);
			if (type.getBelong().code() == WorksTypeEnum.COMIC.code()) {
				topic.setWorksType(WorksTypeEnum.COMIC);
			} else {
				topic.setWorksType(WorksTypeEnum.NOVEL);
			}
			save(topic);
			switch (type.getBelong()) {
				case COMIC:
					int comicCount = comicMapper.selectCount(new QueryWrapper<Comic>()
						.in("id", request.getWorksIds()));
					if (comicCount != request.getWorksIds().size()) {
						throw new InvalidArgumentException("存在不存在的漫画");
					}
					comicMapper.update(new Comic(), new UpdateWrapper<Comic>()
						.set("topic_id", topic.getId())
						.in("id", request.getWorksIds()));
					break;
				case NOVEL:
					int novelCount = novelMapper.selectCount(new QueryWrapper<Novel>()
						.in("id", request.getWorksIds()));
					if (novelCount != request.getWorksIds().size()) {
						throw new InvalidArgumentException("存在不存在的小说");
					}
					novelMapper.update(new Novel(), new UpdateWrapper<Novel>()
						.set("topic_id", topic.getId())
						.in("id", request.getWorksIds()));
					break;
			}
		} else {
			topic = getById(request.getTopicId());
			if (topic == null) {
				throw new InvalidArgumentException("专题不存在");
			}
			BeanUtils.copyProperties(request, topic);
			updateById(topic);
		}
		return Response.ok(topic);
	}

	@RequireEditor
	@Override
	public PageResponse<WorksTopicDto> listTopic(ListWorksTopicRequest request) {
		Type type = typeMapper.selectById(request.getTypeId());
		if (type == null) {
			throw new InvalidArgumentException("分类不存在");
		}
		IPage<WorksTopicDto> data = topicMapper.findTopicByTypeId(pageUtil.of(request), request.getTypeId());
		return PageResponse.of(data, request.getPageSize());
	}

	@RequireEditor
	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<String> deleteTopic(DeleteWorksTopicRequest request) {
		WorksTopic topic = getById(request.getTopicId());
		if (topic == null) {
			throw new InvalidArgumentException("专题不存在");
		}
		removeById(request.getTopicId());
		return Response.ok();
	}

	@RequireEditor
	@Override
	public GetWorksTopicResponse getTopic(GetTopicRequest request) {
		WorksTopic topic = getById(request.getTopicId());
		if (topic == null) {
			throw new InvalidArgumentException("专题不存在");
		}
		GetWorksTopicResponse response = new GetWorksTopicResponse();
		response.getTopicDto().setTopicId(topic.getId());
		BeanUtils.copyProperties(topic, response.getTopicDto());
		if (topic.getWorksType().equals(WorksTypeEnum.COMIC)) {
			List<ComicDto> comics = comicMapper.findComicByTopicId(request.getTopicId());
			response.setComics(comics);
		} else {
			List<NovelDto> novels = novelMapper.findNovelByTopicId(request.getTopicId());
			response.setNovels(novels);
		}
		return response;
	}

	@Override
	public PageResponse<WorksTopicDto> listWorksTopic(ListWorksTopicRequest request) {
		IPage<WorksTopicDto> topicDtos =
				topicMapper.findTopicByWorksType(pageUtil.of(request), request.getWorksType().code());
		return PageResponse.of(topicDtos, request.getPageSize());
	}

	@Override
	public ListWorksTopicWorksResponse listTopicWorks(ListTopicWorksRequest request, WorksTopic worksTopic) {
		ListWorksTopicWorksResponse response = new ListWorksTopicWorksResponse();
		response.getTopicDto().setTopicId(worksTopic.getId());
		BeanUtils.copyProperties(worksTopic, response.getTopicDto());
		if (worksTopic.getWorksType().equals(WorksTypeEnum.COMIC)) {
			List<ComicDto> comics = comicMapper.findComicByWorksTopicId(request.getWorksTopicId());
			response.setComics(comics);
		} else {
			List<NovelDto> novels = novelMapper.findNovelByWorksTopicId(request.getWorksTopicId());
			response.setNovels(novels);
		}
		return response;
	}

	@RequireEditor
	@Transactional(rollbackFor = RuntimeException.class)
	@Override
	public Response<String> addTopicWorks(AddTopicWorksRequest request, WorksTopic worksTopic) {
		switch (request.getWorksType()) {
			case COMIC:
				Comic comic = comicMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(comic, "漫画不存在");
				comic.setTopicId(request.getWorksTopicId());
				comicMapper.updateById(comic);
				break;
			case NOVEL:
				Novel novel = novelMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(novel, "小说不存在");
				novel.setTopicId(request.getWorksTopicId());
				novelMapper.updateById(novel);
				break;
			default:
				throw new InvalidArgumentException("无效的作品类型");
		}
		return Response.ok();
	}

}
