package com.anturo.qa.center.order.bdd.order.apis;

import com.alibaba.fastjson.JSON;
import com.anturo.qa.center.order.main.model.dto.OrderAddProductFetchProductPriceDTO;
import com.anturo.qa.center.order.main.model.dto.OrderCustomerPreDiscountFetchProductPriceDTO;
import com.anturo.qa.center.order.main.model.dto.OrderRegionPreDiscountFetchProductPriceDTO;
import com.anturo.qa.center.order.main.model.dto.OrderSelectProjectFetchProductPriceDTO;
import com.anturo.qa.center.order.main.model.pojo.Domain;
import com.anturo.qa.center.order.main.support.RestfulApiInitializer;
import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

/**
 * @Author Tesla
 * @Date 2024/07/30
 * @Description
 */

@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class OrderApiPrediscountFetchPrice01 {

	private Domain medicineMgmtDomain = RestfulApiInitializer.platform("medicine-mgmt", false, true).getDomain();

	@Step(value = "[REQUEST]客户预折添加商品时获取商品价格")
	public Response customerPreDiscountFetchProductPrice(OrderCustomerPreDiscountFetchProductPriceDTO orderCustomerPreDiscountFetchProductPriceDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/queryProductAndPrice")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderCustomerPreDiscountFetchProductPriceDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]大区预折添加商品时获取商品价格")
	public Response regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO orderRegionPrediscountFetchProductPriceDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/queryProductAndPrice")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderRegionPrediscountFetchProductPriceDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]创建订单添加商品时获取商品价格")
	public Response orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO orderAddProductFetchProductPriceDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/queryDiscountAndPrice")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderAddProductFetchProductPriceDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]创建订单选择项目时获取商品价格")
	public Response orderSelectProjectFetchProductPrice(OrderSelectProjectFetchProductPriceDTO orderSelectProjectFetchProductPriceDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/queryOrderAllocationAndPrice")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(orderSelectProjectFetchProductPriceDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}
}
