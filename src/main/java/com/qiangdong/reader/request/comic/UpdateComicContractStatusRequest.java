package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateComicContractStatusRequest extends BaseRequest {

    private Long comicId = 0L;
    @NotNone(message = "无效的签约类型")
    private WorksContractStatusEnum contractStatus = WorksContractStatusEnum.NONE;

    public Long getComicId() {
        return comicId;
    }

    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    public WorksContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(WorksContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }
}
