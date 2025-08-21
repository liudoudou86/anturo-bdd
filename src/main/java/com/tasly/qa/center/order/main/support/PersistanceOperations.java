package com.anturo.qa.center.order.main.support;

import com.panzer.tool.nosql.RedisTools;
import com.panzer.tool.sql.MySqlTools;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @Author Mayer Yu
 * @Date 2022/2/15 14:13
 **/
public class PersistanceOperations {

	private final static Map<String, Map<String, ? extends Map<String, Object>>> yaml = com.anturo.qa.center.order.main.support.AutoConfigureSelector.config();

	public static MySqlTools mysql(String platform) {
		String mysql_host = yaml.get("mysql").get(platform).get("host").toString();
		String mysql_port = yaml.get("mysql").get(platform).get("port").toString();
		String mysql_database = yaml.get("mysql").get(platform).get("database").toString();
		String mysql_username = yaml.get("mysql").get(platform).get("username").toString();
		String mysql_password = yaml.get("mysql").get(platform).get("password").toString();
		String JDBC = MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?useOldAliasMetadataBehavior=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false", mysql_host, mysql_port, mysql_database);
		return new MySqlTools(JDBC, mysql_username, mysql_password);
	}

	public static Jedis redis(String platform) {
		String redis_host = yaml.get("redis").get(platform).get("host").toString();
		int redis_port = Integer.parseInt(yaml.get("redis").get(platform).get("port").toString());
		int db = Integer.parseInt(yaml.get("redis").get(platform).get("db").toString());
		int max_active = Integer.parseInt(yaml.get("redis").get(platform).get("Max_Active").toString());
		int max_idle = Integer.parseInt(yaml.get("redis").get(platform).get("Max_Idle").toString());
		int max_wait = Integer.parseInt(yaml.get("redis").get(platform).get("Max_Wait").toString());
		boolean test_on_barrow = Boolean.parseBoolean(yaml.get("redis").get(platform).get("Test_On_Barrow").toString());
		String auth_password = yaml.get("redis").get(platform).get("auth_password").toString();
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(max_active);
		poolConfig.setMaxIdle(max_idle);
		poolConfig.setMaxWaitMillis(max_wait);
		poolConfig.setTestOnBorrow(test_on_barrow);
		Jedis redis = RedisTools.getRedis(redis_host, redis_port, poolConfig);
		redis.auth(auth_password);
		redis.select(db);
		return redis;
	}
}
