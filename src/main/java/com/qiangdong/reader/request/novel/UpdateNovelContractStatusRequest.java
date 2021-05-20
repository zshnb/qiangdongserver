package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateNovelContractStatusRequest extends BaseRequest {

    private Long novelId = 0L;
    @NotNone(message = "无效的签约类型")
    private WorksContractStatusEnum contractStatus = WorksContractStatusEnum.NONE;

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public WorksContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(WorksContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }
}
