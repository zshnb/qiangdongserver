package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.UserAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_agreement.AddOrUpdateUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.DeleteUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementByTypeRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.ListUserAgreementRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-09
 */
public interface IUserAgreementService extends IService<UserAgreement> {

	PageResponse<UserAgreement> listUserAgreement(ListUserAgreementRequest request);

	Response<UserAgreement> getUserAgreement(GetUserAgreementRequest request, UserAgreement userAgreement);

    Response<UserAgreement> getUserAgreementByType(GetUserAgreementByTypeRequest request);

    Response<UserAgreement> addOrUpdateUserAgreement(AddOrUpdateUserAgreementRequest request);

	Response<String> deleteUserAgreement(DeleteUserAgreementRequest request,UserAgreement userAgreement);

	Response<UserAgreement> getLatestUserAgreement(BaseRequest request);
}
