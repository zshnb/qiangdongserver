package com.qiangdong.reader.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.utils.Md5Utils;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.config.TencentOssConfig;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.utils.JwtUtil;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OssUtilTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private COSClient cosClient;

    @Autowired
    private TencentOssConfig ossConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void uploadNovelChapterSuccessful() throws Exception {
        String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
        String fileName = "./src/test/resources/test-file/novel1-chapter1.txt";
        byte[] sourceFileBytes = Files.readAllBytes(Paths.get(fileName));

        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.multipart("/upload/novel/single-chapter")
                .file(new MockMultipartFile("file", fileName, "text/plain", sourceFileBytes))
                .param("novelId", "1")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                .header(JwtInterceptor.HEADER_AUTHENTICATION, token))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        Response<String> response = new Gson().fromJson(body, new TypeToken<Response<String>>(){}.getType());
        Assert.assertThat(response.getData(), Matchers.startsWith(ossConfig.getNovelDomain()));
        Assert.assertThat(response.getData(), Matchers.endsWith(".txt"));

        GetObjectRequest getObjectRequest = new GetObjectRequest(ossConfig.getNovelBucketName(),
            response.getData().substring(response.getData().indexOf("/1")).substring(1));
        getObjectRequest.setTrafficLimit(80*1024*1024);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
        byte[] cosObjectMd5 = Md5Utils.computeMD5Hash(cosObjectInputStream);
        byte[] sourceFileMd5 = Md5Utils.computeMD5Hash(new FileInputStream(fileName));
        Assert.assertArrayEquals(cosObjectMd5, sourceFileMd5);

        cosClient.deleteObject(ossConfig.getNovelBucketName(),
            response.getData().substring(response.getData().indexOf("/1")).substring(1));
        cosClient.shutdown();
    }

    @Test
    public void uploadComicChapterSuccessful() throws Exception {
        String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
        String fileName = "./src/test/resources/test-file/comic1-chapter1.jpg";
        byte[] sourceFileBytes = Files.readAllBytes(Paths.get(fileName));

        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.multipart("/upload/comic/single-chapter")
                .file(new MockMultipartFile("file", fileName, "image/jpeg", sourceFileBytes))
                .param("comicId", "1")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                .header(JwtInterceptor.HEADER_AUTHENTICATION, token))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        Response<String> response = new Gson().fromJson(body, new TypeToken<Response<String>>(){}.getType());
        Assert.assertThat(response.getData(), Matchers.startsWith(ossConfig.getComicDomain()));
        Assert.assertThat(response.getData(), Matchers.endsWith(".jpg"));

        GetObjectRequest getObjectRequest = new GetObjectRequest(ossConfig.getComicBucketName(),
            response.getData().substring(response.getData().indexOf("/1")).substring(1));
        getObjectRequest.setTrafficLimit(80*1024*1024);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
        byte[] cosObjectMd5 = Md5Utils.computeMD5Hash(cosObjectInputStream);
        byte[] sourceFileMd5 = Md5Utils.computeMD5Hash(new FileInputStream(fileName));
        Assert.assertArrayEquals(cosObjectMd5, sourceFileMd5);

        cosClient.deleteObject(ossConfig.getNovelBucketName(),
            response.getData().substring(response.getData().indexOf("/1")).substring(1));
        cosClient.shutdown();
    }

    @Test
    public void uploadImageSuccessful() throws Exception {
        String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
        String fileName = "./src/test/resources/test-file/comic1-chapter1.jpg";
        byte[] sourceFileBytes = Files.readAllBytes(Paths.get(fileName));

        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.multipart("/upload/image")
                .file(new MockMultipartFile("file", fileName, "image/jpeg", sourceFileBytes))
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                .header(JwtInterceptor.HEADER_AUTHENTICATION, token))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        Response<String> response = new Gson().fromJson(body, new TypeToken<Response<String>>(){}.getType());
        Assert.assertThat(response.getData(), Matchers.startsWith(ossConfig.getImageDomain()));
        Assert.assertThat(response.getData(), Matchers.endsWith(".jpg"));

        GetObjectRequest getObjectRequest = new GetObjectRequest(ossConfig.getImageBucketName(),
            response.getData().substring(response.getData().indexOf("images")));
        getObjectRequest.setTrafficLimit(80*1024*1024);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
        byte[] bytes = new byte[10240];
        int cosFileSize = cosObjectInputStream.read(bytes);
        int sourceFileSize = Math.toIntExact(FileUtils.sizeOf(new File(fileName)));
        assertThat(cosFileSize).isLessThan(sourceFileSize);

        cosClient.deleteObject(ossConfig.getNovelBucketName(),
            response.getData().substring(response.getData().indexOf("image")));
        cosClient.shutdown();
    }
}
