package com.qiangdong.reader.entity;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.third_party_works.ThirdPartyEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiangdong app
 * @since 2020-10-02
 */
public class ThirdPartyWorks implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String thirdPartyWorksId;

    private ThirdPartyEnum thirdParty;

    private Long worksId;

    private WorksTypeEnum worksType;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getThirdPartyWorksId() {
        return thirdPartyWorksId;
    }

    public void setThirdPartyWorksId(String thirdPartyWorksId) {
        this.thirdPartyWorksId = thirdPartyWorksId;
    }
    public ThirdPartyEnum getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdPartyEnum thirdParty) {
        this.thirdParty = thirdParty;
    }
    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }
    public WorksTypeEnum getWorksType() {
        return worksType;
    }

    public void setWorksType(WorksTypeEnum worksType) {
        this.worksType = worksType;
    }
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "ThirdPartyWorks{" +
            "id=" + id +
            ", thirdPartyWorksId=" + thirdPartyWorksId +
            ", thirdParty=" + thirdParty +
            ", worksId=" + worksId +
            ", worksType=" + worksType +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }
}
