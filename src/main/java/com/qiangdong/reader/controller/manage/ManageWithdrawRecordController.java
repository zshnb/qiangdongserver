package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.WithdrawRecordDto;
import com.qiangdong.reader.request.withdraw_record.ApplyWithdrawRequest;
import com.qiangdong.reader.request.withdraw_record.ListWithdrawRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.WithdrawRecordServiceImpl;
import java.math.BigDecimal;
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
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/manage/withdraw-record")
public class ManageWithdrawRecordController {
    private final WithdrawRecordServiceImpl withdrawRecordService;

    public ManageWithdrawRecordController(WithdrawRecordServiceImpl withdrawRecordService) {
        this.withdrawRecordService = withdrawRecordService;
    }

    @PostMapping("/apply")
    public Response<String> applyWithdraw(@RequestBody ApplyWithdrawRequest request) {
        return withdrawRecordService.applyWithdraw(request);
    }

    @PostMapping("/list")
    public PageResponse<WithdrawRecordDto> listWithdrawRecordsByAuthor(@RequestBody ListWithdrawRecordRequest request) {
        return withdrawRecordService.listWithdrawRecords(request);
    }
}
