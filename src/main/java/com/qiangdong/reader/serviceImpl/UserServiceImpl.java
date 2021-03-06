package com.qiangdong.reader.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.qiangdong.reader.annotation.*;
import com.qiangdong.reader.common.AdminConstant;
import com.qiangdong.reader.common.CommonConstant;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dao.*;
import com.qiangdong.reader.dto.RewardDto;
import com.qiangdong.reader.dto.SubscribeDto;
import com.qiangdong.reader.entity.*;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.response.user.GetAuthorIncomeResponse;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.enums.user.*;
import com.qiangdong.reader.search.UserForSearch;
import com.qiangdong.reader.utils.UserUtil;
import com.qiangdong.reader.utils.UuidUtil;
import com.qiangdong.reader.utils.VerifyCodeUtil;
import com.qiangdong.reader.config.JwtConfig;
import com.qiangdong.reader.dto.LastUpdateWorksSummaryDto;
import com.qiangdong.reader.dto.comic.ComicAndLastUpdateChapterDto;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.dto.novel.NovelAndLastUpdateChapterDto;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.dto.user.UserAuthorDto;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user.GetAuthorCenterResponse;
import com.qiangdong.reader.response.user.GetAuthorCompletionResponse;
import com.qiangdong.reader.service.IUserService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.JwtUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.validate.user.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final NovelMapper novelMapper;
    private final NovelChapterMapper novelChapterMapper;
    private final ComicMapper comicMapper;
    private final ComicChapterMapper comicChapterMapper;
    private final UserMapper userMapper;
    private final VerifyCodeUtil verifyCodeUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtils;
    private final FollowRelationMapper followRelationMapper;
    private final JwtConfig jwtConfig;
    private final CommentMapper commentMapper;
    private final PageUtil pageUtil;
    private final WorkDaySummaryMapper workDaySummaryMapper;
    private final TypeMapper typeMapper;
    private final Producer producer;
    private final UserConsumptionMapper userConsumptionMapper;
    private final UserUtil userUtil;
    private final SubscribeMapper subscribeMapper;
    private final RewardMapper rewardMapper;
    private final MenuMapper menuMapper;
    private final UuidUtil uuidUtil;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final UserAppPushMapper userAppPushMapper;

    public UserServiceImpl(UserMapper userMapper, NovelMapper novelMapper,
                           NovelChapterMapper novelChapterMapper,
                           RewardMapper rewardMapper, TypeMapper typeMapper,
                           ComicMapper comicMapper,
                           ComicChapterMapper comicChapterMapper,
                           VerifyCodeUtil verifyCodeUtil,
                           RedisTemplate<String, String> redisTemplate,
                           UserUtil userUtil, Producer producer,
                           JwtUtil jwtUtils,
                           FollowRelationMapper followRelationMapper,
                           PageUtil pageUtil,
                           JwtConfig jwtConfig,
                           WorkDaySummaryMapper workDaySummaryMapper,
                           UserConsumptionMapper userConsumptionMapper,
                           CommentMapper commentMapper,
                           SubscribeMapper subscribeMapper, MenuMapper menuMapper,
                           UuidUtil uuidUtil,
                           ElasticsearchTemplate elasticsearchTemplate,
                           UserAppPushMapper userAppPushMapper) {
        this.userMapper = userMapper;
        this.novelMapper = novelMapper;
        this.novelChapterMapper = novelChapterMapper;
        this.rewardMapper = rewardMapper;
        this.typeMapper = typeMapper;
        this.comicMapper = comicMapper;
        this.comicChapterMapper = comicChapterMapper;
        this.verifyCodeUtil = verifyCodeUtil;
        this.redisTemplate = redisTemplate;
        this.userUtil = userUtil;
        this.producer = producer;
        this.jwtUtils = jwtUtils;
        this.followRelationMapper = followRelationMapper;
        this.pageUtil = pageUtil;
        this.jwtConfig = jwtConfig;
        this.workDaySummaryMapper = workDaySummaryMapper;
        this.userConsumptionMapper = userConsumptionMapper;
        this.commentMapper = commentMapper;
        this.subscribeMapper = subscribeMapper;
        this.menuMapper = menuMapper;
        this.uuidUtil = uuidUtil;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.userAppPushMapper = userAppPushMapper;
    }

    @Override
    public Response<UserDto> login(UserLoginRequest request,
                                   HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
            .eq("mobile", request.getMobile())
            .in("role", UserRoleEnum.USER.getCode(), UserRoleEnum.AUTHOR.getCode(), UserRoleEnum.ADMINISTRATOR));
        AssertUtil.assertNotNull(user, "???????????????");
        if (user.getPassword().equals(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()))) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            if (StrUtil.isNotEmpty(user.getPassword())) {
                userDto.setExistPassword(true);
            }
            if (!StrUtil.isBlank(request.getClientId())) {
                UserAppPush userAppPush = userAppPushMapper.selectOne(new QueryWrapper<UserAppPush>()
                    .eq("user_id", user.getId()));
                if (userAppPush == null) {
                    userAppPush = new UserAppPush();
                    userAppPush.setClientId(request.getClientId());
                    userAppPush.setUserId(user.getId());
                    userAppPushMapper.insert(userAppPush);
                } else if (!request.getClientId().equals(userAppPush.getClientId())){
                    userAppPush.setClientId(request.getClientId());
                    userAppPushMapper.updateById(userAppPush);
                }
            }
            userDto.setAge(userUtil.getAge(user));
            userUtil.setCreatorIdentity(userDto);
            userUtil.afterLogin(user);
            String headerLoginPlatform = httpServletRequest.getHeader(JwtInterceptor.HEADER_LOGIN_PLATFORM);
            String token = jwtUtils.signJwtToken(user.getId(), headerLoginPlatform);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", JwtInterceptor.HEADER_AUTHENTICATION);
            httpServletResponse.setHeader(JwtInterceptor.HEADER_AUTHENTICATION, token);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(String.format(JwtInterceptor.KEY_JWT_TOKEN, user.getId()),
                token, jwtConfig.getExpire() * 2, TimeUnit.MILLISECONDS);
            return Response.ok(userDto);
        } else {
            throw new InvalidArgumentException("????????????");
        }
    }

    @Override
    public Response<UserDto> tokenLogin(BaseRequest request, User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userUtil.setCreatorIdentity(userDto);
        userDto.setAge(userUtil.getAge(user));
        if (StrUtil.isNotEmpty(user.getPassword())) {
            userDto.setExistPassword(true);
        }
        userUtil.afterLogin(user);
        return Response.ok(userDto);
    }

    @Validation(FastLoginValidator.class)
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserDto> fastLogin(UserFastLoginRequest request,
                                       HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
            .eq("mobile", request.getMobile())
            .in("role", UserRoleEnum.USER.getCode(), UserRoleEnum.AUTHOR.getCode(), UserRoleEnum.ADMINISTRATOR));

        UserDto userDto = new UserDto();
        if (user == null) {
            BeanUtils.copyProperties(request, userDto);
            user = new User();
            BeanUtils.copyProperties(userDto, user);
            user.setId(null);
            user.setUsername(String.format("qd-%s", uuidUtil.uuid().substring(0, 8)));
            user.setPassword("");
            user.setRole(UserRoleEnum.USER);
            user.setChatStatus(UserChatStatusEnum.ONLINE);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLevelId(1L);
            user.setAvatar(UserConstant.DEFAULT_AVATAR);
            save(user);
            userUtil.indexUser(user);
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            updateById(user);
        }
        userDto.setId(user.getId());
        userDto.setAge(userUtil.getAge(user));
        userUtil.setCreatorIdentity(userDto);
        userUtil.afterLogin(user);
        if (StrUtil.isNotEmpty(user.getPassword())){
            userDto.setExistPassword(true);
        }
        if (!StrUtil.isBlank(request.getClientId())) {
            UserAppPush userAppPush = userAppPushMapper.selectOne(new QueryWrapper<UserAppPush>().eq("user_id", user.getId()));
            if (userAppPush == null) {
                userAppPush = new UserAppPush();
                userAppPush.setClientId(request.getClientId());
                userAppPush.setUserId(user.getId());
                userAppPushMapper.insert(userAppPush);
            } else if (!request.getClientId().equals(userAppPush.getClientId())){
                userAppPush.setClientId(request.getClientId());
                userAppPushMapper.updateById(userAppPush);
            }
        }
        String headerLoginPlatform = httpServletRequest.getHeader(JwtInterceptor.HEADER_LOGIN_PLATFORM);
        String token = jwtUtils.signJwtToken(user.getId(), headerLoginPlatform);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", JwtInterceptor.HEADER_AUTHENTICATION);
        httpServletResponse.setHeader(JwtInterceptor.HEADER_AUTHENTICATION, token);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(String.format(JwtInterceptor.KEY_JWT_TOKEN, user.getId()),
                token, jwtConfig.getExpire() * 2, TimeUnit.MILLISECONDS);
        return Response.ok(userDto);
    }

    @Override
    public Response<UserDto> managerLogin(ManagerLoginRequest request,
                                          HttpServletRequest httpServletRequest,
                                          HttpServletResponse httpServletResponse) {
        String account = request.getAccount();
        UserDto userDto = new UserDto();
        long userId;
        if (userUtil.isValidMobile(account)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("mobile", account)
                .eq("role", UserRoleEnum.AUTHOR.code());
            User user = getOne(queryWrapper);
            AssertUtil.assertNotNull(user, "?????????????????????!");
            if (userUtil.validPassword(user.getPassword(), request.getPassword())) {
                throw new InvalidArgumentException("?????????????????????!");
            }
            BeanUtils.copyProperties(user, userDto);
            userDto.setMenuList(menuMapper.findMenuByRole(UserRoleEnum.AUTHOR.code()));
            userId = user.getId();
            userUtil.setCreatorIdentity(userDto);
            userUtil.afterLogin(user);
        } else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                    .eq("username", account);
            User admin = userMapper.selectOne(queryWrapper);
            if (admin == null) {
                throw new InvalidArgumentException("???????????????!");
            }
            if (userUtil.validPassword(admin.getPassword(), request.getPassword())) {
                throw new InvalidArgumentException("?????????????????????!");
            }
            BeanUtils.copyProperties(admin, userDto);
            if (admin.getRole().equals(UserRoleEnum.ADMINISTRATOR)) {
                userDto.setMenuList(menuMapper.selectList(new QueryWrapper<>()));
            } else {
                userDto.setMenuList(menuMapper.findMenuByRole(UserRoleEnum.EDITOR.code()));
            }
            if (admin.getRole().equals(UserRoleEnum.EDITOR)) {
                Type type = typeMapper.selectById(admin.getTypeId());
                userDto.setBelong(type.getBelong());
            }
            userId = admin.getId();
            userUtil.afterLogin(admin);
        }
        String headerLoginPlatform = httpServletRequest.getHeader(JwtInterceptor.HEADER_LOGIN_PLATFORM);
        String token = jwtUtils.signJwtToken(userId, headerLoginPlatform);
        // ?????????????????????????????????token
        httpServletResponse.setHeader("Access-Control-Expose-Headers", JwtInterceptor.HEADER_AUTHENTICATION);
        httpServletResponse.setHeader(JwtInterceptor.HEADER_AUTHENTICATION, token);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(String.format(JwtInterceptor.KEY_JWT_TOKEN, userId),
                token, jwtConfig.getExpire() * 2, TimeUnit.MILLISECONDS);
        return Response.ok(userDto);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Validation(ManagerRegisterValidator.class)
    @Override
    public Response<String> managerRegister(ManagerRegisterRequest request) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("deleted", UserDelStatusEnum.NORMAL.code())
                .eq("mobile", request.getMobile()));
        if (user != null) {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            User entity = new User();
            BeanUtils.copyProperties(request, entity);
            entity.setId(user.getId());
            entity.setRole(UserRoleEnum.AUTHOR);
            userMapper.updateById(entity);
        } else {
            // ?????????????????????????????????????????????
            User entity = new User();
            BeanUtils.copyProperties(request, entity);
            entity.setUsername(entity.getMobile());
            entity.setRole(UserRoleEnum.AUTHOR);
            entity.setUserSignStatus(UserSignStatusEnum.UNSIGNED);
            entity.setDeleted(UserDelStatusEnum.NORMAL.code());
            entity.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
            userMapper.insert(entity);
            userUtil.indexUser(entity);
        }
        return Response.ok();
    }

    /**
     * ???????????????????????????
     * */
    @Validation(RegisterVerifyCodeValidator.class)
    @Override
    public Response<String> sendVerifyCode(SendMessageRegisterRequest request) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
            .select("id")
            .eq("mobile", request.getMobile()));
        AssertUtil.assertNull(user, "??????????????????");
        String value = redisTemplate.opsForValue().get(
            String.format(UserConstant.KEY_USER_REGISTER_VERIFY_CODE, request.getMobile()));
        if (value != null) {
            throw new InvalidArgumentException("???????????????????????????");
        }

        int code;
        try {
            code = verifyCodeUtil.sendPhoneVerifyCode(request.getMobile());
        } catch (ClientException e) {
            LOGGER.error(String.format("send code to %s failed", request.getMobile()), e);
            return Response.error("????????????");
        }
        redisTemplate.opsForValue().set(String.format(UserConstant.KEY_USER_REGISTER_VERIFY_CODE, request.getMobile()),
            String.valueOf(code), UserConstant.KEY_USER_REGISTER_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        return Response.ok();
    }

    @Override
    public Response<UserDto> getPersonalCenterInfo(GetPersonalCenterInfoRequest request) {
        User user = userMapper.selectById(request.getTargetUserId());
        AssertUtil.assertNotNull(user, "???????????????");

        boolean isFollow = false;
        if (!request.getTargetUserId().equals(request.getUserId())) {
            FollowRelation followRelation = followRelationMapper.selectOne(new QueryWrapper<FollowRelation>()
                .eq("follower_id", request.getUserId())
                .eq("followed_id", request.getTargetUserId()));
            if (followRelation != null) {
                isFollow = true;
            }
        }

        UserDto userDto = new UserDto();
        userDto.setFollow(isFollow);
        BeanUtils.copyProperties(user, userDto);
        userDto.setAge(userUtil.getAge(user));
        if (StrUtil.isNotEmpty(user.getPassword())){
            userDto.setExistPassword(true);
        }
        return Response.ok(userDto);
    }

    @Override
    public PageResponse<UserForSearch> searchUser(SearchUserRequest request) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(request.getUsername(), "username");

        Pageable pageable = PageRequest.of((int)(request.getPageNumber() - 1), request.getPageSize().intValue());
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();
        Page<UserForSearch> page = elasticsearchTemplate.queryForPage(searchQuery, UserForSearch.class);
        List<UserForSearch> userForSearches = page.getContent();

        userForSearches.forEach(it -> {
            int count = followRelationMapper.selectCount(new QueryWrapper<FollowRelation>()
                .eq("followed_id", it.getId()));
            it.setFansUserCount(count);
        });
        return PageResponse.of(userForSearches, request.getPageSize(), page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<User> becomeAuthor(BecomeAuthorRequest request) {
        User user = getById(request.getUserId());
        user.setRole(UserRoleEnum.AUTHOR);
        BeanUtils.copyProperties(request, user, "creatorIdentity");
        if (user.getCreatorIdentity() == null) {
            user.setCreatorIdentity(request.getCreatorIdentity());
        } else if (user.getCreatorIdentity().equals(UserCreatorIdentityEnum.AUTHOR) &&
            request.getCreatorIdentity().equals(UserCreatorIdentityEnum.PAINTER)) {
            user.setCreatorIdentity(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER);
        } else if (user.getCreatorIdentity().equals(UserCreatorIdentityEnum.PAINTER) &&
            request.getCreatorIdentity().equals(UserCreatorIdentityEnum.AUTHOR)) {
            user.setCreatorIdentity(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER);
        }
        updateById(user);
        return Response.ok(user);
    }

    /**
     * ?????????????????????
     * */
    @Override
    public GetAuthorCenterResponse getAuthorCenter(GetAuthorCenterRequest request) {
        User user = getById(request.getUserId());
        GetAuthorCenterResponse response = new GetAuthorCenterResponse();
        response.setCreateCountDay((int) Duration.between(user.getCreateAt(), LocalDateTime.now()).toDays());
        switch (request.getType()) {
            case NOVEL: {
                Novel lastUpdateNovel = novelMapper.selectOne(new QueryWrapper<Novel>()
                    .select("id", "recommend_ticket", "wall_ticket", "coin", "click_count",
                        "collect_count", "name")
                    .eq("author_id", request.getUserId())
                    .orderByDesc("update_at")
                    .last("limit 1"));
                if (lastUpdateNovel != null) {
                    List<Long> chapterIds =
                        novelChapterMapper.findChaptersByNovelId(lastUpdateNovel.getId()).stream()
                            .map(NovelChapter::getId).collect(Collectors.toList());
                    int commentCount = 0;
                    if (!CollectionUtils.isEmpty(chapterIds)) {
                        commentCount = commentMapper.findNovelChapterSummaryByNovelId(chapterIds)
                            .stream()
                            .mapToInt(NovelChapterCommentSummaryDto::getCommentCount)
                            .sum();
                    }
                    long writeWordCount = novelMapper.selectList(new QueryWrapper<Novel>()
                        .eq("author_id", request.getUserId())).stream()
                        .mapToInt(Novel::getWordCount)
                        .asLongStream()
                        .sum();
                    List<NovelAndLastUpdateChapterDto> recentUpdateNovels =
                        novelMapper.findNovelAndLastChapterByUserId(pageUtil.of(request), request.getUserId())
                            .getRecords();
                    response.setWriteCount(writeWordCount);
                    LastUpdateWorksSummaryDto dto = new LastUpdateWorksSummaryDto();
                    dto.setWorksId(lastUpdateNovel.getId());
                    dto.setCommentCount(commentCount);
                    BeanUtils.copyProperties(lastUpdateNovel, dto);
                    response.setLastUpdateWorksSummaryDto(dto);
                    response.setRecentUpdateNovels(recentUpdateNovels);
                }
                break;
            }
            case COMIC: {
                Comic lastUpdateComic = comicMapper.selectOne(new QueryWrapper<Comic>()
                    .select("id", "recommend_ticket", "wall_ticket", "coin", "click_count",
                        "collect_count", "name")
                    .eq("author_id", request.getUserId())
                    .orderByDesc("update_at")
                    .last("limit 1"));
                if (lastUpdateComic != null) {
                    List<Long> chapterIds =
                        comicChapterMapper.findChaptersByComicId(lastUpdateComic.getId()).stream()
                            .map(ComicChapter::getId).collect(Collectors.toList());
                    int commentCount = 0;
                    if (!CollectionUtils.isEmpty(chapterIds)) {
                        commentCount = commentMapper.findComicChapterSummaryByNovelId(chapterIds)
                            .stream()
                            .mapToInt(ComicChapterCommentSummaryDto::getCommentCount)
                            .sum();
                    }
                    long writeCount = comicMapper.selectList(new QueryWrapper<Comic>()
                        .eq("author_id", request.getUserId())).stream()
                        .mapToInt(Comic::getPictureCount)
                        .asLongStream()
                        .sum();
                    response.setWriteCount(writeCount);
                    List<ComicAndLastUpdateChapterDto> recentUpdateComics =
                        comicMapper.findComicAndLastChapterByUserId(pageUtil.of(request), request.getUserId())
                            .getRecords();
                    LastUpdateWorksSummaryDto dto = new LastUpdateWorksSummaryDto();
                    dto.setWorksId(lastUpdateComic.getId());
                    dto.setCommentCount(commentCount);
                    BeanUtils.copyProperties(lastUpdateComic, dto);
                    response.setLastUpdateWorksSummaryDto(dto);
                    response.setRecentUpdateComics(recentUpdateComics);
                }
                break;
            }
            default:
                throw new InvalidArgumentException("?????????????????????");
        }
        return response;
    }

    @Override
    public PageResponse<WorkDaySummary> listAuthorWorkDaySummary(BaseRequest request) {
        return PageResponse.of(workDaySummaryMapper.findCurrentMonthWorkDaySummary(request.getUserId()));
    }

    @Override
    public Response<UserDto> getAuthorDetail(BaseRequest request) {
        UserDto author = userMapper.findAuthorInfoById(request.getUserId());
        AssertUtil.assertNotNull(author, "???????????????");
        return Response.ok(author);
    }

    @Override
    @RequireEditor
    @Transactional
    public Response<String> enableAuthorAllowCharge(EnableAuthorAllowChargeRequest request) {
        User author = getById(request.getAuthorId());
        AssertUtil.assertNotNull(author, "???????????????");
        author.setAllowCharge(true);
        updateById(author);
        return Response.ok();
    }

    /**
     * ????????????
     * */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<Integer> voteRecommendTicket(VoteTicketRequest request, User user) {
        if (user.getRecommendTicket() < request.getCount()) {
            throw new InvalidArgumentException("???????????????");
        }
        UserConsumption userConsumption = new UserConsumption();
        userConsumption.setDescription("?????????");
        userConsumption.setUserId(request.getUserId());
        userConsumption.setCount(request.getCount());
        userConsumption.setType(ConsumptionTypeEnum.RECOMMEND_TICKET);
        switch (request.getWorksType()) {
            case NOVEL: {
                Novel novel = novelMapper.selectById(request.getWorksId());
                AssertUtil.assertNotNull(novel, "???????????????");
                novel.setRecommendTicket(novel.getRecommendTicket() + request.getCount());
                novelMapper.updateById(novel);
                userConsumption.setAssociateId(novel.getId());
                userConsumptionMapper.insert(userConsumption);
                break;
            }
            case COMIC: {
                Comic comic = comicMapper.selectById(request.getWorksId());
                AssertUtil.assertNotNull(comic, "???????????????");
                comic.setRecommendTicket(comic.getRecommendTicket() + request.getCount());
                comicMapper.updateById(comic);
                userConsumption.setAssociateId(comic.getId());
                userConsumptionMapper.insert(userConsumption);
                break;
            }
            default:
                throw new InvalidArgumentException("?????????????????????");
        }
        user.setRecommendTicket(user.getRecommendTicket() - request.getCount());
        updateById(user);
        return Response.ok(user.getRecommendTicket());
    }

    /**
     * ?????????
     * */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<Integer> voteWallTicket(VoteTicketRequest request, User user) {
        if (user.getWallTicket() < request.getCount()) {
            throw new InvalidArgumentException("????????????");
        }
        UserConsumption userConsumption = new UserConsumption();
        userConsumption.setDescription("??????");
        userConsumption.setUserId(request.getUserId());
        userConsumption.setCount(request.getCount());
        userConsumption.setType(ConsumptionTypeEnum.WALL_TICKET);
        switch (request.getWorksType()) {
            case NOVEL: {
                Novel novel = novelMapper.selectById(request.getWorksId());
                AssertUtil.assertNotNull(novel, "???????????????");
                novel.setWallTicket(novel.getWallTicket() + request.getCount());
                novelMapper.updateById(novel);
                userConsumption.setAssociateId(novel.getId());
                userConsumptionMapper.insert(userConsumption);
                break;
            }
            case COMIC: {
                Comic comic = comicMapper.selectById(request.getWorksId());
                AssertUtil.assertNotNull(comic, "???????????????");
                comic.setWallTicket(comic.getWallTicket() + request.getCount());
                comicMapper.updateById(comic);
                userConsumption.setAssociateId(comic.getId());
                userConsumptionMapper.insert(userConsumption);
                break;
            }
            default:
                throw new InvalidArgumentException("?????????????????????");
        }
        user.setWallTicket(user.getWallTicket() - request.getCount());
        updateById(user);
        return Response.ok(user.getWallTicket());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<User> addOrUpdateUserEditor(AddOrUpdateUserEditorRequest request) {
        Type type = typeMapper.selectById(request.getTypeId());
        AssertUtil.assertNotNull(type, "???????????????");
        User user;
        if (request.getEditorId() == 0L) {
            user = new User();
            BeanUtils.copyProperties(request, user);
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            user.setRole(UserRoleEnum.EDITOR);
            user.setDeleted(0);
            user.setChatStatus(UserChatStatusEnum.OFFLINE);
            Long incrResult;
            if (type.getBelong().equals(TypeBelongEnum.COMIC)) {
                incrResult = redisTemplate.opsForValue()
                    .increment(AdminConstant.KEY_ADMIN_STAFF_COMIC);
                user.setUsername("QD01" + incrResult);
            } else {
                incrResult = redisTemplate.opsForValue()
                    .increment(AdminConstant.KEY_ADMIN_STAFF_NOVEL);
                user.setUsername("QD02" + incrResult);
            }
            save(user);
        } else {
            user = getById(request.getEditorId());
            AssertUtil.assertNotNull(user, "?????????????????????");
            if (!request.getPassword().isEmpty()) {
                request.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
            }
            BeanUtils.copyProperties(request, user);
            updateById(user);
        }
        return Response.ok(user);
    }

    @Override
    @RequireAdmin
    public PageResponse<UserDto> listUserEditor(BaseRequest request) {
        IPage<UserDto> editors = userMapper.findUserEditor(pageUtil.of(request));
        return PageResponse.of(editors, request.getPageSize());
    }

    @RequireAdmin
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> deleteUser(DeleteUserRequest request) {
        User user = getById(request.getTargetUserId());
        AssertUtil.assertNotNull(user, "???????????????");
        removeById(request.getTargetUserId());
        return Response.ok();
    }

    @Override
    @RequireAdmin
    public Response<UserDto> getUserEditor(GetUserEditorRequest request) {
        UserDto adminStaffDto = userMapper.findUserEditorByUserId(request.getTargetUserId());
        AssertUtil.assertNotNull(adminStaffDto, "?????????????????????");
        return Response.ok(adminStaffDto);
    }

    @Override
    @RequireAdmin
    public PageResponse<UserAuthorDto> listAuthor(BaseRequest request) {
        IPage<UserAuthorDto> data = userMapper.findUserAuthor(pageUtil.of(request));
        data.getRecords().forEach(author -> {
            if (author.getCheckComic() == 1 && author.getCheckNovel() == 1) {
                author.setIdentity(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER);
            } else if (author.getCheckComic() == 1) {
                author.setIdentity(UserCreatorIdentityEnum.PAINTER);
            } else if (author.getCheckNovel() == 1) {
                author.setIdentity(UserCreatorIdentityEnum.AUTHOR);
            }
        });
        return PageResponse.of(data, request.getPageSize());
    }

    @RequireEditor
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<User> updateAuthorByEditor(UpdateAuthorByEditorRequest request) {
        User targetUser = getById(request.getTargetUserId());
        AssertUtil.assertNotNull(targetUser, "???????????????");
        if (!targetUser.getNickname().equals(request.getNickname())) {
            User maxExistUser = getOne(new QueryWrapper<User>()
                .eq("nickname", request.getNickname()));
            AssertUtil.assertNull(maxExistUser, "???????????????");
        }

        targetUser.setNickname(request.getNickname());
        targetUser.setLevelId(request.getLevelId());
        targetUser.setUserSignStatus(request.getUserSignStatus());
        updateById(targetUser);
        return Response.ok(targetUser);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> updateUserInfo(UpdateUserInfoRequest request) {
        User user = getById(request.getUserId());
        if (!user.getUsername().equals(request.getUsername())) {
            User sameUser = getOne(new QueryWrapper<User>()
                .eq("username", request.getUsername()));
            AssertUtil.assertNull(sameUser, "???????????????");
        }
        user.setBirthday(request.getBirthday().atTime(0, 0, 0 ));
        user.setUsername(request.getUsername());
        user.setAddress(request.getAddress());
        user.setSex(request.getSex());
        user.setAvatar(request.getAvatar());
        user.setSignature(request.getSignature());
        userMapper.updateById(user);
        userUtil.indexUser(user);
        return Response.ok();
    }

    @Override
    public void generateCaptcha(CaptchaLoginRequest request, HttpServletResponse response) {
        String requestUuid = request.getUuid();
        if (StrUtil.isBlank(requestUuid)) {
            throw new InvalidArgumentException("uuid????????????");
        }
        //?????????????????????
        String code = producer.createText();
        redisTemplate.opsForValue().set(UserConstant.KEY_ADMIN_LOGIN_CAPTCHA_CODE + requestUuid, code,
            UserConstant.KEY_ADMIN_LOGIN_CAPTCHA_CODE_TIMEOUT, TimeUnit.SECONDS);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //?????????????????????
        ServletOutputStream out = null;
        BufferedImage image = producer.createImage(code);
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    @Override
    public Response<String> logout(HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader(JwtInterceptor.HEADER_AUTHENTICATION);
        jwtUtils.expireJwtToken(jwtToken);
        return Response.ok();
    }

    @Override
    public Response<User> getSecurityCenter(BaseRequest request) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
            .select("id, username, mobile, email, password")
            .eq("id", request.getUserId());
        User user = getOne(queryWrapper);
        user.setMobile(userUtil.blurPhone(user.getMobile()));
        return Response.ok(user);
    }

    @Override
    public Response<String> sendSecurityPhoneVerifyCode(BaseRequest request, User user) {
        verifyCodeUtil.checkVerifyCodeExist(
                String.format(UserConstant.KEY_USER_SECURITY_VERIFY_CODE, user.getMobile()));

        int code;
        try {
            code = verifyCodeUtil.sendPhoneVerifyCode(user.getMobile());
        } catch (ClientException e) {
            LOGGER.error(String.format("send code to %s failed", user.getMobile()), e);
            return Response.error("????????????");
        }
        String key = String.format(UserConstant.KEY_USER_SECURITY_VERIFY_CODE, user.getMobile());
        redisTemplate.opsForValue().set(key, String.valueOf(code),
            UserConstant.KEY_USER_SECURITY_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        return Response.ok(String.valueOf(code));
    }

    @Validation(FastLoginVerifyCodeValidator.class)
    @Override
    public Response<String> sendFastLoginVerifyCode(SendFastLoginVerifyCodeRequest request) {
        verifyCodeUtil.checkVerifyCodeExist(
                String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, request.getMobile()));

        int code;
        try {
            code = verifyCodeUtil.sendPhoneVerifyCode(request.getMobile());
        } catch (ClientException e) {
            LOGGER.error(String.format("send code to %s failed", request.getMobile()), e);
            return Response.error("????????????");
        }
        String key = String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, request.getMobile());
        redisTemplate.opsForValue().set(key, String.valueOf(code),
                UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        return Response.ok();
    }

    @Override
    public Response<String> sendSecurityEmailVerifyCode(BaseRequest request, User user) {
        int code;
        try {
            code = verifyCodeUtil.sendEmailVerifyCode(user.getEmail());
        } catch (MessagingException e) {
            LOGGER.error(String.format("send code to %s failed", user.getEmail()), e);
            return Response.error("????????????");
        }
        String key = String.format(UserConstant.KEY_USER_SECURITY_VERIFY_CODE, user.getEmail());
        redisTemplate.opsForValue().set(key, String.valueOf(code),
            UserConstant.KEY_USER_SECURITY_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        return Response.ok(String.valueOf(code));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> updatePassword(UpdateUserPasswordRequest request, User user) {
        if (!request.getOldPassword().isEmpty() && !StringUtils.isEmpty(user.getPassword())) {
            if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(request.getOldPassword().getBytes()))) {
                throw new InvalidArgumentException("???????????????");
            }
            user.setPassword(DigestUtils.md5DigestAsHex(request.getNewPassword().getBytes()));
            updateById(user);
        } else if (!request.getVerifyCode().isEmpty()) {
            if (!verifyCodeUtil.isValidVerifyCode(user, request.getVerifyCode(), request.getVerifyWay())) {
                throw new InvalidArgumentException("???????????????");
            }
            user.setPassword(DigestUtils.md5DigestAsHex(request.getNewPassword().getBytes()));
            updateById(user);
        } else {
            throw new InvalidArgumentException("?????????????????????");
        }
        return Response.ok();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Validation(UpdateUserEmailValidator.class)
    @Override
    public Response<String> updateEmail(UpdateUserEmailRequest request, User user) {
        user.setEmail(request.getEmail());
        updateById(user);
        return Response.ok();
    }

    /**
     * ????????????
     * */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> deactivateAccount(BaseRequest request) {
        userMapper.deleteById(request.getUserId());
        return Response.ok();
    }

    @Override
    public Response<String> checkVerifyCode(CheckSecurityVerifyCodeRequest request, User user) {
        if (!verifyCodeUtil.isValidVerifyCode(user, request.getVerifyCode(), request.getVerifyWay())) {
            throw new InvalidArgumentException("???????????????");
        }
        return Response.ok();
    }

    @RequireAdmin
    @Override
    public PageResponse<User> listUser(ListUserRequest request) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
            .select("id, username, avatar, mobile, coin, create_at, last_login_time, sex")
            .and(role -> role.eq("role", UserRoleEnum.USER.code())
                .or()
                .eq("role", UserRoleEnum.AUTHOR.code()))
            .like(!request.getUsername().isEmpty(), "username", request.getUsername());
        IPage<User> userList = page(pageUtil.of(request), queryWrapper);
        return PageResponse.of(userList, request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> updateSecrecyConfig(UpdateSecrecyConfigRequest request) {
        userMapper.updateSecrecyConfig(request.getSecrecyConfig(), request.getUserId());
        return Response.ok();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    @RequireAuthor
    public Response<User> updateAuthorDetail(UpdateAuthorDetailRequest request, User user) {
        if (Duration.between(user.getUpdateAt(), LocalDateTime.now()).toDays() <= 30) {
            throw new InvalidArgumentException("30???????????????????????????");
        }
        if (!user.getNickname().equals(request.getNickname())) {
            User maxExistUser = getOne(new QueryWrapper<User>()
                .eq("nickname", request.getNickname()));
            AssertUtil.assertNull(maxExistUser, "???????????????");
        }

        BeanUtils.copyProperties(request, user);
        user.setUpdateAt(LocalDateTime.now());
        updateById(user);
        return Response.ok(user);
    }

    @Override
    public Response<SecrecyConfig> getSecrecyConfig(BaseRequest request) {
        User user = userMapper.findSecrecyConfigByUserId(request.getUserId());
        if (user.getSecrecyConfig() == null) {
            user.setSecrecyConfig(new SecrecyConfig());
        }
        return Response.ok(user.getSecrecyConfig());
    }

    @RequireAuthor
    @Override
    public GetAuthorCompletionResponse getAuthorCompletion(BaseRequest request, User user) {
        GetAuthorCompletionResponse response = new GetAuthorCompletionResponse();
        Boolean isAuthentication = !user.getIdCard().isEmpty() && !user.getAuthName().isEmpty();
        int authorCompletion = userUtil.calculationAuthorCompletion(user);
        response.setAuthorCompletion(authorCompletion);
        response.setAuthentication(isAuthentication);
        return response;
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    public Response<String> getPersonalCenterQrCode(BaseRequest request) {
        String url = String.format("www.qiangdong.com/user/%s", request.getUserId());
        return Response.ok(url);
    }

    /**
     * ???????????????????????????????????????????????????????????????
     */
    @Override
    public Response<UserIdNameDto> checkUserExist(CheckUserRequest request) {
        User user = getById(request.getTargetUserId());
        AssertUtil.assertNotNull(user, "???????????????");

        UserIdNameDto userIdNameDto = new UserIdNameDto();
        userIdNameDto.setUserId(user.getId());
        userIdNameDto.setUsername(user.getUsername());
        return Response.ok(userIdNameDto);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<User> updateChatStatus(UpdateUserChatStatusRequest request, User user) {
        user.setChatStatus(request.getChatStatus());
        updateById(user);
        return Response.ok(user);
    }

    @RequireAuthor
    @Override
    public GetAuthorIncomeResponse getAuthorIncome(GetAuthorIncomeRequest request) {
        Integer subscribeCoin =
            subscribeMapper.sumSubscribeByWorks(request.getWorksId(), request.getWorksType().code(), request.getTime());
        List<SubscribeDto> subscribeDtos =
            subscribeMapper.findByWorksAndTime(request.getWorksId(), request.getWorksType().code(), request.getTime());
        Integer rewardCoin =
            rewardMapper.sumRewardByWorksAndTime(request.getWorksId(), request.getWorksType().code(), request.getTime());
        List<RewardDto> rewardDtos =
            rewardMapper.findByWorksAndTime(request.getWorksId(), request.getWorksType().code(), request.getTime());
        GetAuthorIncomeResponse response = new GetAuthorIncomeResponse();
        BigDecimal subscribePrice = new BigDecimal(subscribeCoin / CommonConstant.COIN_CONVERSION_RATE);
        BigDecimal rewardPrice = new BigDecimal(rewardCoin / CommonConstant.COIN_CONVERSION_RATE);
        response.setSubscribeCount(subscribePrice);
        response.setRewardCount(rewardPrice);
        response.setRewardDtos(rewardDtos);
        response.setSubscribeDtos(subscribeDtos);
        return response;
    }

    /**
     * ????????????????????????????????????
     * */
    @Transactional
    @Override
    public Response<User> updateNewPassword(UpdateNewPasswordRequest request, User user) {
        if (StrUtil.isNotEmpty(user.getPassword())) {
            throw new InvalidArgumentException("??????????????????");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
        updateById(user);
        return Response.ok(user);
    }
}
