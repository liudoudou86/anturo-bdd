package com.anturo.qa.center.order.main.support;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.util.Lists;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author yuzhonghua
 * @Date 2023/09/22 13:09:21
 * @Description
 **/
@SuppressWarnings("all")
@Slf4j
public class MiddlewareOperations {

	private static Map<String, Map<String, ? extends Map<String, Object>>> yaml = com.anturo.qa.center.order.main.support.AutoConfigureSelector.config();

	/**
	 * 【REQUEST】手动获取指定TOPIC中开始时间~结束时间的最新MQ消息
	 *
	 * @param beginTimeMills
	 * @param endTimeMills
	 * @param topic
	 * @return
	 */
	private static String manualQueryMQ(String beginTimeMills, String endTimeMills, String topic) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.warn(MessageFormat.format("开始手动获取[TOPIC:{0}]中开始时间[{1}]~结束时间[{2}]的最新MQ消息", topic, sdf.format(Long.parseLong(beginTimeMills)), sdf.format(Long.parseLong(endTimeMills))));
		String msgId = "";
		Response response = SerenityRest.given()
				.baseUri("http://10.6.3.13:30756").basePath("/message/queryMessageByTopic.query")
				.contentType(ContentType.JSON.toString())
				.queryParams("begin", beginTimeMills, "end", endTimeMills, "topic", topic)
				.log().all().when()
				.get().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		List<String> msgIds = response.jsonPath().getList("data.msgId", String.class);
		if (msgIds.size() == 0) {
			log.warn(MessageFormat.format("[TOPIC:{0}]中开始时间[{1}]~结束时间[{2}]暂无任何消息！", topic,
					sdf.format(Long.parseLong(beginTimeMills)),
					sdf.format(Long.parseLong(endTimeMills))
			));
			msgId = "EMPTY-MSGID";
		} else {
			msgId = Optional.ofNullable(msgIds.get(0)).orElse("NULL-MSGID");
		}
		return msgId;
	}

	/**
	 * 【REQUEST】手动获取指定TOPIC中开始时间~结束时间的最新MQ消息（批量）
	 *
	 * @param beginTimeMills
	 * @param endTimeMills
	 * @param topic
	 * @return
	 */
	private static List<String> manualBatchQueryMQ(String beginTimeMills, String endTimeMills, String topic) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.warn(MessageFormat.format("开始手动获取[TOPIC:{0}]中开始时间[{1}]~结束时间[{2}]的最新MQ消息", topic, sdf.format(Long.parseLong(beginTimeMills)), sdf.format(Long.parseLong(endTimeMills))));
		Response response = SerenityRest.given()
				.baseUri("http://10.6.3.13:30756").basePath("/message/queryMessageByTopic.query")
				.contentType(ContentType.JSON.toString())
				.queryParams("begin", beginTimeMills, "end", endTimeMills, "topic", topic)
				.log().all().when()
				.get().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		List<String> msgIds = Optional.ofNullable(response.jsonPath().getList("data.msgId", String.class)).orElse(Lists.emptyList());
		if (msgIds.size() == 0) {
			log.warn(MessageFormat.format("[TOPIC:{0}]中开始时间[{1}]~结束时间[{2}]暂无任何消息！", topic,
					sdf.format(Long.parseLong(beginTimeMills)),
					sdf.format(Long.parseLong(endTimeMills))
			));
		}
		return msgIds;
	}

	/**
	 * 【REQUEST】手动消费指定CONSUMER-GROUP、TOPIC、MSGID的MQ消息
	 *
	 * @param consumerGroup
	 * @param topic
	 * @param msgId
	 * @return
	 */
	private static String manualConsumeMQ(String consumerGroup, String topic, String msgId) {
		log.info(MessageFormat.format("开始手动消费[CONSUMER-GROUP:{0}]、[TOPIC:{1}]、[MSGID:{2}]的MQ消息", consumerGroup, topic, msgId));
		Response response = SerenityRest.given()
				.baseUri("http://10.6.3.13:30756").basePath("/message/consumeMessageDirectly.do")
				.contentType(ContentType.JSON.toString())
				.queryParams("consumerGroup", consumerGroup, "topic", topic, "msgId", msgId)
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		if (response.jsonPath().getInt("status") == 0) {
			log.info("已通过API重发消息ID<{}>成功.", msgId);
		} else {
			log.warn("通过API重发消息ID<{}>失败，请检查！", msgId);
		}
		return MessageFormat.format("[CONSUMER-GROUP:{0}]、[TOPIC:{1}]、[MSGID:{2}]", consumerGroup, topic, msgId);
	}

	/**
	 * 【REQUEST】手动检查指定CONSUMER-GROUP、TOPIC、MSGID的MQ消息内容
	 *
	 * @param consumerGroup
	 * @param topic
	 * @param msgId
	 * @return
	 */
	private static String manualCheckMQ(String consumerGroup, String topic, String msgId) {
		log.info(MessageFormat.format("开始手动检查[CONSUMER-GROUP:{0}]、[TOPIC:{1}]、[MSGID:{2}]的MQ消息内容", consumerGroup, topic, msgId));
		String msgBody = "";
		Response response = SerenityRest.given()
				.baseUri("http://10.6.3.13:30756").basePath("/message/viewMessage.query")
				.contentType(ContentType.JSON.toString())
				.queryParams("topic", topic, "msgId", msgId)
				.log().all().when()
				.get().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		if (response.jsonPath().getInt("status") == 0) {
			log.info("已通过API检查消息ID<{}>消息内容成功.", msgId);
			msgBody = Optional.ofNullable(response.jsonPath().getString("data.messageView.messageBody")).orElse("NOTHING-MSG-BODY");
		} else {
			log.warn("通过API检查消息ID<{}>消息内容失败，请检查！", msgId);
			msgBody = "NULL-MSG-BODY";
		}
		return msgBody;
	}

	/**
	 * 手动消费MQ入口方法
	 *
	 * @param internalMills
	 * @param consumerGroup
	 * @param topic
	 * @return
	 */
	public static String manualConsumeMQ(long internalMills, String consumerGroup, String topic) {
		String profileUpperCase = yaml.get("bdd").get("profile").get("current").toString().toUpperCase();
		long currentTimeMills = System.currentTimeMillis();
		String msgId = manualQueryMQ(String.valueOf(currentTimeMills - internalMills), String.valueOf(currentTimeMills), String.format("%s-%s", topic.toUpperCase(), profileUpperCase));
		String mq = manualConsumeMQ(String.format("%s-%s", consumerGroup.toUpperCase(), profileUpperCase), String.format("%s-%s", topic.toUpperCase(), profileUpperCase), msgId);
		return mq;
	}

	/**
	 * 手动检查MQ入口方法
	 *
	 * @param internalMills
	 * @param consumerGroup
	 * @param topic
	 * @return
	 */
	public static List<String> manualCheckMQ(long internalMills, String consumerGroup, String topic) {
		List<String> msgBodys = new ArrayList<>();
		String profileUpperCase = yaml.get("bdd").get("profile").get("current").toString().toUpperCase();
		long currentTimeMills = System.currentTimeMillis();
		List<String> msgIds = manualBatchQueryMQ(String.valueOf(currentTimeMills - internalMills), String.valueOf(currentTimeMills), String.format("%s-%s", topic.toUpperCase(), profileUpperCase));
		msgIds.forEach(msgId -> {
			String msgBody = manualCheckMQ(String.format("%s-%s", consumerGroup.toUpperCase(), profileUpperCase), String.format("%s-%s", topic.toUpperCase(), profileUpperCase), msgId);
			msgBodys.add(msgBody);
		});
		log.info("【msgIds】：" + msgIds);
		log.info("【msgBodys】：" + msgBodys);
		return msgBodys;
	}
}
