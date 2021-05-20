package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.SwiperMapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.dto.SwiperDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.Swiper;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.enums.swiper.LinkTypeEnum;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.swiper.AddOrUpdateSwiperRequest;
import com.qiangdong.reader.request.swiper.DeleteSwiperRequest;
import com.qiangdong.reader.request.swiper.ListSwiperRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.ISwiperService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.utils.TypeUtil;
import com.qiangdong.reader.validate.swiper.AddOrUpdateSwiperValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-11
 */
@Service
public class SwiperServiceImpl extends ServiceImpl<SwiperMapper, Swiper> implements ISwiperService {
	private final TypeUtil typeUtil;
	private final NovelMapper novelMapper;
	private final ComicMapper comicMapper;
	private final PageUtil pageUtil;
	private final TypeMapper typeMapper;

	public SwiperServiceImpl(TypeUtil typeUtil, NovelMapper novelMapper,
	                         ComicMapper comicMapper, PageUtil pageUtil,
	                         TypeMapper typeMapper) {
		this.typeUtil = typeUtil;
		this.novelMapper = novelMapper;
		this.comicMapper = comicMapper;
		this.pageUtil = pageUtil;
		this.typeMapper = typeMapper;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@Validation(AddOrUpdateSwiperValidator.class)
	@RequireAdmin
	public Response<Swiper> addOrUpdateSwiper(AddOrUpdateSwiperRequest request) {
		Swiper swiper;
		String link = "";
		if (request.getLinkType().equals(LinkTypeEnum.INNER)) {
			switch (request.getType()) {
				case NOVEL: {
					Novel novel = novelMapper.selectOne(new QueryWrapper<Novel>()
					    .select("id")
					    .eq("id", request.getItemId()));
					AssertUtil.assertNotNull(novel, "小说不存在");
					link = String.format("www.qiangdong.com/novel/%s", novel.getId());
					break;
				}
				case COMIC: {
					Comic comic = comicMapper.selectOne(new QueryWrapper<Comic>()
							.select("id")
							.eq("id", request.getItemId()));
					AssertUtil.assertNotNull(comic, "漫画不存在");
					link = String.format("www.qiangdong.com/comic/%s", comic.getId());
					break;
				}
			}
		} else {
			link = request.getLink();
		}
		if (request.getSwiperId() == 0L) {
			swiper = new Swiper();
			BeanUtils.copyProperties(request, swiper, "link");
			swiper.setLink(link);
			save(swiper);
		} else {
			swiper = getById(request.getSwiperId());
			BeanUtils.copyProperties(request, swiper, "link");
			swiper.setLink(link);
			updateById(swiper);
		}
		return Response.ok(swiper);
	}

	@Override
	@RequireAdmin
	public PageResponse<SwiperDto> listSwiperByManager(ListSwiperRequest request) {
		IPage<Swiper> swiperIPage = page(pageUtil.of(request), new QueryWrapper<Swiper>()
			.orderByDesc("create_at"));
		List<SwiperDto> swiperDtos = swiperIPage.getRecords().stream().map(it -> {
			switch (it.getType()) {
				case NOVEL: {
					SwiperDto swiperDto = new SwiperDto();
					swiperDto.setWorksId(it.getItemId());
					BeanUtils.copyProperties(it, swiperDto);
					if (it.getLinkType().equals(LinkTypeEnum.INNER)) {
						Novel novel = novelMapper.selectOne(new QueryWrapper<Novel>()
							.select("type_id, name")
							.eq("id", it.getItemId()));
						if (novel != null) {
							Type type = typeUtil.getNovelRootType(typeMapper.selectById(novel.getTypeId()));
							swiperDto.setWorksName(novel.getName());
							swiperDto.setTypeName(type.getName());
						}
					} else {
						swiperDto.setTypeName(TypeBelongEnum.NOVEL.description());
					}
					return swiperDto;
				}
				case COMIC: {
					SwiperDto swiperDto = new SwiperDto();
					swiperDto.setWorksId(it.getItemId());
					BeanUtils.copyProperties(it, swiperDto);
					if (it.getLinkType().equals(LinkTypeEnum.INNER)) {
						Comic comic = comicMapper.selectOne(new QueryWrapper<Comic>()
							.select("name, type_id")
							.eq("id", it.getItemId()));
						if (comic != null) {
							Type type = typeMapper.selectById(comic.getTypeId());
							swiperDto.setWorksName(comic.getName());
							swiperDto.setTypeName(type.getName());
						}
					} else {
						swiperDto.setTypeName(TypeBelongEnum.COMIC.description());
					}
					return swiperDto;
				}
				default: throw new InvalidArgumentException("无效的作品类型");
			}
		}).collect(Collectors.toList());

		return PageResponse.of(swiperDtos, request.getPageSize());
	}

	@Override
	public PageResponse<Swiper> listSwiper(ListSwiperRequest request) {
		IPage<Swiper> swiperIPage = page(pageUtil.of(request), new QueryWrapper<Swiper>()
			.eq("type", request.getTypeBelong()));
		return PageResponse.of(swiperIPage, request.getPageSize());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public Response<String> deleteSwiper(DeleteSwiperRequest request, Swiper swiper) {
		removeById(request.getSwiperId());
		return Response.ok();
	}
}
