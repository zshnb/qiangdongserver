package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.Draft;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.draft.AddOrUpdateDraftRequest;
import com.qiangdong.reader.request.draft.DeleteDraftRequest;
import com.qiangdong.reader.request.draft.ListWorksDraftRequest;
import com.qiangdong.reader.request.draft.PublishDraftRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
public interface IDraftService extends IService<Draft> {

	Response<Draft> addOrUpdateDraft(AddOrUpdateDraftRequest request);

	PageResponse<Draft> listWorksDraft(ListWorksDraftRequest request);

	Response<String> publishDraft(PublishDraftRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> deleteDraft(DeleteDraftRequest request);
}
