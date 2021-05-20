package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.common.CommentConstant;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.CommentMapper;
import com.qiangdong.reader.dao.MessageMapper;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.UserActivityMapper;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.dto.user_activity.ActivityData;
import com.qiangdong.reader.dto.user_activity.AgreeActivity;
import com.qiangdong.reader.dto.user_activity.CreateActivity;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Message;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import com.qiangdong.reader.enums.user_activity.AgreeActivityTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.AgainstCommentRequest;
import com.qiangdong.reader.request.comment.AgreeCommentRequest;
import com.qiangdong.reader.request.comment.ChangeTopRequest;
import com.qiangdong.reader.request.comment.DeleteCommentRequest;
import com.qiangdong.reader.request.comment.ListComicChapterCommentSummaryRequest;
import com.qiangdong.reader.request.comment.ListComicCommentRequest;
import com.qiangdong.reader.request.comment.ListNovelChapterCommentSummaryRequest;
import com.qiangdong.reader.request.comment.ListNovelCommentRequest;
import com.qiangdong.reader.request.comment.ListReplyRequest;
import com.qiangdong.reader.request.comment.ListUserActivityCommentRequest;
import com.qiangdong.reader.request.comment.PublishComicChapterCommentRequest;
import com.qiangdong.reader.request.comment.PublishComicCommentRequest;
import com.qiangdong.reader.request.comment.PublishNovelChapterCommentRequest;
import com.qiangdong.reader.request.comment.PublishNovelCommentRequest;
import com.qiangdong.reader.request.comment.PublishUserActivityCommentRequest;
import com.qiangdong.reader.request.comment.ReplyCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.ICommentService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
	private final CommentMapper commentMapper;
	private final NovelMapper novelMapper;
	private final NovelChapterMapper novelChapterMapper;
	private final ComicMapper comicMapper;
	private final ComicChapterMapper comicChapterMapper;
	private final UserActivityMapper userActivityMapper;
	private final MessageMapper messageMapper;
	private final PageUtil pageUtil;
	private final RedisTemplate<String, String> redisTemplate;

	public CommentServiceImpl(CommentMapper commentMapper,
	                          NovelMapper novelMapper,
	                          NovelChapterMapper novelChapterMapper,
	                          ComicMapper comicMapper,
	                          ComicChapterMapper comicChapterMapper,
	                          UserActivityMapper userActivityMapper,
	                          MessageMapper messageMapper,
	                          PageUtil pageUtil,
	                          RedisTemplate<String, String> redisTemplate) {
		this.commentMapper = commentMapper;
		this.novelMapper = novelMapper;
		this.novelChapterMapper = novelChapterMapper;
		this.comicMapper = comicMapper;
		this.comicChapterMapper = comicChapterMapper;
		this.userActivityMapper = userActivityMapper;
		this.messageMapper = messageMapper;
		this.pageUtil = pageUtil;
		this.redisTemplate = redisTemplate;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> publishNovelComment(PublishNovelCommentRequest request, Novel novel) {
		Comment comment = new Comment();
		comment.setUserId(request.getUserId());
		comment.setAssociateId(request.getNovelId());
		comment.setContent(request.getContent());
		comment.setType(CommentTypeEnum.NOVEL);
		comment.setImages("");
		save(comment);
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> publishNovelChapterComment(PublishNovelChapterCommentRequest request) {
		NovelChapter chapter = novelChapterMapper.selectById(request.getChapterId());
		AssertUtil.assertNotNull(chapter, "小说章节不存在");

		Comment comment = new Comment();
		comment.setUserId(request.getUserId());
		comment.setAssociateId(request.getChapterId());
		comment.setContent(request.getContent());
		comment.setType(CommentTypeEnum.NOVEL_CHAPTER);
		comment.setImages("");
		save(comment);
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> publishComicComment(PublishComicCommentRequest request, Comic comic) {
		Comment comment = new Comment();
		comment.setUserId(request.getUserId());
		comment.setAssociateId(request.getComicId());
		comment.setContent(request.getContent());
		comment.setType(CommentTypeEnum.COMIC);
		comment.setImages("");
		save(comment);
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> publishComicChapterComment(PublishComicChapterCommentRequest request) {
		ComicChapter comicChapter = comicChapterMapper.selectById(request.getChapterId());
		AssertUtil.assertNotNull(comicChapter, "章节不存在");

		Comment comment = new Comment();
		comment.setUserId(request.getUserId());
		comment.setAssociateId(request.getChapterId());
		comment.setContent(request.getContent());
		comment.setType(CommentTypeEnum.COMIC_CHAPTER);
		comment.setImages("");
		save(comment);
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Comment> publishUserActivityComment(PublishUserActivityCommentRequest request) {
		Comment comment = new Comment();
		comment.setAssociateId(request.getUserActivityId());
		comment.setType(CommentTypeEnum.USER_ACTIVITY);
		comment.setContent(request.getContent());
		comment.setUserId(request.getUserId());
		comment.setImages(request.getImages());
		save(comment);

		UserActivity userActivity = userActivityMapper.findById(request.getUserActivityId());
		AssertUtil.assertNotNull(userActivity, "动态不存在");

		CreateActivity createActivity = userActivity.getActivityData().getCreateActivity();
		createActivity.setCommentCount(createActivity.getCommentCount() + 1);
		userActivity.getActivityData().setCreateActivity(createActivity);
		userActivityMapper.update(userActivity);

		Message message = new Message();
		message.setAssociateId(request.getUserActivityId());
		message.setReadStatus(MessageReadStatusEnum.UNREAD);
		message.setType(MessageTypeEnum.COMMENT);
		message.setUserId(userActivity.getUserId());
		messageMapper.insert(message);
		return Response.ok(comment);
	}

	/**
	 * 回复评论，嵌套等级只有2级，也就是说对于评论下的回复也是属于评论的回复
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Comment> replyComment(ReplyCommentRequest request) {
		Comment comment = getById(request.getCommentId());
		AssertUtil.assertNotNull(comment, "评论不存在");

		Comment reply = new Comment();
		BeanUtils.copyProperties(request, reply);
		if (comment.getType().equals(CommentTypeEnum.REPLY)) {
			reply.setAssociateId(comment.getAssociateId());
		} else {
			reply.setAssociateId(comment.getId());
		}
		reply.setType(CommentTypeEnum.REPLY);
		reply.setImages("");
		save(reply);

		Message message = new Message();
		message.setAssociateId(comment.getId());
		message.setReadStatus(MessageReadStatusEnum.UNREAD);
		message.setType(MessageTypeEnum.REPLY);
		message.setUserId(comment.getUserId());
		messageMapper.insert(message);
		return Response.ok(reply);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAuthor
	public Response<String> deleteComment(DeleteCommentRequest request, Comment comment) {
		removeById(request.getCommentId());
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAuthor
	public Response<String> changeTop(ChangeTopRequest request, Comment comment) {
		comment.setTop(request.getTop());
		updateById(comment);
		return Response.ok();
	}

	/**
	 * 小说评论列表
	 * */
	@Override
	public PageResponse<WorksCommentDto> listNovelComment(ListNovelCommentRequest request, Novel novel) {
		IPage<WorksCommentDto> comments =
			commentMapper.findNovelCommentByNovelId(pageUtil.of(request), request.getNovelId());
		return PageResponse.of(comments, request.getPageSize());
	}

	/**
	 * 漫画评论列表
	 * */
	@Override
	public PageResponse<WorksCommentDto> listComicComment(ListComicCommentRequest request, Comic comic) {
		IPage<WorksCommentDto> comments =
			commentMapper.findComicCommentByComicId(pageUtil.of(request), request.getComicId());
		return PageResponse.of(comments, request.getPageSize());
	}

	@Override
	public PageResponse<NovelChapterCommentSummaryDto> listNovelChapterCommentSummary(
		ListNovelChapterCommentSummaryRequest request) {
		Novel novel = novelMapper.selectById(request.getNovelId());
		if (novel == null) {
			throw new InvalidArgumentException("小说不存在");
		}
		Page<NovelChapter> page = new Page<>(request.getPageNumber(), request.getPageSize());

		List<Long> chapterIds = novelChapterMapper.selectPage(page, new QueryWrapper<NovelChapter>()
			.eq("novel_id", request.getNovelId())).getRecords().stream()
			.map(NovelChapter::getId).collect(Collectors.toList());

		List<NovelChapterCommentSummaryDto> summaryDtos =
			commentMapper.findNovelChapterSummaryByNovelId(chapterIds);

		return PageResponse.of(summaryDtos, request.getPageSize());
	}

	@Override
	public PageResponse<ComicChapterCommentSummaryDto> listComicChapterCommentSummary(
		ListComicChapterCommentSummaryRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		if (comic == null) {
			throw new InvalidArgumentException("漫画不存在");
		}
		Page<ComicChapter> page = new Page<>(request.getPageNumber(), request.getPageSize());

		List<Long> chapterIds = comicChapterMapper.selectPage(page, new QueryWrapper<ComicChapter>()
			.eq("comic_id", request.getComicId())).getRecords().stream()
			.map(ComicChapter::getId).collect(Collectors.toList());

		List<ComicChapterCommentSummaryDto> summaryDtos =
			commentMapper.findComicChapterSummaryByNovelId(chapterIds);

		return PageResponse.of(summaryDtos, request.getPageSize());
	}

	@Override
	public PageResponse<CommentDto> listUserActivityComment(ListUserActivityCommentRequest request,
	                                                        UserActivity userActivity) {
		IPage<CommentDto> comments = commentMapper.findUserActivityCommentByUserActivityId(
			pageUtil.of(request), request.getUserActivityId());
		return PageResponse.of(comments, request.getPageSize());
	}

	@Override
	public PageResponse<CommentDto> listReply(ListReplyRequest request, Comment comment){
		IPage<CommentDto> comments = commentMapper.findReplyCommentByCommentId(
			pageUtil.of(request), request.getCommentId());
		return PageResponse.of(comments, request.getPageSize());
	}

	@Override
	@Transactional
	public Response<Integer> agreeComment(AgreeCommentRequest request, Comment comment) {
		String key = String.format(CommentConstant.KEY_COMMENT_AGREE, comment.getId());
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		if (zSetOperations.rank(key, request.getUserId().toString()) != null) {
			throw new InvalidArgumentException("已赞同");
		}

		UserActivity agreeActivity = new UserActivity();
		agreeActivity.setUserId(request.getUserId());
		agreeActivity.setType(ActivityTypeEnum.AGREE_ACTIVITY);
		agreeActivity.setTop(false);

		AgreeActivity agreeActivityData = new AgreeActivity();
		agreeActivityData.setType(AgreeActivityTypeEnum.COMMENT);
		agreeActivityData.setCommentId(comment.getId());
		ActivityData activityData = new ActivityData();
		activityData.setAgreeActivity(agreeActivityData);
		agreeActivity.setActivityData(activityData);
		userActivityMapper.save(agreeActivity);

		Message message = new Message();
		message.setUserId(comment.getUserId());
		message.setType(MessageTypeEnum.AGREE);
		message.setReadStatus(MessageReadStatusEnum.UNREAD);
		message.setAssociateId(agreeActivity.getId());
		messageMapper.insert(message);

		zSetOperations.add(key, request.getUserId().toString(), System.currentTimeMillis());
		return Response.ok(zSetOperations.size(key).intValue());
	}

	@Override
	@Transactional
	public Response<Integer> againstComment(AgainstCommentRequest request, Comment comment) {
		String key = String.format(CommentConstant.KEY_COMMENT_AGAINST, comment.getId());
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		if (zSetOperations.rank(key, request.getUserId().toString()) != null) {
			throw new InvalidArgumentException("已反对");
		}

		zSetOperations.add(key, request.getUserId().toString(), System.currentTimeMillis());
		return Response.ok(zSetOperations.size(key).intValue());
	}
}
