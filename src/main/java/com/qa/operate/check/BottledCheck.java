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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Tesla Liu
 * @date 2022/12/21 09:41
 * 描述 铺底货校验
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class BottledCheck {

    @Step(value = "信用使用情况")
    public void CheckCreditUse(DataTable dataTable, Response interfaceResult) {
        List<Map<String, String>> testcaseData = dataTable.asMaps().stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("productVarietyCode", Optional.ofNullable(map.get("productVarietyCode")).orElse("").toString());
                    resultMap.put("annualAvailableExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualAvailableExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("annualOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        List<Map<String, String>> interfaceData = interfaceResult.jsonPath().getList("data.cusAnnVarCreditRespDtoList", Map.class).stream()
                .map(map -> {
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("productVarietyCode", Optional.ofNullable(map.get("productVarietyCode")).orElse("").toString());
                    resultMap.put("annualAvailableExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualAvailableExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualExecuteCreditLimit", new BigDecimal(Optional.ofNullable(map.get("annualExecuteCreditLimit")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    resultMap.put("annualOccupiedAmount", new BigDecimal(Optional.ofNullable(map.get("annualOccupiedAmount")).orElse("0").toString()).setScale(2, RoundingMode.HALF_UP).toString());
                    return resultMap;
                }).collect(Collectors.toList());
        Assertions.assertThat(testcaseData).as("信用使用情况-品种数据校验无误").containsExactlyInAnyOrderElementsOf(interfaceData);
        log.info("信用使用情况-品种数据校验无误");
    }

}
