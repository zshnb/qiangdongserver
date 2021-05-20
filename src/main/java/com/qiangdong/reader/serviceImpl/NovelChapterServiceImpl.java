package com.qiangdong.reader.serviceImpl;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.SubscribeMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.novel.NovelChapterCatalogItemDto;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.Subscribe;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.AddOrUpdateNovelChapterRequest;
import com.qiangdong.reader.request.novel.DeleteNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetLatestNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetRecycleNovelChapterRequest;
import com.qiangdong.reader.request.novel.ListNovelChapterCatalogRequest;
import com.qiangdong.reader.request.novel.ListNovelChaptersRequest;
import com.qiangdong.reader.request.novel.ListRecycleNovelChaptersRequest;
import com.qiangdong.reader.request.novel.UpdateNovelChapterReviewStatusRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.INovelChapterService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.NovelChapterUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.utils.SensitiveWordUtil;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class NovelChapterServiceImpl extends ServiceImpl<NovelChapterMapper, NovelChapter> implements INovelChapterService {
    private final NovelChapterMapper novelChapterMapper;
    private final NovelMapper novelMapper;
    private final SubscribeMapper subscribeMapper;
    private final PageUtil pageUtil;
    private final SensitiveWordUtil sensitiveWordUtil;
    private final UserMapper userMapper;
    private final NovelChapterUtil novelChapterUtil;

    public NovelChapterServiceImpl(NovelChapterMapper novelChapterMapper,
                                   NovelMapper novelMapper,
                                   SubscribeMapper subscribeMapper, PageUtil pageUtil,
                                   SensitiveWordUtil sensitiveWordUtil, UserMapper userMapper,
                                   NovelChapterUtil novelChapterUtil) {
        this.novelChapterMapper = novelChapterMapper;
        this.novelMapper = novelMapper;
        this.subscribeMapper = subscribeMapper;
        this.pageUtil = pageUtil;
        this.sensitiveWordUtil = sensitiveWordUtil;
        this.userMapper = userMapper;
        this.novelChapterUtil = novelChapterUtil;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @RequireAuthor
    public NovelChapter addOrUpdateNovelChapter(AddOrUpdateNovelChapterRequest request) {
        User user = userMapper.selectById(request.getUserId());
        Novel novel = novelMapper.selectById(request.getNovelId());
        AssertUtil.assertNotNull(novel, "小说不存在");
        if (request.getType().equals(WorksChapterTypeEnum.VIP) && !user.getAllowCharge()) {
            throw new InvalidArgumentException("未开通收费章节");
        }
        QueryWrapper<NovelChapter> queryWrapper = new QueryWrapper<>();
        if (request.getChapterId() == 0L) {
            queryWrapper.eq("title", request.getTitle())
                .eq("novel_id", request.getNovelId());
            NovelChapter existChapter = novelChapterMapper.selectOne(queryWrapper);
            AssertUtil.assertNull(existChapter, "章节标题已存在");
        } else {
            NovelChapter existChapter = novelChapterMapper.selectById(request.getChapterId());
            AssertUtil.assertNotNull(existChapter, "章节不存在");
            if (!request.getTitle().equals(existChapter.getTitle())) {
                queryWrapper.eq("title", request.getTitle())
                    .eq("novel_id", request.getNovelId());
                NovelChapter mayExistChapter = novelChapterMapper.selectOne(queryWrapper);
                AssertUtil.assertNull(mayExistChapter, "章节标题已存在");
            }
        }

        if (!StringUtils.isEmpty(request.getTxtUrl())) {
            String content = HttpUtil.get(request.getTxtUrl(), StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(content) &&
                    sensitiveWordUtil.getBadWord(content, SensitiveWordUtil.MatchType.MAX_MATCH_TYPE).size() > 3) {
                throw new InvalidArgumentException("违规词超过3个");
            }
        }

        NovelChapter chapter;
        if (request.getChapterId() == 0L) {
            Integer index = novelChapterMapper.findLastIndexByNovelId(request.getNovelId());
            if (index == null) {
                index = 0;
            }
            chapter = new NovelChapter();
            chapter.setIndex(index + 1);
            chapter.setReviewStatus(WorksChapterReviewStatusEnum.PASS);
            if (request.getType().equals(WorksChapterTypeEnum.VIP)) {
                chapter.setPrice(novelChapterUtil.calculatePrice(request.getWordCount()));
            } else {
                chapter.setPrice(0.0);
            }
            // TODO: 2020/9/1 等编辑审核功能
//            if (index != 0 && !isViolation) {
//                chapter.setReviewStatus(WorksChapterReviewStatusEnum.PASS);
//            } else {
//                chapter.setReviewStatus(WorksChapterReviewStatusEnum.PENDING);
//            }
            BeanUtils.copyProperties(request, chapter);
            save(chapter);
        } else {
            chapter = getById(request.getChapterId());
            chapter.setTitle(request.getTitle());
            chapter.setTxtUrl(request.getTxtUrl());
            if (request.getType().equals(WorksChapterTypeEnum.VIP)) {
                chapter.setPrice(novelChapterUtil.calculatePrice(request.getWordCount()));
            } else {
                chapter.setPrice(0.0);
            }
            chapter.setAuthorWords(request.getAuthorWords());
            chapter.setWordCount(request.getWordCount());
            chapter.setType(request.getType());
            updateById(chapter);
        }
        novel.setWordCount(novel.getWordCount() + chapter.getWordCount());
        novelMapper.updateById(novel);
        return chapter;
    }

    @Override
    public NovelChapter getNovelChapter(GetNovelChapterRequest request) {
        NovelChapter novelChapter = getOne(new QueryWrapper<NovelChapter>()
            .eq("id", request.getChapterId())
            .eq("novel_id", request.getNovelId()));
        AssertUtil.assertNotNull(novelChapter, "章节不存在");

        Novel novel = novelMapper.selectById(request.getNovelId());
        if (!novel.getAuthorId().equals(request.getUserId()) && novelChapter.getType().equals(WorksChapterTypeEnum.VIP)) {
            Subscribe subscribe = subscribeMapper.selectOne(new QueryWrapper<Subscribe>()
                .eq("user_id", request.getUserId())
                .eq("associate_id", request.getChapterId()));
            AssertUtil.assertNotNull(subscribe, "未订阅章节");
        }
        return novelChapter;
    }

    /**
     * 获取最新章节
     * */
    @Override
    public Response<NovelChapter> getLatestChapter(GetLatestNovelChapterRequest request, Novel novel) {
        NovelChapter novelChapter = novelChapterMapper.selectOne(new QueryWrapper<NovelChapter>()
            .eq("novel_id", novel.getId())
            .orderByDesc("`index`")
            .last("limit 1"));
        return Response.ok(novelChapter);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @RequireAuthor
    public Response<String> deleteNovelChapter(DeleteNovelChapterRequest request) {
        QueryWrapper<NovelChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("novel_id", request.getNovelId())
            .eq("id", request.getChapterId());
        NovelChapter chapter = novelChapterMapper.selectOne(queryWrapper);
        AssertUtil.assertNotNull(chapter, "章节不存在");

        remove(queryWrapper);
        return Response.ok();
    }

    @Override
    @RequireEditor
    public PageResponse<NovelChapterCatalogItemDto> listNovelChapterCatalog(
            ListNovelChapterCatalogRequest request) {
        Novel novel = novelMapper.selectById(request.getNovelId());
        AssertUtil.assertNotNull(novel, "小说不存在");

        IPage<NovelChapterCatalogItemDto> catalogItems =
                novelChapterMapper.findNovelChapterCatalogItemByNovelId(pageUtil.of(request), request.getNovelId());
        return PageResponse.of(catalogItems, request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @RequireEditor
    public Response<NovelChapter> updateNovelChapterReviewStatus(
            UpdateNovelChapterReviewStatusRequest request) {
        NovelChapter chapter = getById(request.getChapterId());
        AssertUtil.assertNotNull(chapter, "章节不存在");

        chapter.setReviewStatus(request.getReviewStatus());
        updateById(chapter);
        return Response.ok(chapter);
    }

    @Override
    public PageResponse<NovelChapter> listNovelChapters(ListNovelChaptersRequest request) {
        Novel novel = novelMapper.selectById(request.getNovelId());
        AssertUtil.assertNotNull(novel, "小说不存在");
        QueryWrapper<NovelChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "`index`", "type", "txt_url", "update_at", "author_words")
            .eq("novel_id", request.getNovelId());
        IPage<NovelChapter> novelChapters = page(pageUtil.of(request), queryWrapper);
        return PageResponse.of(novelChapters, request.getPageSize());
    }

    @Override
    @RequireAuthor
    public PageResponse<NovelChapterDto> listRecycleChapters(ListRecycleNovelChaptersRequest request) {
        Novel novel = novelMapper.selectById(request.getNovelId());
        AssertUtil.assertNotNull(novel, "小说不存在");
        IPage<NovelChapterDto> chapters = novelChapterMapper.findDeleteChapters(
            pageUtil.of(request), request.getNovelId());
        return PageResponse.of(chapters, request.getPageSize());
    }

    @Override
    @RequireAuthor
    public Response<NovelChapterDto> getRecycleChapter(GetRecycleNovelChapterRequest request) {
        Novel novel = novelMapper.selectById(request.getNovelId());
        AssertUtil.assertNotNull(novel, "小说不存在");

        NovelChapterDto novelChapterDto = novelChapterMapper.findDeleteChapterById(request.getChapterId());
        AssertUtil.assertNotNull(novelChapterDto, "章节不存在");
        return Response.ok(novelChapterDto);
    }
}
