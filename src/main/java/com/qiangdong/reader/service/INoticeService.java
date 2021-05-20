package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.notice.NoticeDto;
import com.qiangdong.reader.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.notice.AddOrUpdateNoticeRequest;
import com.qiangdong.reader.request.notice.DeleteNoticeRequest;
import com.qiangdong.reader.request.notice.ListNoticeByTypeRequest;
import com.qiangdong.reader.request.notice.GetNoticeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 * 公告表 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
public interface INoticeService extends IService<Notice> {

    PageResponse<Notice> listNoticeByType(ListNoticeByTypeRequest request);

    Response<Notice> getNotice(GetNoticeRequest request,Notice notice);

    Response<Notice> addOrUpdateNotice(AddOrUpdateNoticeRequest request);

    Response<String> deleteNotice(DeleteNoticeRequest request,Notice notice);
}
