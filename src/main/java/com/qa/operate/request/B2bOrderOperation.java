package com.qa.operate.request;

import com.alibaba.fastjson.JSONArray;
import com.qa.data.WsdlData;
import com.qa.tool.YamlUtil;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Tesla Liu
 * @date 2022/11/10 14:54
 * 描述 B2B系统订单操作
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class B2bOrderOperation {

    String url = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Address.B2B"));
    String frontUserName = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Account.FrontStage.username"));
    String frontPassWord = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Account.FrontStage.password"));
    String backUserName = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Account.BackStage.username"));
    String backPassWord = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Account.BackStage.password"));

    @Description(value = "登录B2B前台系统")
    public String loginB2bFrontStageSystem() {
        // 请求参数对象
        Map<String ,String> paramenters = new HashMap<String, String>();
        paramenters.put("username", frontUserName);
        paramenters.put("password", frontPassWord);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/mobile/login")
                .contentType(ContentType.URLENC)
                .urlEncodingEnabled(true)
                .queryParams(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(200), "message", Matchers.equalTo("登陆成功"))
                .extract().response();
        String SESSION = Optional.ofNullable(response.getCookie("SESSION")).orElse("NO-SESSION");
        log.info("FrontStage_SESSION = " + SESSION);
        return SESSION;
    }

    @Description(value = "B2B前台系统下订单")
    public String b2bFrontSystemOrder(String b2bOrderData) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/mobile/order/saveOrder")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bFrontStageSystem())
                .body(b2bOrderData).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(200))
                .extract().response();
        String initOrderId = Optional.ofNullable(response.jsonPath().getString("resData.orderId")).orElse("B2B-ORDER-FAILURE");
        String orderId = StringUtils.join(initOrderId, "_1017_2");
        log.info("【药品订单编号】：{}", orderId);
        return orderId;
    }

    @Description(value = "登录B2B后台系统")
    public String loginB2bBackStageSystem() {
        Map<String ,String> paramenters = new HashMap<String, String>();
        paramenters.put("username", backUserName);
        paramenters.put("password", backPassWord);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/login")
                .contentType(ContentType.URLENC)
                .queryParams(paramenters).log().all()
                .when().post()
                .then().log().all()
                .extract().response();
        String SESSION = Optional.ofNullable(response.getCookie("SESSION")).orElse("NO-SESSION");
        log.info("BackStage_SESSION = " + SESSION);
        return SESSION;
    }



    @Description(value = "B2B后台系统商务审核[订单号{0}]")
    public Response businessApproval(String orderId, JSONArray b2border, String isBottledGoods, String bottledYear)  {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("manualApprove", "1");
        paramenters.put("manualApproveReason", "AI商务审核通过");
        paramenters.put("denomBOList", b2border);
        paramenters.put("orderId", orderId);
        paramenters.put("isSpecialApprove", 0);
        paramenters.put("isBottledGoods", isBottledGoods);
        paramenters.put("bottledYear", bottledYear);
        paramenters.put("temporaryCreditApproveNo", "");
        paramenters.put("rebateMode", "");
        paramenters.put("crmUserId", "");
        paramenters.put("creditType", "2");
        paramenters.put("lockId", null);
        paramenters.put("section", "浙江大区");
        paramenters.put("thisUserRemainRebateAmount", CreditOrderOperation.queryThisUserRemainRebateAmount());

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/OrderRest/businessApproveByOrderId")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("status", Matchers.equalTo("Y"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台系统人工审核[订单号{0}]")
    public Response manualApproval(String orderId, JSONArray b2border) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("manualApprove", "1");
        paramenters.put("manualApproveReason", "AI人工审核通过");
        paramenters.put("denomBOList", b2border);
        paramenters.put("orderId", orderId);
        paramenters.put("rebateMode", "");
        paramenters.put("crmUserId", "");
        paramenters.put("orderDiscountFee", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        paramenters.put("freezeAmount", 0);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/OrderRest/manualApproveByOrderId")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("status", Matchers.equalTo("Y"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台系统信用使用情况")
    public Response creditUse(String orderId) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/creditCenter/queryOrderCreditInfo/" + orderId)
                .contentType(ContentType.JSON.toString())
                .cookie("SESSION", loginB2bBackStageSystem()).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台系统铺底货生效Job")
    public Response bottledJob(String year) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/creditCenter/BottledJob/" + year)
                .contentType(ContentType.JSON.toString())
                .cookie("SESSION", loginB2bBackStageSystem()).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resultMsg", Matchers.equalTo("success"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台订单发货OMS回调[订单号{0}]")
    public Response orderDeliveryOmsApproval(String orderId) throws Exception {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/creditCenter/orderManualApprove/" + orderId)
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(0),"resData", Matchers.equalTo(Boolean.TRUE))
                .extract().response();
        return response;
    }

    @Description(value = "正向发货BLN")
    public Response deliveryBln(String deliverNo, String orderItem, String data, String count, String amount, String blnNumber) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/Services/acceptERPBillingInfo")
                .queryParam("wsdl")
                .contentType(ContentType.XML.toString())
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(WsdlData.forwardBln(deliverNo, orderItem, "ZF2", data, count, amount, blnNumber)).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body(Matchers.containsString("<RSCODE>S</RSCODE>"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台订单发货执行[订单号{0}]")
    public Response orderDelivery(String erpId, String orderId, JSONArray orderItemInfo) throws Exception {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("deliverNo", erpId);
        paramenters.put("orderId", orderId);
        paramenters.put("orderItemInfo", orderItemInfo);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/ERP2Order/updateERP2OrderLogistics")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(0),"resData", Matchers.equalTo(Boolean.TRUE))
                .extract().response();
        return response;
    }

    @Description(value = "B2B前台发起退货")
    public Response b2bFrontStageSystemOrderReturn(String paramenters) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/mobile/orderReturnGoods/saveReturnGoods")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bFrontStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(200),"resData", Matchers.equalTo(Boolean.TRUE))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台退货商务审核")
    public Response b2bReturnBusinessApproval(String orderReturnId) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("auditComment", "AI退货商务审核");
        paramenters.put("orderReturnAuditStatus", 7);
        paramenters.put("orderReturnId",orderReturnId);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/orderReturnGoodsRest/saveAuditAndCheck")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台退货人工审核")
    public Response b2bReturnManualApproval(String orderReturnId) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("leaderComment", "AI退货人工审核");
        paramenters.put("orderReturnAuditStatus", 1);
        paramenters.put("orderReturnId",orderReturnId);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/orderReturnGoodsRest/saveAuditAndCheck")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台退货运营审核")
    public Response b2bReturnOperateApproval(String orderReturnId, JSONArray returnGoodsItemEntityList) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("checkComment", "AI退货运营审核");
        paramenters.put("orderReturnAuditStatus", 3);
        paramenters.put("orderReturnId",orderReturnId);
        paramenters.put("returnGoodsItemEntityList",returnGoodsItemEntityList);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/orderReturnGoodsRest/saveAuditAndCheck")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台订单退货OMS回调[订单号{0}]")
    public Response orderReturnOmsApproval(String returnDeliverNo, String orderId, String itemId, String orderReturnId) throws Exception {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("eccOrderID", returnDeliverNo);
        paramenters.put("erpStatus", "S");
        paramenters.put("itemIds", Arrays.asList(itemId));
        paramenters.put("orderId", orderId);
        paramenters.put("orderReturnId", orderReturnId);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/ERP2Order/returnERPFadeOrderInfo")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(0),"resData", Matchers.equalTo(Boolean.TRUE))
                .extract().response();
        return response;
    }

    @Description(value = "正向退货BLN")
    public Response returnBln(String deliverNo, String orderItem, String data, String count, String amount, String blnNumber) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/Services/acceptERPBillingInfo")
                .queryParam("wsdl")
                .contentType(ContentType.XML.toString())
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(WsdlData.forwardBln(deliverNo, orderItem, "ZRE", data, count, amount, blnNumber)).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body(Matchers.containsString("<RSCODE>S</RSCODE>"))
                .extract().response();
        return response;
    }

    @Description(value = "退货冲销BLN")
    public Response writeOffBln(String deliverNo, String orderItem, String data, String count, String amount, String writeOffBlnNumber, String blnNumber) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/Services/acceptERPBillingInfo")
                .queryParam("wsdl")
                .contentType(ContentType.XML.toString())
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(WsdlData.reverseBln(deliverNo, orderItem, "ZS2", data, count, amount, writeOffBlnNumber, blnNumber)).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body(Matchers.containsString("<RSCODE>S</RSCODE>"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B前台订单取消[订单号{0}]")
    public Response orderCancel(String itemId, String orderId, String productSku) throws Exception {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("cancelMethod", 1);
        paramenters.put("cancelReason", "库存不足");
        paramenters.put("itemId", itemId);
        paramenters.put("orderId", orderId);
        paramenters.put("productSku", productSku);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/mobile/order/cancelOrder")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bFrontStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(200),"resData", Matchers.equalTo(Boolean.TRUE))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台取消商务审核")
    public Response b2bCancelBusinessApproval(String orderId, String item, String sku) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("orderId", orderId);
        paramenters.put("sku", sku);
        paramenters.put("manualApproveitem",1);
        paramenters.put("businessApproveReason","AI订单取消商务审核");
        paramenters.put("item",item);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/OrderRest/orderBusinessReview")
                .contentType(ContentType.URLENC)
                .cookie("SESSION", loginB2bBackStageSystem())
                .queryParams(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("status", Matchers.equalTo("Y"), "message", Matchers.equalTo("审核通过"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台取消运营审核")
    public Response b2bCancelOperateApproval(String orderId, String item, String sku) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("orderId", orderId);
        paramenters.put("Sku", sku);
        paramenters.put("manualApproveitem",1);
        paramenters.put("businessApproveReason","AI订单取消运营审核");
        paramenters.put("item",item);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/OrderRest/orderBusinessOperate")
                .contentType(ContentType.URLENC)
                .cookie("SESSION", loginB2bBackStageSystem())
                .queryParams(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("status", Matchers.equalTo("Y"), "message", Matchers.equalTo("审核通过"))
                .extract().response();
        return response;
    }

    @Description(value = "B2B后台订单取消OMS回调[订单号{0}]")
    public Response orderCancelOmsApproval(String orderId, String orderItemIdList)  {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("eccMessage", "未发货取消订单成功");
        paramenters.put("erpStatus", "S");
        paramenters.put("orderId", orderId);
        paramenters.put("orderItemIdList", Arrays.asList(orderItemIdList));

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/ERP2Order/UnshippedGoods")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(0))
                .extract().response();
        return response;
    }

    @Description(value = "推送金税")
    public Response pushGoldenTax(String eccId, String date, String number, String ticket, String item, String count, String bln) {
        Response response = SerenityRest.given()
                .baseUri(url).basePath("/oms/Services/acceptErpZtaxReturn")
                .queryParam("wsdl")
                .contentType(ContentType.XML.toString())
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(WsdlData.goldenTax(eccId, date, number, ticket, item, count, bln)).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body(Matchers.containsString("<RSCODE>S</RSCODE>"))
                .extract().response();
        return response;
    }

    @Description(value = "申请回款")
    public Response applyCollectMoney(JSONArray returnMoneyEntityBOList, JSONArray advanceChargeEntityList) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("userName", frontUserName);
        paramenters.put("retrunCompany", "1017");
        paramenters.put("returnMoneyEntityBOList", returnMoneyEntityBOList);
        paramenters.put("advanceChargeEntityList", advanceChargeEntityList);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/returnMoneyRest/SaveReturnMoneyDataListDetail")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("status", Matchers.equalTo("Y"), "message", Matchers.equalTo("成功!"))
                .extract().response();
        return response;
    }

    @Description(value = "回款审核")
    public Response collectMoneyApproval(String returnId, String payDate) {
        // 构建JSON对象
        HashMap paramenters = new HashMap();
        paramenters.put("remarks", "AI订单回款审核");
        paramenters.put("auditStatus", "1");
        paramenters.put("returnId", returnId);
        paramenters.put("payDate", payDate);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/advanceChargeRest/auditAndSendReturn")
                .contentType(ContentType.JSON)
                .cookie("SESSION", loginB2bBackStageSystem())
                .body(paramenters).log().all()
                .when().post()
                .then().log().all()
                .statusCode(200)
                .body("resCode", Matchers.equalTo(200),"message", Matchers.equalTo("审核回款成功！"))
                .extract().response();
        return response;
    }

    @Description(value = "回款冲销")
    public Response collectMoneyWriteOff(String applyId, String advanceId, String payDate) {
        HashMap paramenters = new HashMap();
        paramenters.put("applyId", applyId);
        paramenters.put("advanceId", advanceId);
        paramenters.put("payDate", payDate);

        Response response = SerenityRest.given()
                .baseUri(url).basePath("/bms/returnMoneyRest/financialcancellation")
                .contentType(ContentType.URLENC)
                .cookie("SESSION", loginB2bBackStageSystem())
                .queryParams(paramenters).log().all()
                .when().get()
                .then().log().all()
                .statusCode(200)
                .body("state", Matchers.equalTo("Y"), "msg", Matchers.equalTo("冲销成功！"))
                .extract().response();
        return response;
    }

}
