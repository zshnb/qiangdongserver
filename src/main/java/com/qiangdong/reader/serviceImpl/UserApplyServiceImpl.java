package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.common.UserApplyConstant;
import com.qiangdong.reader.dao.*;
import com.qiangdong.reader.entity.*;
import com.qiangdong.reader.enums.apply.ApplyTypeEnum;
import com.qiangdong.reader.enums.common.*;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_apply.AddOrUpdateApplyRequest;
import com.qiangdong.reader.request.user_apply.CheckNovelFullTimeApplicationConditionRequest;
import com.qiangdong.reader.request.user_apply.CheckWorksApplicationConditionRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user_apply.CheckRecommendApplicationConditionResponse;
import com.qiangdong.reader.response.user_apply.CheckNovelFullTimeApplicationConditionResponse;
import com.qiangdong.reader.response.user_apply.CheckSignApplicationConditionResponse;
import com.qiangdong.reader.service.IUserApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Date;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-16
 */
@Service
public class UserApplyServiceImpl extends ServiceImpl<UserApplyMapper, UserApply> implements IUserApplyService {

    private final NovelMapper novelMapper;
    private final NovelChapterMapper novelChapterMapper;
    private final ComicMapper comicMapper;
    private final ComicChapterMapper comicChapterMapper;
    private final UserApplyMapper applyMapper;
    private final UserMapper userMapper;

    public UserApplyServiceImpl(NovelMapper novelMapper,
                                NovelChapterMapper novelChapterMapper,
                                ComicMapper comicMapper,
                                ComicChapterMapper comicChapterMapper,
                                UserApplyMapper applyMapper, UserMapper userMapper) {
        this.novelMapper = novelMapper;
        this.novelChapterMapper = novelChapterMapper;
        this.comicMapper = comicMapper;
        this.comicChapterMapper = comicChapterMapper;
        this.applyMapper = applyMapper;
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserApply> addOrUpdateApply(AddOrUpdateApplyRequest request) {
        UserApply data = null;
        User user = userMapper.selectById(request.getUserId());
        if (user == null) {
            throw new InvalidArgumentException("用户不存在");
        }
        if (WorksTypeEnum.COMIC.equals(request.getWorksType())) {
            Comic comic = comicMapper.selectById(request.getWorksId());
            if (comic == null) {
                throw new InvalidArgumentException("漫画不存在");
            }
        } else if (WorksTypeEnum.NOVEL.equals(request.getWorksType())) {
            Novel novel = novelMapper.selectById(request.getWorksId());
            if (novel == null) {
                throw new InvalidArgumentException("小说不存在");
            }
        }
        if (request.getApplyId() == 0L) {
            data = new UserApply();
            BeanUtils.copyProperties(request, data);
            save(data);
        } else {
            data = getById(request.getApplyId());
            if (data == null) {
                throw new InvalidArgumentException("申请不存在");
            }
            BeanUtils.copyProperties(request, data);
            updateById(data);
        }
        return Response.ok(data);
    }

    @Override
    public CheckSignApplicationConditionResponse checkNovelSignApplicationCondition(
            CheckWorksApplicationConditionRequest request) {
        CheckSignApplicationConditionResponse response = new CheckSignApplicationConditionResponse();
        Novel novel = novelMapper.selectById(request.getWorksId());
        if (novel == null) {
            throw new InvalidArgumentException("小说不存在");
        }

        UserApply apply = applyMapper.selectOne(new QueryWrapper<UserApply>()
                .eq("apply_type", ApplyTypeEnum.SIGN)
                .eq("user_id", novel.getAuthorId())
                .eq("works_type", request.getType())
                .orderByDesc("create_at")
                .last("limit 1"));
        if (apply == null) {
            response.setLastApply(true);
        } else {
            Duration duration = Duration.between(apply.getCreateAt(), LocalDateTime.now());
            if (duration.toDays() >= UserApplyConstant.SIGN_LAST_APPLY_DAY) {
                response.setLastApply(true);
            }
        }
        if (novel.getWordCount() > UserApplyConstant.SIGN_WORD_COUNT) {
            response.setWordCount(true);
        }
        if (novel.getAuthorizationStatus() != WorksAuthorizationStatusEnum.NONE) {
            response.setAuthorization(true);
        }

        NovelChapter chapter = novelChapterMapper.selectOne(new QueryWrapper<NovelChapter>()
                .eq("novel_id", novel.getId())
                .eq("`index`", 1));
        if (chapter == null){
            throw new InvalidArgumentException("章节不存在");
        }
        if (chapter.getReviewStatus().equals(WorksChapterReviewStatusEnum.PASS)) {
            response.setReview(true);
        }
        return response;
    }

    @Override
    public CheckSignApplicationConditionResponse checkComicSignApplicationCondition(
            CheckWorksApplicationConditionRequest request) {
        CheckSignApplicationConditionResponse response = new CheckSignApplicationConditionResponse();
        Comic comic = comicMapper.selectById(request.getWorksId());
        if (comic == null) {
            throw new InvalidArgumentException("漫画不存在");
        }

        UserApply apply = applyMapper.selectOne(new QueryWrapper<UserApply>()
                .eq("apply_type", ApplyTypeEnum.SIGN)
                .eq("user_id", comic.getAuthorId())
                .eq("works_type", request.getType())
                .orderByDesc("create_at")
                .last("limit 1"));
        if (apply == null) {
            response.setLastApply(true);
        } else {
            Duration duration = Duration.between(apply.getCreateAt(), LocalDateTime.now());
            if (duration.toDays() >= UserApplyConstant.SIGN_LAST_APPLY_DAY) {
                response.setLastApply(true);
            }
        }
        if (comic.getAuthorizationStatus() != WorksAuthorizationStatusEnum.NONE) {
            response.setAuthorization(true);
        }

        ComicChapter chapter = comicChapterMapper.selectOne(new QueryWrapper<ComicChapter>()
                .eq("comic_id", comic.getId())
                .eq("`index`", 1));
        if (chapter == null) {
            throw new InvalidArgumentException("章节不存在");
        }
        if (chapter.getReviewStatus().equals(WorksChapterReviewStatusEnum.PASS)) {
            response.setReview(true);
        }
        return response;
    }

    @Override
    public CheckRecommendApplicationConditionResponse checkNovelRecommendApplicationCondition(CheckWorksApplicationConditionRequest request) {
        CheckRecommendApplicationConditionResponse response = new CheckRecommendApplicationConditionResponse();
        Novel novel = novelMapper.selectById(request.getWorksId());
        if (novel == null) {
            throw new InvalidArgumentException("小说不存在");
        }

        UserApply apply = applyMapper.selectOne(new QueryWrapper<UserApply>()
                .eq("apply_type", ApplyTypeEnum.SIGN)
                .eq("user_id", novel.getAuthorId())
                .eq("works_type", request.getType())
                .orderByDesc("create_at")
                .last("limit 1"));
        if (apply == null) {
            response.setWeekApply(true);
        } else {
            Duration duration = Duration.between(apply.getCreateAt(), LocalDateTime.now());
            if (duration.toDays() >= UserApplyConstant.RECOMMEND_WEEK_APPLY_DAY) {
                response.setWeekApply(true);
            }
        }
        if (novel.getWordCount() >= UserApplyConstant.RECOMMEND_WORD_COUNT) {
            response.setWordCount(true);
        }

        NovelChapter chapter = novelChapterMapper.selectOne(new QueryWrapper<NovelChapter>()
                .eq("novel_id", novel.getId())
                .eq("`index`", 1));
        if (chapter == null) {
            throw new InvalidArgumentException("章节不存在");
        }
        if (chapter.getReviewStatus().equals(WorksChapterReviewStatusEnum.PASS)) {
            response.setReview(true);
        }
        return response;
    }

    @Override
    public CheckRecommendApplicationConditionResponse checkComicRecommendApplicationCondition(
            CheckWorksApplicationConditionRequest request) {
        CheckRecommendApplicationConditionResponse response = new CheckRecommendApplicationConditionResponse();
        Comic comic = comicMapper.selectById(request.getWorksId());
        if (comic == null) {
            throw new InvalidArgumentException("漫画不存在");
        }
        UserApply apply = applyMapper.selectOne(new QueryWrapper<UserApply>()
                .eq("apply_type", ApplyTypeEnum.SIGN)
                .eq("user_id", comic.getAuthorId())
                .eq("works_type", request.getType())
                .orderByDesc("create_at")
                .last("limit 1"));
        if (apply == null) {
            response.setWeekApply(true);
        } else {
            Duration duration = Duration.between(apply.getCreateAt(), LocalDateTime.now());
            if (duration.toDays() >= UserApplyConstant.RECOMMEND_WEEK_APPLY_DAY) {
                response.setWeekApply(true);
            }
        }
        ComicChapter chapter = comicChapterMapper.selectOne(new QueryWrapper<ComicChapter>()
                .eq("comic_id", comic.getId())
                .eq("`index`", 1));
        if (chapter == null) {
            throw new InvalidArgumentException("章节不存在");
        }
        if (chapter.getReviewStatus().equals(WorksChapterReviewStatusEnum.PASS)) {
            response.setReview(true);
        }
        return response;
    }

    @Override
    public CheckNovelFullTimeApplicationConditionResponse checkNovelFullTimeApplicationCondition(
            CheckNovelFullTimeApplicationConditionRequest request) {
        CheckNovelFullTimeApplicationConditionResponse response = new CheckNovelFullTimeApplicationConditionResponse();
        Novel novel = novelMapper.selectById(request.getNovelId());
        if (novel == null) {
            throw new InvalidArgumentException("小说不存在");
        }
        if (novel.getShowStatus().equals(WorksShowStatusEnum.VISIBLE)) {
            response.setVip(true);
        }
        if (novel.getContractStatus().equals(WorksContractStatusEnum.SIGNED)) {
            response.setLevel(true);
        }

        UserApply apply = applyMapper.selectOne(new QueryWrapper<UserApply>()
                .eq("apply_type", ApplyTypeEnum.SIGN)
                .eq("user_id", novel.getAuthorId())
                .eq("works_type", WorksTypeEnum.NOVEL)
                .orderByDesc("create_at")
                .last("limit 1"));

        if (apply == null) {
            response.setMonthApply(true);
        } else {
            Duration duration = Duration.between(apply.getCreateAt(), LocalDateTime.now());
            if (duration.toDays() == UserApplyConstant.FULL_TIME_APPLY_DAY) {
                response.setMonthApply(true);
            }
        }

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());
        if (calendar.get(Calendar.DATE)==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            List<NovelChapter> chapters = novelChapterMapper.findCurrentMonthChapterByNovelId(novel.getId());
            int day = calendar.getActualMaximum(Calendar.DATE);
            if (day == chapters.size()) {
                response.setDayWordCount(true);
            }
        }
        return response;
    }

}

