package com.anturo.qa.center.order.bdd.order.apis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anturo.qa.center.order.main.model.dto.OmsReturnReverseEccIdDTO;
import com.anturo.qa.center.order.main.model.dto.ReturnOrderAcceptRespDTO;
import com.anturo.qa.center.order.main.model.dto.ReturnOrderRespDTO;
import com.anturo.qa.center.order.main.model.dto.ReturnOrderSelectIdDTO;
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
import org.apache.groovy.util.Maps;
import org.hamcrest.Matchers;

import java.util.Optional;

/**
 * @Author Tesla
 * @Date 2025/05/06
 * @Description
 */

@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class OrderApiReturnOrderManagement01 {

	private Domain orderCenterDomain = RestfulApiInitializer.platform("order-center", false, true).getDomain();

	private Domain medicineMgmtDomain = RestfulApiInitializer.platform("medicine-mgmt", false, true).getDomain();

	@Step(value = "[REQUEST]退货订单提交申请")
	public Response returnOrderSubmit(ReturnOrderRespDTO returnOrderRespDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/return/saveReturnOrder")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(returnOrderRespDTO, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]退货订单管理查询退货主表ID")
	public String returnOrderSelectId(ReturnOrderSelectIdDTO returnOrderSelectIdDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/return/queryPageByCondition")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(returnOrderSelectIdDTO))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		String returnOrderId = Optional.ofNullable(response.jsonPath().getString("data.records[0].id")).orElse("B2B-ORDER-FAILURE");
		log.info("[退货主表ID]：{}", returnOrderId);
		return returnOrderId;
	}

	@Step(value = "[REQUEST]退货订单商务审核")
	public Response returnOrderBusinessReview(String returnOrderId) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/return/approveBusinessReview")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(Maps.of("returnOrderId", returnOrderId), SerializerFeature.PrettyFormat))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]退货订单运营审核")
	public Response returnOrderManualReview(String returnOrderId) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/return/approveManualReview")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(Maps.of("returnOrderId", returnOrderId), SerializerFeature.PrettyFormat))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]退货订单验收审核")
	public Response returnOrderAcceptReview(ReturnOrderAcceptRespDTO returnOrderAcceptRespDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/return/acceptReturnOrder")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(returnOrderAcceptRespDTO, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue))
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
		return response;
	}

	@Step(value = "[REQUEST]模拟SAP回调退货ECCID")
	public void omsReturnReverseEccId(OmsReturnReverseEccIdDTO omsReturnReverseEccIdDTO) {
		SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/returnOrder/callback/receive/receiveReturnOrderCreateCallBack")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", orderCenterDomain.getToken(),
						"signature", "88888888"))
				.body(JSON.toJSONString(omsReturnReverseEccIdDTO, SerializerFeature.PrettyFormat))
				.log().all()
				.when().post()
				.then().log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
	}

}
