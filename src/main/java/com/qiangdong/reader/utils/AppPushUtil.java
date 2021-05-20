package com.qiangdong.reader.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.DictionaryAlertMsg;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.base.payload.MultiMedia.MediaType;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.google.gson.JsonObject;
import com.qiangdong.reader.common.AppPushConstant;
import com.qiangdong.reader.config.AppPushConfig;
import com.qiangdong.reader.dao.UserAppPushMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.UserAppPush;
import com.qiangdong.reader.enums.app_push.PushNetWorkTypeEnum;
import com.qiangdong.reader.enums.app_push.TransmissionTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * APP 推送工具
 */
@Component
public class AppPushUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppPushUtil.class);
	private final AppPushConfig appPushConfig;
	private final SnowflakeUtil snowflakeUtil;
	private final UserAppPushMapper appPushMapper;
	private final UserMapper userMapper;
	//初始化连接
	private IGtPush push;

	public AppPushUtil(AppPushConfig appPushConfig,
					   SnowflakeUtil snowflakeUtil,
					   UserAppPushMapper appPushMapper,
	                   UserMapper userMapper) {
		this.appPushConfig = appPushConfig;
		this.snowflakeUtil = snowflakeUtil;
		this.appPushMapper = appPushMapper;
		this.userMapper = userMapper;
		this.push = new IGtPush(appPushConfig.getUrl(), appPushConfig.getAppKey(), appPushConfig.getMasterSecret());
	}

	/***
	 * 离线模板处理，厂商通道
	 * @param title
	 * @param text
	 * @param param
	 * @return
	 */
	public TransmissionTemplate offLinetransmissionTemplate(String title, String text, JsonObject param) {
		//厂商通道离线推送注意类的使用，在线和离线使用不同
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appPushConfig.getAppId());
		template.setAppkey(appPushConfig.getAppKey());
		Notify notify = new Notify();
		notify.setTitle(title);
		notify.setContent(text);
		//这是厂商通道连接，
		// 注意参数 component=io.dcloud.xxx/io.dcloud.PandoraEntry，有xxx是你的包名，
		// S.payload这个值是你要穿透的内容，注意连接的长度，过长厂商通道会失效。
		String intent =
			"intent:#Intent;action=android.intent.action.oppopush;"
				+ "launchFlags=0x14000000;"
				+ "component=io.dcloud.UNI59F5329/io.dcloud.PandoraEntry;"
				+ "S.UP-OL-SU=true;"
				+ "S.title="+ title +"; S.content="+ text +";"
				+ "S.payload=" + param.toString() + ";end";
		notify.setIntent(intent);
		notify.setType(GtReq.NotifyInfo.Type._intent);
		//设置第三方通知
		template.set3rdNotifyInfo(notify);
		//ios处理通知消息
		APNPayload payload = new APNPayload();
		payload.setAutoBadge("+1");
		payload.setContentAvailable(0);
		payload.setSound("default");
		for (String key : param.keySet()) {
			payload.addCustomMsg(key, param.get(key).getAsString());
		}
		DictionaryAlertMsg alertMsg = new DictionaryAlertMsg();
		alertMsg.setTitle(title);
		alertMsg.setBody(text);
		payload.setAlertMsg(alertMsg);
		//ios
		template.setAPNInfo(payload);
		//这里也可设置穿透内容
		template.setTransmissionContent(param.toString());
		template.setTransmissionType(TransmissionTypeEnum.NOTIFICATION.code());
		return template;
	}

	/***
	 * 离线通道推送主方法
	 * @param cid
	 * @param title
	 * @param text
	 * @param paramJosn
	 */
	public IPushResult uniPushOffLine(String cid, String title, String text, JsonObject paramJosn) {
		TransmissionTemplate template = offLinetransmissionTemplate(title, text, paramJosn);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(AppPushConstant.OFFLINE_EXPIRETIME);
		message.setData(template);
		message.setPushNetWorkType(PushNetWorkTypeEnum.DEFAULT.code());
		Target target = new Target();
		target.setAppId(appPushConfig.getAppId());
		target.setClientId(cid);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
			if (ret != null) {
				LOGGER.info("推送成功，第三方返回：" + ret.getResponse().toString());
			} else {
				LOGGER.error("推送时第三方服务器响应异常");
			}
			return ret;
		} catch (RequestException e) {
			LOGGER.error("app push error:", e);
			// ret = push.pushMessageToSingle(message, target, e.getRequestId());
			return null;
		}
	}


	/***
	 * unipush 在线推送
	 * @param cid 客户唯一id
	 * @param title 标题
	 * @param text 文章
	 * @param penetrateParam
	 */
	public IPushResult uniPushOnline(String cid, String title, String text, String penetrateParam, String avatar) {
		NotificationTemplate template = onlineNotificationTemplate(title, text, penetrateParam, avatar);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appPushConfig.getAppId());
		target.setClientId(cid);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
			if (ret != null) {
				LOGGER.info("推送成功，第三方返回：" + ret.getResponse().toString());
			} else {
				LOGGER.error("推送时第三方服务器响应异常");
			}
			return ret;
		} catch (RequestException e) {
			LOGGER.error("app push error:", e);
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
			return null;
		}
	}


	/***
	 * 安卓和ios在线个推
	 * @param title
	 * @param text
	 * @param penetrateParam
	 * @return
	 */
	public NotificationTemplate onlineNotificationTemplate(String title, String text, String penetrateParam, String resUrl) {
		//这里可以看到在线和离线使用的不同类进行处理
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appPushConfig.getAppId());
		template.setAppkey(appPushConfig.getAppKey());
		template.setStyle(initPushStyle(title, text, resUrl));
		//ios处理
		APNPayload payload = new APNPayload();
		payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		DictionaryAlertMsg alertMsg = new DictionaryAlertMsg();
		alertMsg.setTitle(title);
		alertMsg.setBody(text);
		payload.setAlertMsg(alertMsg);
		MultiMedia multiMedia = new MultiMedia();
		multiMedia.setResUrl(resUrl);
		multiMedia.setResType(MediaType.pic);
		multiMedia.setOnlyWifi(false);
		multiMedia.setResId(String.valueOf(snowflakeUtil.generateSnowflakeId()));
		payload.addMultiMedia(multiMedia);
		template.setTransmissionType(TransmissionTypeEnum.NOTIFICATION.code());
		template.setTransmissionContent(penetrateParam);
		return template;
	}

	/**
	 * 通知信息模板
	 * @param title
	 * @param text
	 * @param resUrl
	 * @return
	 */
	public Style0 initPushStyle(String title, String text, String resUrl) {
		Style0 style = new Style0();
		style.setTitle(title);
		style.setText(text);
		// 配置通知栏图标
		// style.setLogo("icon.png");
		style.setLogoUrl(resUrl);
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		//style.setChannel("通知渠道id");
		//style.setChannelName("通知渠道名称");
		//设置通知渠道登记
		style.setChannelLevel(3);
		return style;
	}


	public Boolean pushSingleUserChat(Long receiver, String username, String avatar) {
		UserAppPush userAppPush = appPushMapper.selectOne(
			new QueryWrapper<UserAppPush>().eq("user_id", receiver));
		if (userAppPush == null) {
			return false;
		}
		JsonObject json = new JsonObject();
		json.addProperty("type", "reserve");
		json.addProperty("id", receiver);
		uniPushOnline(userAppPush.getClientId(), "墙洞", username + "给你发了一条私信", JSONUtil.toJsonPrettyStr(json), avatar);
		uniPushOffLine(userAppPush.getClientId(), "墙洞", username + "给你发了一条私信", json);
		return true;
	}
}
