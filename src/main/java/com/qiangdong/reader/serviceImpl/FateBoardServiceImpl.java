package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dao.GoodsMapper;
import com.qiangdong.reader.dao.UserGoodsMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.fate_board.FateBoardDto;
import com.qiangdong.reader.dto.fate_board.FateBoardMatchDto;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.entity.FateBoard;
import com.qiangdong.reader.dao.FateBoardMapper;
import com.qiangdong.reader.entity.Goods;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserGoods;
import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.fate_board.AddOrUpdateFateBoardRequest;
import com.qiangdong.reader.request.fate_board.MatchFateBoardRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IFateBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.utils.SensitiveWordUtil;
import com.qiangdong.reader.validate.fate_board.MatchFateBoardValidator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  缘签服务
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-29
 */
@Service
public class FateBoardServiceImpl extends ServiceImpl<FateBoardMapper, FateBoard> implements IFateBoardService {
	private final FateBoardMapper fateBoardMapper;
	private final RedisTemplate<String, Long> redisTemplate;
	private final PageUtil pageUtil;
	private final UserMapper userMapper;
	private final GoodsMapper goodsMapper;
	private final UserGoodsMapper userGoodsMapper;
	private final SensitiveWordUtil sensitiveWordUtil;

	public FateBoardServiceImpl(FateBoardMapper fateBoardMapper,
	                            RedisTemplate<String, Long> redisTemplate,
	                            PageUtil pageUtil, UserMapper userMapper,
	                            GoodsMapper goodsMapper, UserGoodsMapper userGoodsMapper,
	                            SensitiveWordUtil sensitiveWordUtil) {
		this.fateBoardMapper = fateBoardMapper;
		this.redisTemplate = redisTemplate;
		this.pageUtil = pageUtil;
		this.userMapper = userMapper;
		this.goodsMapper = goodsMapper;
		this.userGoodsMapper = userGoodsMapper;
		this.sensitiveWordUtil = sensitiveWordUtil;
	}

	/**
	 * 添加缘签
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<FateBoardDto> addOrUpdateFateBoard(AddOrUpdateFateBoardRequest request) {
	    if (sensitiveWordUtil.getBadWord(request.getContent(), SensitiveWordUtil.MatchType.MAX_MATCH_TYPE).size() > 0) {
	    	throw new InvalidArgumentException("含有敏感词");
		}
		FateBoard fateBoard;
		if (request.getFateBoardId() == 0L) {
		    // 新用户送免费的外观
			List<Goods> freeAppearance = goodsMapper.selectList(new QueryWrapper<Goods>()
				.eq("coin", 0));
			List<UserGoods> userGoodsList = freeAppearance.stream().map(g -> {
				UserGoods userGoods = new UserGoods();
				userGoods.setGoodsId(g.getId());
				userGoods.setUserId(request.getUserId());
				return userGoods;
			}).collect(Collectors.toList());
			userGoodsMapper.saveAll(userGoodsList);

			FateBoard mayExist = getOne(new QueryWrapper<FateBoard>()
				.eq("user_id", request.getUserId()));
			AssertUtil.assertNull(mayExist, "缘签已存在");
			fateBoard = new FateBoard();
			BeanUtils.copyProperties(request, fateBoard);
			save(fateBoard);
		} else {
			fateBoard = getById(request.getFateBoardId());
			AssertUtil.assertNotNull(fateBoard, "缘签不存在");
			BeanUtils.copyProperties(request, fateBoard);
			updateById(fateBoard);
		}
		Goods goods = goodsMapper.findByIdAndTypeAndUserId(request.getGoodsId(),
			GoodsTypeEnum.FATE_BOARD, request.getUserId());
		AssertUtil.assertNotNull(goods, "缘签外观不存在");

		FateBoardDto fateBoardDto = new FateBoardDto();
		BeanUtils.copyProperties(fateBoard, fateBoardDto);

		return Response.ok(fateBoardDto);
	}

	@Override
	public PageResponse<FateBoardDto> listFateBoard(BaseRequest request) {
		Set<Long> onlineUsers = redisTemplate.opsForZSet().range(UserConstant.ONLINE_USERS, 0, -1);
		if (onlineUsers.size() == 0) {
			throw new InternalException("当前没有在线用户");
		}

		IPage<FateBoardDto> fateBoardDtoIPage =
			fateBoardMapper.findByPage(pageUtil.of(request), request.getUserId(), onlineUsers);
		return PageResponse.of(fateBoardDtoIPage, request.getPageSize());
	}

	/**
	 * 缘签匹配
	 */
	@Override
    @Transactional(rollbackFor = RuntimeException.class)
	@Validation(MatchFateBoardValidator.class)
	public Response<UserIdNameDto> matchFateBoard(MatchFateBoardRequest request) {
		FateBoard fateBoard = getOne(new QueryWrapper<FateBoard>()
			.eq("user_id", request.getUserId()));
		AssertUtil.assertNotNull(fateBoard, "缘签不存在");
		fateBoard.setInterest(request.getInterest());
		updateById(fateBoard);

		List<User> users;
		if (fateBoard.getMatchSex().equals(UserSexEnum.NONE)) {
			users = userMapper.selectList(new QueryWrapper<User>()
				.eq("chat_status", UserChatStatusEnum.ONLINE));
		} else {
			users = userMapper.selectList(new QueryWrapper<User>()
                .eq("sex", fateBoard.getMatchSex())
				.eq("chat_status", UserChatStatusEnum.ONLINE));
		}
		User user = users.stream().parallel()
			.filter(item -> Objects.nonNull(redisTemplate.opsForZSet().score(UserConstant.ONLINE_USERS, item.getId())))
			.filter(item -> Objects.equals(item.getChatStatus(), UserChatStatusEnum.ONLINE))
			.findAny()
			.orElseThrow(() -> new InvalidArgumentException("暂时没有符合条件的用户"));
		UserIdNameDto userIdNameDto = new UserIdNameDto();
		userIdNameDto.setUserId(user.getId());
		userIdNameDto.setUsername(user.getUsername());
		return Response.ok(userIdNameDto);
	}

	/**
	 * 取消匹配
	 * */
	@Override
	public Response<String> cancelMatch(BaseRequest request) {
		return Response.ok();
	}

	/**
	 * 获取自己的缘签
	 * */
	@Override
	public Response<FateBoardDto> getFateBoard(BaseRequest request) {
		FateBoardDto fateBoardDto = fateBoardMapper.findByUserId(request.getUserId());
		AssertUtil.assertNotNull(fateBoardDto, "缘签不存在");
		return Response.ok(fateBoardDto);
	}

	@Override
	public Response<Integer> getOnlinePersonCount(BaseRequest request) {
		Long onlinePersonCount = redisTemplate.opsForZSet().zCard(UserConstant.ONLINE_USERS);
		return Response.ok(onlinePersonCount.intValue());
	}
}
