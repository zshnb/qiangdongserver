package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.WorksFaq;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.faq.AddOrUpdateFaqRequest;
import com.qiangdong.reader.request.faq.DeleteFaqRequest;
import com.qiangdong.reader.request.faq.GetFaqRequest;
import com.qiangdong.reader.request.faq.ListFaqManageRequest;
import com.qiangdong.reader.request.faq.ListFaqRequest;
import com.qiangdong.reader.request.faq.ReplyFaqQuestionRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
@RestController
@RequestMapping("/manage/faq")
public class ManageWorksFaqController {

	@Autowired
	private WorksFaqServiceImpl faqService;

	@PostMapping("/add-update")
	public Response<WorksFaq> addOrUpdateFaq(@RequestBody AddOrUpdateFaqRequest request) {
		return faqService.addOrUpdateFaq(request);
	}

	@DeleteMapping("/{faqId}")
	public Response<String> deleteFaq(@RequestBody DeleteFaqRequest request) {
		return faqService.deleteFaq(request);
	}

	@PostMapping("/list")
	public PageResponse<FaqDto> listFaq(@RequestBody ListFaqRequest request) {
		return faqService.listFaq(request);
	}

	@PostMapping("/detail")
	public Response<FaqDto> getFaq(@RequestBody GetFaqRequest request) {
		return faqService.getFaq(request);
	}

	@PostMapping("/unread-count")
	public Response<Integer> getUnReadCount(@RequestBody BaseRequest request) {
		return faqService.getUnReadFaqCount(request);
	}

	@PostMapping("/list-system")
	public PageResponse<FaqDto> getSystemFaq(@RequestBody BaseRequest request){
	    return faqService.getSystemFaq(request);
    }

    @PostMapping("/clear")
	public Response<String> clearUserFaq(@RequestBody BaseRequest request){
		return faqService.clearUserFaq(request);
    }

    @PostMapping("/update-read")
	public Response<String> readUserAllFaq(@RequestBody BaseRequest request){
		return faqService.readUserAllFaq(request);
    }

    @PostMapping("/list-faq")
    public PageResponse<FaqDto> listFaqByType(@RequestBody ListFaqManageRequest request){
		return faqService.listFaqByType(request, new Type());
	}

	@PostMapping("/reply-question")
	public Response<WorksFaq> reply(@RequestBody ReplyFaqQuestionRequest request){
		return faqService.replyFaqQuestion(request, new WorksFaq());
	}
}
