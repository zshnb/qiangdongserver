package com.qiangdong.reader.statistics;

import static org.mockito.ArgumentMatchers.any;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dto.statistics.StatisticsDataOverviewDto;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.UserRegisterRequest;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.UserUtil;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class StatisticsServiceGetStatisticsDataOverviewTest extends BaseTest {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@SpyBean
	private UserUtil userUtil;

	@Before
	public void beforeMock() {
		Mockito.doNothing().when(userUtil).indexUser(any());
	}

	@Test
	public void getDataOverviewSuccessful() throws Exception {
		String key = String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, "13715166707");
		redisTemplate.opsForValue().set(key, "123456",
			UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
		mockMvc.perform(
			MockMvcRequestBuilders.post("/user/fast-login")
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.WEB.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"mobile\": \"13715166707\", \"code\": \"123456\"}"))
			.andExpect(MockMvcResultMatchers.status().isOk());

		BaseRequest request = new BaseRequest();
		request.setUserId(adminUserId);
		StatisticsDataOverviewDto response = statisticsService.getStatisticsDataOverview(request).getData();
		assertThat(response.getRegisterCount()).isEqualTo(6);
		assertThat(response.getRegisterIncrementRatio()).isEqualTo(1.00);
		assertThat(response.getCreditCount()).isEqualTo(BigDecimal.valueOf(9000, 2));
		assertThat(response.getCreditUserCount()).isEqualTo(3);
	}

	@Test
	public void getDataOverviewFailedWhenNoPermission() {
		assertException(PermissionDenyException.class, () -> {
			statisticsService.getStatisticsDataOverview(new BaseRequest());
		});
	}
}
