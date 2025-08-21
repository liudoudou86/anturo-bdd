package com.anturo.qa.center.order.bdd.order.apis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.anturo.qa.center.order.main.model.dto.OrderDataDTO;
import com.anturo.qa.center.order.main.model.dto.OrderDiscountCalcCalculateReqDTO;
import com.anturo.qa.center.order.main.model.dto.OrderQueryPageByConditionDTO;
import com.anturo.qa.center.order.main.model.pojo.Domain;
import com.anturo.qa.center.order.main.support.ReadJsonFile;
import com.anturo.qa.center.order.main.support.RestfulApiInitializer;
import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.Optional;


/**
 * @Author Tesla
 * @Date 2024/05/23
 * @Description
 */

@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class OrderApiOrderManagement01 {

	private ReadJsonFile readJsonFile;

	private Domain orderCenterDomain = RestfulApiInitializer.platform("order-center", false, true).getDomain();

	private Domain medicineMgmtDomain = RestfulApiInitializer.platform("medicine-mgmt", false, true).getDomain();

	@Step(value = "[REQUEST]整单折扣计算")
	public Response discountCalc(OrderDataDTO orderDataDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/fulfillment/discount/average/calculate")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderDataDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]整单折扣计算后数据")
	public OrderDiscountCalcCalculateReqDTO discountCalcAfterData(OrderDataDTO orderDataDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/fulfillment/discount/average/calculate")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderDataDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		String data = JSONPath.read(response.asString(), String.format("$.data")).toString();
		OrderDiscountCalcCalculateReqDTO calcAfterData = JSON.parseObject(data, OrderDiscountCalcCalculateReqDTO.class);
		return calcAfterData;
	}

	@Step(value = "[REQUEST]整单折扣计算数据异常")
	public Response discountCalcAfterDataErr(OrderDataDTO orderDataDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/fulfillment/discount/average/calculate")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderDataDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(400)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]创建订单校验")
	public Response createOrderVerify(OrderDataDTO orderDataDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/verify")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderDataDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}


	@Step(value = "[REQUEST]创建订单校验-提示拦截")
	public Response createOrderBefore(OrderDataDTO orderDataDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/createOrderBefore")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderDataDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]创建订单提交")
	public Response createOrderSubmit(OrderDataDTO orderDataDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/createOrder")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderDataDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]订单管理查询订单")
	public String selectOrderInfo(OrderQueryPageByConditionDTO orderQueryPageByConditionDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/queryPageByCondition")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderQueryPageByConditionDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		String orderNo = Optional.ofNullable(response.jsonPath().getString("data.records[0].orderNo")).orElse("B2B-ORDER-FAILURE");
		log.info("[订单号]：{}", orderNo);
		return orderNo;
	}


}
