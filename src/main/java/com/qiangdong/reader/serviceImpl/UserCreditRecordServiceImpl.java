package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.entity.UserCreditRecord;
import com.qiangdong.reader.dao.UserCreditRecordMapper;
import com.qiangdong.reader.request.user_credit_record.DeleteUserCreditRecordRequest;
import com.qiangdong.reader.request.user_credit_record.ListUserCreditRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserCreditRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 虚拟货币充值记录 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class UserCreditRecordServiceImpl extends ServiceImpl<UserCreditRecordMapper, UserCreditRecord>
	implements IUserCreditRecordService {

	private final UserCreditRecordMapper userCreditRecordMapper;
	private final PageUtil pageUtil;

	public UserCreditRecordServiceImpl(UserCreditRecordMapper userCreditRecordMapper,
	                                   PageUtil pageUtil) {
		this.userCreditRecordMapper = userCreditRecordMapper;
		this.pageUtil = pageUtil;
	}

	@RequireAdmin
	@Override
	public PageResponse<UserCreditRecordDto> listUserCreditRecord(ListUserCreditRecordRequest request) {
		IPage<UserCreditRecordDto> listUserCreditRecord =
				userCreditRecordMapper.listUserCreditRecord(pageUtil.of(request), request.getUsername());
		return PageResponse.of(listUserCreditRecord, request.getPageSize());
	}

	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	@Override
	public Response<String> deleteUserCreditRecord(DeleteUserCreditRecordRequest request, UserCreditRecord userCreditRecord) {
		removeById(request.getUserCreditRecordId());
		return Response.ok();
	}
}
