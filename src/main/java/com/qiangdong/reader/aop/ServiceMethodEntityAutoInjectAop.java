package com.qiangdong.reader.aop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.utils.SpringContextUtils;
import com.qiangdong.reader.dao.*;
import com.qiangdong.reader.entity.*;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.utils.AssertUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(4)
public class ServiceMethodEntityAutoInjectAop {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMethodEntityAutoInjectAop.class);
	private static Map<Class<? extends Serializable>, Class<?>> entityMapperMap = new HashMap<>();
	private static Map<Class<? extends Serializable>, String> entityNotFoundMessageMap = new HashMap<>();

	static {
		entityMapperMap.put(Novel.class, NovelMapper.class);
		entityMapperMap.put(User.class, UserMapper.class);
		entityMapperMap.put(BookStand.class, BookStandMapper.class);
		entityMapperMap.put(ComicChapter.class, ComicChapterMapper.class);
		entityMapperMap.put(Comic.class, ComicMapper.class);
		entityMapperMap.put(Comment.class, CommentMapper.class);
		entityMapperMap.put(Draft.class, DraftMapper.class);
		entityMapperMap.put(FollowRelation.class, FollowRelationMapper.class);
		entityMapperMap.put(FunctionArea.class, FunctionAreaMapper.class);
		entityMapperMap.put(Menu.class, MenuMapper.class);
		entityMapperMap.put(NovelChapter.class, NovelChapterMapper.class);
		entityMapperMap.put(Reward.class, RewardMapper.class);
		entityMapperMap.put(Role.class, RoleMapper.class);
		entityMapperMap.put(Subscribe.class, SubscribeMapper.class);
		entityMapperMap.put(Swiper.class, SwiperMapper.class);
		entityMapperMap.put(Type.class, TypeMapper.class);
		entityMapperMap.put(UserActivity.class, UserActivityMapper.class);
		entityMapperMap.put(UserConsumption.class, UserConsumptionMapper.class);
		entityMapperMap.put(UserCreditRecord.class, UserCreditRecordMapper.class);
		entityMapperMap.put(WorksFaq.class, WorksFaqMapper.class);
		entityMapperMap.put(WorksTag.class, WorksTagMapper.class);
		entityMapperMap.put(WorksTopic.class, WorksTopicMapper.class);
		entityMapperMap.put(Notice.class, NoticeMapper.class);
		entityMapperMap.put(AppInfo.class, AppInfoMapper.class);
		entityMapperMap.put(UserAgreement.class, UserAgreementMapper.class);
		entityMapperMap.put(HelpFeedback.class, HelpFeedbackMapper.class);
		entityMapperMap.put(Topic.class, TopicMapper.class);
		entityMapperMap.put(WorksReadHistory.class, WorksReadHistoryMapper.class);
		entityMapperMap.put(UserPreferType.class, UserPreferTypeMapper.class);
		entityMapperMap.put(UserActivityCollection.class, UserActivityCollectionMapper.class);
		entityMapperMap.put(BlockUser.class, BlockUserMapper.class);
		entityMapperMap.put(FateBoard.class, FateBoardMapper.class);
		entityMapperMap.put(UserChat.class, UserChatMapper.class);
		entityMapperMap.put(Goods.class, GoodsMapper.class);
		entityMapperMap.put(UserHobby.class, UserHobbyMapper.class);
		entityMapperMap.put(UserAppPush.class, UserAppPushMapper.class);

		entityNotFoundMessageMap.put(Novel.class, "???????????????");
		entityNotFoundMessageMap.put(User.class, "???????????????");
		entityNotFoundMessageMap.put(BookStand.class, "?????????????????????");
		entityNotFoundMessageMap.put(ComicChapter.class, "?????????????????????");
		entityNotFoundMessageMap.put(Comic.class, "???????????????");
		entityNotFoundMessageMap.put(Comment.class, "???????????????");
		entityNotFoundMessageMap.put(Draft.class, "???????????????");
		entityNotFoundMessageMap.put(FollowRelation.class, "???????????????");
		entityNotFoundMessageMap.put(FunctionArea.class, "????????????????????????");
		entityNotFoundMessageMap.put(Menu.class, "???????????????");
		entityNotFoundMessageMap.put(NovelChapter.class, "?????????????????????");
		entityNotFoundMessageMap.put(ReadRecord.class, "?????????????????????");
		entityNotFoundMessageMap.put(Reward.class, "?????????????????????");
		entityNotFoundMessageMap.put(Role.class, "???????????????");
		entityNotFoundMessageMap.put(Subscribe.class, "?????????????????????");
		entityNotFoundMessageMap.put(Swiper.class, "??????????????????");
		entityNotFoundMessageMap.put(Type.class, "???????????????");
		entityNotFoundMessageMap.put(UserActivity.class, "?????????????????????");
		entityNotFoundMessageMap.put(UserConsumption.class, "?????????????????????");
		entityNotFoundMessageMap.put(UserCreditRecord.class, "?????????????????????");
		entityNotFoundMessageMap.put(WorksFaq.class, "???????????????");
		entityNotFoundMessageMap.put(WorksTag.class, "???????????????");
		entityNotFoundMessageMap.put(WorksTopic.class, "???????????????");
		entityNotFoundMessageMap.put(Notice.class, "???????????????");
		entityNotFoundMessageMap.put(AppInfo.class, "apk???????????????");
		entityNotFoundMessageMap.put(UserAgreement.class, "?????????????????????");
		entityNotFoundMessageMap.put(HelpFeedback.class, "?????????????????????");
		entityNotFoundMessageMap.put(Topic.class, "???????????????");
		entityNotFoundMessageMap.put(WorksReadHistory.class, "?????????????????????");
		entityNotFoundMessageMap.put(UserPreferType.class, "???????????????????????????");
		entityNotFoundMessageMap.put(BlockUser.class, "??????????????????");
		entityNotFoundMessageMap.put(FateBoard.class, "???????????????");
		entityNotFoundMessageMap.put(UserChat.class, "???????????????");
		entityNotFoundMessageMap.put(Goods.class, "???????????????");
		entityNotFoundMessageMap.put(UserHobby.class, "?????????????????????");
		entityNotFoundMessageMap.put(UserAppPush.class, "?????????????????????");
	}

	@Autowired
	private SpringContextUtils springContextUtils;

	@Pointcut("execution(public * com.qiangdong.reader.serviceImpl.*.*(..))")
	public void entityInjectPointCut() {
	}

	@Around("entityInjectPointCut()")
	public Object entityInject(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		if (args.length > 1) {
			Class<?> requestClass = args[0].getClass();
			for (int i = 1; i < args.length; i++) {
				if (args[i] instanceof Serializable) {
					Class<?> clz = args[i].getClass();
					Class<?> mapperClz = entityMapperMap.get(clz);
					BaseMapper<?> mapper =
						(BaseMapper<?>) springContextUtils
							.getBean(StringUtils.uncapitalize(mapperClz.getSimpleName()), mapperClz);
					long id;
					try {
						id = (long) requestClass.getMethod(String.format("get%sId", clz.getSimpleName()))
							.invoke(args[0]);
					} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
						LOGGER.error(String.format("%s doesn't have getId method", clz.getSimpleName()), e);
						throw new InvalidArgumentException("????????????????????????");
					}
					Object entity = mapper.selectById(id);

					AssertUtil.assertNotNull(entity, entityNotFoundMessageMap.get(clz));
					args[i] = entity;
				}
			}
		}
		return joinPoint.proceed(args);
	}
}
