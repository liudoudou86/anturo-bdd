package com.anturo.qa.center.order.bdd.order.apis;

import com.anturo.qa.center.order.main.model.dto.OmsReturnPositiveEccIdDTO;
import com.anturo.qa.center.order.main.model.dto.OrderApproveShippingInfoDTO;
import com.anturo.qa.center.order.main.model.dto.OrderDeliveryDTO;
import com.anturo.qa.center.order.main.model.pojo.Domain;
import com.anturo.qa.center.order.main.support.ReadJsonFile;
import com.anturo.qa.center.order.main.support.RestfulApiInitializer;
import com.google.common.collect.ImmutableMap;
import com.panzer.core.util.FreeMarkerUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.hamcrest.Matchers;

import java.io.IOException;
import java.util.List;

/**
 * @Author Tesla
 * @Date 2025/04/28
 * @Description
 */

@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class OrderApiDelivery01 {

	private ReadJsonFile readJsonFile;

	private Domain orderCenterDomain = RestfulApiInitializer.platform("order-center", false, true).getDomain();

	private Domain medicineMgmtDomain = RestfulApiInitializer.platform("medicine-mgmt", false, true).getDomain();

	@Step(value = "[REQUEST]复制订单提交")
	public Response copyOrderSubmit(String sapCode) {
		Response response = null;
		try {
			response = SerenityRest.given()
					.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
					.basePath("/api/medicine-mgmt/v1/purchase/order/createOrder")
					.contentType(ContentType.JSON)
					.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
							"signature", "88888888"))
					.body(readJsonFile.readJsonFromFile(sapCode + ".json").toString())
					.log().all().when()
					.post().then()
					.log().all()
					.statusCode(200)
					.extract().response();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	@Step(value = "[REQUEST]商务审核")
	public void orderApprove(OrderApproveShippingInfoDTO orderApproveShippingInfoDTO) {
		SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", medicineMgmtDomain.getProtocal(), medicineMgmtDomain.getHost(), medicineMgmtDomain.getPort()))
				.basePath("/api/medicine-mgmt/v1/purchase/order/business-review/approve")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", medicineMgmtDomain.getToken(),
						"signature", "88888888"))
				.body(orderApproveShippingInfoDTO)
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
	}

	@Step(value = "[REQUEST]下发OMS")
	public void orderSendToOmsTask() {
		SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/purchase/order/startOrderSendToSapTask")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", orderCenterDomain.getToken(),
						"signature", "88888888"))
				.log().all().when()
				.get().then()
				.log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
	}

	@Step(value = "[REQUEST]模拟SAP回调正向ECCID")
	public void omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO omsReturnPositiveEccIdDTO) {
		SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/callback/receive/orderCreateCallBack")
				.contentType(ContentType.JSON)
				.headers(ImmutableMap.of("ACCESS-TOKEN", orderCenterDomain.getToken(),
						"signature", "88888888"))
				.body(omsReturnPositiveEccIdDTO).log().all()
				.when().post()
				.then().log().all()
				.statusCode(200)
				.body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"))
				.extract().response();
	}

	@Step(value = "【REQUEST】创建正向交货单过账发货")
	public Response createPositiveDeliveryShipment(List<OrderDeliveryDTO> orderDeliveryDTO) {
		String BODY = "NO-BODY";
		final String BODY_TEMPLATE = "<B2B_DELIVERY_ITEM_ROOT>\n" +
				"        <BASEINFO>\n" +
				"                <S_SYSTEM>GERP</S_SYSTEM>\n" +
				"                <SERVICENAME>B2B203</SERVICENAME>\n" +
				"                <T_SYSTEM>b2b</T_SYSTEM>\n" +
				"                <RETRY/>\n" +
				"        </BASEINFO>\n" +
				"        <#list orderDeliveryDTO as orderDeliveryDTO>\n" +
				"          <DELIVERY_ITEM>\n" +
				"                  <WADAT_IST>${orderDeliveryDTO.date}</WADAT_IST>\n" +
				"                  <VKORG>1017</VKORG>\n" +
				"                  <VTWEG>22</VTWEG>\n" +
				"                  <SPART>01</SPART>\n" +
				"                  <VBELN>${orderDeliveryDTO.deliverNo}</VBELN>\n" +
				"                  <POSNR>${orderDeliveryDTO.orderItem}</POSNR>\n" +
				"                  <LGORT>1201</LGORT>\n" +
				"                  <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
				"                  <CHARG>202504</CHARG>\n" +
				"                  <MATNR/>\n" +
				"                  <LFIMG>${orderDeliveryDTO.batchNum}</LFIMG>\n" +
				"                  <VGBEL>${orderDeliveryDTO.eccid}</VGBEL>\n" +
				"                  <VGPOS>0000${orderDeliveryDTO.orderItem}</VGPOS>\n" +
				"                  <KWMENG>${orderDeliveryDTO.orderItemQuantityTotal}</KWMENG>\n" +
				"                  <BSTKD>${orderDeliveryDTO.orderNo}</BSTKD>\n" +
				"                  <B2B_ORDER_ID>${orderDeliveryDTO.orderNo}</B2B_ORDER_ID>\n" +
				"                  <B2B_ITEM_ID>${orderDeliveryDTO.orderItem}</B2B_ITEM_ID>\n" +
				"                  <CARRIER_ID/>\n" +
				"                  <YUNDAN_NO/>\n" +
				"                  <UECHA>000000</UECHA>\n" +
				"                  <LFART>YLF1</LFART>\n" +
				"                  <BWART>601</BWART>\n" +
				"                  <MBLNR>${orderDeliveryDTO.materialNumber}</MBLNR>\n" +
				"                  <MJAHR>2025</MJAHR>\n" +
				"                  <WBSTA/>\n" +
				"                  <SJAHR/>\n" +
				"                  <SMBLN/>\n" +
				"          </DELIVERY_ITEM>\n" +
				"        </#list>\n" +
				"</B2B_DELIVERY_ITEM_ROOT>";
		try {
			BODY = FreeMarkerUtils.transformToString(BODY_TEMPLATE, ImmutableMap.builder()
					.put("orderDeliveryDTO", orderDeliveryDTO)
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/sap/push/sapDelivery")
				.contentType("text/xml;charset=UTF-8")
				.header("SOAPAction", "")
				.body(BODY)
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();

		// 增加接口返回成功后等待时间
		waitForProcessingToComplete(1);

		return response;
	}

	@Step(value = "【REQUEST】冲销正向交货单过账发货")
	public Response writeOffPositiveDeliveryShipment(List<OrderDeliveryDTO> OrderDeliveryDTO) {
		String BODY = "NO-BODY";
		final String BODY_TEMPLATE = "<B2B_DELIVERY_ITEM_ROOT>\n" +
				"        <BASEINFO>\n" +
				"                <S_SYSTEM>GERP</S_SYSTEM>\n" +
				"                <SERVICENAME>B2B203</SERVICENAME>\n" +
				"                <T_SYSTEM>b2b</T_SYSTEM>\n" +
				"                <RETRY/>\n" +
				"        </BASEINFO>\n" +
				"        <#list orderDeliveryDTO as orderDeliveryDTO>\n" +
				"          <DELIVERY_ITEM>\n" +
				"                  <WADAT_IST>${orderDeliveryDTO.date}</WADAT_IST>\n" +
				"                  <VKORG>1017</VKORG>\n" +
				"                  <VTWEG>22</VTWEG>\n" +
				"                  <SPART>01</SPART>\n" +
				"                  <VBELN>${orderDeliveryDTO.deliverNo}</VBELN>\n" +
				"                  <POSNR>${orderDeliveryDTO.orderItem}</POSNR>\n" +
				"                  <LGORT>1201</LGORT>\n" +
				"                  <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
				"                  <CHARG>202504</CHARG>\n" +
				"                  <MATNR/>\n" +
				"                  <LFIMG>${orderDeliveryDTO.batchNum}</LFIMG>\n" +
				"                  <VGBEL>${orderDeliveryDTO.eccid}</VGBEL>\n" +
				"                  <VGPOS>0000${orderDeliveryDTO.orderItem}</VGPOS>\n" +
				"                  <KWMENG>${orderDeliveryDTO.orderItemQuantityTotal}</KWMENG>\n" +
				"                  <BSTKD>${orderDeliveryDTO.orderNo}</BSTKD>\n" +
				"                  <B2B_ORDER_ID>${orderDeliveryDTO.orderNo}</B2B_ORDER_ID>\n" +
				"                  <B2B_ITEM_ID>${orderDeliveryDTO.orderItem}</B2B_ITEM_ID>\n" +
				"                  <CARRIER_ID/>\n" +
				"                  <YUNDAN_NO/>\n" +
				"                  <UECHA>000000</UECHA>\n" +
				"                  <LFART>ZLR1</LFART>\n" +
				"                  <BWART>602</BWART>\n" +
				"                  <MBLNR>${orderDeliveryDTO.materialNumber}</MBLNR>\n" +
				"                  <MJAHR>2025</MJAHR>\n" +
				"                  <WBSTA/>\n" +
				"                  <SJAHR>2025</SJAHR>\n" +
				"                  <SMBLN>${orderDeliveryDTO.writeOffMaterialNumber}</SMBLN>\n" +
				"          </DELIVERY_ITEM>\n" +
				"        </#list>\n" +
				"</B2B_DELIVERY_ITEM_ROOT>";
		try {
			BODY = FreeMarkerUtils.transformToString(BODY_TEMPLATE, ImmutableMap.builder()
					.put("orderDeliveryDTO", OrderDeliveryDTO)
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/sap/push/sapDelivery")
				.contentType("text/xml;charset=UTF-8")
				.header("SOAPAction", "")
				.body(BODY)
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();

		// 增加接口返回成功后等待时间
		waitForProcessingToComplete(1);

		return response;
	}

	@Step(value = "【REQUEST】创建退货交货单过账发货")
	public Response createRefundDeliveryShipment(List<OrderDeliveryDTO> OrderDeliveryDTO) {
		String BODY = "NO-BODY";
		final String BODY_TEMPLATE = "<B2B_DELIVERY_ITEM_ROOT>\n" +
				"        <BASEINFO>\n" +
				"                <S_SYSTEM>GERP</S_SYSTEM>\n" +
				"                <SERVICENAME>B2B203</SERVICENAME>\n" +
				"                <T_SYSTEM>b2b</T_SYSTEM>\n" +
				"                <RETRY/>\n" +
				"        </BASEINFO>\n" +
				"        <#list orderDeliveryDTO as orderDeliveryDTO>\n" +
				"          <DELIVERY_ITEM>\n" +
				"                  <WADAT_IST>${orderDeliveryDTO.date}</WADAT_IST>\n" +
				"                  <VKORG>1017</VKORG>\n" +
				"                  <VTWEG>22</VTWEG>\n" +
				"                  <SPART>01</SPART>\n" +
				"                  <VBELN>${orderDeliveryDTO.deliverNo}</VBELN>\n" +
				"                  <POSNR>${orderDeliveryDTO.orderItem}</POSNR>\n" +
				"                  <LGORT>1201</LGORT>\n" +
				"                  <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
				"                  <CHARG>202504</CHARG>\n" +
				"                  <MATNR/>\n" +
				"                  <LFIMG>${orderDeliveryDTO.batchNum}</LFIMG>\n" +
				"                  <VGBEL>${orderDeliveryDTO.eccid}</VGBEL>\n" +
				"                  <VGPOS>0000${orderDeliveryDTO.orderItem}</VGPOS>\n" +
				"                  <KWMENG>${orderDeliveryDTO.orderItemQuantityTotal}</KWMENG>\n" +
				"                  <BSTKD>${orderDeliveryDTO.orderNo}</BSTKD>\n" +
				"                  <B2B_ORDER_ID>${orderDeliveryDTO.refundOrderNo}</B2B_ORDER_ID>\n" +
				"                  <B2B_ITEM_ID>${orderDeliveryDTO.orderItem}</B2B_ITEM_ID>\n" +
				"                  <CARRIER_ID/>\n" +
				"                  <YUNDAN_NO/>\n" +
				"                  <UECHA>000000</UECHA>\n" +
				"                  <LFART>YLF1</LFART>\n" +
				"                  <BWART>657</BWART>\n" +
				"                  <MBLNR>${orderDeliveryDTO.materialNumber}</MBLNR>\n" +
				"                  <MJAHR>2025</MJAHR>\n" +
				"                  <WBSTA/>\n" +
				"                  <SJAHR/>\n" +
				"                  <SMBLN/>\n" +
				"          </DELIVERY_ITEM>\n" +
				"        </#list>\n" +
				"</B2B_DELIVERY_ITEM_ROOT>";
		try {
			BODY = FreeMarkerUtils.transformToString(BODY_TEMPLATE, ImmutableMap.builder()
					.put("orderDeliveryDTO", OrderDeliveryDTO)
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/sap/push/sapDelivery")
				.contentType("text/xml;charset=UTF-8")
				.header("SOAPAction", "")
				.body(BODY)
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();

		// 增加接口返回成功后等待时间
		waitForProcessingToComplete(1);

		return response;
	}

	@Step(value = "【REQUEST】冲销退货交货单过账发货")
	public Response writeOffRefundDeliveryShipment(List<OrderDeliveryDTO> OrderDeliveryDTO) {
		String BODY = "NO-BODY";
		final String BODY_TEMPLATE = "<B2B_DELIVERY_ITEM_ROOT>\n" +
				"        <BASEINFO>\n" +
				"                <S_SYSTEM>GERP</S_SYSTEM>\n" +
				"                <SERVICENAME>B2B203</SERVICENAME>\n" +
				"                <T_SYSTEM>b2b</T_SYSTEM>\n" +
				"                <RETRY/>\n" +
				"        </BASEINFO>\n" +
				"        <#list orderDeliveryDTO as orderDeliveryDTO>\n" +
				"          <DELIVERY_ITEM>\n" +
				"                  <WADAT_IST>${orderDeliveryDTO.date}</WADAT_IST>\n" +
				"                  <VKORG>1017</VKORG>\n" +
				"                  <VTWEG>22</VTWEG>\n" +
				"                  <SPART>01</SPART>\n" +
				"                  <VBELN>${orderDeliveryDTO.deliverNo}</VBELN>\n" +
				"                  <POSNR>${orderDeliveryDTO.orderItem}</POSNR>\n" +
				"                  <LGORT>1201</LGORT>\n" +
				"                  <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
				"                  <CHARG>202504</CHARG>\n" +
				"                  <MATNR/>\n" +
				"                  <LFIMG>${orderDeliveryDTO.batchNum}</LFIMG>\n" +
				"                  <VGBEL>${orderDeliveryDTO.eccid}</VGBEL>\n" +
				"                  <VGPOS>0000${orderDeliveryDTO.orderItem}</VGPOS>\n" +
				"                  <KWMENG>${orderDeliveryDTO.orderItemQuantityTotal}</KWMENG>\n" +
				"                  <BSTKD>${orderDeliveryDTO.orderNo}</BSTKD>\n" +
				"                  <B2B_ORDER_ID>${orderDeliveryDTO.refundOrderNo}</B2B_ORDER_ID>\n" +
				"                  <B2B_ITEM_ID>${orderDeliveryDTO.orderItem}</B2B_ITEM_ID>\n" +
				"                  <CARRIER_ID/>\n" +
				"                  <YUNDAN_NO/>\n" +
				"                  <UECHA>000000</UECHA>\n" +
				"                  <LFART>ZLR1</LFART>\n" +
				"                  <BWART>658</BWART>\n" +
				"                  <MBLNR>${orderDeliveryDTO.materialNumber}</MBLNR>\n" +
				"                  <MJAHR>2025</MJAHR>\n" +
				"                  <WBSTA/>\n" +
				"                  <SJAHR>2025</SJAHR>\n" +
				"                  <SMBLN>${orderDeliveryDTO.writeOffMaterialNumber}</SMBLN>\n" +
				"          </DELIVERY_ITEM>\n" +
				"        </#list>\n" +
				"</B2B_DELIVERY_ITEM_ROOT>";
		try {
			BODY = FreeMarkerUtils.transformToString(BODY_TEMPLATE, ImmutableMap.builder()
					.put("orderDeliveryDTO", OrderDeliveryDTO)
					.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/sap/push/sapDelivery")
				.contentType("text/xml;charset=UTF-8")
				.header("SOAPAction", "")
				.body(BODY)
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();

		// 增加接口返回成功后等待时间
		waitForProcessingToComplete(1);

		return response;
	}

	@Step(value = "【REQUEST】正向订单正向和冲销交货单同时过账发货")
	public Response createAndWriteOffPositiveDeliveryShipment(OrderDeliveryDTO orderDeliveryDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/sap/push/sapDelivery")
				.contentType("text/xml;charset=UTF-8")
				.header("SOAPAction", "")
				.body("<B2B_DELIVERY_ITEM_ROOT>\n" +
						"  <BASEINFO>\n" +
						"    <S_SYSTEM>GERP</S_SYSTEM>\n" +
						"    <SERVICENAME>B2B203</SERVICENAME>\n" +
						"    <T_SYSTEM>b2b</T_SYSTEM>\n" +
						"    <RETRY/>\n" +
						"  </BASEINFO>\n" +
						"  <DELIVERY_ITEM>\n" +
						"    <WADAT_IST>" + orderDeliveryDTO.getDate() + "</WADAT_IST>\n" +
						"    <VKORG>1017</VKORG>\n" +
						"    <VTWEG>22</VTWEG>\n" +
						"    <SPART>01</SPART>\n" +
						"    <VBELN>" + orderDeliveryDTO.getDeliverNo() + "</VBELN>\n" +
						"    <POSNR>0000" + orderDeliveryDTO.getOrderItem() + "</POSNR>\n" +
						"    <LGORT>1201</LGORT>\n" +
						"    <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
						"    <CHARG>202504</CHARG>\n" +
						"    <MATNR/>\n" +
						"    <LFIMG>" + orderDeliveryDTO.getBatchNum() + "</LFIMG>\n" +
						"    <VGBEL>" + orderDeliveryDTO.getEccid() + "</VGBEL>\n" +
						"    <VGPOS>0000" + orderDeliveryDTO.getOrderItem() + "</VGPOS>\n" +
						"    <KWMENG>" + orderDeliveryDTO.getOrderItemQuantityTotal() + "</KWMENG>\n" +
						"    <BSTKD>" + orderDeliveryDTO.getOrderNo() + "</BSTKD>\n" +
						"    <B2B_ORDER_ID>" + orderDeliveryDTO.getOrderNo() + "</B2B_ORDER_ID>\n" +
						"    <B2B_ITEM_ID>" + orderDeliveryDTO.getOrderItem() + "</B2B_ITEM_ID>\n" +
						"    <CARRIER_ID/>\n" +
						"    <YUNDAN_NO/>\n" +
						"    <UECHA>000000</UECHA>\n" +
						"    <LFART>YLF1</LFART>\n" +
						"    <BWART>601</BWART>\n" +
						"    <MBLNR>" + orderDeliveryDTO.getMaterialNumber() + "</MBLNR>\n" +
						"    <MJAHR>2025</MJAHR>\n" +
						"    <WBSTA/>\n" +
						"    <SJAHR/>\n" +
						"    <SMBLN/>\n" +
						"  </DELIVERY_ITEM>\n" +
						"  <DELIVERY_ITEM>\n" +
						"    <WADAT_IST>" + orderDeliveryDTO.getDate() + "</WADAT_IST>\n" +
						"    <VKORG>1017</VKORG>\n" +
						"    <VTWEG>22</VTWEG>\n" +
						"    <SPART>01</SPART>\n" +
						"    <VBELN>" + orderDeliveryDTO.getWriteOffDeliverNo() + "</VBELN>\n" +
						"    <POSNR>" + orderDeliveryDTO.getOrderItem() + "</POSNR>\n" +
						"    <LGORT>1201</LGORT>\n" +
						"    <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
						"    <CHARG>202504</CHARG>\n" +
						"    <MATNR/>\n" +
						"    <LFIMG>" + orderDeliveryDTO.getBatchNum() + "</LFIMG>\n" +
						"    <VGBEL>" + orderDeliveryDTO.getEccid() + "</VGBEL>\n" +
						"    <VGPOS>0000" + orderDeliveryDTO.getOrderItem() + "</VGPOS>\n" +
						"    <KWMENG>" + orderDeliveryDTO.getOrderItemQuantityTotal() + "</KWMENG>\n" +
						"    <BSTKD>" + orderDeliveryDTO.getOrderNo() + "</BSTKD>\n" +
						"    <B2B_ORDER_ID>" + orderDeliveryDTO.getOrderNo() + "</B2B_ORDER_ID>\n" +
						"    <B2B_ITEM_ID>" + orderDeliveryDTO.getOrderItem() + "</B2B_ITEM_ID>\n" +
						"    <CARRIER_ID/>\n" +
						"    <YUNDAN_NO/>\n" +
						"    <UECHA>000000</UECHA>\n" +
						"    <LFART>ZLR1</LFART>\n" +
						"    <BWART>602</BWART>\n" +
						"    <MBLNR>" + orderDeliveryDTO.getWriteOffMaterialNumber() + "</MBLNR>\n" +
						"    <MJAHR>2025</MJAHR>\n" +
						"    <WBSTA/>\n" +
						"    <SJAHR>2025</SJAHR>\n" +
						"    <SMBLN>" + orderDeliveryDTO.getMaterialNumber() + "</SMBLN>\n" +
						"  </DELIVERY_ITEM>\n" +
						"</B2B_DELIVERY_ITEM_ROOT>")
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();

		// 增加接口返回成功后等待时间
		waitForProcessingToComplete(1);

		return response;
	}

	@Step(value = "【REQUEST】退货订单正向和冲销交货单同时过账发货")
	public Response createAndWriteOffRefundDeliveryShipment(OrderDeliveryDTO orderDeliveryDTO) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", orderCenterDomain.getProtocal(), orderCenterDomain.getHost(), orderCenterDomain.getPort()))
				.basePath("/v1/sap/push/sapDelivery")
				.contentType("text/xml;charset=UTF-8")
				.header("SOAPAction", "")
				.body("<B2B_DELIVERY_ITEM_ROOT>\n" +
						"  <BASEINFO>\n" +
						"    <S_SYSTEM>GERP</S_SYSTEM>\n" +
						"    <SERVICENAME>B2B203</SERVICENAME>\n" +
						"    <T_SYSTEM>b2b</T_SYSTEM>\n" +
						"    <RETRY/>\n" +
						"  </BASEINFO>\n" +
						"  <DELIVERY_ITEM>\n" +
						"    <WADAT_IST>" + orderDeliveryDTO.getDate() + "</WADAT_IST>\n" +
						"    <VKORG>1017</VKORG>\n" +
						"    <VTWEG>22</VTWEG>\n" +
						"    <SPART>01</SPART>\n" +
						"    <VBELN>" + orderDeliveryDTO.getDeliverNo() + "</VBELN>\n" +
						"    <POSNR>" + orderDeliveryDTO.getOrderItem() + "</POSNR>\n" +
						"    <LGORT>1201</LGORT>\n" +
						"    <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
						"    <CHARG>202504</CHARG>\n" +
						"    <MATNR/>\n" +
						"    <LFIMG>" + orderDeliveryDTO.getBatchNum() + "</LFIMG>\n" +
						"    <VGBEL>" + orderDeliveryDTO.getEccid() + "</VGBEL>\n" +
						"    <VGPOS>0000" + orderDeliveryDTO.getOrderItem() + "</VGPOS>\n" +
						"    <KWMENG>" + orderDeliveryDTO.getOrderItemQuantityTotal() + "</KWMENG>\n" +
						"    <BSTKD>" + orderDeliveryDTO.getOrderNo() + "</BSTKD>\n" +
						"    <B2B_ORDER_ID>" + orderDeliveryDTO.getRefundOrderNo() + "</B2B_ORDER_ID>\n" +
						"    <B2B_ITEM_ID>" + orderDeliveryDTO.getOrderItem() + "</B2B_ITEM_ID>\n" +
						"    <CARRIER_ID/>\n" +
						"    <YUNDAN_NO/>\n" +
						"    <UECHA>000000</UECHA>\n" +
						"    <LFART>YLF1</LFART>\n" +
						"    <BWART>657</BWART>\n" +
						"    <MBLNR>" + orderDeliveryDTO.getMaterialNumber() + "</MBLNR>\n" +
						"    <MJAHR>2025</MJAHR>\n" +
						"    <WBSTA/>\n" +
						"    <SJAHR/>\n" +
						"    <SMBLN/>\n" +
						"  </DELIVERY_ITEM>\n" +
						"  <DELIVERY_ITEM>\n" +
						"    <WADAT_IST>" + orderDeliveryDTO.getDate() + "</WADAT_IST>\n" +
						"    <VKORG>1017</VKORG>\n" +
						"    <VTWEG>22</VTWEG>\n" +
						"    <SPART>01</SPART>\n" +
						"    <VBELN>" + orderDeliveryDTO.getWriteOffDeliverNo() + "</VBELN>\n" +
						"    <POSNR>" + orderDeliveryDTO.getOrderItem() + "</POSNR>\n" +
						"    <LGORT>1201</LGORT>\n" +
						"    <WMS_SYSTEM>at</WMS_SYSTEM>\n" +
						"    <CHARG>202504</CHARG>\n" +
						"    <MATNR/>\n" +
						"    <LFIMG>" + orderDeliveryDTO.getBatchNum() + "</LFIMG>\n" +
						"    <VGBEL>" + orderDeliveryDTO.getEccid() + "</VGBEL>\n" +
						"    <VGPOS>0000" + orderDeliveryDTO.getOrderItem() + "</VGPOS>\n" +
						"    <KWMENG>" + orderDeliveryDTO.getOrderItemQuantityTotal() + "</KWMENG>\n" +
						"    <BSTKD>" + orderDeliveryDTO.getOrderNo() + "</BSTKD>\n" +
						"    <B2B_ORDER_ID>" + orderDeliveryDTO.getRefundOrderNo() + "</B2B_ORDER_ID>\n" +
						"    <B2B_ITEM_ID>" + orderDeliveryDTO.getOrderItem() + "</B2B_ITEM_ID>\n" +
						"    <CARRIER_ID/>\n" +
						"    <YUNDAN_NO/>\n" +
						"    <UECHA>000000</UECHA>\n" +
						"    <LFART>ZLR1</LFART>\n" +
						"    <BWART>658</BWART>\n" +
						"    <MBLNR>" + orderDeliveryDTO.getWriteOffMaterialNumber() + "</MBLNR>\n" +
						"    <MJAHR>2025</MJAHR>\n" +
						"    <WBSTA/>\n" +
						"    <SJAHR>2025</SJAHR>\n" +
						"    <SMBLN>" + orderDeliveryDTO.getMaterialNumber() + "</SMBLN>\n" +
						"  </DELIVERY_ITEM>\n" +
						"</B2B_DELIVERY_ITEM_ROOT>")
				.log().all().when()
				.post().then()
				.log().all()
				.statusCode(200)
				.extract().response();

		// 增加接口返回成功后等待时间
		waitForProcessingToComplete(1);

		return response;
	}

	@Step("等待系统处理完成 {0} 秒")
	private void waitForProcessingToComplete(int seconds) {
		try {
			log.info("等待系统处理完成，等待{}秒...", seconds);
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			log.error("等待过程中被中断", e);
			Thread.currentThread().interrupt();
		}
	}

}
