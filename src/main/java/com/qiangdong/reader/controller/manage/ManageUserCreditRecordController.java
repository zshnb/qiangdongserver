package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.entity.UserCreditRecord;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_credit_record.DeleteUserCreditRecordRequest;
import com.qiangdong.reader.request.user_credit_record.ListUserCreditRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserCreditRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/manage/user-credit-record")
public class ManageUserCreditRecordController {

    @Autowired
    private UserCreditRecordServiceImpl userCreditRecordService;

    @PostMapping("/list")
    public PageResponse<UserCreditRecordDto> listUserCreditRecord(@RequestBody ListUserCreditRecordRequest request){
        return userCreditRecordService.listUserCreditRecord(request);
    }

    @DeleteMapping("/{userCreditRecordId}")
    public Response<String> deleteUserCreditRecord(@RequestBody DeleteUserCreditRecordRequest request){
        return userCreditRecordService.deleteUserCreditRecord(request, new UserCreditRecord());
    }
}
