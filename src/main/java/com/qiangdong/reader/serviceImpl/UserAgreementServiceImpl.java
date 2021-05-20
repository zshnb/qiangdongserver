package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.dao.UserAgreementMapper;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_agreement.AddOrUpdateUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.DeleteUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementByTypeRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementRequest;
import com.qiangdong.reader.request.user_agreement.ListUserAgreementRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-09
 */
@Service
public class UserAgreementServiceImpl extends ServiceImpl<UserAgreementMapper, UserAgreement> implements
	IUserAgreementService {
	private PageUtil pageUtil;

	public UserAgreementServiceImpl(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	@Override
	public PageResponse<UserAgreement> listUserAgreement(ListUserAgreementRequest request) {
		if (request.getType().equals(UserAgreementTypeEnum.NONE)) {
			throw new InvalidArgumentException("无效的协议类型");
		}
		IPage<UserAgreement> userAgreementIPage = page(pageUtil.of(request), new QueryWrapper<UserAgreement>()
			.eq("type", request.getType()));
		return PageResponse.of(userAgreementIPage, request.getPageSize());
	}

	@Override
	@RequireAdmin
	public Response<UserAgreement> getUserAgreement(GetUserAgreementRequest request, UserAgreement userAgreement) {
		return Response.ok(userAgreement);
	}

	/**
	 * app获取协议（隐私或者用户）
	 * */
	@Override
	public Response<UserAgreement> getUserAgreementByType(GetUserAgreementByTypeRequest request) {
		UserAgreement userAgreement = getOne(new QueryWrapper<UserAgreement>()
			.eq("type", request.getType())
			.orderByDesc("update_at")
			.last("limit 1"));
		return Response.ok(userAgreement);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public Response<UserAgreement> addOrUpdateUserAgreement(AddOrUpdateUserAgreementRequest request) {
		UserAgreement userAgreement;
		if (request.getUserAgreementId() == 0L) {
			userAgreement = new UserAgreement();
			BeanUtils.copyProperties(request, userAgreement);
			save(userAgreement);
		} else {
			userAgreement = getById(request.getUserAgreementId());
			AssertUtil.assertNotNull(userAgreement, "协议不存在");
			BeanUtils.copyProperties(request, userAgreement);
			updateById(userAgreement);
		}
		return Response.ok(userAgreement);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public Response<String> deleteUserAgreement(DeleteUserAgreementRequest request, UserAgreement userAgreement) {
		removeById(request.getUserAgreementId());
		return Response.ok();
	}

	@Override
	public Response<UserAgreement> getLatestUserAgreement(BaseRequest request) {
		return Response.ok(getOne(new QueryWrapper<UserAgreement>()
			.orderByDesc("update_at")
			.last("limit 1")));
	}
}
