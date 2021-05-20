package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.WorksFaq;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.faq.AddOrUpdateFaqRequest;
import com.qiangdong.reader.request.faq.DeleteFaqRequest;
import com.qiangdong.reader.request.faq.GetFaqRequest;
import com.qiangdong.reader.request.faq.ListFaqManageRequest;
import com.qiangdong.reader.request.faq.ListFaqRequest;
import com.qiangdong.reader.request.faq.ReplyFaqQuestionRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
public interface IWorksFaqService extends IService<WorksFaq> {

    Response<WorksFaq> addOrUpdateFaq(AddOrUpdateFaqRequest request);

    Response<String> deleteFaq(DeleteFaqRequest request);

    PageResponse<FaqDto> listFaq(ListFaqRequest request);

    Response<FaqDto> getFaq(GetFaqRequest request);

    Response<Integer> getUnReadFaqCount(BaseRequest request);

    PageResponse<FaqDto> getSystemFaq(BaseRequest request);

    Response<String> clearUserFaq(BaseRequest request);

    Response<String> readUserAllFaq(BaseRequest request);

    PageResponse<FaqDto> listFaqByType(ListFaqManageRequest request, Type type);

    Response<WorksFaq> replyFaqQuestion(ReplyFaqQuestionRequest request, WorksFaq faq);
}
