package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.request.notice.AddOrUpdateNoticeRequest;
import com.qiangdong.reader.request.notice.DeleteNoticeRequest;
import com.qiangdong.reader.request.notice.ListNoticeByTypeRequest;
import com.qiangdong.reader.request.notice.GetNoticeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.INoticeService;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 总后台的公告模块
 */
@RestController
@RequestMapping("/manage/notice")
public class ManageNoticeController {

	@Autowired
	private NoticeServiceImpl noticeService;

	/**
	 * 总后台的公告分页列表 按时间来升序
	 *
	 * @param request 按type来查询
	 * @return
	 */
	@PostMapping("/list-notice")
	public PageResponse<Notice> listNoticeByType(@RequestBody ListNoticeByTypeRequest request) {
		return noticeService.listNoticeByType(request);
	}

	/**
	 * 总后台的查看公告详情
	 */
	@PostMapping("/detail")
	public Response<Notice> getNotice(@RequestBody GetNoticeRequest request) {
		return noticeService.getNotice(request, new Notice());
	}

	@PostMapping("/add-update")
	public Response<Notice> addOrUpdateNotice(@RequestBody @Valid AddOrUpdateNoticeRequest request) {
		return noticeService.addOrUpdateNotice(request);
	}

	@DeleteMapping("/{noticeId}")
	public Response<String> deleteNotice(@RequestBody DeleteNoticeRequest request) {
		return noticeService.deleteNotice(request, new Notice());
	}

}
