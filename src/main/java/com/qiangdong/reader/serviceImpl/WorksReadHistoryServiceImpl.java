package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.qiangdong.reader.dao.WorksReadHistoryMapper;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.works_history.AddOrUpdateWorksHistoryRequest;
import com.qiangdong.reader.request.works_history.ListWorksReadHistoryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IWorksReadHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-20
 */
@Service
public class WorksReadHistoryServiceImpl extends ServiceImpl<WorksReadHistoryMapper, WorksReadHistory>
	implements IWorksReadHistoryService {
	private final NovelMapper novelMapper;
	private final ComicMapper comicMapper;
	private final WorksReadHistoryMapper worksReadHistoryMapper;
	private final PageUtil pageUtil;

	public WorksReadHistoryServiceImpl(NovelMapper novelMapper, ComicMapper comicMapper,
	                                   WorksReadHistoryMapper worksReadHistoryMapper,
	                                   PageUtil pageUtil) {
		this.novelMapper = novelMapper;
		this.comicMapper = comicMapper;
		this.worksReadHistoryMapper = worksReadHistoryMapper;
		this.pageUtil = pageUtil;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<WorksReadHistory> addOrUpdateWorksReadHistory(AddOrUpdateWorksHistoryRequest request) {
		switch (request.getWorksType()) {
			case NOVEL: {
				Novel novel = novelMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(novel, "小说不存在");
				break;
			}
			case COMIC: {
				Comic comic = comicMapper.selectById(request.getWorksId());
				AssertUtil.assertNotNull(comic, "漫画不存在");
				break;
			}
			default: throw new InvalidArgumentException("无效的作品类型");
		}

		WorksReadHistory history;
		if (request.getHistoryId() == 0L) {
			history = new WorksReadHistory();
			history.setWorksId(request.getWorksId());
			history.setWorksType(request.getWorksType());
			history.setLastReadChapterIndex(request.getLastReadChapterIndex());
			history.setUserId(request.getUserId());
			save(history);
		} else {
			history = getById(request.getHistoryId());
			AssertUtil.assertNotNull(history, "阅读记录不存在");
			history.setLastReadChapterIndex(request.getLastReadChapterIndex());
			updateById(history);
		}
		return Response.ok(history);
	}

	@Override
	public PageResponse<WorksReadHistoryDto> listWorksReadHistory(ListWorksReadHistoryRequest request) {
		IPage<WorksReadHistoryDto> worksReadHistories;
		switch (request.getWorksType()) {
			case COMIC: {
				worksReadHistories = worksReadHistoryMapper.findComicHistoryDto(pageUtil.of(request), request.getUserId());
				break;
			}
			case NOVEL: {
				worksReadHistories = worksReadHistoryMapper.findNovelHistoryDto(pageUtil.of(request), request.getUserId());
				break;
			}
			default: throw new InvalidArgumentException("无效的作品类型");
		}
		return PageResponse.of(worksReadHistories, request.getPageSize());
	}
}
