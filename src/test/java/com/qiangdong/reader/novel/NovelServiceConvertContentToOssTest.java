package com.qiangdong.reader.novel;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.utils.Md5Utils;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.config.TencentOssConfig;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.ConvertContentToOssRequest;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceConvertContentToOssTest extends BaseTest {
    @Autowired
    private NovelServiceImpl novelService;

    @Autowired
    private COSClient cosClient;

    @Autowired
    private TencentOssConfig ossConfig;

    @Test
    public void convertSuccessful() throws IOException {
        ConvertContentToOssRequest request = new ConvertContentToOssRequest();
        request.setNovelId(1L);
        request.setContent("你好");
        String url = novelService.convertContentToOss(request, new Novel()).getData();

        cosClient.deleteObject(ossConfig.getNovelBucketName(),
            url.substring(url.indexOf("/1")).substring(1));
        cosClient.shutdown();
    }

    @Test
    public void convertFailedWhenNovelNotExist() {
        ConvertContentToOssRequest request = new ConvertContentToOssRequest();
        request.setNovelId(-1L);
        request.setContent("你好");
        assertException(InvalidArgumentException.class, () -> {
            try {
                novelService.convertContentToOss(request, new Novel());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
