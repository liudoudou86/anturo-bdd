package com.anturo.qa.center.order.bdd.order.checks;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Tesla
 * @Date 2024/06/04
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Slf4j
public class DiscountCheckAllocationPlan01 {

	@Step(value = "整单折扣分配计划分摊校验")
	public void checkDiscountAllocationPlan(List<Map<String, String>> caseDatas, List<Map<String, Object>> sqlResult) {
		List<Map<String, String>> caseData = caseDatas.stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("parentOrderNo", Optional.ofNullable(map.get("parentOrderNo")).orElse("").toString());
			resultMap.put("parentOrderItemNo", Optional.ofNullable(map.get("parentOrderItemNo")).orElse("").toString());
			resultMap.put("orderNo", Optional.ofNullable(map.get("orderNo")).orElse("").toString());
			resultMap.put("orderItemNo", Optional.ofNullable(map.get("orderItemNo")).orElse("").toString());
			resultMap.put("orderDiscountApplicationPlanId", Optional.ofNullable(map.get("orderDiscountApplicationPlanId")).orElse("").toString());
			resultMap.put("orderDiscountApplicationPlanDetailId", Optional.ofNullable(map.get("orderDiscountApplicationPlanDetailId")).orElse("").toString());
			resultMap.put("operateType", Optional.ofNullable(map.get("operateType")).orElse("").toString());
			resultMap.put("operateAmount", new BigDecimal(Optional.ofNullable(map.get("operateAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			return resultMap;
		}).collect(Collectors.toList());
		List<Map<String, String>> sqlData = sqlResult.stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("parentOrderNo", Optional.ofNullable(map.get("parentOrderNo")).orElse("").toString());
			resultMap.put("parentOrderItemNo", Optional.ofNullable(map.get("parentOrderItemNo")).orElse("").toString());
			resultMap.put("orderNo", Optional.ofNullable(map.get("orderNo")).orElse("").toString());
			resultMap.put("orderItemNo", Optional.ofNullable(map.get("orderItemNo")).orElse("").toString());
			resultMap.put("orderDiscountApplicationPlanId", Optional.ofNullable(map.get("orderDiscountApplicationPlanId")).orElse("").toString());
			resultMap.put("orderDiscountApplicationPlanDetailId", Optional.ofNullable(map.get("orderDiscountApplicationPlanDetailId")).orElse("").toString());
			resultMap.put("operateType", Optional.ofNullable(map.get("operateType")).orElse("").toString());
			resultMap.put("operateAmount", new BigDecimal(Optional.ofNullable(map.get("operateAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			return resultMap;
		}).collect(Collectors.toList());
		Assertions.assertThat(caseData).as("当前整单折扣分配计划分摊应准确无误（创建订单阶段）").containsExactlyInAnyOrderElementsOf(sqlData);
		log.info("【✅PASS】检查当前整单折扣分配计划分摊准确无误（创建订单阶段）");
	}

}
