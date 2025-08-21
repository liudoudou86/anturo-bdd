package com.anturo.qa.center.order.main.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author Tesla
 * @Date 2025/04/29
 * @Description
 */

public class ReadJsonFile {

	private static final ResourceLoader resourceLoader;

	static {
		resourceLoader = new DefaultResourceLoader();
	}

	/**
	 * 根据文件名读取位于resources/json目录下的JSON文件内容。
	 *
	 * @param fileName JSON文件的名称
	 * @return JSONObject 包含文件内容的JSONObject
	 * @throws IOException 如果文件读取失败
	 */
	public static JSONObject readJsonFromFile(String fileName) throws IOException {

		if (resourceLoader == null) {
			throw new IllegalStateException("ResourceLoader is not initialized.");
		}

		StringBuilder jsonStringBuilder = new StringBuilder();
		Resource resource = resourceLoader.getResource("classpath:data/order/delivery/" + fileName);

		try (InputStream inputStream = resource.getInputStream();
		     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				jsonStringBuilder.append(currentLine);
			}
		}

		String jsonString = jsonStringBuilder.toString();
		if (jsonString.isEmpty()) {
			throw new IOException("The JSON file is empty: " + fileName);
		}

		// 将字符串转换为JSONObject
		return JSON.parseObject(jsonString);
	}
}
