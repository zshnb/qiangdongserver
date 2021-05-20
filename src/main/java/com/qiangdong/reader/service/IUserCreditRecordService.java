package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.entity.UserCreditRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.user_credit_record.DeleteUserCreditRecordRequest;
import com.qiangdong.reader.request.user_credit_record.ListUserCreditRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 * 虚拟货币充值记录 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface IUserCreditRecordService extends IService<UserCreditRecord> {

    PageResponse<UserCreditRecordDto> listUserCreditRecord(ListUserCreditRecordRequest request);

    Response<String> deleteUserCreditRecord(DeleteUserCreditRecordRequest request, UserCreditRecord userCreditRecord);
}
