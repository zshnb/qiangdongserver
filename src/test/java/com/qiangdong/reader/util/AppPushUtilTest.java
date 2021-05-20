package com.qiangdong.reader.util;

import cn.hutool.json.JSONUtil;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.google.gson.JsonObject;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.config.AppPushConfig;
import com.qiangdong.reader.utils.AppPushUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppPushUtilTest extends BaseTest {

	@Autowired
	private AppPushUtil appPushUtil;
	@Autowired
	private AppPushConfig appPushConfig;
	private static String url  = "http://sdk.open.api.igexin.com/apiex.htm";

	@Test
	public void pushSuccessful() {
		String title = "墙洞推送";
		String text = "tttt";
		JsonObject json = new JsonObject();
		json.addProperty("type", "reserve");
		json.addProperty("id", userId);

		//安卓
		IPushResult result = appPushUtil.uniPushOffLine(String.valueOf(userId), title, text, json);
		assertThat(result.getResponse()).isNotNull();
	}

	/**
	 * 群推案例
	 */
	// @Test
	public void allPush() {
		IGtPush push = new IGtPush(url, appPushConfig.getAppKey(), appPushConfig.getMasterSecret());
		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
		LinkTemplate template = new LinkTemplate();
		template.setAppId(appPushConfig.getAppId());
		template.setAppkey(appPushConfig.getAppKey());
		template.setTitle("qiangdong");
		template.setText("hello,world~~~");
		template.setUrl("http://getui.com");
		List<String> appIds = new ArrayList<>();
		appIds.add(appPushConfig.getAppId());
		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
		AppMessage message = new AppMessage();
		message.setData(template);
		message.setAppIdList(appIds);
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);
		IPushResult ret = push.pushMessageToApp(message);
		System.out.println(ret.getResponse().toString());
	}
}
