package com.qiangdong.reader.serviceImpl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.dao.UserCreditRecordMapper;
import com.qiangdong.reader.dao.UserLoginCountMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.statistics.CreditStatisticsDto;
import com.qiangdong.reader.dto.statistics.CreditUserStatisticsDto;
import com.qiangdong.reader.dto.statistics.DauStatisticsDto;
import com.qiangdong.reader.dto.statistics.RegisterStatisticsDto;
import com.qiangdong.reader.dto.statistics.StatisticsDataOverviewDto;
import com.qiangdong.reader.dto.statistics.UserCreditRankDto;
import com.qiangdong.reader.entity.UserCreditRecord;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.statistics.ListCreditStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListCreditUserStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListDauStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListRegisterStatisticsRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.utils.DateUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl {
	private final UserCreditRecordMapper userCreditRecordMapper;
	private final UserLoginCountMapper userLoginCountMapper;
	private final UserMapper userMapper;
	private final DateUtil dateUtil;
	private final com.qiangdong.reader.utils.NumberUtil numberUtil;
	private final PageUtil pageUtil;

	public StatisticsServiceImpl(UserCreditRecordMapper userCreditRecordMapper,
	                             UserLoginCountMapper userLoginCountMapper,
	                             UserMapper userMapper, DateUtil dateUtil,
	                             com.qiangdong.reader.utils.NumberUtil numberUtil,
	                             PageUtil pageUtil) {
		this.userCreditRecordMapper = userCreditRecordMapper;
		this.userLoginCountMapper = userLoginCountMapper;
		this.userMapper = userMapper;
		this.dateUtil = dateUtil;
		this.numberUtil = numberUtil;
		this.pageUtil = pageUtil;
	}

	/**
	 * 数据统计总览
	 * */
	@RequireAdmin
	public Response<StatisticsDataOverviewDto> getStatisticsDataOverview(BaseRequest request) {
		StatisticsDataOverviewDto statisticsDataOverviewDto = new StatisticsDataOverviewDto();
		// 注册数据
		int registerCount = numberUtil.secureInteger(userMapper.selectCount(new QueryWrapper<>()));
		int todayRegisterCount = numberUtil.secureInteger(userMapper.countRegisterUserByTime(LocalDateTime.now()));
		int yesterdayRegisterCount = numberUtil.secureInteger(userMapper.countRegisterUserByTime(dateUtil.getYesterday()));
		double registerIncrementRatio;
		if (yesterdayRegisterCount == 0) {
			registerIncrementRatio = NumberUtil.div(Double.valueOf(todayRegisterCount), Double.valueOf(1), 2);
		} else {
			registerIncrementRatio = NumberUtil.div(Double.valueOf(todayRegisterCount- yesterdayRegisterCount),
				Double.valueOf(yesterdayRegisterCount), 2);
		}
		statisticsDataOverviewDto.setRegisterCount(registerCount);
		statisticsDataOverviewDto.setRegisterIncrementRatio(registerIncrementRatio);

		// 日活数据
		int todayDauCount = numberUtil.secureInteger(userMapper.countTodayLoginUser());
		int yesterdayDauCount = numberUtil.secureInteger(userLoginCountMapper.countByTime(dateUtil.getYesterday()));
		double dauIncrementRatio;
		if (yesterdayDauCount == 0) {
			dauIncrementRatio = NumberUtil.div(Double.valueOf(todayDauCount), Double.valueOf(1), 2);
		} else {
			dauIncrementRatio = NumberUtil.div(Double.valueOf(todayDauCount - yesterdayDauCount),
				Double.valueOf(yesterdayDauCount), 2);
		}
		statisticsDataOverviewDto.setDauCount(todayDauCount);
		statisticsDataOverviewDto.setDauCountIncrementRatio(dauIncrementRatio);

		// 充值数据
		BigDecimal creditCount = userCreditRecordMapper.sumCredit();
		BigDecimal todayCreditCount = numberUtil.secureBigDecimal(userCreditRecordMapper.sumByTime(LocalDateTime.now()));
		BigDecimal yesterdayCreditCount = numberUtil.secureBigDecimal(userCreditRecordMapper.sumByTime(dateUtil.getYesterday()));
		double creditIncrementRatio;
		if (yesterdayCreditCount.equals(BigDecimal.ZERO)) {
			creditIncrementRatio = todayCreditCount.divide(BigDecimal.ONE, 2, RoundingMode.CEILING).doubleValue();
		} else {
			creditIncrementRatio = todayCreditCount.subtract(yesterdayCreditCount)
				.divide(yesterdayCreditCount, 2, BigDecimal.ROUND_CEILING).doubleValue();
		}
		statisticsDataOverviewDto.setCreditCount(creditCount);
		statisticsDataOverviewDto.setCreditIncrementRatio(creditIncrementRatio);

		// 充值用户数据
		int creditUserCount = numberUtil.secureInteger(userCreditRecordMapper.selectCount(new QueryWrapper<UserCreditRecord>()
			.select("distinct(user_id)")));
		int todayCreditUserCount = numberUtil.secureInteger(userCreditRecordMapper.countCreditUserByTime(LocalDateTime.now()));
		int yesterdayCreditUserCount = numberUtil.secureInteger(userCreditRecordMapper.countCreditUserByTime(dateUtil.getYesterday()));
		double creditUserIncrementRatio;
		if (yesterdayCreditUserCount == 0) {
			creditUserIncrementRatio = NumberUtil.div(Double.valueOf(todayCreditUserCount), Double.valueOf(1), 2);
		} else {
			creditUserIncrementRatio = NumberUtil.div(Double.valueOf(todayCreditUserCount - yesterdayCreditUserCount),
				Double.valueOf(yesterdayCreditUserCount), 2);
		}
		statisticsDataOverviewDto.setCreditUserCount(creditUserCount);
		statisticsDataOverviewDto.setCreditUserIncrementRatio(creditUserIncrementRatio);
		return Response.ok(statisticsDataOverviewDto);
	}

	/**
	 * 到目前为止的注册用户统计数据，根据时间单位不同，分别是30日，12月，5年
	 * */
	@RequireAdmin
	public PageResponse<RegisterStatisticsDto> listRegisterStatistics(ListRegisterStatisticsRequest request) {
		switch (request.getDateUnit()) {
			case DAY: return PageResponse.of(userMapper.findRegisterStatisticsByDay(), 30L);
			case MONTH: return PageResponse.of(userMapper.findRegisterStatisticsByMonth(), 12L);
			case YEAR: return PageResponse.of(userMapper.findRegisterStatisticsByYear(), 5L);
			default: throw new InvalidArgumentException("无效的时间单位");
		}
	}

	/**
	 * 每日活跃用户统计数据，根据时间单位不同，分别是30日，12月，5年
	 * */
	@RequireAdmin
	public PageResponse<DauStatisticsDto> listDauStatistics(ListDauStatisticsRequest request) {
		switch (request.getDateUnit()) {
			case YEAR: return PageResponse.of(userLoginCountMapper.findDauStatisticsByYear(), 5L);
			case MONTH: return PageResponse.of(userLoginCountMapper.findDauStatisticsByMonth(), 12L);
			case DAY: return PageResponse.of(userLoginCountMapper.findDauStatisticsByDay(), 30L);
			default: throw new InvalidArgumentException("无效的时间单位");
		}
	}

	/**
	 * 到目前为止的充值统计数据，根据时间单位不同，分别是30日，12月，5年
	 * */
	@RequireAdmin
	public PageResponse<CreditStatisticsDto> listCreditStatistics(ListCreditStatisticsRequest request) {
		switch (request.getDateUnit()) {
			case YEAR: return PageResponse.of(userCreditRecordMapper.findCreditStatisticsByYear(), 5L);
			case MONTH: return PageResponse.of(userCreditRecordMapper.findCreditStatisticsByMonth(), 12L);
			case DAY: return PageResponse.of(userCreditRecordMapper.findCreditStatisticsByDay(), 30L);
			default: throw new InvalidArgumentException("无效的时间单位");
		}
	}

	/**
	 * 到目前为止的充值用户统计数据，根据时间单位不同，分别是30日，12月，5年
	 * */
	@RequireAdmin
	public PageResponse<CreditUserStatisticsDto> listCreditUserStatistics(ListCreditUserStatisticsRequest request) {
		switch (request.getDateUnit()) {
			case YEAR: return PageResponse.of(userCreditRecordMapper.findCreditUserStatisticsByYear(), 5L);
			case MONTH: return PageResponse.of(userCreditRecordMapper.findCreditUserStatisticsByMonth(), 12L);
			case DAY: return PageResponse.of(userCreditRecordMapper.findCreditUserStatisticsByDay(), 30L);
			default: throw new InvalidArgumentException("无效的时间单位");
		}
	}

	/**
	 * 用户充值排名
	 * */
	@RequireAdmin
	public PageResponse<UserCreditRankDto> listUserCreditRank(BaseRequest request) {
		return PageResponse.of(userCreditRecordMapper.findUserCreditUserRank(pageUtil.of(request)), request.getPageSize());
	}
}
