package com.anturo.qa.center.order.main.support;

import com.panzer.core.util.YamlUtils;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @Author yuzhonghua
 * @Date 2023/04/06 15:00:47
 * @Description
 **/
public class AutoConfigureSelector {
	public static Map<String, Map<String, ? extends Map<String, Object>>> config() {
		Map<String, Map<String, ? extends Map<String, Object>>> entranceYaml = YamlUtils.loadYamlConfig(ClassLoader.getSystemResourceAsStream("config/ApplicationConfig.yaml"));
		String profile = entranceYaml.get("bdd").get("profile").get("active").toString();
		Map<String, Map<String, ? extends Map<String, Object>>> yaml = YamlUtils.loadYamlConfig(ClassLoader.getSystemResourceAsStream(MessageFormat.format("config/ApplicationConfig-{0}.yaml", profile)));
		return yaml;
	}
}
