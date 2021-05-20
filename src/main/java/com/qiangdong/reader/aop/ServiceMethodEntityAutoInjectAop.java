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

		entityNotFoundMessageMap.put(Novel.class, "小说不存在");
		entityNotFoundMessageMap.put(User.class, "用户不存在");
		entityNotFoundMessageMap.put(BookStand.class, "书架记录不存在");
		entityNotFoundMessageMap.put(ComicChapter.class, "漫画章节不存在");
		entityNotFoundMessageMap.put(Comic.class, "漫画不存在");
		entityNotFoundMessageMap.put(Comment.class, "评论不存在");
		entityNotFoundMessageMap.put(Draft.class, "草稿不存在");
		entityNotFoundMessageMap.put(FollowRelation.class, "关注不存在");
		entityNotFoundMessageMap.put(FunctionArea.class, "金刚区菜单不存在");
		entityNotFoundMessageMap.put(Menu.class, "菜单不存在");
		entityNotFoundMessageMap.put(NovelChapter.class, "小说章节不存在");
		entityNotFoundMessageMap.put(ReadRecord.class, "阅读记录不存在");
		entityNotFoundMessageMap.put(Reward.class, "打赏记录不存在");
		entityNotFoundMessageMap.put(Role.class, "角色不存在");
		entityNotFoundMessageMap.put(Subscribe.class, "订阅记录不存在");
		entityNotFoundMessageMap.put(Swiper.class, "轮播图不存在");
		entityNotFoundMessageMap.put(Type.class, "分类不存在");
		entityNotFoundMessageMap.put(UserActivity.class, "用户动态不存在");
		entityNotFoundMessageMap.put(UserConsumption.class, "消费记录不存在");
		entityNotFoundMessageMap.put(UserCreditRecord.class, "充值记录不存在");
		entityNotFoundMessageMap.put(WorksFaq.class, "咨询不存在");
		entityNotFoundMessageMap.put(WorksTag.class, "标签不存在");
		entityNotFoundMessageMap.put(WorksTopic.class, "专题不存在");
		entityNotFoundMessageMap.put(Notice.class, "公告不存在");
		entityNotFoundMessageMap.put(AppInfo.class, "apk版本不存在");
		entityNotFoundMessageMap.put(UserAgreement.class, "用户协议不存在");
		entityNotFoundMessageMap.put(HelpFeedback.class, "帮助反馈不存在");
		entityNotFoundMessageMap.put(Topic.class, "话题不存在");
		entityNotFoundMessageMap.put(WorksReadHistory.class, "阅读记录不存在");
		entityNotFoundMessageMap.put(UserPreferType.class, "用户阅读喜好不存在");
		entityNotFoundMessageMap.put(BlockUser.class, "黑名单不存在");
		entityNotFoundMessageMap.put(FateBoard.class, "缘签不存在");
		entityNotFoundMessageMap.put(UserChat.class, "聊天不存在");
		entityNotFoundMessageMap.put(Goods.class, "商品不存在");
		entityNotFoundMessageMap.put(UserHobby.class, "兴趣爱好不存在");
		entityNotFoundMessageMap.put(UserAppPush.class, "兴趣爱好不存在");
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
						throw new InvalidArgumentException("自动注入实体异常");
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
