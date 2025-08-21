package com.anturo.qa.center.order.bdd.order.checks;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
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
 * @Date 2024/05/23
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Slf4j
public class OrderCheckOrderManagement01 {

	@Step(value = "整单折扣计算校验")
	public void checkOrderDiscountCalc(DataTable dataTable, Response apiResponse) {
		List<Map<String, String>> caseData = dataTable.asMaps().stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("orderWideDiscountSubject", Optional.ofNullable(map.get("orderWideDiscountSubject")).orElse("").toString());
			resultMap.put("itemSapCode", Optional.ofNullable(map.get("itemSapCode")).orElse("").toString());
			resultMap.put("unitPrice", new BigDecimal(Optional.ofNullable(map.get("unitPrice")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("quantity", Optional.ofNullable(map.get("quantity")).orElse("").toString());
			resultMap.put("totalInline", new BigDecimal(Optional.ofNullable(map.get("totalInline")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("preDiscountTotal", new BigDecimal(Optional.ofNullable(map.get("preDiscountTotal")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("orderDiscountTotal", new BigDecimal(Optional.ofNullable(map.get("orderDiscountTotal")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			return resultMap;
		}).collect(Collectors.toList());
		List<Map<String, String>> apiData = apiResponse.jsonPath().getList("data.groupList[0].list", Map.class).stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("orderWideDiscountSubject", Optional.ofNullable(map.get("orderWideDiscountSubject")).orElse("").toString());
			resultMap.put("itemSapCode", Optional.ofNullable(map.get("itemSapCode")).orElse("").toString());
			resultMap.put("unitPrice", new BigDecimal(Optional.ofNullable(map.get("unitPrice")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("quantity", Optional.ofNullable(map.get("quantity")).orElse("").toString());
			resultMap.put("totalInline", new BigDecimal(Optional.ofNullable(map.get("totalInline")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("preDiscountTotal", new BigDecimal(Optional.ofNullable(map.get("preDiscountTotal")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("orderDiscountTotal", new BigDecimal(Optional.ofNullable(map.get("orderDiscountTotal")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			return resultMap;
		}).collect(Collectors.toList());
		Assertions.assertThat(caseData).as("当前整单折扣计算应准确无误（创建订单阶段）").containsExactlyInAnyOrderElementsOf(apiData);
		log.info("【✅PASS】检查当前整单折扣计算准确无误（创建订单阶段）");
	}

	@Step(value = "添加商品时获取商品价格校验")
	public void checkAddProductFetchPrice(List<Map<String, String>> testCaseData, Response apiResponse) {
		List<Map<String, String>> caseData = testCaseData.stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("saleOrgSapCode", Optional.ofNullable(map.get("saleOrgSapCode")).orElse("").toString());
			resultMap.put("customerSapCode", Optional.ofNullable(map.get("customerSapCode")).orElse("").toString());
			resultMap.put("customerRegionCode", Optional.ofNullable(map.get("customerRegionCode")).orElse("").toString());
			resultMap.put("itemCode", Optional.ofNullable(map.get("itemCode")).orElse("").toString());
			resultMap.put("price", new BigDecimal(Optional.ofNullable(map.get("price")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("distributionChannelCode", Optional.ofNullable(map.get("distributionChannelCode")).orElse("").toString());
			return resultMap;
		}).collect(Collectors.toList());
		List<Map<String, String>> apiData = apiResponse.jsonPath().getList("data", Map.class).stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("saleOrgSapCode", Optional.ofNullable(map.get("saleOrgSapCode")).orElse("").toString());
			resultMap.put("customerSapCode", Optional.ofNullable(map.get("customerSapCode")).orElse("").toString());
			resultMap.put("customerRegionCode", Optional.ofNullable(map.get("customerRegionCode")).orElse("").toString());
			resultMap.put("itemCode", Optional.ofNullable(map.get("itemCode")).orElse("").toString());
			resultMap.put("price", new BigDecimal(Optional.ofNullable(map.get("price")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("distributionChannelCode", Optional.ofNullable(map.get("distributionChannelCode")).orElse("").toString());
			return resultMap;
		}).collect(Collectors.toList());
		Assertions.assertThat(caseData).as("当前添加商品时获取商品价格应准确无误").containsExactlyInAnyOrderElementsOf(apiData);
		log.info("【✅PASS】检查当前添加商品时获取商品价格应准确无误");
	}

	@Step(value = "选择项目时获取商品价格校验")
	public void checkSelectProjectFetchPrice(List<Map<String, String>> testCaseData, Response apiResponse) {
		List<Map<String, String>> caseData = testCaseData.stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("saleOrgSapCode", Optional.ofNullable(map.get("saleOrgSapCode")).orElse("").toString());
			resultMap.put("customerSapCode", Optional.ofNullable(map.get("customerSapCode")).orElse("").toString());
			resultMap.put("customerRegionCode", Optional.ofNullable(map.get("customerRegionCode")).orElse("").toString());
			resultMap.put("itemCode", Optional.ofNullable(map.get("itemCode")).orElse("").toString());
			resultMap.put("price", new BigDecimal(Optional.ofNullable(map.get("price")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("distributionChannelCode", Optional.ofNullable(map.get("distributionChannelCode")).orElse("").toString());
			return resultMap;
		}).collect(Collectors.toList());
		List<Map<String, String>> apiData = apiResponse.jsonPath().getList("data.giftDetail", Map.class).stream().map(map -> {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("saleOrgSapCode", Optional.ofNullable(map.get("saleOrgSapCode")).orElse("").toString());
			resultMap.put("customerSapCode", Optional.ofNullable(map.get("customerEntSapCode")).orElse("").toString());
			resultMap.put("customerRegionCode", Optional.ofNullable(map.get("regionCode")).orElse("").toString());
			resultMap.put("itemCode", Optional.ofNullable(map.get("itemSapCode")).orElse("").toString());
			resultMap.put("price", new BigDecimal(Optional.ofNullable(map.get("price")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
			resultMap.put("distributionChannelCode", Optional.ofNullable(map.get("distributionChannelCode")).orElse("").toString());
			return resultMap;
		}).collect(Collectors.toList());
		Assertions.assertThat(caseData).as("当前选择项目时获取商品价格应准确无误").containsExactlyInAnyOrderElementsOf(apiData);
		log.info("【✅PASS】检查当前选择项目时获取商品价格应准确无误");
	}

}
