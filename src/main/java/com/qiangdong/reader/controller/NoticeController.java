package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.request.notice.GetNoticeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
     private final NoticeServiceImpl noticeService;

    public NoticeController(NoticeServiceImpl noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/detail/{noticeId}")
    public Response<Notice> getNotice(@RequestBody GetNoticeRequest request) {
        return noticeService.getNotice(request, new Notice());
    }
}
