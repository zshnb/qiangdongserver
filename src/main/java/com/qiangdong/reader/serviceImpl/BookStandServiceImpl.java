package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dto.BookStandDto;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.dao.BookStandMapper;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.bookstand.AddBookStandRequest;
import com.qiangdong.reader.request.bookstand.DeleteBookStandRequest;
import com.qiangdong.reader.request.bookstand.ListBookStandByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IBookStandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 书架记录 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class BookStandServiceImpl extends ServiceImpl<BookStandMapper, BookStand> implements IBookStandService {
	private final NovelMapper novelMapper;
	private final ComicMapper comicMapper;
	private final BookStandMapper bookStandMapper;
	private final PageUtil pageUtil;

	public BookStandServiceImpl(NovelMapper novelMapper, ComicMapper comicMapper,
	                            BookStandMapper bookStandMapper, PageUtil pageUtil) {
		this.novelMapper = novelMapper;
		this.comicMapper = comicMapper;
		this.bookStandMapper = bookStandMapper;
		this.pageUtil = pageUtil;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<BookStand> addBookStand(AddBookStandRequest request) {
		switch (request.getAssociateType()) {
			case NOVEL: {
				Novel novel = novelMapper.selectById(request.getAssociateId());
				AssertUtil.assertNotNull(novel, "小说不存在");
				novel.setCollectCount(novel.getCollectCount() + 1);
				novelMapper.updateById(novel);
				break;
			}
			case COMIC: {
				Comic comic = comicMapper.selectById(request.getAssociateId());
				AssertUtil.assertNotNull(comic, "漫画不存在");
				comic.setCollectCount(comic.getClickCount() + 1);
				comicMapper.updateById(comic);
				break;
			}
			default: throw new InvalidArgumentException("无效的作品类型");
		}
		BookStand mayExistBookStand = getOne(new QueryWrapper<BookStand>()
			.eq("user_id", request.getUserId())
			.eq("associate_id", request.getAssociateId())
			.eq("associate_type", request.getAssociateType()));
		AssertUtil.assertNull(mayExistBookStand, "书架书籍已存在");
		BookStand bookStand = new BookStand();
		BeanUtils.copyProperties(request, bookStand);
		save(bookStand);
		return Response.ok(bookStand);
	}

	@Override
	public PageResponse<BookStandDto> listBookStandByType(ListBookStandByTypeRequest request) {
		switch (request.getType()) {
			case NOVEL: {
				IPage<BookStandDto> bookStands =
					bookStandMapper.findNovelBookStand(pageUtil.of(request), request.getUserId());
				return PageResponse.of(bookStands, request.getPageSize());
			}
			case COMIC: {
				IPage<BookStandDto> bookStands =
					bookStandMapper.findComicBookStand(pageUtil.of(request), request.getUserId());
				return PageResponse.of(bookStands, request.getPageSize());
			}
			default: throw new InvalidArgumentException("无效的作品类型");
		}
	}

	@Override
	public Response<String> deleteBookStand(DeleteBookStandRequest request, BookStand bookStand) {
		removeById(request.getBookStandId());
		return Response.ok();
	}
}
