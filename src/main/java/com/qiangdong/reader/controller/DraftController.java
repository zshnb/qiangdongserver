package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Draft;
import com.qiangdong.reader.request.draft.AddOrUpdateDraftRequest;
import com.qiangdong.reader.request.draft.DeleteDraftRequest;
import com.qiangdong.reader.request.draft.ListWorksDraftRequest;
import com.qiangdong.reader.request.draft.PublishDraftRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.DraftServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/draft")
public class DraftController {
	@Autowired
	private DraftServiceImpl draftService;

	@PostMapping("/add-update")
	public Response<Draft> addOrUpdateDraft(@RequestBody AddOrUpdateDraftRequest request) {
		return draftService.addOrUpdateDraft(request);
	}

	@PostMapping("/list")
	public PageResponse<Draft> listWorksDraft(@RequestBody ListWorksDraftRequest request) {
		return draftService.listWorksDraft(request);
	}

	@PostMapping("/publish")
	public Response<String> publishDraft(@RequestBody PublishDraftRequest request) {
		return draftService.publishDraft(request);
	}

	@DeleteMapping("/{draftId}")
	public Response<String> deleteDraft(@RequestBody DeleteDraftRequest request) {
		return draftService.deleteDraft(request);
	}
}
