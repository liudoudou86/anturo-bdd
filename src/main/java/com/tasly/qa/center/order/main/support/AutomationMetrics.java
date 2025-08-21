package com.anturo.qa.center.order.main.support;


import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;

/**
 * @Author yuzhonghua
 * @Date 2023/06/14 11:09:06
 * @Description
 **/
@Slf4j
public class AutomationMetrics {

	private static String year;

	private static Jedis redis;

	private static HTTPServer httpServer;

	private static final Gauge AUTOMATION_MILESTONE_TOTAL_COUNTER = Gauge.build()
			.name("automation_milestone_total_counter")
			.help("Gauge - automation milestone total count of automation app executed.")
			.labelNames("app", "year")
			.register();
	private static final Counter APP_REALTIME_TOTAL_COUNTER = Counter.build()
			.name("automation_app_realtime_total_counter")
			.help("Counter - realtime total count of automation app executed.")
			.labelNames("app")
			.register();
	private static final Counter APP_REALTIME_PASSED_COUNTER = Counter.build()
			.name("automation_app_realtime_passed_counter")
			.help("Counter - realtime passed count of automation app executed.")
			.labelNames("app")
			.register();
	private static final Counter FEATURE_REALTIME_TOTAL_COUNTER = Counter.build()
			.name("automation_feature_realtime_total_counter")
			.help("Counter - realtime total count of automation feature executed.")
			.labelNames("app", "feature")
			.register();
	private static final Counter FEATURE_REALTIME_PASSED_COUNTER = Counter.build()
			.name("automation_feature_realtime_passed_counter")
			.help("Counter - realtime passed count of automation feature executed.")
			.labelNames("app", "feature")
			.register();
	private static final Counter SCENARIO_REALTIME_TOTAL_COUNTER = Counter.build()
			.name("automation_scenario_realtime_total_counter")
			.help("Counter - realtime total count of automation scenario executed.")
			.labelNames("app", "feature", "scenario")
			.register();
	private static final Counter SCENARIO_REALTIME_PASSED_COUNTER = Counter.build()
			.name("automation_scenario_realtime_passed_counter")
			.help("Counter - realtime passed count of automation scenario executed.")
			.labelNames("app", "feature", "scenario")
			.register();

	static {
		try {
			year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			final String IPv4 = InetAddress.getLocalHost().getHostAddress();
			log.info("【IPv4】：当前本机IPv4地址为：" + IPv4);
			redis = com.anturo.qa.center.order.main.support.PersistanceOperations.redis("qa");
			log.info("【Redis】：埋点数据缓存服务已启动.");
			httpServer = new HTTPServer(IPv4, 10004, false);
			DefaultExports.initialize();
			log.info("【Prometheus】：埋点数据采集服务已启动.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void preMetrics(String app, String feature, String scenario) {
		Double milestoneTotalCounter = Double.valueOf(redis.incr("anturo-center-order-milestone-total-counter"));
		AUTOMATION_MILESTONE_TOTAL_COUNTER.labels(app, year).set(milestoneTotalCounter);
		APP_REALTIME_TOTAL_COUNTER.labels(app).inc();
		FEATURE_REALTIME_TOTAL_COUNTER.labels(app, feature).inc();
		SCENARIO_REALTIME_TOTAL_COUNTER.labels(app, feature, scenario).inc();
		log.info("【前置埋点】：「{}」→「{}」→「{}」埋点数据采集完毕.", app, feature, scenario);
	}

	public static void postMetrics(String app, String feature, String scenario) {
		APP_REALTIME_PASSED_COUNTER.labels(app).inc();
		FEATURE_REALTIME_PASSED_COUNTER.labels(app, feature).inc();
		SCENARIO_REALTIME_PASSED_COUNTER.labels(app, feature, scenario).inc();
		log.info("【后置埋点】：「{}」→「{}」→「{}」埋点数据采集完毕.", app, feature, scenario);
	}

	public static void stop() {
		httpServer.stop();
	}
}