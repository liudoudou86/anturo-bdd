package com.qa.operate.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qa.tool.TimeUtil;
import com.qa.tool.YamlUtil;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Tesla Liu
 * @date 2022/11/23 09:53
 * 描述 信用系统操作
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class CreditOrderOperation {

    static String url = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Address.Credit"));

    @Step(value = "登录折让系统-首页登录")
    public static String loginRebateSystem01() {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/identity/v3/captcha")
                .contentType(ContentType.JSON)
                .queryParam("time","0.3018877418067274").log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .extract().response();
        String uniqueId = Optional.ofNullable(response.jsonPath().getString("data.uniqueId")).orElse("NO-UniqueId");
        log.info("Rebate_uniqueId = " + uniqueId);
        return uniqueId;
    }

    @Step(value = "登录折让系统-获取token")
    public static String loginRebateSystem02() {
        String uniqueId = loginRebateSystem01();

        JSONObject paramenters = new JSONObject();
        paramenters.put("userName", "yuanxuehong");
        paramenters.put("password", "VEBzbHk3ODkj");
        paramenters.put("uniqueid", uniqueId);
        paramenters.put("checkCodeUniqueId", uniqueId);
        paramenters.put("code", "");
        paramenters.put("instanceId", 1);
        paramenters.put("loginSource", "8406099850");
        paramenters.put("loginType", 0);
        paramenters.put("tenantId", 1);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/identity/v2/user/token")
                .contentType(ContentType.JSON.toString())
                .header("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a")
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        String TOKEN = Optional.ofNullable(response.jsonPath().getString("data.token")).orElse("NO-TOKEN");
        log.info("Rebate_Token = " + TOKEN);
        return TOKEN;
    }

    @Step(value = "查询折让大区上限金额")
    public static String queryRegionLimitRebateAmount() {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", CreditOrderOperation.loginRebateSystem02());
        // 请求参数
        Map<String ,String> paramenters = new HashMap<String, String>();
        paramenters.put("regionCode", "370000");
        paramenters.put("saleOrgCode", "1000006570");
        paramenters.put("firstLevelReceivingEnterpriseCode", "1000002261");

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/rebate/v1/rebate/query/queryRebateAvailableAmount")
                .contentType(ContentType.URLENC)
                .urlEncodingEnabled(true)
                .headers(headers)
                .params(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        BigDecimal regionLimitRebateAmount = new BigDecimal(Optional.ofNullable(response.jsonPath().getString("data.regionLimitRebateAmount")).orElse("NO-AMOUNT")).setScale(2, RoundingMode.HALF_UP);
        String regionLimitRebateAmountStr = new DecimalFormat("#.##").format(regionLimitRebateAmount);
        return regionLimitRebateAmountStr;
    }

    @Step(value = "查询折让当前客户金额")
    public static String queryThisUserRemainRebateAmount() {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", CreditOrderOperation.loginRebateSystem02());
        // 请求参数
        Map<String ,String> paramenters = new HashMap<String, String>();
        paramenters.put("regionCode", "370000");
        paramenters.put("saleOrgCode", "1000006570");
        paramenters.put("firstLevelReceivingEnterpriseCode", "1000002261");

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/rebate/v1/rebate/query/queryRebateAvailableAmount")
                .contentType(ContentType.URLENC)
                .urlEncodingEnabled(true)
                .headers(headers)
                .params(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        BigDecimal regionLimitRebateAmount = new BigDecimal(Optional.ofNullable(response.jsonPath().getString("data.enterpriseAvailableRebateAmount")).orElse("NO-AMOUNT")).setScale(2, RoundingMode.HALF_UP);
        String regionLimitRebateAmountStr = new DecimalFormat("#.##").format(regionLimitRebateAmount);
        return regionLimitRebateAmountStr;
    }

    @Step(value = "查询信用客户产品集合")
    public Response queryProductSet(String customerEntCode) {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());
        // 构建JSON对象
        JSONObject filter = new JSONObject();
        filter.put("customerEntCode", customerEntCode);
        // 请求参数
        Map<String ,String> paramenters = new HashMap<String, String>();
        paramenters.put("filter", JSON.toJSONString(filter));
        paramenters.put("pageNum", "1");
        paramenters.put("pageSize", "10");

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/medicine-mgmt/v1/credit/annuals/summary/product-set")
                .contentType(ContentType.JSON.toString())
                .headers(headers)
                .params(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Step(value = "查询信用客户品种")
    public Response queryProductVariety(String customerEntCode) {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());
        // 构建JSON对象
        JSONObject filter = new JSONObject();
        filter.put("customerEntCode", customerEntCode);
        // 请求参数
        Map<String ,String> paramenters = new HashMap<String, String>();
        paramenters.put("filter", JSON.toJSONString(filter));
        paramenters.put("pageNum", "1");
        paramenters.put("pageSize", "10");

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/medicine-mgmt/v1/credit/annuals/product-variety/page")
                .contentType(ContentType.JSON.toString())
                .headers(headers)
                .params(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Step(value = "停用某品种")
    public Response disableProductVariety(String customerEntCode, String productVarietyCode, String saleOrgCode) {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());
        // 构建JSON对象
        JSONObject paramenters = new JSONObject();
        paramenters.put("customerEntCode", customerEntCode);
        paramenters.put("productVarietyCode", productVarietyCode);
        paramenters.put("saleOrgCode", saleOrgCode);
        paramenters.put("state", 0);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/medicine-mgmt/v1/credit/annuals")
                .contentType(ContentType.JSON.toString())
                .headers(headers)
                .body(paramenters).log().all()
                .when().put()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Step(value = "月度结转")
    public Response MonthTransfer() {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/credit/v1/credit/temporaries/transfer-balance")
                .contentType(ContentType.JSON.toString())
                .headers(headers).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Step(value = "手动生效")
    public Response AnnualEffectuate() {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());

        Response response = SerenityRest.given()
                .baseUri(url).basePath("api/credit/v1/credit/detail/annuals/effectuate")
                .contentType(ContentType.JSON.toString())
                .headers(headers).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Step(value = "订单退货")
    public Response orderReturn(String orderId, JSONArray b2border) {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());
        // 构建JSON对象
        JSONObject filter = new JSONObject();
        // 构建JSON对象
        JSONObject paramenters = new JSONObject();
        paramenters.put("extFields", filter);
        paramenters.put("instanceId", 0);
        paramenters.put("orderItems", b2border);
        paramenters.put("requestId", "OCCUPY-" + TimeUtil.randomTimeStamp() + "-" + orderId);
        paramenters.put("tenantId", 0);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/credit/v1/credit/release/refund")
                .contentType(ContentType.JSON.toString())
                .headers(headers)
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Step(value = "订单退货冲销")
    public Response orderReturnWriteOff(String orderId, JSONArray b2border) {
        // 请求头
        Map<String ,String> headers = new HashMap<String, String>();
        headers.put("Application-Key", "fff40a74a4dc2a11bf0d20bfc2ae8f7a");
        headers.put("Access-Token", loginRebateSystem02());
        // 构建JSON对象
        JSONObject filter = new JSONObject();
        // 构建JSON对象
        JSONObject paramenters = new JSONObject();
        paramenters.put("extFields", filter);
        paramenters.put("instanceId", 0);
        paramenters.put("orderItems", b2border);
        paramenters.put("requestId", "OCCUPY-" + TimeUtil.randomTimeStamp() + "-" + orderId);
        paramenters.put("tenantId", 0);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/api/credit/v1/credit/release/refund/write-off")
                .contentType(ContentType.JSON.toString())
                .headers(headers)
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

}
