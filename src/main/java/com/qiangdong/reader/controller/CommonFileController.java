package com.qiangdong.reader.controller;

import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.utils.OssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class CommonFileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonFileController.class);

    private final OssUtil ossUtil;

    public CommonFileController(OssUtil ossUtil) {
        this.ossUtil = ossUtil;
    }

    @PostMapping("/novel/single-chapter")
    public Response<String> uploadNovelChapter(@RequestParam("file")MultipartFile file,
                                               @RequestParam("novelId") String novelId) {
        try {
            String resultPath = ossUtil.uploadNovelChapterTxt(file, novelId);
            return Response.ok(resultPath);
        } catch (IOException e) {
            LOGGER.error(String.format("upload %s failed", novelId), e);
            throw new InternalException("上传文件异常，请稍后重试");
        }
    }

    @PostMapping("/comic/single-chapter")
    public Response<String> uploadComicChapter(@RequestParam("file")MultipartFile file,
                                               @RequestParam("comicId") String comicId) {
        try {
            String resultPath = ossUtil.uploadComicChapterPicture(file, comicId);
            return Response.ok(resultPath);
        } catch (IOException e) {
            LOGGER.error(String.format("upload %s failed", comicId), e);
            throw new InternalException("上传文件异常，请稍后重试");
        }
    }

    @PostMapping("/image")
    public Response<String> uploadImage(@RequestParam("file")MultipartFile file) {
        try {
            String resultPath = ossUtil.uploadImage(file);
            return Response.ok(resultPath);
        } catch (IOException e) {
            LOGGER.error("upload image failed", e);
            throw new InternalException("上传文件异常，请稍后重试");
        }
    }
}
