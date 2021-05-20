package com.qiangdong.reader.service;

import com.qiangdong.reader.annotation.RequireAuthor;
import com.qiangdong.reader.annotation.RequireEditor;
import com.qiangdong.reader.dto.WithdrawRecordDto;
import com.qiangdong.reader.entity.WithdrawRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.withdraw_record.ApplyWithdrawRequest;
import com.qiangdong.reader.request.withdraw_record.ListWithdrawRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import java.math.BigDecimal;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-29
 */
public interface IWithdrawRecordService extends IService<WithdrawRecord> {

    @RequireAuthor
    @Transactional(rollbackFor = RuntimeException.class)
    Response<String> applyWithdraw(ApplyWithdrawRequest request);

    PageResponse<WithdrawRecordDto> listWithdrawRecords(ListWithdrawRecordRequest request);
}
