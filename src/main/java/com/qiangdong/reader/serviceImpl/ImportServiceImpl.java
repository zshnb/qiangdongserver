package com.qiangdong.reader.serviceImpl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.ThirdPartyWorks;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.enums.common.WorksShowStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.enums.third_party_works.ThirdPartyEnum;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.utils.OssUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImportServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);
    private final NovelMapper novelMapper;
    private final NovelChapterMapper novelChapterMapper;
    private final TypeMapper typeMapper;
    private final OkHttpClient okHttpClient;
    private final OssUtil ossUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final DataSourceTransactionManager dataSourceTransactionManager;
    private final TransactionDefinition transactionDefinition;
    private final ThirdPartyWorksServiceImpl thirdPartyWorksService;
    private Map<String, Long> typeMap;

    public ImportServiceImpl(NovelMapper novelMapper, NovelChapterMapper novelChapterMapper,
                             TypeMapper typeMapper, OkHttpClient okHttpClient,
                             OssUtil ossUtil,
                             RedisTemplate<String, String> redisTemplate,
                             DataSourceTransactionManager dataSourceTransactionManager,
                             TransactionDefinition transactionDefinition,
                             ThirdPartyWorksServiceImpl thirdPartyWorksService) {
        this.novelMapper = novelMapper;
        this.novelChapterMapper = novelChapterMapper;
        this.typeMapper = typeMapper;
        this.okHttpClient = okHttpClient;
        this.ossUtil = ossUtil;
        this.redisTemplate = redisTemplate;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
        this.transactionDefinition = transactionDefinition;
        this.thirdPartyWorksService = thirdPartyWorksService;
        typeMap = new HashMap<>();
        typeMap.put("东方玄幻", 7L);
        typeMap.put("亡灵异族", 7L);
        typeMap.put("都市言情", 19L);
        typeMap.put("商战职场", 19L);
        typeMap.put("豪门总裁", 19L);
        typeMap.put("热血青春", 19L);
        typeMap.put("恩怨情仇", 18L);
        typeMap.put("复仇青春", 18L);
        typeMap.put("穿越女强", 18L);
        typeMap.put("宫闱宅斗", 18L);
        typeMap.put("官场沉浮", 18L);
        typeMap.put("古典仙侠", 21L);
        typeMap.put("幻想修仙", 49L);
        typeMap.put("现代修仙", 49L);
        typeMap.put("菁菁校园", 23L);
        typeMap.put("魔法校园", 23L);
        typeMap.put("青梅竹马", 23L);
        typeMap.put("探险盗墓", 15L);
        typeMap.put("同人", 29L);
        typeMap.put("网游竞技", 25L);
        typeMap.put("西方奇幻", 8L);
        typeMap.put("相爱职场", 19L);
        typeMap.put("新派武侠", 9L);
        typeMap.put("星际争霸", 14L);
        typeMap.put("悬疑惊悚", 15L);
        typeMap.put("异界争霸", 7L);
        typeMap.put("异术超能", 4L);
        typeMap.put("娱乐明星", 19L);
        typeMap.put("远古神话", 20L);
        typeMap.put("种田经商", 18L);
        typeMap.put("重生复仇", 4L);
        typeMap.put("重生娱乐", 4L);
        typeMap.put("社科励志", 4L);
    }

    @RequireAdmin
    @Transactional
    public com.qiangdong.reader.response.Response<String> importNovelFromSiWei(BaseRequest baseRequest) throws IOException {
        List<ThirdPartyWorks> thirdPartyWorksList;
        String url = "http://www.siweiip.com/api/qiangdong/booklist.php?appid=10088&apikey=q2Ia0n2Gd0O092N1g";
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        JSONArray jsonArray = JSONUtil.parseArray(response.body().string());

        thirdPartyWorksList = jsonArray.stream().map(it -> {
            JSONObject jsonObject = (JSONObject)it;
            String id = jsonObject.getStr("id");
            String name = jsonObject.getStr("bookname");
            String bookInfoUrl = String.format(
                "http://www.siweiip.com/api/qiangdong/bookinfo.php?appid=10088&apikey=q2Ia0n2Gd0O092N1g&bookid=%s", id);
            Request bookInfoRequest = new Builder().url(bookInfoUrl).build();
            Response bookInfoResponse = null;
            long typeId = 3L;
            try {
                bookInfoResponse = okHttpClient.newCall(bookInfoRequest).execute();
                jsonObject = JSONUtil.parseObj(bookInfoResponse.body().string());
                String clazz = jsonObject.getStr("class");
                Type type = typeMapper.selectOne(new QueryWrapper<Type>()
                    .eq("name", clazz));
                if (type != null) {
                    typeId = type.getId();
                } else {
					try {
                        typeId = typeMap.get(clazz);
                    } catch (Exception e) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Novel novel = novelMapper.selectOne(new QueryWrapper<Novel>()
                .eq("name", name));
            novel.setTypeId(typeId);
            novelMapper.updateById(novel);

            ThirdPartyWorks thirdPartyWorks = new ThirdPartyWorks();
            thirdPartyWorks.setThirdParty(ThirdPartyEnum.SI_WEI);
            thirdPartyWorks.setThirdPartyWorksId(id);
            thirdPartyWorks.setWorksType(WorksTypeEnum.NOVEL);
            thirdPartyWorks.setWorksId(novel.getId());
            return thirdPartyWorks;
//            TransactionStatus transactionStatus = null;
//            String bookInfoUrl = String.format(
//                "http://www.siweiip.com/api/qiangdong/bookinfo.php?appid=10088&apikey=q2Ia0n2Gd0O092N1g&bookid=%s", id);
//            Request bookInfoRequest = new Request.Builder().url(bookInfoUrl).build();
//            try {
//                transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
//                Response bookInfoResponse = okHttpClient.newCall(bookInfoRequest).execute();
//                JSONObject jsonObject = JSONUtil.parseObj(bookInfoResponse.body().string());
//                Novel novel = buildNovel(jsonObject);
//                novelMapper.insert(novel);
//                List<NovelChapter> chapters = buildNovelChapters(id, novel.getId());
//                novelChapterMapper.saveAll(chapters);
//                dataSourceTransactionManager.commit(transactionStatus);
//                setOperations.add(key, id);
//            } catch (Exception e) {
//                dataSourceTransactionManager.rollback(transactionStatus);
//            }
        }).collect(Collectors.toList());
//        thirdPartyWorksService.saveBatch(thirdPartyWorksList);
        return com.qiangdong.reader.response.Response.ok();
    }

    private Novel buildNovel(JSONObject jsonObject) {
        NovelDto novelDto = new NovelDto();
        Novel novel = new Novel();
        BeanUtils.copyProperties(novelDto, novel);
        novel.setCover(jsonObject.getStr("BookPic"));
        novel.setAuthorId(1L);
        novel.setDescription(jsonObject.getStr("intro"));
        novel.setName(jsonObject.getStr("name"));
        novel.setWordCount(jsonObject.getInt("WordNum"));
        novel.setShowStatus(WorksShowStatusEnum.VISIBLE);
        novel.setTopicId(0L);
        int completeStatus = jsonObject.getInt("complete_status");
        if (completeStatus == 0) {
            novel.setUpdateStatus(WorksUpdateStatusEnum.UPDATING);
        } else {
            novel.setUpdateStatus(WorksUpdateStatusEnum.END);
        }
        String className = jsonObject.getStr("class");
        Type type = typeMapper.selectOne(new QueryWrapper<Type>()
            .eq("name", className));
        if (type != null) {
            novel.setTypeId(type.getId());
        } else {
            novel.setTypeId(3L);
        }
        return novel;
    }

    private List<NovelChapter> buildNovelChapters(String bookId, Long novelId) throws IOException {
        AtomicInteger index = new AtomicInteger(0);
        String chaptersUrl = String.format(
            "http://www.siweiip.com/api/qiangdong/bookchapters.php?appid=10088&apikey=q2Ia0n2Gd0O092N1g&bookid=%s", bookId);
        Request request = new Request.Builder().url(chaptersUrl).build();
        Response response = okHttpClient.newCall(request).execute();
        JSONArray jsonArray = JSONUtil.parseArray(response.body().string());
        List<String> chapterIds = jsonArray.stream().map(it -> {
            JSONObject jsonObject = (JSONObject)it;
            return jsonObject.getStr("id");
        }).collect(Collectors.toList());

        return chapterIds.stream().map(it -> {
            String chapterInfoUrl = String.format(
                "http://www.siweiip.com/api/qiangdong/bookchapterinfo.php?appid=10088&apikey=q2Ia0n2Gd0O092N1g&bookid=%s&chapterid=%s", bookId, it);
            Request chapterInfoRequest = new Request.Builder().url(chapterInfoUrl).build();
            try {
                Response chapterInfoResponse = okHttpClient.newCall(chapterInfoRequest).execute();
                JSONObject jsonObject = JSONUtil.parseObj(chapterInfoResponse.body().string());
                NovelChapter chapter = new NovelChapter();
                chapter.setTitle(jsonObject.getStr("name"));
                chapter.setNovelId(novelId);
                chapter.setPrice(jsonObject.getDouble("price"));
                chapter.setWordCount(jsonObject.getInt("chaptersize"));
                chapter.setIndex(index.getAndIncrement());
                chapter.setReviewStatus(WorksChapterReviewStatusEnum.PASS);
                chapter.setAuthorWords("");
                chapter.setProgress(0.0);
                String isVip = jsonObject.getStr("isvip");
                if (isVip.equals("0")) {
                    chapter.setType(WorksChapterTypeEnum.PUBLIC);
                } else {
                    chapter.setType(WorksChapterTypeEnum.VIP);
                }
                String txtUrl = ossUtil.uploadNovelChapterTxt(jsonObject.getStr("content"), novelId);
                chapter.setTxtUrl(txtUrl);
                return chapter;
            } catch (IOException e) {
                logger.error("import error", e);
                return null;
            }
        }).collect(Collectors.toList());
    }
}
