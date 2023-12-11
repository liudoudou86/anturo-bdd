package com.qa.operate.check;

import io.cucumber.datatable.DataTable;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Tesla Liu
 * @date 2022/11/22 11:02
 * 描述 年度资信加超额度信用金额校验
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class AnnualQuotaCheck {
    @Step(value = "年度资信加超额度信用金额-产品集合")
    public void CheckProductSetData(DataTable dataTable, Response interfaceResult) {
        List<Map<String, String>> testcaseData = dataTable.asMaps().stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("productSetCode", Optional.ofNullable(map.get("productSetCode")).orElse("").toString());
                    resultMap.put("annualAvailableExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualAvailableExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("annualOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaCreditLimit", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaAvailableAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaAvailableAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaIneffectiveAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaIneffectiveAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        List<Map<String, String>> interfaceData = interfaceResult.jsonPath().getList("data", Map.class).stream()
                .filter(map -> !Objects.equals(Optional.ofNullable(map.get("saleOrgCode")).orElse("").toString(), "1000003931")&&
                        !Objects.equals(Optional.ofNullable(map.get("productSetCode")).orElse("").toString(), ""))
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("productSetCode", Optional.ofNullable(map.get("productSetCode")).orElse("").toString());
                    resultMap.put("annualAvailableExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualAvailableExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("annualOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaCreditLimit", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaAvailableAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaAvailableAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaIneffectiveAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaIneffectiveAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        Assertions.assertThat(testcaseData).as("年度资信加超额度信用金额-产品集合数据校验无误").containsExactlyInAnyOrderElementsOf(interfaceData);
        log.info("年度资信加超额度信用金额-产品集合数据校验无误");
    }

    @Step(value = "年度资信加超额度信用金额-品种")
    public void CheckProductVarietyData(DataTable dataTable, Response interfaceResult) {
        List<Map<String, String>> testcaseData = dataTable.asMaps().stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("productSetCode", Optional.ofNullable(map.get("productSetCode")).orElse("").toString());
                    resultMap.put("annualAvailableExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualAvailableExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("annualOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaCreditLimit", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaAvailableAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaAvailableAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaIneffectiveAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaIneffectiveAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        List<Map<String, String>> interfaceData = interfaceResult.jsonPath().getList("data.list", Map.class).stream()
                .filter(map -> !Objects.equals(Optional.ofNullable(map.get("saleOrgCode")).orElse("").toString(), "1000003931"))
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("productSetCode", Optional.ofNullable(map.get("productSetCode")).orElse("").toString());
                    resultMap.put("annualAvailableExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualAvailableExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("annualOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaCreditLimit", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaAvailableAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaAvailableAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("exceedingQuotaIneffectiveAmount", new BigDecimal(Optional.ofNullable(map.get("exceedingQuotaIneffectiveAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        Assertions.assertThat(testcaseData).as("年度资信加超额度信用金额-品种数据校验无误").containsExactlyInAnyOrderElementsOf(interfaceData);
        log.info("年度资信加超额度信用金额-品种数据校验无误");
    }

    @Step(value = "年度资信加超额度信用金额-临时信用申请")
    public void CheckTemporaryCreditData(DataTable dataTable, List<Map<String, Object>> sqlResult) {
        List<Map<String, String>> testcaseData = dataTable.asMaps().stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("TYPE", Optional.ofNullable(map.get("TYPE")).orElse("").toString());
                    resultMap.put("TRADE_NO", Optional.ofNullable(map.get("TRADE_NO")).orElse("").toString());
                    resultMap.put("CREDIT_LIMIT", new BigDecimal(Optional.ofNullable(map.get("CREDIT_LIMIT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("OCCUPIED_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("OCCUPIED_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("FROZEN_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("FROZEN_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        List<Map<String, String>> sqlData = sqlResult.stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("TYPE", Optional.ofNullable(map.get("TYPE")).orElse("").toString());
                    resultMap.put("TRADE_NO", Optional.ofNullable(map.get("TRADE_NO")).orElse("").toString());
                    resultMap.put("CREDIT_LIMIT", new BigDecimal(Optional.ofNullable(map.get("CREDIT_LIMIT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("OCCUPIED_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("OCCUPIED_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("FROZEN_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("FROZEN_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        Assertions.assertThat(testcaseData).as("年度资信加超额度信用金额-临时信用申请数据校验无误").containsExactlyInAnyOrderElementsOf(sqlData);
        log.info("年度资信加超额度信用金额-临时信用申请数据校验无误");
    }

    @Step(value = "年度资信加超额度信用金额-信用流水")
    public void CheckCreditJounalData(DataTable dataTable, List<Map<String, Object>> sqlResult) {
        List<Map<String, String>> testcaseData = dataTable.asMaps().stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("CREDIT_TRADE_NO", Optional.ofNullable(map.get("CREDIT_TRADE_NO")).orElse("").toString());
                    resultMap.put("CREDIT_LIMIT", new BigDecimal(Optional.ofNullable(map.get("CREDIT_LIMIT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("OCCUPIED_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("OCCUPIED_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("FROZEN_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("FROZEN_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("OCCUPIED_CHANGE_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("OCCUPIED_CHANGE_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        List<Map<String, String>> sqlData = sqlResult.stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("CREDIT_TRADE_NO", Optional.ofNullable(map.get("CREDIT_TRADE_NO")).orElse("").toString());
                    resultMap.put("CREDIT_LIMIT", new BigDecimal(Optional.ofNullable(map.get("CREDIT_LIMIT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("OCCUPIED_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("OCCUPIED_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("FROZEN_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("FROZEN_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("OCCUPIED_CHANGE_AMOUNT", new BigDecimal(Optional.ofNullable(map.get("OCCUPIED_CHANGE_AMOUNT")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        Assertions.assertThat(testcaseData).as("年度资信加超额度信用金额-信用流水数据校验无误").containsExactlyInAnyOrderElementsOf(sqlData);
        log.info("年度资信加超额度信用金额-信用流水数据校验无误");
    }
}