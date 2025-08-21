package com.anturo.qa.center.order.bdd.order.checks;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Tesla
 * @Date 2025/04/29
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Slf4j
public class DeliveryCheckOrderManagement01 {

	@Step(value = "交货单内容校验")
	public void checkDelivery(List<Map<String, String>> caseDatas, List<Map<String, Object>> sqlResult) {
		List<Map<String, String>> caseData = caseDatas.stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("item_id", Optional.ofNullable(map.get("item_id")).orElse("").toString());
			resultMap.put("num", Optional.ofNullable(map.get("num")).orElse("").toString());
			resultMap.put("bwart", Optional.ofNullable(map.get("bwart")).orElse("").toString());
			resultMap.put("status", Optional.ofNullable(map.get("status")).orElse("").toString());
			return resultMap;
		}).collect(Collectors.toList());
		List<Map<String, String>> sqlData = sqlResult.stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("item_id", Optional.ofNullable(map.get("item_id")).orElse("").toString());
			resultMap.put("num", Optional.ofNullable(map.get("num")).orElse("").toString());
			resultMap.put("bwart", Optional.ofNullable(map.get("bwart")).orElse("").toString());
			resultMap.put("status", Optional.ofNullable(map.get("status")).orElse("").toString());
			return resultMap;
		}).collect(Collectors.toList());
		Assertions.assertThat(caseData).as("当前交货单数据应准确无误").containsExactlyInAnyOrderElementsOf(sqlData);
		log.info("【✅PASS】检查当前交货单数据准确无误");
	}
}
